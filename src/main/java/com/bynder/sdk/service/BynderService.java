/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import com.bynder.sdk.model.User;

public interface BynderService {

    User login(String username, String password);

    void getRequestToken();

    void getAccessToken();

    String getAuthoriseUrl(final String callbackUrl);

    void logout();

    AssetBankManager getAssetBankManager();
}
