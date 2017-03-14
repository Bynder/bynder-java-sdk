/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.util.LinkedHashMap;

import com.bynder.sdk.api.AmazonApi;
import com.bynder.sdk.model.UploadRequest;
import com.bynder.sdk.service.AmazonService;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class AmazonServiceImpl implements AmazonService {

    private final AmazonApi amazonApi;

    public AmazonServiceImpl(final String awsBucket) {
        Retrofit retrofit = new Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(awsBucket).build();
        amazonApi = retrofit.create(AmazonApi.class);
    }

    @Override
    public Observable<Response<Void>> uploadPartToAmazon(final String filename, final UploadRequest uploadRequest, final int chunkNumber, final byte[] fileContent, final int numberOfChunks) {
        String finalKey = String.format("%s/p%s", uploadRequest.getMultipartParams().getKey(), chunkNumber);

        LinkedHashMap<String, RequestBody> params = new LinkedHashMap<>();
        params.put("x-amz-credential", RequestBody.create(MediaType.parse("multipart/form-data"), uploadRequest.getMultipartParams().getAwsAccessKeyId()));
        params.put("key", RequestBody.create(MediaType.parse("multipart/form-data"), finalKey));
        params.put("Policy", RequestBody.create(MediaType.parse("multipart/form-data"), uploadRequest.getMultipartParams().getPolicy()));
        params.put("X-Amz-Signature", RequestBody.create(MediaType.parse("multipart/form-data"), uploadRequest.getMultipartParams().getSignature()));
        params.put("acl", RequestBody.create(MediaType.parse("multipart/form-data"), uploadRequest.getMultipartParams().getAcl()));
        params.put("x-amz-algorithm", RequestBody.create(MediaType.parse("multipart/form-data"), uploadRequest.getMultipartParams().getAlgorithm()));
        params.put("x-amz-date", RequestBody.create(MediaType.parse("multipart/form-data"), uploadRequest.getMultipartParams().getDate()));
        params.put("success_action_status", RequestBody.create(MediaType.parse("multipart/form-data"), uploadRequest.getMultipartParams().getSuccessActionStatus()));
        params.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), uploadRequest.getMultipartParams().getContentType()));
        params.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), filename));
        params.put("chunk", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(chunkNumber)));
        params.put("chunks", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(numberOfChunks)));
        params.put("Filename", RequestBody.create(MediaType.parse("multipart/form-data"), finalKey));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileContent);
        params.put("file", requestFile);

        return amazonApi.uploadPartToAmazon(params);
    }
}
