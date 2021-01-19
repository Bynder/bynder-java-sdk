/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.configuration;

import java.net.MalformedURLException;
import java.net.URL;

public class Configuration {

    /**
     * Bynder portal base URL.
     */
    private URL baseUrl;

    /**
     * OAuth settings for the OAuth 2.0 authorization flow.
     */
    private OAuthSettings oauthSettings;

    /**
     * Connection settings for the HTTP communication with Bynder.
     */
    private HttpConnectionSettings httpConnectionSettings;

    /**
     * Prevents the instantiation of the class.
     */
    private Configuration() {
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public OAuthSettings getOAuthSettings() {
        return this.oauthSettings;
    }

    public HttpConnectionSettings getHttpConnectionSettings() {
        return this.httpConnectionSettings;
    }

    /**
     * Builder class used to create a new instance of {@link Configuration}.
     */
    public static class Builder {

        private final URL baseUrl;
        private final OAuthSettings oauthSettings;
        private HttpConnectionSettings httpConnectionSettings;

        public Builder(final String baseUrl, final OAuthSettings oauthSettings)
                throws MalformedURLException {
            this.baseUrl = new URL(baseUrl);
            this.oauthSettings = oauthSettings;
            this.httpConnectionSettings = new HttpConnectionSettings();
        }

        public Builder setHttpConnectionSettings(HttpConnectionSettings httpConnectionSettings) {
            this.httpConnectionSettings = httpConnectionSettings;
            return this;
        }

        public Configuration build() {
            Configuration configuration = new Configuration();
            configuration.baseUrl = this.baseUrl;
            configuration.oauthSettings = this.oauthSettings;
            configuration.httpConnectionSettings = this.httpConnectionSettings;
            return configuration;
        }
    }
}
