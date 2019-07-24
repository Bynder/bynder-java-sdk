/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.bynder.sdk.model.AccountInformation;
import com.bynder.sdk.model.Derivative;
import com.bynder.sdk.model.Settings;
import com.bynder.sdk.model.User;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Interface to login to Bynder and to get instance of {@link AssetBankService}.
 */
public interface BynderService {

  /**
   * Login using API. To be able to use this method we need to provide an request token key/secret
   * with login permissions in {@link Settings}.
   *
   * @param username Username/email.
   * @param password Password.
   * @return Observable with {@link User} information.
   * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
   *         information.
   */
  @Deprecated
  Observable<User> login(String username, String password) throws IllegalAccessException;

  /**
   * Gets temporary request token pair used to build the authorise URL and login through the browser.
   *
   * @return Observable with the request token.
   */
  Observable<String> getRequestToken();

  /**
   * Gets the URL needed to open the browser so the user can login and authorise the temporary request
   * token pair.
   *
   * @param callbackUrl Callback URL to be redirected to when login is successful.
   * @return Authorise URL we need to open the browser with in order to login.
   * @throws MalformedURLException If no protocol is specified, or an unknown protocol is found, or
   *         spec is null while instantiating the URL.
   */
  URL getAuthoriseUrl(final String callbackUrl) throws MalformedURLException;

  /**
   * Gets temporary access token pair once the user has already accessed the authorise URL and logged
   * in through the browser.
   *
   * @return Observable with the access token.
   */
  Observable<String> getAccessToken();

  /**
   * Logout resets your credentials. If the access token key/secret provided in the {@link Settings}
   * have full permission, even after this call, calls to any API endpoint will still work.
   */
  void logout();

  /**
   * Gets the information for the current account.
   *
   * @return Observable with {@link AccountInformation}.
   */
  Observable<Response<AccountInformation>> getAccountInformation();

  /**
   * Gets the list of the derivatives configured for the current account.
   *
   * @return Observable with the list of {@link Derivative}.
   */
  Observable<Response<List<Derivative>>> getDerivatives();

  /**
   * Gets an instance of the asset bank service to perform Bynder Asset Bank operations.
   *
   * @return Instance of {@link AssetBankService}.
   */
  AssetBankService getAssetBankService();

  /**
   * Gets an instance of the collection service to perform Bynder Collections related operations.
   *
   * @return Instance of {@link CollectionService}.
   */
  CollectionService getCollectionService();

  /**
   * Gets an instance of the collection service to perform Bynder Users related operations.
   *
   * @return Instance of {@link UserManagementService}.
   */
  UserManagementService getUserManagementService();

  /**
   * Gets an instance of the collection service to perform Bynder Security Profiles related
   * operations.
   *
   * @return Instance of {@link SecurityProfileService}.
   */
  SecurityProfileService getSecurityProfileService();
}
