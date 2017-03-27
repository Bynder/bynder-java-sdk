/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.util.Map;

import com.bynder.sdk.model.Settings;
import com.bynder.sdk.model.User;

/**
 * Interface to login to Bynder and to get instance of {@link AssetBankManager}.
 */
public interface BynderService {

    /**
     * Login using API. To be able to use this method we need to provide an request token key/secret
     * with login permissions in {@link Settings}.
     *
     * @param username Username/email.
     * @param password Password.
     *
     * @return {@link User} information.
     */
    User login(String username, String password);

    /**
     * Gets temporary request token pair used to build the authorise URL and login through the
     * browser.
     *
     * @return Map containing the request token key/secret pair.
     */
    Map<String, String> getRequestToken();

    /**
     * Gets the URL needed to open the browser so the user can login and authorize the temporary
     * request token pair.
     *
     * @param callbackUrl Callback URL to be redirected to when login is successful.
     *
     * @return String with the authorise URL we need to open the browser with in order to login.
     */
    String getAuthoriseUrl(final String callbackUrl);

    /**
     * Gets temporary access token pair once the user has already accessed the authorise URL and
     * logged in through the browser.
     *
     * @return Map containing the access token key/secret pair.
     */
    Map<String, String> getAccessToken();

    /**
     * Logout resets your credentials. If the access token key/secret provided in the
     * {@link Settings} have full permission, even after this call, calls to any API endpoint will
     * still work.
     */
    void logout();

    /**
     * Gets an instance of the asset bank manager to perform Bynder Asset Bank operations.
     *
     * @return Instance of {@link AssetBankManager}.
     */
    AssetBankManager getAssetBankManager();
}
