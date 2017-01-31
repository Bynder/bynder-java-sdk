/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import org.apache.commons.lang.StringUtils;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.User;
import com.bynder.sdk.service.AssetBankManager;
import com.bynder.sdk.service.BynderService;
import com.bynder.sdk.service.BynderServiceCall;
import com.bynder.sdk.util.Utils;

import retrofit2.Call;

public class BynderServiceImpl implements BynderService {

    private final String baseUrl;
    private final String consumerKey;
    private final String consumerSecret;
    private final BynderApi bynderApi;
    private AssetBankManager assetBankManager;

    public BynderServiceImpl(final String baseUrl, final String consumerKey, final String consumerSecret, final String token, final String tokenSecret) {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;

        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, token, tokenSecret);
    }

    @Override
    public BynderServiceCall<User> login(final String username, final String password) {
        Call<User> call = bynderApi.login(username, password);
        return Utils.createServiceCall(call);
    }

    @Override
    public BynderServiceCall<String> getRequestToken() {
        BynderApi bynderApiForRequestToken = Utils.createApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, null, null);
        Call<String> call = bynderApiForRequestToken.getRequestToken();
        return Utils.createServiceCall(call);
    }

    @Override
    public BynderServiceCall<String> getAccessToken(final String requestToken, final String requestTokenSecret) {
        BynderApi bynderApiForAccessToken = Utils.createApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, requestToken, requestTokenSecret);
        Call<String> call = bynderApiForAccessToken.getAccessToken();
        return Utils.createServiceCall(call);
    }

    @Override
    public String getAuthoriseUrl(final String requestToken, final String callbackUrl) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl).append("v4/oauth/authorise/?oauth_token=").append(requestToken);

        if (StringUtils.isNotEmpty(callbackUrl)) {
            stringBuilder.append("&callback=").append(callbackUrl);
        }

        return stringBuilder.toString();
    }

    @Override
    public AssetBankManager getAssetBankManager() {
        if (assetBankManager == null) {
            assetBankManager = new AssetBankManagerImpl(bynderApi);
        }

        return assetBankManager;
    }
}
