/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.api;

import com.bynder.sdk.model.oauth.Token;
import io.reactivex.Observable;
import java.util.Map;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Interface of the Bynder OAuth2 provider to handle the HTTP communication.
 */
public interface OAuthApi {

    /**
     * Gets a new access token by sending an authorization grant or refresh token.
     *
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with the {@link Token} information.
     */
    @FormUrlEncoded
    @POST("/v6/authentication/oauth2/token")
    Observable<Response<Token>> getAccessToken(@FieldMap Map<String, String> params);
}
