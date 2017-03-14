/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Credentials;
import com.bynder.sdk.model.Settings;
import com.bynder.sdk.model.User;
import com.bynder.sdk.query.LoginQuery;
import com.bynder.sdk.service.AssetBankManager;
import com.bynder.sdk.service.BynderService;
import com.bynder.sdk.util.Utils;

import retrofit2.Response;

public class BynderServiceImpl implements BynderService {

    private final String baseUrl;
    private Credentials credentials;
    private BynderApi bynderApi;
    private AssetBankManager assetBankManager;

    private BynderServiceImpl(final String baseUrl, final Credentials credentials) {
        this.baseUrl = baseUrl;
        this.credentials = credentials;

        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials.getConsumerKey(), credentials.getConsumerSecret(), credentials.getToken(), credentials.getTokenSecret());
    }

    public static BynderService create(final Settings settings) {
        Credentials credentials = new Credentials(settings.getConsumerKey(), settings.getConsumerSecret(), settings.getToken(), settings.getTokenSecret());
        return new BynderServiceImpl(settings.getBaseUrl(), credentials);
    }

    @Override
    public User login(final String username, final String password) {
        return login(new LoginQuery(username, password));
    }

    @Override
    public void getRequestToken() {
        Response<String> response = bynderApi.getRequestToken().blockingSingle();
        updateTokensFromResponse(response.body());
    }

    @Override
    public void getAccessToken() {
        Response<String> response = bynderApi.getAccessToken().blockingSingle();
        updateTokensFromResponse(response.body());
    }

    @Override
    public String getAuthoriseUrl(final String callbackUrl) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl).append("v4/oauth/authorise/?oauth_token=").append(credentials.getToken());

        if (StringUtils.isNotEmpty(callbackUrl)) {
            stringBuilder.append("&callback=").append(callbackUrl);
        }

        return stringBuilder.toString();
    }

    @Override
    public void logout() {
        this.credentials.reset();
    };

    @Override
    public AssetBankManager getAssetBankManager() {
        if (assetBankManager == null) {
            assetBankManager = new AssetBankManagerImpl(bynderApi);
        }

        return assetBankManager;
    }

    private void updateTokensFromResponse(final String response) {
        Map<String, String> oauthTokens = Utils.buildMapFromResponse(response);
        credentials.set(oauthTokens.get("oauth_token"), oauthTokens.get("oauth_token_secret"));
        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials.getConsumerKey(), credentials.getConsumerSecret(), credentials.getToken(), credentials.getTokenSecret());
    }

    private User login(final LoginQuery loginQuery) {
        User user = bynderApi.login(loginQuery.getUsername(), loginQuery.getPassword()).blockingSingle().body();
        credentials.set(user.getTokenKey(), user.getTokenSecret());
        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials.getConsumerKey(), credentials.getConsumerSecret(), credentials.getToken(), credentials.getTokenSecret());
        return user;
    }
}
