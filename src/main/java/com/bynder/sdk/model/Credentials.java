/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

/**
 * Token credentials to call the API.
 */
public class Credentials {

    /**
     * Consumer key.
     */
    private String consumerKey;
    /**
     * Consumer secret.
     */
    private String consumerSecret;
    /**
     * Access token key.
     */
    private String token;
    /**
     * Access token secret.
     */
    private String tokenSecret;
    /**
     * Initial token key. Used when we want to reset credentials.
     */
    private String initialToken;
    /**
     * Initial token secret. Used when we want to reset credentials.
     */
    private String initialTokenSecret;

    /**
     * Initialises new instance with specified values.
     *
     * @param consumerKey Consumer key.
     * @param consumerSecret Consumer secret.
     * @param token Token key. This can be null if we are going to log in into Bynder through the
     *        browser.
     * @param tokenSecret Token secret. This can be null if we are going to log in into Bynder
     *        through the browser.
     */
    public Credentials(final String consumerKey, final String consumerSecret, final String token, final String tokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.tokenSecret = tokenSecret;

        this.initialToken = token;
        this.initialTokenSecret = tokenSecret;
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

    /**
     * Resets access token key/secret to the initial ones.
     */
    public void reset() {
        this.token = this.initialToken;
        this.tokenSecret = this.initialTokenSecret;
    }

    /**
     * Sets new access token key/secret.
     *
     * @param token new access token key.
     * @param tokenSecret new access token secret.
     */
    public void set(final String token, final String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }
}
