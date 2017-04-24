/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.api;

import java.util.Map;

import com.bynder.sdk.model.MultipartParameters;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Interface of the Amazon API to handle the HTTP communication.
 */
public interface AmazonApi {

    /**
     * Uploads a file part to Amazon.
     *
     * @param params Parameters from the {@link MultipartParameters} provided in the response of
     *        {@link BynderApi#getUploadInformation(Map)}.
     *
     * @return {@link Observable} with the {@link Response}.
     */
    @Multipart
    @POST("/")
    Observable<Response<Void>> uploadPartToAmazon(@PartMap Map<String, RequestBody> params);
}
