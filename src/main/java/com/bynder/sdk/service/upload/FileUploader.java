/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 * <p>
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.exception.BynderUploadException;
import com.bynder.sdk.model.upload.*;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.upload.*;
import com.bynder.sdk.service.amazons3.AmazonS3Service;
import com.bynder.sdk.util.RXUtils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Class used to upload files to Bynder.
 */
public class FileUploader {

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
     * Creates a new instance of the class.
     *
     * @param bynderApi    Instance to handle the HTTP communication with the Bynder API.
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
    public Single<SaveMediaResponse> uploadFile(final UploadQuery uploadQuery) {
        return getClosestS3Endpoint().flatMap(awsBucket -> {
            String filename = uploadQuery.getFilename();
            AmazonS3Service amazonS3Service = AmazonS3Service.Builder.create(awsBucket);
            return getUploadInformation(new RequestUploadQuery(filename))
                    .flatMap(uploadRequest -> uploadChunk(
                            amazonS3Service,
                            uploadRequest,
                            uploadQuery,
                            filename
                    ).count().flatMap(chunkCount -> finaliseUpload(new FinaliseUploadQuery(
                            uploadRequest.getS3File().getUploadId(),
                            uploadRequest.getS3File().getTargetId(),
                            uploadRequest.getS3Filename(),
                            chunkCount
                    ))));
        }).flatMap(importId ->
                pollProcessing(importId).andThen(saveUploadedMedia(importId, uploadQuery))
        );
    }

