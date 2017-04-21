/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 * <p>
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Map;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.FinaliseResponse;
import com.bynder.sdk.model.PollStatus;
import com.bynder.sdk.model.UploadRequest;
import com.bynder.sdk.query.FinaliseUploadQuery;
import com.bynder.sdk.query.PollStatusQuery;
import com.bynder.sdk.query.RegisterChunkQuery;
import com.bynder.sdk.query.RequestUploadQuery;
import com.bynder.sdk.query.SaveMediaQuery;
import com.bynder.sdk.query.UploadQuery;
import com.bynder.sdk.service.AmazonService;
import com.bynder.sdk.service.exception.BynderUploadException;
import com.bynder.sdk.service.impl.AmazonServiceImpl;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
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
     * Amazon service used to upload parts (chunks).
     */
    private AmazonService amazonService;

    /**
     * Creates a new instance of the class.
     *
     * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
     */
    public FileUploader(final BynderApi bynderApi) {
        this.bynderApi = bynderApi;
    }

    /**
     * Uploads a file with the information specified in the query parameter.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     *
     * @return {@link Observable} with Boolean indicating if upload was successful or not.
     */
    public Observable<Boolean> uploadFile(final UploadQuery uploadQuery) {
        return Observable.create(observableEmitter -> {
            Observable<Response<String>> s3EndpointObs = getClosestS3Endpoint();
            s3EndpointObs.subscribe(awsBucketResponse -> {
                this.amazonService = new AmazonServiceImpl(awsBucketResponse.body());
                Observable<Response<UploadRequest>> uploadInformationObs = getUploadInformation(new RequestUploadQuery(uploadQuery.getFilepath()));
                uploadInformationObs.subscribe(uploadRequestResponse -> {
                    UploadRequest uploadRequest = uploadRequestResponse.body();
                    File file = new File(uploadQuery.getFilepath());
                    if (!file.exists()) {
                        observableEmitter.onError(new BynderUploadException(String.format("File: %s not found. Upload not completed.", file.getName())));
                        observableEmitter.onComplete();
                        return;
                    }
                    startUploadProcess(uploadQuery, observableEmitter, uploadRequest, file);
                }, throwable -> observableEmitter.onError(throwable));
            }, throwable -> observableEmitter.onError(throwable));
        });
    }

    /**
     * Starts the process to upload a file to Bynder, after the {@link AmazonService} was
     * successfully instantiated with the closest s3 endpoint and the upload was successfully
     * initialise in Bynder.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @param observableEmitter Observable returned by {@link FileUploader#uploadFile(UploadQuery)}.
     * @param uploadRequest Upload authorisation information.
     * @param file File to be uploaded.
     */
    private void startUploadProcess(final UploadQuery uploadQuery, final ObservableEmitter<Boolean> observableEmitter, final UploadRequest uploadRequest, final File file) {
        Observable<Integer> uploadPartsObs = uploadParts(file, uploadRequest);
        uploadPartsObs.subscribe(chunksResponse -> {
            Observable<Response<FinaliseResponse>> finaliseUploadedFileObs =
                    finaliseUpload(new FinaliseUploadQuery(uploadRequest.getS3File().getUploadId(), uploadRequest.getS3File().getTargetId(), uploadRequest.getS3Filename(), chunksResponse));
            finaliseUploadedFileObs.subscribe(finaliseResponse -> processFinaliseResponse(uploadQuery, observableEmitter, file, finaliseResponse), throwable -> observableEmitter.onError(throwable));
        }, throwable -> observableEmitter.onError(throwable));
    }

    /**
     * Uploads the parts (chunks) to Amazon and registers them in Bynder.
     *
     * @param file File to be uploaded.
     * @param uploadRequest Upload authorisation information.
     */
    private Observable<Integer> uploadParts(final File file, final UploadRequest uploadRequest) {
        return Observable.create(observableEmitter -> {
            FileInputStream fileInputStream = new FileInputStream(file);
            UploadProcessData uploadProcessData = new UploadProcessData(file, fileInputStream, uploadRequest, MAX_CHUNK_SIZE);
            uploadProcessData.incrementChunk();
            processChunk(uploadProcessData).repeatUntil(() -> {
                boolean isProcessed = uploadProcessData.isCompleted();
                if (isProcessed) {
                    observableEmitter.onNext(uploadProcessData.getNumberOfChunks());
                    observableEmitter.onComplete();
                } else {
                    uploadProcessData.incrementChunk();
                }
                return isProcessed;
            }).subscribe(booleanResponse -> {
            }, throwable -> observableEmitter.onError(throwable));
        });
    }

    /**
     * Calls the {@link AmazonService} to upload the chunk to Amazon and after registers the
     * uploaded chunk in Bynder.
     *
     * @param uploadProcessData Upload process data of the file being uploaded.
     *
     * @return {@link Observable} with Boolean indicating if upload was successful or not.
     */
    private Observable<Boolean> processChunk(final UploadProcessData uploadProcessData) {
        return Observable.create(observableEmitter -> {
            try {
                Observable<Response<Void>> uploadPartToAmazonObs = amazonService.uploadPartToAmazon(uploadProcessData.getFile().getName(), uploadProcessData.getUploadRequest(),
                        uploadProcessData.getChunkNumber(), uploadProcessData.getBuffer(), uploadProcessData.getNumberOfChunks());
                uploadPartToAmazonObs.subscribe(voidResponse -> registerUploadedChunk(uploadProcessData, observableEmitter), throwable -> observableEmitter.onError(throwable));
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
     *
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void registerUploadedChunk(final UploadProcessData uploadProcessData, final ObservableEmitter<Boolean> observableEmitter) throws IllegalArgumentException, IllegalAccessException {
        String filename = String.format("%s/p%s", uploadProcessData.getUploadRequest().getS3Filename(), Integer.toString(uploadProcessData.getChunkNumber()));
        Observable<Response<Void>> registerChunkObs = registerChunk(new RegisterChunkQuery(uploadProcessData.getUploadRequest().getS3File().getUploadId(), uploadProcessData.getChunkNumber(),
                uploadProcessData.getUploadRequest().getS3File().getTargetId(), filename));
        registerChunkObs.subscribe(voidResponse -> {
            observableEmitter.onNext(true);
            observableEmitter.onComplete();
        }, throwable -> observableEmitter.onError(throwable));
    }

    /**
     * Calls the {@link FileUploader#hasFinishedSuccessfully(String)} using the information from the
     * {@link FinaliseResponse} to check if the upload was completed successfully.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @param observableEmitter Observable returned by {@link FileUploader#uploadFile(UploadQuery)}.
     * @param file File uploaded.
     * @param finaliseResponse Finalise response returned by
     *        {@link FileUploader#finaliseUpload(FinaliseUploadQuery)}.
     *
     * @throws InterruptedException
     */
    private void processFinaliseResponse(final UploadQuery uploadQuery, final ObservableEmitter<Boolean> observableEmitter, final File file, final Response<FinaliseResponse> finaliseResponse)
            throws InterruptedException {
        String importId = finaliseResponse.body().getImportId();
        hasFinishedSuccessfully(importId).subscribe(hasFinishedSuccessfully -> {
            if (hasFinishedSuccessfully) {
                saveUploadedMedia(uploadQuery, observableEmitter, file, importId, uploadQuery.isAudit());
            } else {
                observableEmitter.onError(new BynderUploadException("Converter did not finished. Upload not completed."));
                observableEmitter.onComplete();
            }
        }, throwable -> observableEmitter.onError(throwable));
    }

    /**
     * Method to check if file has finished converting within expected timeout.
     *
     * @param importId Import id of the upload.
     *
     * @return True if file has finished converting successfully. False otherwise.
     *
     * @throws InterruptedException
     */
    private Observable<Boolean> hasFinishedSuccessfully(final String importId) throws InterruptedException {
        return Observable.create(observableEmitter -> {
            FileConverterStatus fileConverterStatus = new FileConverterStatus(MAX_POLLING_ITERATIONS);
            getPollStatus(new PollStatusQuery(Arrays.asList(importId))).repeatUntil(() -> {
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
        });
    }

    /**
     * Calls {@link FileUploader#saveMedia(SaveMediaQuery)} to save the completely uploaded file in
     * Bynder.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @param observableEmitter Observable returned by {@link FileUploader#uploadFile(UploadQuery)}.
     * @param file File uploaded.
     * @param importId Import id of the upload.
     *
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void saveUploadedMedia(final UploadQuery uploadQuery, final ObservableEmitter<Boolean> observableEmitter, final File file, final String importId, final boolean audit)
            throws IllegalArgumentException, IllegalAccessException {
        Observable<Response<Void>> saveMediaObs;
        if (uploadQuery.getMediaId() == null) {
            saveMediaObs = saveMedia(new SaveMediaQuery(importId).setBrandId(uploadQuery.getBrandId()).setName(file.getName()).setAudit(audit));
        } else {
            saveMediaObs = saveMedia(new SaveMediaQuery(importId).setMediaId(uploadQuery.getMediaId()).setAudit(audit));
        }
        saveMediaObs.subscribe(voidResponse -> observableEmitter.onNext(true), throwable -> observableEmitter.onError(throwable), () -> observableEmitter.onComplete());
    }

    /**
     * Check {@link BynderApi#getClosestS3Endpoint()} for more information.
     */
    private Observable<Response<String>> getClosestS3Endpoint() {
        return bynderApi.getClosestS3Endpoint();
    }

    /**
     * Check {@link BynderApi#getUploadInformation(Map)} for more information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private Observable<Response<UploadRequest>> getUploadInformation(final RequestUploadQuery requestUploadQuery) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(requestUploadQuery);
        return bynderApi.getUploadInformation(params);
    }

    /**
     * Check {@link BynderApi#registerChunk(Map)} for more information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private Observable<Response<Void>> registerChunk(final RegisterChunkQuery registerChunkQuery) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(registerChunkQuery);
        return bynderApi.registerChunk(params);
    }

    /**
     * Check {@link BynderApi#finaliseUpload(Map)} for more information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private Observable<Response<FinaliseResponse>> finaliseUpload(final FinaliseUploadQuery finaliseUploadQuery) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(finaliseUploadQuery);
        return bynderApi.finaliseUpload(params);
    }

    /**
     * Check {@link BynderApi#getPollStatus(Map)} for more information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private Observable<Response<PollStatus>> getPollStatus(final PollStatusQuery pollStatusQuery) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(pollStatusQuery);
        return bynderApi.getPollStatus(params);
    }

    /**
     * Check {@link BynderApi#saveMedia(Map)} for more information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private Observable<Response<Void>> saveMedia(final SaveMediaQuery saveMediaQuery) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(saveMediaQuery);
        return bynderApi.saveMedia(params);
    }
}
