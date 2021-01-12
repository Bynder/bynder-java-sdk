/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.configuration;

import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.service.oauth.OAuthService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Consumer;

public class OAuthSettings {

    public static class Builder {

        private final String clientId;
        private final String clientSecret;

        private URI redirectUri;
        private List<String> scopes;
        private Consumer<Token> refreshTokenCallback;

        public Builder(final String clientId, final String clientSecret) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }

        public OAuthSettings build() {
            OAuthSettings oAuthSettings = new OAuthSettings();
            oAuthSettings.clientId = clientId;
            oAuthSettings.clientSecret = clientSecret;
            oAuthSettings.redirectUri = redirectUri;
            oAuthSettings.scopes = scopes;
            oAuthSettings.refreshTokenCallback = refreshTokenCallback;
            return oAuthSettings;
        }

        public Builder setRedirectUri(String redirectUri)
                throws URISyntaxException {
            this.redirectUri = new URI(redirectUri);
            return this;
        }

        public Builder setScopes(List<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder setRefreshTokenCallback(Consumer<Token> refreshTokenCallback) {
            this.refreshTokenCallback = refreshTokenCallback;
            return this;
        }
    }

    // Prevent construction without using the Builder.
    private OAuthSettings() {}

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
     * List of scopes to request to be granted to the access token.
     * Can only be a subset of the scopes requested in the Authorize application request.
     * When not passed, all the scopes will be requested.
     */
    private List<String> scopes;

    /**
     * Optional callback method to be triggered when token is refreshed.
     */
    private Consumer<Token> refreshTokenCallback;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public URI getRedirectUri() {
        return redirectUri;
    }

    public List<String> getScopes() {
        return scopes;
    };

    public void refreshTokenCallback(final Token token) {
        refreshTokenCallback.accept(token);
    }

}
