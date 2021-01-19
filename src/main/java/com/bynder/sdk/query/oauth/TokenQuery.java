/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.oauth;

import com.bynder.sdk.api.OAuthApi;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.query.decoder.ApiField;

import java.util.Map;

/**
 * Query used to call {@link OAuthApi#getAccessToken(Map)}.
 */
public abstract class TokenQuery {

    /**
     * OAuth application client id.
     */
    @ApiField(name = "client_id")
    protected final String clientId;

    /**
     * OAuth application client secret.
     */
    @ApiField(name = "client_secret")
    protected final String clientSecret;

    /**
     * The authorization grant type. Possible values: {@link GrantType#AUTHORIZATION_CODE} and
     * {@link GrantType#REFRESH_TOKEN}.
     */
    @ApiField(name = "grant_type")
    protected final GrantType grantType;

    protected TokenQuery(final OAuthSettings oAuthSettings, final GrantType grantType) {
        this.clientId = oAuthSettings.getClientId();
        this.clientSecret = oAuthSettings.getClientSecret();
        this.grantType = grantType;
    }

}
