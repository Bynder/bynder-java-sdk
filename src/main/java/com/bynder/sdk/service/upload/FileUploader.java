/**
 * Copyright (c) Bynder. All rights reserved.
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
import com.bynder.sdk.service.BynderServiceCall;
import com.bynder.sdk.service.exception.BynderUploadException;
import com.bynder.sdk.service.impl.AmazonServiceImpl;
import com.bynder.sdk.util.Utils;

import retrofit2.Call;

public class FileUploader {

    private static final int MAX_CHUNK_SIZE = 1024 * 1024 * 5;
    private static final int MAX_POLLING_ITERATIONS = 60;
    private static final int POLLING_IDDLE_TIME = 2000;

    private final BynderApi bynderApi;
    private final AmazonService amazonService;
    private String awsBucket;

    public FileUploader(final BynderApi bynderApi) {
        this.bynderApi = bynderApi;
        this.awsBucket = getClosestS3Endpoint().execute();
        this.amazonService = new AmazonServiceImpl(awsBucket);
    }

    private BynderServiceCall<UploadRequest> getUploadInformation(final String filename) {
        Call<UploadRequest> call = bynderApi.getUploadInformation(filename);
        return Utils.createServiceCall(call);
    }

    private BynderServiceCall<String> getClosestS3Endpoint() {
        Call<String> call = bynderApi.getClosestS3Endpoint();
        return Utils.createServiceCall(call);
    }

    private BynderServiceCall<Void> registerChunk(final UploadRequest uploadRequest, final int chunkNumber) {
        String filename = String.format("%s/p%s", uploadRequest.getS3Filename(), Integer.toString(chunkNumber));
        Call<Void> call = bynderApi.registerChunk(uploadRequest.getS3File().getUploadId(), chunkNumber, uploadRequest.getS3File().getTargetId(), filename);
        return Utils.createServiceCall(call);
    }

    private BynderServiceCall<FinaliseResponse> finaliseUploaded(final UploadRequest uploadRequest, final int chunks) {
        String filename = String.format("%s/p%s", uploadRequest.getS3Filename(), Integer.toString(chunks));
        Call<FinaliseResponse> call = bynderApi.finaliseUploaded(uploadRequest.getS3File().getUploadId(), uploadRequest.getS3File().getTargetId(), filename, chunks);
        return Utils.createServiceCall(call);
    }

    private BynderServiceCall<PollStatus> pollStatus(final List<String> items) {
        Call<PollStatus> call = bynderApi.pollStatus(StringUtils.join(items, Utils.STR_COMMA));
        return Utils.createServiceCall(call);
    }

    private BynderServiceCall<Void> saveMedia(final String importId, final String brandId, final String name) {
        Call<Void> call = bynderApi.saveMedia(importId, brandId, name);
        return Utils.createServiceCall(call);
    }

    public void uploadFile(final UploadQuery uploadQuery) throws BynderUploadException, IOException, InterruptedException, RuntimeException {
        UploadRequest uploadRequest = getUploadInformation(uploadQuery.getFilepath()).execute();

        int chunkNumber = 0;
        File file = new File(uploadQuery.getFilepath());
        byte[] buffer = new byte[MAX_CHUNK_SIZE];
        long numberOfChunks = (file.length() + MAX_CHUNK_SIZE - 1) / MAX_CHUNK_SIZE;
        FileInputStream fileInputStream = new FileInputStream(file);

        while (fileInputStream.read(buffer, 0, buffer.length) > 0) {
            ++chunkNumber;

            uploadPart(file.getName(), buffer, chunkNumber, uploadRequest, (int) numberOfChunks);
        }
        fileInputStream.close();

        FinaliseResponse finaliseResponse = finaliseUploaded(uploadRequest, chunkNumber).execute();

        if (hasFinishedSuccessfully(finaliseResponse)) {
            saveMedia(finaliseResponse.getImportId(), uploadQuery.getBrandId(), file.getName()).execute();
        } else {
            throw new BynderUploadException("Converter did not finished. Upload not completed");
        }
    }

    private void uploadPart(final String filename, final byte[] buffer, final int chunkNumber, final UploadRequest uploadRequest, final int numberOfChunks) {
        amazonService.uploadPartToAmazon(filename, uploadRequest, chunkNumber, buffer, numberOfChunks).execute();
        registerChunk(uploadRequest, chunkNumber).execute();
    }

    private boolean hasFinishedSuccessfully(final FinaliseResponse finaliseResponse) throws InterruptedException {
        for (int i = MAX_POLLING_ITERATIONS; i > 0; --i) {
            PollStatus pollStatus = pollStatus(Arrays.asList(finaliseResponse.getImportId())).execute();

            if (pollStatus != null) {
                if (pollStatus.getItemsDone().contains(finaliseResponse.getImportId())) {
                    return true;
                }
                if (pollStatus.getItemsFailed().contains(finaliseResponse.getImportId())) {
                    return false;
                }
            }
            Thread.sleep(POLLING_IDDLE_TIME);
        }
        return false;
    }
}
