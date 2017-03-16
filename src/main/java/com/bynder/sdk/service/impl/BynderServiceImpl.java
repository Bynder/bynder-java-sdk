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

/**
 * Implementation of {@link BynderService}.
 */
public class BynderServiceImpl implements BynderService {

    /**
     * Base URL needed to instantiate the {@link BynderApi} interface and generate authorize URL.
     */
    private final String baseUrl;
    /**
     * Credentials used to login, instantiate the {@link BynderApi} interface and generate authorize
     * URL.
     */
    private Credentials credentials;
    /**
     * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
     */
    private BynderApi bynderApi;
    /**
     * Instance of the asset bank manager.
     */
    private AssetBankManager assetBankManager;

    /**
     * Initializes a new instance of the class.
     *
     * @param baseUrl Base URL where we want to point the API calls.
     * @param credentials Credentials to use to call the API.
     */
    private BynderServiceImpl(final String baseUrl, final Credentials credentials) {
        this.baseUrl = baseUrl;
        this.credentials = credentials;

        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials.getConsumerKey(), credentials.getConsumerSecret(), credentials.getToken(), credentials.getTokenSecret());
    }

    /**
     * Creates an instance of {@link BynderService} using {@link Settings} as parameter.
     *
     * @param settings Settings to correctly configure the {@link BynderService} instance.
     *
     * @return {@link BynderService} instance to communicate with Bynder API.
     */
    public static BynderService create(final Settings settings) {
        Credentials credentials = new Credentials(settings.getConsumerKey(), settings.getConsumerSecret(), settings.getToken(), settings.getTokenSecret());
        return new BynderServiceImpl(settings.getBaseUrl(), credentials);
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public User login(final String username, final String password) {
        return login(new LoginQuery(username, password));
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public void getRequestToken() {
        Response<String> response = bynderApi.getRequestToken().blockingSingle();
        updateTokensFromResponse(response.body());
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public String getAuthoriseUrl(final String callbackUrl) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl).append("v4/oauth/authorise/?oauth_token=").append(credentials.getToken());

        if (StringUtils.isNotEmpty(callbackUrl)) {
            stringBuilder.append("&callback=").append(callbackUrl);
        }

        return stringBuilder.toString();
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public void getAccessToken() {
        Response<String> response = bynderApi.getAccessToken().blockingSingle();
        updateTokensFromResponse(response.body());
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public void logout() {
        this.credentials.reset();
    };

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public AssetBankManager getAssetBankManager() {
        if (assetBankManager == null) {
            assetBankManager = new AssetBankManagerImpl(bynderApi);
        }

        return assetBankManager;
    }

    /**
     * Helper method update the credentials and the {@link BynderApi} instance.
     *
     * @param response Response string.
     */
    private void updateTokensFromResponse(final String response) {
        Map<String, String> oauthTokens = Utils.buildMapFromResponse(response);
        credentials.set(oauthTokens.get("oauth_token"), oauthTokens.get("oauth_token_secret"));
        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials.getConsumerKey(), credentials.getConsumerSecret(), credentials.getToken(), credentials.getTokenSecret());
    }

    /**
     * Returns {@link User} containing user information. Sends the request to {@link BynderApi}.
     *
     * @param loginQuery Information to call the login API endpoint.
     *
     * @return {@link User} information.
     */
    private User login(final LoginQuery loginQuery) {
        User user = bynderApi.login(loginQuery.getUsername(), loginQuery.getPassword()).blockingSingle().body();
        credentials.set(user.getTokenKey(), user.getTokenSecret());
        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials.getConsumerKey(), credentials.getConsumerSecret(), credentials.getToken(), credentials.getTokenSecret());
        return user;
    }
}
