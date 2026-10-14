/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.api;

import com.bynder.sdk.model.upload.MultipartParameters;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

import java.util.Map;

/**
 * Interface of the Amazon S3 API.
 */
public interface AmazonS3Api {

    /**
     * Uploads a file part to Amazon S3.
     *
     * @param params Parameters from the {@link MultipartParameters} provided in the response of
     * {@link BynderApi#getUploadInformation(Map)}.
     * @return {@link Single} with the {@link Response}.
     */
    @Multipart
    // Use "." (relative) rather than "/" so the upload posts to the full base URL returned by
    // getClosestS3Endpoint(), including any path. A leading "/" is resolved as an absolute path and
    // would drop the base URL's path (e.g. posting to the host root instead of
    // ".../api/1/boss/v4upload/"). For bucket-root S3 endpoints both resolve the same; "." also
    // works when the endpoint is served behind a path.
    @POST(".")
    Single<Response<Void>> uploadPartToAmazon(@PartMap Map<String, RequestBody> params);
}
