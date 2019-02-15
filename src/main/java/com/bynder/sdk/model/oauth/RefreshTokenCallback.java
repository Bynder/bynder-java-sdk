package com.bynder.sdk.model.oauth;

/**
 * Interface of the refresh token callback method.
 */
public interface RefreshTokenCallback {

    void execute(final Token token);
}
