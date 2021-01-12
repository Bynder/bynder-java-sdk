/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.oauth;

import com.bynder.sdk.api.OAuthApi;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Token model returned by {@link OAuthApi#getAccessToken(Map)}.
 */
public class Token {

    /**
     * The access token issued by the authorization server.
     */
    @SerializedName("access_token")
    private final String accessToken;

    /**
     * The remaining lifetime in seconds of the access token.
     */
    @SerializedName("expires_in")
    private final int expiresIn;

    /**
     * The type of the issued token.
     */
    @SerializedName("token_type")
    private final String tokenType;

    /**
     * Scope of the issued token.
     */
    private final String scope;

    /**
     * The refresh token which can be used to obtain new access tokens.
     */
    @SerializedName("refresh_token")
    private String refreshToken;

    /**
     * The access token expiration date.
     */
    private Date accessTokenExpiration;

    public Token(final String accessToken, final int expiresIn, final String tokenType,
        final String scope, final String refreshToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.scope = scope;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getScope() {
        return scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Token setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Date getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expiresIn);
        this.accessTokenExpiration = calendar.getTime();
    }
}
