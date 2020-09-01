/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.configuration;

import com.bynder.sdk.model.oauth.RefreshTokenCallback;
import com.bynder.sdk.model.oauth.Token;

import java.net.URI;

public class OAuthSettings {

    /**
     * OAuth application client id.
     */
    private String clientId;
    /**
     * OAuth application client secret.
     */
    private String clientSecret;
    /**
     * URI to redirect to after application has been authorized.
     */
    private URI redirectUri;
    /**
     * Token information.
     */
    private Token token;
    /**
     * Optional callback method to be triggered when token is refreshed.
     */
    private RefreshTokenCallback callback = new RefreshTokenCallback() {
        @Override
        public void execute(Token token) {
            return;
        }
    };

    public OAuthSettings() {
    }

    public OAuthSettings(String clientId, String clientSecret, URI redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public OAuthSettings(String clientId, String clientSecret, URI redirectUri, RefreshTokenCallback callback) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.callback = callback;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public URI getRedirectUri() {
        return redirectUri;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(final Token token) {
        this.token = token;
    }

    public void refreshToken(final Token newToken) {
        newToken.setRefreshToken(this.token.getRefreshToken());
        this.token = newToken;
    }

    public void callback(final Token token) {
        callback.execute(token);
    }
}
