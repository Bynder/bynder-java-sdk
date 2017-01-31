package com.bynder.sdk;

import java.io.IOException;

import com.bynder.sdk.util.Utils;

/**
 *
 * @deprecated
 */
@Deprecated
public final class BynderApiServiceBuilder {

    private String baseUrl;
    private String consumerKey;
    private String consumerSecret;
    private String accessTokenKey;
    private String accessTokenSecret;
    private String requestTokenKey;
    private String requestTokenSecret;

    public BynderApiServiceBuilder() {}

    public BynderApiServiceBuilder setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public BynderApiServiceBuilder setConsumerKey(final String consumerKey) {
        this.consumerKey = consumerKey;
        return this;
    }

    public BynderApiServiceBuilder setConsumerSecret(final String consumerSecret) {
        this.consumerSecret = consumerSecret;
        return this;
    }

    public BynderApiServiceBuilder setAccessTokenKey(final String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
        return this;
    }

    public BynderApiServiceBuilder setAccessTokenSecret(final String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
        return this;
    }

    public BynderApiServiceBuilder setRequestTokenKey(final String requestTokenKey) {
        this.requestTokenKey = requestTokenKey;
        return this;
    }

    public BynderApiServiceBuilder setRequestTokenSecret(final String requestTokenSecret) {
        this.requestTokenSecret = requestTokenSecret;
        return this;
    }

    public BynderApiService create() throws IOException {
        Utils.checkNotNull("baseUrl", baseUrl);
        Utils.checkNotNull("consumerKey", consumerKey);
        Utils.checkNotNull("consumerSecret", consumerSecret);
        Utils.checkNotNull("accessTokenKey", accessTokenKey);
        Utils.checkNotNull("accessTokenSecret", accessTokenSecret);

        return new BynderApiService(baseUrl, consumerKey, consumerSecret, accessTokenKey, accessTokenSecret);
    }

    public BynderApiService createWithLogin(final String username, final String password) throws IOException {
        Utils.checkNotNull("baseUrl", baseUrl);
        Utils.checkNotNull("consumerKey", consumerKey);
        Utils.checkNotNull("consumerSecret", consumerSecret);
        Utils.checkNotNull("requestTokenKey", requestTokenKey);
        Utils.checkNotNull("requestTokenSecret", requestTokenSecret);
        Utils.checkNotNull("username", username);
        Utils.checkNotNull("password", password);

        return new BynderApiService(baseUrl, consumerKey, consumerSecret, requestTokenKey, requestTokenSecret, username, password);
    }
}
