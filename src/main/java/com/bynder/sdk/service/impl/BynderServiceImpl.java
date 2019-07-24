/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Credentials;
import com.bynder.sdk.model.Derivative;
import com.bynder.sdk.model.HttpConnectionSettings;
import com.bynder.sdk.model.Settings;
import com.bynder.sdk.model.User;
import com.bynder.sdk.query.LoginQuery;
import com.bynder.sdk.service.AssetBankService;
import com.bynder.sdk.service.BynderService;
import com.bynder.sdk.service.CollectionService;
import com.bynder.sdk.service.UserManagementService;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
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
  private final Credentials credentials;
  /**
   * Settings for the HTTP connection to Bynder.
   */
  private final HttpConnectionSettings httpConnectionSettings;
  /**
   * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
   */
  private BynderApi bynderApi;
  /**
   * Instance of the asset bank service.
   */
  private AssetBankService assetBankService;
  /**
   * Instance of the collection service.
   */
  private CollectionService collectionService;
  /**
   * Instance of the user management service.
   */
  private UserManagementService userManagementService;

  /**
   * Initialises a new instance of the class.
   *
   * @param baseUrl Base URL where we want to point the API calls.
   * @param credentials Credentials to use to call the API.
   * @param httpConnectionSettings Settings for the http connection to Bynder
   */
  private BynderServiceImpl(final URL baseUrl, final Credentials credentials,
      final HttpConnectionSettings httpConnectionSettings) {
    this.baseUrl = baseUrl;
    this.credentials = credentials;
    this.httpConnectionSettings = httpConnectionSettings;
    bynderApi = Utils
        .createApiService(BynderApi.class, baseUrl, credentials, httpConnectionSettings);
  }

  /**
   * Creates an instance of {@link BynderService} using {@link Settings} as parameter.
   *
   * @param settings Settings to correctly configure the {@link BynderService} instance.
   * @return {@link BynderService} instance to communicate with Bynder API.
   */
  public static BynderService create(final Settings settings) {
    Credentials credentials = new Credentials(settings.getConsumerKey(),
        settings.getConsumerSecret(), settings.getToken(), settings.getTokenSecret());
    return new BynderServiceImpl(settings.getBaseUrl(), credentials,
        settings.getHttpConnectionSettings());
  }

  /**
   * Check {@link BynderService} for more information.
   */
  @Override
  public Observable<User> login(final String username, final String password)
      throws IllegalAccessException {
    return login(new LoginQuery(username, password));
  }

  /**
   * Check {@link BynderService} for more information.
   */
  @Override
  public Observable<String> getRequestToken() {
    Observable<Response<String>> requestTokenObservable = bynderApi.getRequestToken();

    return requestTokenObservable.map(response -> {
      String requestToken = response.body();
      updateTokensFromResponse(requestToken);
      return requestToken;
    });
  }

  /**
   * Check {@link BynderService} for more information.
   */
  @Override
  public URL getAuthoriseUrl(final String callbackUrl) throws MalformedURLException {
    StringBuilder stringBuilder = new StringBuilder("/api/v4/oauth/authorise/?oauth_token=")
        .append(credentials.getToken());

    if (StringUtils.isNotEmpty(callbackUrl)) {
      stringBuilder.append("&callback=").append(callbackUrl);
    }

    URL authoriseUrl = new URL(baseUrl, stringBuilder.toString());
    return authoriseUrl;
  }

  /**
   * Check {@link BynderService} for more information.
   */
  @Override
  public Observable<String> getAccessToken() {
    Observable<Response<String>> accessTokenObservable = bynderApi.getAccessToken();

    return accessTokenObservable.map(response -> {
      String accessToken = response.body();
      updateTokensFromResponse(accessToken);
      return accessToken;
    });
  }

  /**
   * Check {@link BynderService} for more information.
   */
  @Override
  public void logout() {
    this.credentials.reset();
  }

  @Override
  public Observable<Response<List<Derivative>>> getDerivatives() {
    return bynderApi.getDerivatives();
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
   * Check {@link BynderService} for more information.
   */
  @Override
  public CollectionService getCollectionService() {
    if (collectionService == null) {
      collectionService = new CollectionServiceImpl(bynderApi);
    }

    return collectionService;
  }

  /**
   * Check {@link BynderService} for more information.
   */
  @Override
  public UserManagementService getUserManagementService() {
    if (userManagementService == null) {
      userManagementService = new UserManagementServiceImpl(bynderApi);
    }

    return userManagementService;
  }

  /**
   * Helper method update the credentials and the {@link BynderApi} instance.
   *
   * @param response Response string.
   */
  private void updateTokensFromResponse(final String response) {
    Map<String, String> oauthTokens = Utils.buildMapFromResponse(response);
    credentials.set(oauthTokens.get("oauth_token"), oauthTokens.get("oauth_token_secret"));
    bynderApi = Utils
        .createApiService(BynderApi.class, baseUrl, credentials, httpConnectionSettings);
  }

  /**
   * Returns {@link User} containing user information. Sends the request to {@link BynderApi}.
   *
   * @param loginQuery Information to call the login API endpoint.
   * @return Observable with {@link User} information.
   */
  private Observable<User> login(final LoginQuery loginQuery) throws IllegalAccessException {
    Map<String, String> params = Utils.getApiParameters(loginQuery);
    Observable<Response<User>> loginObservable = bynderApi.login(params);

    return loginObservable.map(response -> {
      User user = response.body();
      credentials.set(user.getTokenKey(), user.getTokenSecret());
      bynderApi = Utils
          .createApiService(BynderApi.class, baseUrl, credentials, httpConnectionSettings);
      return user;
    });
  }
}
