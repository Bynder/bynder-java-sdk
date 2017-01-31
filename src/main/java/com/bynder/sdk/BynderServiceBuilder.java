package com.bynder.sdk;

import java.io.IOException;

import com.bynder.sdk.service.BynderServiceOld;
import com.bynder.sdk.util.Utils;

/**
 *
 * @deprecated
 */
@Deprecated
public final class BynderServiceBuilder {

    private String baseUrl;
    private String consumerKey;
    private String consumerSecret;
    private String accessTokenKey;
    private String accessTokenSecret;

    public BynderServiceBuilder setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public BynderServiceBuilder setConsumerKey(final String consumerKey) {
        this.consumerKey = consumerKey;
        return this;
    }

    public BynderServiceBuilder setConsumerSecret(final String consumerSecret) {
        this.consumerSecret = consumerSecret;
        return this;
    }

    public BynderServiceBuilder setAccessTokenKey(final String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
        return this;
    }

    public BynderServiceBuilder setAccessTokenSecret(final String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
        return this;
    }

    public BynderService create() throws IOException {
        Utils.checkNotNull("baseUrl", baseUrl);
        Utils.checkNotNull("consumerKey", consumerKey);
        Utils.checkNotNull("consumerSecret", consumerSecret);
        Utils.checkNotNull("accessTokenKey", accessTokenKey);
        Utils.checkNotNull("accessTokenSecret", accessTokenSecret);

        return new BynderService(baseUrl, consumerKey, consumerSecret, accessTokenKey, accessTokenSecret);
    }
}
