/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.oauth;

import com.bynder.sdk.api.OAuthApi;
import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.query.decoder.QueryDecoder;
import io.reactivex.Observable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * OAuth2 service interface.
 */
public interface OAuthService {

    /**
     * Gets the authorization URL needed to open a web view so the user can login and be
     * authorised with Bynder.
     *
     * @param state A random string used to maintain state between the request and callback (used
     * to protect against cross-site request forgery attacks).
     */
    URL getAuthorizationUrl(final String state)
        throws MalformedURLException, UnsupportedEncodingException, IllegalArgumentException;

    /**
     * Gets an access token using the code authorization grant.
     *
     * @param code The code included in the redirect URI.
     * @return {@link Observable} with {@link Token} information.
     */
    Observable<Token> getAccessToken(final String code);

    /**
     * Gets a new access token using the refresh token.
     *
     * @return {@link Observable} with {@link Token} information.
     */
    Observable<Token> refreshAccessToken();

    /**
     * Builder class used to create a new instance of {@link OAuthService}.
     */
    class Builder {

        private Builder() {
        }

        public static OAuthService create(final Configuration configuration, OAuthApi oauthClient,
            final QueryDecoder queryDecoder) {
            return new OAuthServiceImpl(configuration, oauthClient, queryDecoder);
        }
    }
}