    public Single<UploadAdditionalMediaResponse> uploadAdditionalFile(final UploadQuery uploadQuery) {
        return getClosestS3Endpoint().flatMap(awsBucket -> {
            String filename = uploadQuery.getFilename();
            AmazonS3Service amazonS3Service = AmazonS3Service.Builder.create(awsBucket);
            return getUploadInformation(new RequestUploadQuery(filename))
                    .flatMap(uploadRequest -> uploadChunk(
                            amazonS3Service,
                            uploadRequest,
                            uploadQuery,
                            filename
                    ).count().flatMap(chunkCount -> finaliseUploadAdditional(new FinaliseUploadAdditionalQuery(
                            uploadRequest.getS3File().getUploadId(),
                            uploadRequest.getS3File().getTargetId(),
                            uploadRequest.getS3Filename(),
                            chunkCount
                    ), uploadQuery.getMediaId())));
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
        String filename = uploadQuery.getFilename();
        return getClosestS3Endpoint().flatMapObservable(awsBucket -> {
            AmazonS3Service amazonS3Service = AmazonS3Service.Builder.create(awsBucket);
            return getUploadInformation(new RequestUploadQuery(filename))
                    .flatMapObservable(uploadRequest -> uploadChunk(
                            amazonS3Service,
                            uploadRequest,
                            uploadQuery,
                            filename
                    ));
        });
    }

    private Observable<UploadProgress> uploadChunk(
            AmazonS3Service amazonS3Service,
            UploadRequest uploadRequest,
            UploadQuery uploadQuery,
            String filename
    ) throws IOException {
        long fileSize = Files.size(Paths.get(uploadQuery.getFilepath()));
        UploadProgress uploadProgress = new UploadProgress(fileSize);
        return RXUtils.mapWithIndex(
                RXUtils.readFileChunks(uploadQuery.getFilepath(), MAX_CHUNK_SIZE),
                1
        ).flatMapSingle(chunk -> amazonS3Service.uploadPartToAmazon(
                chunk,
                filename,
                (int) ((fileSize - 1) / MAX_CHUNK_SIZE + 1),
                uploadRequest.getMultipartParams()
        ).andThen(registerChunk(new RegisterChunkQuery(
                chunk.getIndex(),
                uploadRequest.getS3File().getUploadId(),
                uploadRequest.getS3File().getTargetId(),
                String.format(
                        "%s/p%s",
                        uploadRequest.getS3Filename(),
                        chunk.getIndex()
                )
        ))).toSingle(() -> uploadProgress.addProgress(chunk.getValue().length)));
    }

    /**
     * Check {@link BynderApi#getPollStatus} for more information.
     */
    private Completable pollProcessing(final String importId) {
        return getPollStatus(new PollStatusQuery(importId.split(",")))
                .delay(POLLING_IDLE_TIME, TimeUnit.MILLISECONDS)
                .repeat()
                .take(MAX_POLLING_ITERATIONS)
                .takeUntil(pollStatus -> pollStatus.processingDone(importId) || pollStatus.processingFailed(importId))
                .lastElement()
                .toSingle()
                .flatMapCompletable(pollStatus -> {
                    if (pollStatus.processingFailed(importId)) {
                        return Completable.error(new BynderUploadException("Processing media failed."));
                    }
                    return Completable.complete();
                });
    }

    /**
     * Calls {@link FileUploader#saveMedia(SaveMediaQuery)} to save the completely uploaded file in
     * Bynder.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @return {@link Single} with the {@link SaveMediaResponse} information.
     */
    private Single<SaveMediaResponse> saveUploadedMedia(final String importId, final UploadQuery uploadQuery) {
        SaveMediaQuery saveMediaQuery = new SaveMediaQuery(importId)
                .setAudit(uploadQuery.isAudit())
                .setMetaproperties(uploadQuery.getMetaproperties());

        if (uploadQuery.getMediaId() == null) {
            // A new asset will be created for the uploaded file.
            return saveMedia(saveMediaQuery
                    .setBrandId(uploadQuery.getBrandId())
                    .setName(uploadQuery.getAssetName())
                    .setTags(uploadQuery.getTags())
            );
        } else {
            // The uploaded file will be attached to an existing asset.
            return saveMedia(saveMediaQuery
                    .setMediaId(uploadQuery.getMediaId())
            );
        }
    }

    /**
     * Check {@link BynderApi#getClosestS3Endpoint()} for more information.
     */
    private Single<String> getClosestS3Endpoint() {
        return bynderApi.getClosestS3Endpoint().singleOrError().map(RXUtils::getResponseBody);
    }

    /**
     * Check {@link BynderApi#getUploadInformation(Map)} for more information.
     */
    private Single<UploadRequest> getUploadInformation(final RequestUploadQuery requestUploadQuery) {
        Map<String, String> params = queryDecoder.decode(requestUploadQuery);
        return bynderApi.getUploadInformation(params).singleOrError().map(RXUtils::getResponseBody);
    }

    /**
     * Check {@link BynderApi#registerChunk(Map)} for more information.
     */
    private Completable registerChunk(final RegisterChunkQuery registerChunkQuery) {
        Map<String, String> params = queryDecoder.decode(registerChunkQuery);
        return bynderApi.registerChunk(params).ignoreElements();
    }

    /**
     * Check {@link BynderApi#finaliseUpload(Map)} for more information.
     */
    private Single<String> finaliseUpload(final FinaliseUploadQuery finaliseUploadQuery) {
        Map<String, String> params = queryDecoder.decode(finaliseUploadQuery);
        return bynderApi.finaliseUpload(params).singleOrError().map(RXUtils::getResponseBody)
                .map(FinaliseResponse::getImportId);
    }

    private Single<UploadAdditionalMediaResponse> finaliseUploadAdditional(final FinaliseUploadAdditionalQuery finaliseUploadQuery, String mediaId) {
        Map<String, String> params = queryDecoder.decode(finaliseUploadQuery);
        return bynderApi.finaliseUploadAdditional(mediaId, params).singleOrError().map(RXUtils::getResponseBody);
    }

    /**
     * Check {@link BynderApi#getPollStatus(Map)} for more information.
     */
    private Single<PollStatus> getPollStatus(final PollStatusQuery pollStatusQuery) {
        Map<String, String> params = queryDecoder.decode(pollStatusQuery);
        return bynderApi.getPollStatus(params).singleOrError().map(RXUtils::getResponseBody);
    }

    /**
     * Check {@link BynderApi#saveMedia(Map)} for more information.
     */
    private Single<SaveMediaResponse> saveMedia(final SaveMediaQuery saveMediaQuery) {
        Map<String, String> params = queryDecoder.decode(saveMediaQuery);
        return bynderApi.saveMedia(params).singleOrError().map(RXUtils::getResponseBody);
    }
}
