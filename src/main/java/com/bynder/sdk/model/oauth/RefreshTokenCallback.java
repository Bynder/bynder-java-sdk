/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.oauth;

/**
 * Interface of the refresh token callback method.
 */
public interface RefreshTokenCallback {

    void execute(final Token token);
}
