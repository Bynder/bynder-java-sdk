/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.configuration;

import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.util.RefreshTokenCallback;
import java.net.URI;
import java.net.URL;

public class Configuration {

    /**
     * Bynder portal URL.
     */
    private URL baseUrl;
    /**
     * Settings for the HTTP connection to Bynder.
     */
    private HttpConnectionSettings httpConnectionSettings;
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

    /**
     * Prevents the instantiation of the class.
     */
    private Configuration() {
    }

    public URL getBaseUrl() {
        return baseUrl;
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

    public void callback(final Token token) {
        callback.execute(token);
    }

    public HttpConnectionSettings getHttpConnectionSettings() {
        return  this.httpConnectionSettings;
    }

    /**
     * Builder class used to create a new instance of {@link Configuration}.
     */
    public static class Builder {

        private URL baseUrl;
        private String clientId;
        private String clientSecret;
        private URI redirectUri;
        private Token token;
        private RefreshTokenCallback callback;
        private HttpConnectionSettings httpConnectionSettings;

        public Builder(final URL baseUrl, final String clientId, final String clientSecret, final URI redirectUri) {
            this.baseUrl = baseUrl;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.redirectUri = redirectUri;
        }

        public Builder setToken(Token token) {
            this.token = token;
            return this;
        }

        public Builder setCallback(RefreshTokenCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder setHttpConnectionSettings(HttpConnectionSettings httpConnectionSettings) {
            this.httpConnectionSettings = httpConnectionSettings;
            return this;
        }

        public Configuration build() {
            Configuration configuration = new Configuration();
            configuration.baseUrl = this.baseUrl;
            configuration.clientId = this.clientId;
            configuration.clientSecret = this.clientSecret;
            configuration.redirectUri = this.redirectUri;
            configuration.token = this.token;
            configuration.httpConnectionSettings = this.httpConnectionSettings;
            configuration.callback = this.callback;

            return configuration;
        }
    }
}
