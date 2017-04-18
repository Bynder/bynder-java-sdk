/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 * <p>
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(FileUploader.class);

    /**
     * Max chunk size
     */
    private static final int MAX_CHUNK_SIZE = 1024 * 1024 * 5;
    /**
     * Max polling iterations to wait for the media asset to be converted.
     */
    private static final int MAX_POLLING_ITERATIONS = 60;
    /**
     * Idle time between polling iterations.
     */
    private static final int POLLING_IDDLE_TIME = 2000;

    /**
     * Instance of {@link BynderApi} which handles the HTTP communication.
     */
    private final BynderApi bynderApi;
    /**
     * Amazon service used to upload parts.
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
     * @param uploadQuery Upload query information to upload a file.
     * @throws BynderUploadException Thrown when upload does not finish within the expected time.
     * @throws IOException
     * @throws InterruptedException
     * @throws RuntimeException
     */
    public Observable<Boolean> uploadFile(final UploadQuery uploadQuery) {
        return Observable.create(observableEmitter -> {
            Observable<Response<String>> s3Obs = initializeAmazonService();
            s3Obs.subscribe(stringResponse -> {
                this.amazonService = new AmazonServiceImpl(stringResponse.body());

                Observable<Response<UploadRequest>> uploadRequestObs = getUploadInformation(new RequestUploadQuery(uploadQuery.getFilepath()));

                uploadRequestObs.subscribe(uploadRequestResponse -> {
                    UploadRequest uploadRequest = uploadRequestResponse.body();
                    File file = new File(uploadQuery.getFilepath());
                    if (!file.exists()) {
                        observableEmitter.onError(new FileNotFoundException(file.getName() + " not found"));
                        observableEmitter.onComplete();
                        return;
                    }
                    startUploadProcess(uploadQuery, observableEmitter, uploadRequest, file);
                }, throwable -> observableEmitter.onError(throwable));
            }, throwable -> observableEmitter.onError(throwable));
        });
    }

    private void startUploadProcess(final UploadQuery uploadQuery, final ObservableEmitter<Boolean> observableEmitter, final UploadRequest uploadRequest, final File file) {
        Observable<Integer> chunksObs = uploadParts(file, uploadRequest);
        chunksObs.subscribe(chunks -> {
            Observable<Response<FinaliseResponse>> finaliseResponse =
                    finaliseUploaded(new FinaliseUploadQuery(uploadRequest.getS3File().getUploadId(), uploadRequest.getS3File().getTargetId(), uploadRequest.getS3Filename(), chunks));
            finaliseResponse.subscribe(finaliseResponseResponse -> {
                processFinaliseResponse(uploadQuery, observableEmitter, file, finaliseResponseResponse);
            }, throwable -> observableEmitter.onError(throwable));
        }, throwable -> LOG.error(throwable.getMessage()));
    }

    private void processFinaliseResponse(final UploadQuery uploadQuery, final ObservableEmitter<Boolean> observableEmitter, final File file, final Response<FinaliseResponse> finaliseResponseResponse)
            throws InterruptedException {
        String importId = finaliseResponseResponse.body().getImportId();
        hasFinishedSuccessfully(importId).subscribe(hasFinishedSuccessfully -> {
            if (hasFinishedSuccessfully) {
                saveMedia(uploadQuery, observableEmitter, file, importId);
            } else {
                observableEmitter.onError(new BynderUploadException("Converter did not finishe. Upload not completed"));
                observableEmitter.onComplete();
            }
        }, throwable -> observableEmitter.onError(throwable));
    }

    private void saveMedia(final UploadQuery uploadQuery, final ObservableEmitter<Boolean> observableEmitter, final File file, final String importId)
            throws IllegalArgumentException, IllegalAccessException {
        Observable<Response<Void>> saveMediaObs;
        if (uploadQuery.getMediaId() == null) {
            saveMediaObs = saveMedia(new SaveMediaQuery(importId).setBrandId(uploadQuery.getBrandId()).setName(file.getName()));
        } else {
            saveMediaObs = saveMedia(new SaveMediaQuery(importId).setMediaId(uploadQuery.getMediaId()));
        }

        saveMediaObs.subscribe(voidResponse -> observableEmitter.onNext(true), throwable -> observableEmitter.onError(throwable), () -> observableEmitter.onComplete());
    }

    /**
     * Gets the closest Amazon S3 upload endpoint and initializes {@link AmazonService}.
     */
    private Observable<Response<String>> initializeAmazonService() {
        return getClosestS3Endpoint();
    }

    /**
     * Check {@link BynderApi#getClosestS3Endpoint()} for more information.
     */
    private Observable<Response<String>> getClosestS3Endpoint() {
        return bynderApi.getClosestS3Endpoint();
    }

    /**
     * Check {@link BynderApi#getUploadInformation(String)} for more information.
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
     * Check {@link BynderApi#finaliseUploaded(Map)} for more information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private Observable<Response<FinaliseResponse>> finaliseUploaded(final FinaliseUploadQuery finaliseUploadQuery) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(finaliseUploadQuery);
        return bynderApi.finaliseUploaded(params);
    }

    /**
     * Check {@link BynderApi#getPollStatus(Map)} for more information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private Observable<Response<PollStatus>> pollStatus(final PollStatusQuery pollStatusQuery) throws IllegalArgumentException, IllegalAccessException {
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

    /**
     * Uploads the parts to Amazon and registers them in Bynder.
     *
     * @param file Path of the file to be uploaded.
     * @param uploadRequest Upload authorization information.
     */
    private Observable<Integer> uploadParts(final File file, final UploadRequest uploadRequest) {
        return Observable.create(observableEmitter -> {
            FileInputStream fis = new FileInputStream(file);
            UploadProcessData data = new UploadProcessData(file, fis, MAX_CHUNK_SIZE, uploadRequest);

            data.incrementChunk();
            processChunk(data).repeatUntil(() -> {
                boolean isDataCompleted = data.isCompleted();
                if (isDataCompleted) {
                    observableEmitter.onNext(data.getNumberOfChunks());
                    observableEmitter.onComplete();
                } else {
                    data.incrementChunk();
                }
                return isDataCompleted;
            }).subscribe(aBoolean -> {
            }, throwable -> observableEmitter.onError(throwable));
        });
    }

    private Observable<Boolean> processChunk(final UploadProcessData data) {
        return Observable.create(observableEmitter -> {
            try {
                Observable<Response<Void>> uploadToAmazon =
                        amazonService.uploadPartToAmazon(data.getFile().getName(), data.getUploadRequest(), data.getChunkNumber(), data.getBuffer(), data.getNumberOfChunks());
                uploadToAmazon.subscribe(voidResponse -> registerChunk(data, observableEmitter), throwable -> observableEmitter.onError(throwable));
            } catch (Exception e) {
                observableEmitter.onError(e);
            }
        });
    }

    private void registerChunk(final UploadProcessData data, final ObservableEmitter<Boolean> observableEmitter) throws IllegalArgumentException, IllegalAccessException {
        String filename = String.format("%s/p%s", data.getUploadRequest().getS3Filename(), Integer.toString(data.getChunkNumber()));
        Observable<Response<Void>> chunkObs =
                registerChunk(new RegisterChunkQuery(data.getUploadRequest().getS3File().getUploadId(), data.getChunkNumber(), data.getUploadRequest().getS3File().getTargetId(), filename));
        chunkObs.subscribe(voidResponse -> {
            observableEmitter.onNext(true);
            observableEmitter.onComplete();
        }, throwable -> observableEmitter.onError(throwable));
    }

    /**
     * Method to check if file has finished converting within expected timeout.
     *
     * @param importId Import id of the upload.
     * @return True if file has finished converting successfully. False otherwise.
     * @throws InterruptedException
     */
    private Observable<Boolean> hasFinishedSuccessfully(final String importId) throws InterruptedException {
        return Observable.create(observableEmitter -> {
            PollingStatus pollingStatus = new PollingStatus(MAX_POLLING_ITERATIONS);
            pollStatus(new PollStatusQuery(Arrays.asList(importId))).repeatUntil(() -> {
                if (pollingStatus.isDone()) {
                    observableEmitter.onNext(pollingStatus.isSuccessful());
                    observableEmitter.onComplete();
                    return true;
                }
                if (!pollingStatus.nextAttempt()) {
                    observableEmitter.onNext(false);
                    observableEmitter.onComplete();
                    return true;
                } else {
                    Thread.sleep(POLLING_IDDLE_TIME);
                    return false;
                }
            }).subscribe(pollStatusResponse -> {
                PollStatus pollStatus = pollStatusResponse.body();
                if (pollStatus != null) {
                    if (pollStatus.getItemsDone().contains(importId)) {
                        pollingStatus.setDone(true);
                    }
                    if (pollStatus.getItemsFailed().contains(importId)) {
                        pollingStatus.setDone(false);
                    }
                }
            }, throwable -> observableEmitter.onError(throwable));
        });
    }
}
