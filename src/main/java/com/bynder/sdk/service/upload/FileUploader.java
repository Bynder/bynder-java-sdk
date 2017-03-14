/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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

public class FileUploader {

    private static final int CHUNK_SIZE = 1024 * 1024 * 5;
    private static final int MAX_POLLING_ITERATIONS = 60;
    private static final int POLLING_IDDLE_TIME = 2000;

    private final BynderApi bynderApi;
    private final AmazonService amazonService;
    private String awsBucket;

    private FileUploader(final BynderApi bynderApi) {
        this.bynderApi = bynderApi;
        this.awsBucket = getClosestS3Endpoint().blockingSingle().body();
        this.amazonService = new AmazonServiceImpl(awsBucket);
    }

    public static FileUploader create(final BynderApi bynderApi) {
        return new FileUploader(bynderApi);
    }

    public void uploadFile(final UploadQuery uploadQuery) throws BynderUploadException, IOException, InterruptedException, RuntimeException {
        UploadRequest uploadRequest = getUploadInformation(uploadQuery.getFilepath()).blockingSingle().body();

        int chunkNumber = 0;
        File file = new File(uploadQuery.getFilepath());
        byte[] buffer = new byte[CHUNK_SIZE];
        long numberOfChunks = (file.length() + CHUNK_SIZE - 1) / CHUNK_SIZE;
        FileInputStream fileInputStream = new FileInputStream(file);

        while (fileInputStream.read(buffer, 0, buffer.length) > 0) {
            ++chunkNumber;
            uploadPart(file.getName(), buffer, chunkNumber, uploadRequest, (int) numberOfChunks);
        }
        fileInputStream.close();

        FinaliseResponse finaliseResponse = finaliseUploaded(uploadRequest, chunkNumber).blockingSingle().body();
        String importId = finaliseResponse.getImportId();

        if (hasFinishedSuccessfully(importId)) {
            if (uploadQuery.getMediaId() == null) {
                saveMedia(importId, uploadQuery.getBrandId(), file.getName()).blockingSingle();
            } else {
                saveMediaVersion(uploadQuery.getMediaId(), importId);
            }
        } else {
            throw new BynderUploadException("Converter did not finished. Upload not completed");
        }
    }

    private Observable<Response<String>> getClosestS3Endpoint() {
        return bynderApi.getClosestS3Endpoint();
    }

    private Observable<Response<UploadRequest>> getUploadInformation(final String filename) {
        return bynderApi.getUploadInformation(filename);
    }

    private Observable<Response<Void>> registerChunk(final UploadRequest uploadRequest, final int chunkNumber) {
        String filename = String.format("%s/p%s", uploadRequest.getS3Filename(), Integer.toString(chunkNumber));
        return bynderApi.registerChunk(uploadRequest.getS3File().getUploadId(), chunkNumber, uploadRequest.getS3File().getTargetId(), filename);
    }

    private Observable<Response<FinaliseResponse>> finaliseUploaded(final UploadRequest uploadRequest, final int chunks) {
        String filename = String.format("%s/p%s", uploadRequest.getS3Filename(), Integer.toString(chunks));
        return bynderApi.finaliseUploaded(uploadRequest.getS3File().getUploadId(), uploadRequest.getS3File().getTargetId(), filename, chunks);
    }

    private Observable<Response<PollStatus>> pollStatus(final List<String> items) {
        return bynderApi.pollStatus(StringUtils.join(items, Utils.STR_COMMA));
    }

    private Observable<Response<Void>> saveMedia(final String importId, final String brandId, final String name) {
        return bynderApi.saveMedia(importId, brandId, name);
    }

    private Observable<Response<Void>> saveMediaVersion(final String mediaId, final String importId) {
        return bynderApi.saveMediaVersion(mediaId, importId);
    }

    private void uploadPart(final String filename, final byte[] buffer, final int chunkNumber, final UploadRequest uploadRequest, final int numberOfChunks) {
        amazonService.uploadPartToAmazon(filename, uploadRequest, chunkNumber, buffer, numberOfChunks).blockingSingle();
        registerChunk(uploadRequest, chunkNumber).blockingSingle();
    }

    private boolean hasFinishedSuccessfully(final String importId) throws InterruptedException {
        for (int i = MAX_POLLING_ITERATIONS; i > 0; --i) {
            PollStatus pollStatus = pollStatus(Arrays.asList(importId)).blockingSingle().body();

            if (pollStatus != null) {
                if (pollStatus.getItemsDone().contains(importId)) {
                    return true;
                }
                if (pollStatus.getItemsFailed().contains(importId)) {
                    return false;
                }
            }
            Thread.sleep(POLLING_IDDLE_TIME);
        }
        return false;
    }
}
