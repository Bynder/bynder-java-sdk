/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.net.URL;

import com.bynder.sdk.service.BynderService;

/**
 * Settings needed to configure {@link BynderService}.
 */
public class Settings {

    /**
     * Bynder domain URL where we want to point the API calls.
     */
    private URL baseUrl;
    /**
     * Oauth consumer key.
     */
    private String consumerKey;
    /**
     * Oauth consumer secret.
     */
    private String consumerSecret;
    /**
     * Oauth token key.
     */
    private String token;
    /**
     * Oauth token secret.
     */
    private String tokenSecret;
    /**
     * Settings for the HTTP connection to Bynder.
     */
    private HttpConnectionSettings httpConnectionSettings;


    public Settings(final URL baseUrl, final String consumerKey, final String consumerSecret, final String token, final String tokenSecret) {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.tokenSecret = tokenSecret;
        this.httpConnectionSettings = new HttpConnectionSettings();
    }

    public Settings(final URL baseUrl, final String consumerKey, final String consumerSecret, final String token, final String tokenSecret, final HttpConnectionSettings httpConnectionSettings) {
        this(baseUrl, consumerKey, consumerSecret, token, tokenSecret);
        this.httpConnectionSettings = httpConnectionSettings;
    }

    public Settings(final URL baseUrl, final String consumerKey, final String consumerSecret) {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public HttpConnectionSettings getHttpConnectionSettings() {
        return httpConnectionSettings;
    }
}
