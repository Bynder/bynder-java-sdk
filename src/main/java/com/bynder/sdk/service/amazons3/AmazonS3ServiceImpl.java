/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.amazons3;

import com.bynder.sdk.api.AmazonS3Api;
import com.bynder.sdk.api.ApiFactory;
import com.bynder.sdk.model.upload.UploadRequest;
import io.reactivex.Observable;
import java.util.LinkedHashMap;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Implementation of {@link AmazonS3Service}.
 */
public class AmazonS3ServiceImpl implements AmazonS3Service {

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
    public Observable<Response<Void>> uploadPartToAmazon(final String filename,
        final UploadRequest uploadRequest, final int chunkNumber, final byte[] fileContent,
        final int numberOfChunks) {
        String finalKey = String
            .format("%s/p%s", uploadRequest.getMultipartParams().getKey(), chunkNumber);

        LinkedHashMap<String, RequestBody> params = new LinkedHashMap<>();
        MediaType contentType = MediaType.parse("multipart/form-data");

        params.put("x-amz-credential", RequestBody
            .create(contentType, uploadRequest.getMultipartParams().getAwsAccessKeyId()));
        params.put("key", RequestBody.create(contentType, finalKey));
        params.put("Policy",
            RequestBody.create(contentType, uploadRequest.getMultipartParams().getPolicy()));
        params.put("X-Amz-Signature",
            RequestBody.create(contentType, uploadRequest.getMultipartParams().getSignature()));
        params.put("acl",
            RequestBody.create(contentType, uploadRequest.getMultipartParams().getAcl()));
        params.put("x-amz-algorithm",
            RequestBody.create(contentType, uploadRequest.getMultipartParams().getAlgorithm()));
        params.put("x-amz-date",
            RequestBody.create(contentType, uploadRequest.getMultipartParams().getDate()));
        params.put("success_action_status", RequestBody
            .create(contentType, uploadRequest.getMultipartParams().getSuccessActionStatus()));
        params.put("Content-Type",
            RequestBody.create(contentType, uploadRequest.getMultipartParams().getContentType()));
        params.put("name", RequestBody.create(contentType, filename));
        params.put("chunk", RequestBody.create(contentType, String.valueOf(chunkNumber)));
        params.put("chunks", RequestBody.create(contentType, String.valueOf(numberOfChunks)));
        params.put("Filename", RequestBody.create(contentType, finalKey));

        RequestBody requestFile = RequestBody.create(contentType, fileContent);
        params.put("file", requestFile);

        return amazonS3Api.uploadPartToAmazon(params);
    }
}
