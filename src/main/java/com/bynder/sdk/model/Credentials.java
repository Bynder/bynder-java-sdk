/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

public class Credentials {

    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSecret;
    private String initialToken;
    private String initialTokenSecret;

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

    public void reset() {
        this.token = this.initialToken;
        this.tokenSecret = this.initialTokenSecret;
    }

    public void set(final String token, final String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }
}
