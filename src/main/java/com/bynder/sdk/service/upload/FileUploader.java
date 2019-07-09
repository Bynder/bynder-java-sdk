/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 * <p>
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.exception.BynderUploadException;
import com.bynder.sdk.model.upload.FileConverterStatus;
import com.bynder.sdk.model.upload.FinaliseResponse;
import com.bynder.sdk.model.upload.PollStatus;
import com.bynder.sdk.model.upload.SaveMediaResponse;
import com.bynder.sdk.model.upload.UploadProcessData;
import com.bynder.sdk.model.upload.UploadProgress;
import com.bynder.sdk.model.upload.UploadRequest;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.upload.FinaliseUploadQuery;
import com.bynder.sdk.query.upload.PollStatusQuery;
import com.bynder.sdk.query.upload.RegisterChunkQuery;
import com.bynder.sdk.query.upload.RequestUploadQuery;
import com.bynder.sdk.query.upload.SaveMediaQuery;
import com.bynder.sdk.query.upload.UploadQuery;
import com.bynder.sdk.service.amazons3.AmazonS3Service;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import retrofit2.Response;

/**
 * Class used to upload files to Bynder.
 */
public class FileUploader {

    /**
     * Max chunk size.
     */
    private static final int MAX_CHUNK_SIZE = 1024 * 1024 * 5;
    /**
     * Max polling iterations to wait for the file to be converted.
     */
    private static final int MAX_POLLING_ITERATIONS = 60;
    /**
     * Idle time between polling iterations.
     */
    private static final int POLLING_IDLE_TIME = 2000;

    /**
     * Instance of {@link BynderApi} which handles the HTTP communication.
     */
    private final BynderApi bynderApi;
    /**
     * Instance of {@link QueryDecoder} to decode query objects into API parameters.
     */
    private final QueryDecoder queryDecoder;
    /**
     * Amazon S3 service used to upload parts (chunks).
     */
    private AmazonS3Service amazonS3Service;

    /**
     * Creates a new instance of the class.
     *
     * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
     * @param queryDecoder Query decoder.
     */
    public FileUploader(final BynderApi bynderApi, final QueryDecoder queryDecoder) {
        this.bynderApi = bynderApi;
        this.queryDecoder = queryDecoder;
    }

