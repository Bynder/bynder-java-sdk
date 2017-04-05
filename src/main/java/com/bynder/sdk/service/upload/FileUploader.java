/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 * <p>
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.FinaliseResponse;
import com.bynder.sdk.model.PollStatus;
import com.bynder.sdk.model.UploadRequest;
import com.bynder.sdk.query.UploadQuery;
import com.bynder.sdk.service.AmazonService;
import com.bynder.sdk.service.exception.BynderUploadException;
import com.bynder.sdk.service.impl.AmazonServiceImpl;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
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
    public Observable<Boolean> uploadFile(final UploadQuery uploadQuery) throws InterruptedException, BynderUploadException {
        return Observable.create(observableEmitter -> {
            Observable<Response<String>> s3Obs = initializeAmazonService();
            s3Obs
                    .doOnError(throwable -> observableEmitter.onError(throwable))
                    .doOnNext(stringResponse -> {
                        this.amazonService = new AmazonServiceImpl(stringResponse.body());

                        UploadRequest uploadRequest = getUploadInformation(uploadQuery.getFilepath()).blockingSingle().body();
                        File file = new File(uploadQuery.getFilepath());
                        if (!file.exists()) {
                            observableEmitter.onError(new FileNotFoundException(file.getName() + " not found"));
                            observableEmitter.onComplete();
                            return;
                        }

                        Observable<Integer> chunksObs = uploadParts(file, uploadRequest);
                        chunksObs
                                .doOnError(throwable -> LOG.error(throwable.getMessage()))
                                .doOnNext(chunks -> {
                                    Observable<Response<FinaliseResponse>> finaliseResponse = finaliseUploaded(uploadRequest, chunks);
                                    finaliseResponse
                                            .doOnError(throwable -> observableEmitter.onError(throwable))
                                            .doOnNext(finaliseResponseResponse -> {
                                                String importId = finaliseResponseResponse.body().getImportId();
                                                //Response<Void> response = null;
                                                hasFinishedSuccessfully(importId)
                                                        .doOnError(throwable -> observableEmitter.onError(throwable))
                                                        .doOnNext(hasFinishedSuccessfully -> {
                                                            if (hasFinishedSuccessfully) {
                                                                Observable<Response<Void>> saveMediaObs;
                                                                if (uploadQuery.getMediaId() == null) {
                                                                    /*response = */
                                                                    saveMediaObs = saveMedia(importId, uploadQuery.getBrandId(), file.getName());
                                                                } else {
                                                                    saveMediaObs = saveMediaVersion(uploadQuery.getMediaId(), importId);
                                                                }
                                                                saveMediaObs
                                                                        .doOnError(throwable -> observableEmitter.onError(throwable))
                                                                        .doOnNext(voidResponse -> observableEmitter.onNext(true))
                                                                        .doOnComplete(() ->  observableEmitter.onComplete())
                                                                        .subscribe();
                                                            } else {
                                                                //throw new BynderUploadException("Converter did not finished. Upload not completed");
                                                                observableEmitter.onError(new BynderUploadException("Converter did not finished. Upload not completed"));
                                                                observableEmitter.onComplete();
                                                            }
                                                        })
                                                        .subscribe();

                                            })
                                            .subscribe();
                                })
                                .subscribe();
                    })
                    .subscribe();
        });
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
     */
    private Observable<Response<UploadRequest>> getUploadInformation(final String filename) {
        return bynderApi.getUploadInformation(filename);
    }

    /**
     * Check {@link BynderApi#registerChunk(String, int, String, String)} for more information.
     */
    private Observable<Response<Void>> registerChunk(final UploadRequest uploadRequest, final int chunkNumber) {
        String filename = String.format("%s/p%s", uploadRequest.getS3Filename(), Integer.toString(chunkNumber));
        return bynderApi.registerChunk(uploadRequest.getS3File().getUploadId(), chunkNumber, uploadRequest.getS3File().getTargetId(), filename);
    }

    /**
     * Check {@link BynderApi#finaliseUploaded(String, String, String, int)} for more information.
     */
    private Observable<Response<FinaliseResponse>> finaliseUploaded(final UploadRequest uploadRequest, final int chunks) {
        String filename = String.format("%s/p%s", uploadRequest.getS3Filename(), Integer.toString(chunks));
        return bynderApi.finaliseUploaded(uploadRequest.getS3File().getUploadId(), uploadRequest.getS3File().getTargetId(), filename, chunks);
    }

    /**
     * Check {@link BynderApi#getPollStatus(String)} (String)} for more information.
     */
    private Observable<Response<PollStatus>> pollStatus(final List<String> items) {
        return bynderApi.getPollStatus(StringUtils.join(items, Utils.STR_COMMA));
    }

    /**
     * Check {@link BynderApi#saveMedia(String, String, String)} for more information.
     */
    private Observable<Response<Void>> saveMedia(final String importId, final String brandId, final String name) {
        return bynderApi.saveMedia(importId, brandId, name);
    }

    /**
     * Check {@link BynderApi#saveMediaVersion(String, String)} for more information.
     */
    private Observable<Response<Void>> saveMediaVersion(final String mediaId, final String importId) {
        return bynderApi.saveMediaVersion(mediaId, importId);
    }

    /**
     * Uploads the parts to Amazon and registers them in Bynder.
     *
     * @param file          Path of the file to be uploaded.
     * @param uploadRequest Upload authorization information.
     */
    private Observable<Integer> uploadParts(final File file, final UploadRequest uploadRequest) {
        int chunkNumber = 0;
        byte[] buffer = new byte[MAX_CHUNK_SIZE];
        int numberOfChunks = (Math.toIntExact(file.length()) + MAX_CHUNK_SIZE - 1) / MAX_CHUNK_SIZE;

        UploadProcessData data = new UploadProcessData(file, buffer, MAX_CHUNK_SIZE, uploadRequest);
        return Observable
                .create(observableEmitter -> {
                    data.incrementChunk();
                    processChunk(data)
                            .repeatUntil(() -> {
                                if (data.isCompleted()) {
                                    observableEmitter.onNext(data.getNumberOfChunks());
                                    observableEmitter.onComplete();
                                } else {
                                    data.incrementChunk();
                                }
                                return data.isCompleted();
                            })
                            .doOnError(throwable -> observableEmitter.onError(throwable))
                            .subscribe();
                });
    }

    private Observable<Boolean> processChunk(UploadProcessData data) {
        return Observable.create(observableEmitter -> {
            Observable<Response<Void>> uploadToAmazon = amazonService.uploadPartToAmazon(data.getFile().getName(), data.getUploadRequest(), data.getChunkNumber(), data.getBuffer(), data.getNumberOfChunks());
            uploadToAmazon
                    .doOnError(throwable -> observableEmitter.onError(throwable))
                    .doOnNext(voidResponse -> {
                        Observable<Response<Void>> chunkObs = registerChunk(data.getUploadRequest(), data.getChunkNumber());
                        chunkObs
                                .doOnNext(voidResponse1 -> {
                                    observableEmitter.onNext(true);
                                    observableEmitter.onComplete();
                                })
                                .doOnError(throwable -> observableEmitter.onError(throwable))
                                .subscribe();
                    })
                    .subscribe();
        });
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
            for (int i = MAX_POLLING_ITERATIONS; i > 0; --i) {
                pollStatus(Arrays.asList(importId))
                        .doOnError(throwable -> observableEmitter.onError(throwable))
                        .doOnNext(pollStatusResponse -> {
                            PollStatus pollStatus = pollStatusResponse.body();
                            if (pollStatus != null) {
                                if (pollStatus.getItemsDone().contains(importId)) {
                                    observableEmitter.onNext(true);
                                }
                                if (pollStatus.getItemsFailed().contains(importId)) {
                                    observableEmitter.onNext(true);
                                }
                            }
                            Thread.sleep(POLLING_IDDLE_TIME);
                        });
            }
            observableEmitter.onNext(false);
            observableEmitter.onComplete();
        });
    }
}
