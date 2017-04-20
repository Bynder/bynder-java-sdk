/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.net.URL;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Credentials;
import com.bynder.sdk.model.Settings;
import com.bynder.sdk.model.User;
import com.bynder.sdk.query.LoginQuery;
import com.bynder.sdk.service.AssetBankService;
import com.bynder.sdk.service.BynderService;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Implementation of {@link BynderService}.
 */
public class BynderServiceImpl implements BynderService {

    /**
     * Base URL needed to instantiate the {@link BynderApi} interface and generate authorise URL.
     */
    private final URL baseUrl;
    /**
     * Credentials used to login, instantiate the {@link BynderApi} interface and generate authorise
     * URL.
     */
    private Credentials credentials;
    /**
     * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
     */
    private BynderApi bynderApi;
    /**
     * Instance of the asset bank service.
     */
    private AssetBankService assetBankService;

    /**
     * Initialises a new instance of the class.
     *
     * @param baseUrl Base URL where we want to point the API calls.
     * @param credentials Credentials to use to call the API.
     */
    private BynderServiceImpl(final URL baseUrl, final Credentials credentials) {
        this.baseUrl = baseUrl;
        this.credentials = credentials;

        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials);
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
    public Observable<User> login(final String username, final String password) throws IllegalArgumentException, IllegalAccessException {
        return login(new LoginQuery(username, password));
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public Observable<String> getRequestToken() {
        Observable<Response<String>> requestTokenObservable = bynderApi.getRequestToken();

        return requestTokenObservable.map(new Function<Response<String>, String>() {
            @Override
            public String apply(final Response<String> response) throws Exception {
                String requestToken = response.body();
                updateTokensFromResponse(requestToken);
                return requestToken;
            }
        });
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public String getAuthoriseUrl(final String callbackUrl) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl.toString()).append("/api/v4/oauth/authorise/?oauth_token=").append(credentials.getToken());

        if (StringUtils.isNotEmpty(callbackUrl)) {
            stringBuilder.append("&callback=").append(callbackUrl);
        }

        return stringBuilder.toString();
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public Observable<String> getAccessToken() {
        Observable<Response<String>> accessTokenObservable = bynderApi.getAccessToken();

        return accessTokenObservable.map(new Function<Response<String>, String>() {
            @Override
            public String apply(final Response<String> response) throws Exception {
                String accessToken = response.body();
                updateTokensFromResponse(accessToken);
                return accessToken;
            }
        });
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public void logout() {
        this.credentials.reset();
    }

    /**
     * Check {@link BynderService} for more information.
     */
    @Override
    public AssetBankService getAssetBankService() {
        if (assetBankService == null) {
            assetBankService = new AssetBankServiceImpl(bynderApi);
        }

        return assetBankService;
    }

    /**
     * Helper method update the credentials and the {@link BynderApi} instance.
     *
     * @param response Response string.
     */
    private void updateTokensFromResponse(final String response) {
        Map<String, String> oauthTokens = Utils.buildMapFromResponse(response);
        credentials.set(oauthTokens.get("oauth_token"), oauthTokens.get("oauth_token_secret"));
        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials);
    }

    /**
     * Returns {@link User} containing user information. Sends the request to {@link BynderApi}.
     *
     * @param loginQuery Information to call the login API endpoint.
     *
     * @return Observable with {@link User} information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private Observable<User> login(final LoginQuery loginQuery) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> params = Utils.getApiParameters(loginQuery);
        Observable<Response<User>> loginObservable = bynderApi.login(params);

        return loginObservable.map(new Function<Response<User>, User>() {
            @Override
            public User apply(final Response<User> response) throws Exception {
                User user = response.body();
                credentials.set(user.getTokenKey(), user.getTokenSecret());
                bynderApi = Utils.createApiService(BynderApi.class, baseUrl, credentials);
                return user;
            }
        });
    }
}