    /**
     * Uploads a file with the information specified in the query parameter.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @return {@link Observable} with the {@link SaveMediaResponse} information.
     */
    public Observable<SaveMediaResponse> uploadFile(final UploadQuery uploadQuery) {
        return Observable.create(emitter -> {
            try {
                Observable<UploadProgress> uploadProgressObservable = uploadFileWithProgress(
                    uploadQuery);
                uploadProgressObservable.subscribe(uploadProgress -> {
                    if (uploadProgress.isFinished()) {
                        emitter.onNext(uploadProgress.getSaveMediaResponse());
                    }
                }, throwable -> emitter.onError(throwable), () -> emitter.onComplete());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    /**
     * Uploads a file with the information specified in the query parameter
     * while providing information on the progress of the upload via the Observable returned.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @return {@link Observable} with the {@link UploadProgress} information.
     */
    public Observable<UploadProgress> uploadFileWithProgress(final UploadQuery uploadQuery) {
        return Observable.create(observableEmitter -> {
            try {
                // S3 endpoint
                Observable<Response<String>> s3EndpointObs = getClosestS3Endpoint();
                s3EndpointObs.subscribe(awsBucketResponse -> {
                    this.amazonS3Service = AmazonS3Service.Builder.create(awsBucketResponse.body());
                    // Get Upload Information
                    final File file = new File(uploadQuery.getFilepath());
                    Observable<Response<UploadRequest>> uploadInformationObs = getUploadInformation(
                        new RequestUploadQuery(file.getName()));
                    uploadInformationObs.subscribe(uploadRequestResponse -> {
                        UploadRequest uploadRequest = uploadRequestResponse.body();
                        if (!file.exists()) {
                            observableEmitter.onError(new BynderUploadException(String
                                .format("File: %s not found. Upload not completed.",
                                    file.getName())));
                            return;
                        }

                        // Upload Chunks
                        uploadParts(file, uploadRequest).subscribe(uploadProgress -> {
                            // Emit progress
                            observableEmitter.onNext(uploadProgress);

                            if (uploadProgress.areChunksFinished()) {
                                // Finalising
                                Observable<Response<FinaliseResponse>> finaliseUploadObs =
                                    finaliseUpload(
                                    new FinaliseUploadQuery(uploadRequest.getS3File().getUploadId(),
                                        uploadRequest.getS3File().getTargetId(),
                                        uploadRequest.getS3Filename(),
                                        uploadProgress.getUploadedChunks()));

                                finaliseUploadObs.subscribe(finaliseResponse -> {
                                    String importId = finaliseResponse.body().getImportId();
                                    checkUploadFinished(importId)
                                        .subscribe(hasFinishedSuccessfully -> {
                                            if (hasFinishedSuccessfully) {
                                                // Save Media
                                                saveUploadedMedia(uploadQuery, file, importId)
                                                    .subscribe(saveMediaResponse -> {
                                                        // Successful Upload
                                                        uploadProgress.setSaveMediaResponse(
                                                            saveMediaResponse);
                                                        uploadProgress.setFinished(true);
                                                        observableEmitter.onNext(uploadProgress);
                                                        observableEmitter.onComplete();
                                                    }, throwable -> {
                                                        // Failed Upload
                                                        observableEmitter.onError(throwable);
                                                    });
                                            } else {
                                                observableEmitter.onError(new BynderUploadException(
                                                    "Converter did not finished. Upload not "
                                                        + "completed."));
                                            }
                                        }, throwable -> observableEmitter.onError(throwable));
                                }, throwable -> observableEmitter.onError(throwable));
                            }
                        }, throwable -> observableEmitter.onError(throwable));
                    }, throwable -> observableEmitter.onError(throwable));
                }, throwable -> observableEmitter.onError(throwable));
            } catch (Exception e) {
                observableEmitter.onError(e);
            }
        });
    }

    /**
     * Uploads the parts (chunks) to Amazon and registers them in Bynder.
     *
     * @param file File to be uploaded.
     * @return {@link Observable} with the {@link UploadProgress} information.
     */
    private Observable<UploadProgress> uploadParts(final File file,
        final UploadRequest uploadRequest) {
        return Observable.create(observableEmitter -> {
            try {

                FileInputStream fileInputStream = new FileInputStream(file);
                UploadProgress uploadProgress = new UploadProgress(file.length());
                UploadProcessData uploadProcessData = new UploadProcessData(file, fileInputStream,
                    uploadRequest, MAX_CHUNK_SIZE);
                uploadProcessData.incrementChunk();
                processChunk(uploadProcessData).repeatUntil(() -> {
                    boolean isProcessed = uploadProcessData.isCompleted();
                    if (isProcessed) {
                        observableEmitter.onComplete();
                    } else {
                        uploadProcessData.incrementChunk();
                    }
                    return isProcessed;
                }).subscribe(chunkSize -> {
                    uploadProgress.addProgress(chunkSize);
                    observableEmitter.onNext(uploadProgress);
                }, throwable -> observableEmitter.onError(throwable));
            } catch (Exception e) {
                observableEmitter.onError(e);
            }
        });
    }

    /**
     * Calls the {@link AmazonS3Service} to upload the chunk to Amazon and after registers the
     * uploaded chunk in Bynder.
     *
     * @param uploadProcessData Upload process data of the file being uploaded.
     * @return {@link Observable} with Integer indicating the number of bytes that were uploaded
     * in the current chunk.
     */
    private Observable<Integer> processChunk(final UploadProcessData uploadProcessData) {
        return Observable.create(observableEmitter -> {
            try {
                byte[] chunk = uploadProcessData.getBuffer();
                Observable<Response<Void>> uploadPartToAmazonObs = amazonS3Service
                    .uploadPartToAmazon(uploadProcessData.getFile().getName(),
                        uploadProcessData.getUploadRequest(), uploadProcessData.getChunkNumber(),
                        chunk, uploadProcessData.getNumberOfChunks());

                uploadPartToAmazonObs.subscribe(voidResponse -> {
                    registerUploadedChunk(uploadProcessData, observableEmitter, chunk.length);
                    observableEmitter.onNext(chunk.length);
                }, throwable -> observableEmitter.onError(throwable));
            } catch (Exception e) {
                observableEmitter.onError(e);
            }
        });
    }

    /**
     * Calls {@link FileUploader#registerChunk(RegisterChunkQuery)} to register the uploaded chunk
     * in Bynder.
     *
     * @param uploadProcessData Upload process data of the file being uploaded.
     * @param observableEmitter Observable returned by {@link FileUploader#uploadFile(UploadQuery)}.
     */
    private void registerUploadedChunk(final UploadProcessData uploadProcessData,
        final ObservableEmitter<Integer> observableEmitter, final Integer chunkSize)
        throws IllegalAccessException {
        String filename = String
            .format("%s/p%s", uploadProcessData.getUploadRequest().getS3Filename(),
                Integer.toString(uploadProcessData.getChunkNumber()));
        Observable<Response<Void>> registerChunkObs = registerChunk(
            new RegisterChunkQuery(uploadProcessData.getUploadRequest().getS3File().getUploadId(),
                uploadProcessData.getChunkNumber(),
                uploadProcessData.getUploadRequest().getS3File().getTargetId(), filename));
        registerChunkObs.subscribe(voidResponse -> {
            observableEmitter.onNext(chunkSize);
            observableEmitter.onComplete();
        }, throwable -> observableEmitter.onError(throwable));
    }

    /**
     * Method to check if file has finished converting within expected timeout.
     *
     * @param importId Import id of the upload.
     * @return {@link Observable} with a Boolean indicating whether the file finished converting
     * successfully.
     */
    private Observable<Boolean> checkUploadFinished(final String importId) {
        return Observable.create(observableEmitter -> {
            try {
                FileConverterStatus fileConverterStatus = new FileConverterStatus(
                    MAX_POLLING_ITERATIONS);
                getPollStatus(new PollStatusQuery(importId.split(","))).repeatUntil(() -> {
                    if (fileConverterStatus.isDone()) {
                        observableEmitter.onNext(fileConverterStatus.isSuccessful());
                        observableEmitter.onComplete();
                        return true;
                    }
                    if (!fileConverterStatus.nextAttempt()) {
                        observableEmitter.onNext(false);
                        observableEmitter.onComplete();
                        return true;
                    } else {
                        Thread.sleep(POLLING_IDLE_TIME);
                        return false;
                    }
                }).subscribe(pollStatusResponse -> {
                    PollStatus pollStatus = pollStatusResponse.body();
                    if (pollStatus != null) {
                        if (pollStatus.getItemsDone().contains(importId)) {
                            fileConverterStatus.setDone(true);
                        }
                        if (pollStatus.getItemsFailed().contains(importId)) {
                            fileConverterStatus.setDone(false);
                        }
                    }
                }, throwable -> observableEmitter.onError(throwable));
            } catch (Exception e) {
                observableEmitter.onError(e);
            }
        });
    }

    /**
     * Calls {@link FileUploader#saveMedia(SaveMediaQuery)} to save the completely uploaded file in
     * Bynder.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @param file File uploaded.
     * @param importId Import id of the upload.
     * @return {@link Observable} with the {@link SaveMediaResponse} information.
     */
    private Observable<SaveMediaResponse> saveUploadedMedia(final UploadQuery uploadQuery,
        final File file, final String importId) throws IllegalAccessException {
        return Observable.create(emitter -> {
            Observable<Response<SaveMediaResponse>> saveMediaObs;
            if (uploadQuery.getMediaId() == null) {
                saveMediaObs = saveMedia(
                    new SaveMediaQuery(importId).setBrandId(uploadQuery.getBrandId())
                        .setName(file.getName()).setAudit(uploadQuery.isAudit()));
            } else {
                saveMediaObs = saveMedia(
                    new SaveMediaQuery(importId).setMediaId(uploadQuery.getMediaId())
                        .setAudit(uploadQuery.isAudit()));
            }
            saveMediaObs.subscribe(saveMediaResponse -> emitter.onNext(saveMediaResponse.body()),
                throwable -> emitter.onError(throwable), () -> emitter.onComplete());
        });
    }

    /**
     * Check {@link BynderApi#getClosestS3Endpoint()} for more information.
     */
    private Observable<Response<String>> getClosestS3Endpoint() {
        return bynderApi.getClosestS3Endpoint();
    }

    /**
     * Check {@link BynderApi#getUploadInformation(Map)} for more information.
     */
    private Observable<Response<UploadRequest>> getUploadInformation(
        final RequestUploadQuery requestUploadQuery) {
        Map<String, String> params = queryDecoder.decode(requestUploadQuery);
        return bynderApi.getUploadInformation(params);
    }

    /**
     * Check {@link BynderApi#registerChunk(Map)} for more information.
     */
    private Observable<Response<Void>> registerChunk(final RegisterChunkQuery registerChunkQuery) {
        Map<String, String> params = queryDecoder.decode(registerChunkQuery);
        return bynderApi.registerChunk(params);
    }

    /**
     * Check {@link BynderApi#finaliseUpload(Map)} for more information.
     */
    private Observable<Response<FinaliseResponse>> finaliseUpload(
        final FinaliseUploadQuery finaliseUploadQuery) {
        Map<String, String> params = queryDecoder.decode(finaliseUploadQuery);
        return bynderApi.finaliseUpload(params);
    }

    /**
     * Check {@link BynderApi#getPollStatus(Map)} for more information.
     */
    private Observable<Response<PollStatus>> getPollStatus(final PollStatusQuery pollStatusQuery) {
        Map<String, String> params = queryDecoder.decode(pollStatusQuery);
        return bynderApi.getPollStatus(params);
    }

    /**
     * Check {@link BynderApi#saveMedia(Map)} for more information.
     */
    private Observable<Response<SaveMediaResponse>> saveMedia(final SaveMediaQuery saveMediaQuery) {
        Map<String, String> params = queryDecoder.decode(saveMediaQuery);
        return bynderApi.saveMedia(params);
    }
}
