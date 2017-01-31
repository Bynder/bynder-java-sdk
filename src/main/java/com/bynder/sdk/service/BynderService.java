/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import com.bynder.sdk.model.User;

public interface BynderService {

    BynderServiceCall<User> login(String username, String password);

    BynderServiceCall<String> getRequestToken();

    BynderServiceCall<String> getAccessToken(final String requestToken, final String requestTokenSecret);

    String getAuthoriseUrl(final String requestToken, final String callbackUrl);

    AssetBankManager getAssetBankManager();
}
