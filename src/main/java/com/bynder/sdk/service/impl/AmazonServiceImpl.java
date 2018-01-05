/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import com.bynder.sdk.api.AmazonApi;
import com.bynder.sdk.model.UploadRequest;
import com.bynder.sdk.service.AmazonService;
import io.reactivex.Observable;
import java.util.LinkedHashMap;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Implementation of {@link AmazonService}.
 */
public class AmazonServiceImpl implements AmazonService {

    /**
     * Instance of {@link AmazonApi} which handles the HTTP communication with the Amazon API.
     */
    private final AmazonApi amazonApi;

    /**
     * Initialises a new instance of the class.
     *
     * @param awsBucket AWS bucket with the URL to upload the part to.
     */
    public AmazonServiceImpl(final String awsBucket) {
        Retrofit retrofit = new Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(awsBucket).build();
        amazonApi = retrofit.create(AmazonApi.class);
    }

    /**
     * Check {@link AmazonService} for more information.
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

        return amazonApi.uploadPartToAmazon(params);
    }
}
