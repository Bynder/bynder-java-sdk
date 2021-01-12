/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.configuration;

import com.bynder.sdk.model.oauth.Token;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

public class OAuthSettings {

    /**
     * OAuth application client id.
     */
    private final String clientId;

    /**
     * OAuth application client secret.
     */
    private final String clientSecret;

    /**
     * URI to redirect to after application has been authorized.
     */
    private final URI redirectUri;

    /**
     * Optional callback method to be triggered when token is refreshed.
     */
    private final Consumer<Token> refreshTokenCallback;

    public OAuthSettings(
            final String clientId,
            final String clientSecret
    ) {
        this(clientId, clientSecret, token -> {});
    }

    public OAuthSettings(
            final String clientId,
            final String clientSecret,
            final Consumer<Token> refreshTokenCallback
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = null;
        this.refreshTokenCallback = refreshTokenCallback;
    }

    public OAuthSettings(
            final String clientId,
            final String clientSecret,
            final String redirectUri
    )
            throws URISyntaxException {
        this(clientId, clientSecret, redirectUri, token -> {});
    }

    public OAuthSettings(
            final String clientId,
            final String clientSecret,
            final String redirectUri,
            final Consumer<Token> refreshTokenCallback
    )
            throws URISyntaxException {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = new URI(redirectUri);
        this.refreshTokenCallback = refreshTokenCallback;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public URI getRedirectUri() {
        return redirectUri;
    }

    public void refreshTokenCallback(final Token token) {
        refreshTokenCallback.accept(token);
    }

}
