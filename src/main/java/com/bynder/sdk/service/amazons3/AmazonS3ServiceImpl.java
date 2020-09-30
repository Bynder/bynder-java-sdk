/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.amazons3;

import com.bynder.sdk.api.AmazonS3Api;
import com.bynder.sdk.api.ApiFactory;
import com.bynder.sdk.model.upload.MultipartParameters;
import com.bynder.sdk.model.upload.UploadRequest;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of {@link AmazonS3Service}.
 */
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private static final MediaType FORM_DATA = MediaType.parse("multipart/form-data");

    /**
     * Instance of {@link AmazonS3Api} which handles the HTTP communication with the Amazon S3 API.
     */
    private final AmazonS3Api amazonS3Api;

    /**
     * Initialises a new instance of the class.
     *
     * @param bucket AWS bucket with the URL to upload the part to.
     */
    public AmazonS3ServiceImpl(final String bucket) {
        amazonS3Api = ApiFactory.createAmazonS3Client(bucket);
    }

    /**
     * Check {@link AmazonS3Service} for more information.
     */
    @Override
    public Observable<Response<Void>> uploadPartToAmazon(
            final String filename,
            final UploadRequest uploadRequest,
            final int chunkNumber,
            final byte[] fileContent,
            final int numberOfChunks
    ) {
        Map<String, RequestBody> params = new LinkedHashMap<>();

        MultipartParameters multipartParams = uploadRequest.getMultipartParams();
        RequestBody key = encodeField(
                String.format("%s/p%s", multipartParams.getKey(), chunkNumber)
        );

        params.put("chunk", encodeField(String.valueOf(chunkNumber)));
        params.put("chunks", encodeField(String.valueOf(numberOfChunks)));
        params.put("file", encodeField(fileContent));
        params.put("Filename", key);
        params.put("key", key);
        params.put("name", encodeField(filename));

        params.put("acl", encodeField(multipartParams.getAcl()));
        params.put("Content-Type", encodeField(multipartParams.getContentType()));
        params.put("Policy", encodeField(multipartParams.getPolicy()));
        params.put("success_action_status", encodeField(multipartParams.getSuccessActionStatus()));
        params.put("x-amz-algorithm", encodeField(multipartParams.getAlgorithm()));
        params.put("x-amz-credential", encodeField(multipartParams.getAwsAccessKeyId()));
        params.put("x-amz-date", encodeField(multipartParams.getDate()));
        params.put("X-Amz-Signature", encodeField(multipartParams.getSignature()));

        return amazonS3Api.uploadPartToAmazon(params);
    }

    private RequestBody encodeField(byte[] field) {
        return RequestBody.create(field, FORM_DATA);
    }

    private RequestBody encodeField(String field) {
        return RequestBody.create(field, FORM_DATA);
    }

}
