package com.bynder.sdk.util;

import com.bynder.sdk.model.oauth.Token;

/**
 * Interface of the refresh token callback method.
 */
public interface RefreshTokenCallback {

    void execute(final Token token);
}
