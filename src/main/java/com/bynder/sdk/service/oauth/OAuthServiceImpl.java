/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.oauth;

import com.bynder.sdk.api.OAuthApi;
import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.oauth.ResponseType;
import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.oauth.AccessTokenQuery;
import com.bynder.sdk.query.oauth.ClientCredentialsQuery;
import com.bynder.sdk.query.oauth.RefreshTokenQuery;
import com.bynder.sdk.util.RXUtils;
import com.bynder.sdk.util.Utils;
import io.reactivex.Single;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class OAuthServiceImpl implements OAuthService {

    /**
     * Instance of {@link OAuthApi} which handles the HTTP communication with the OAuth2
     * provider.
     */
    private final OAuthApi oauthClient;
    /**
     * Instance of {@link QueryDecoder} to decode query objects into API parameters.
     */
    private final QueryDecoder queryDecoder;

    private final URL baseUrl;
    private final OAuthSettings oAuthSettings;

    private Token token;

    /**
     * Initialises a new instance of the class.
     *
     * @param configuration Configuration settings.
     * @param oauthClient OAuth2 client instance.
     */
    public OAuthServiceImpl(
            final Configuration configuration,
            final OAuthApi oauthClient,
            final QueryDecoder queryDecoder
    ) {
        this.baseUrl = configuration.getBaseUrl();
        this.oAuthSettings = configuration.getOAuthSettings();
        this.oauthClient = oauthClient;
        this.queryDecoder = queryDecoder;
    }

    /**
     * Check {@link OAuthService} for more information.
     */
    @Override
    public URL getAuthorizationUrl(final String state)
            throws MalformedURLException, UnsupportedEncodingException, IllegalArgumentException {
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException(state);
        }

        return new URL(
                baseUrl +
                "/v6/authentication/oauth2/auth" +
                "?client_id=" + Utils.encodeParameterValue(oAuthSettings.getClientId()) +
                "&redirect_uri=" + Utils.encodeParameterValue(oAuthSettings.getRedirectUri().toString()) +
                "&response_type=" + Utils.encodeParameterValue(ResponseType.CODE.toString()) +
                "&scope=" + Utils.encodeParameterValue(String.join(" ", oAuthSettings.getScopes())) +
                "&state=" + Utils.encodeParameterValue(state)
        );
    }

    public Token getToken() {
        return token;
    }

    private Token setToken(Token token) {
        token.setAccessTokenExpiration();
        this.token = token;
        return token;
    }

    /**
     * Check {@link OAuthService} for more information.
     */
    @Override
    public Single<Token> getAccessToken(final String code) {
        return RXUtils.handleResponseBody(oauthClient.getAccessToken(queryDecoder.decode(
                new AccessTokenQuery(oAuthSettings, code)
        ))).map(this::setToken);
    }

    /**
     * Check {@link OAuthService} for more information.
     */
    @Override
    public Single<Token> getClientCredentials() {
        return RXUtils.handleResponseBody(oauthClient.getAccessToken(queryDecoder.decode(
                new ClientCredentialsQuery(oAuthSettings)
        ))).map(this::setToken);
    }

    /**
     * Check {@link OAuthService} for more information.
     */
    @Override
    public Single<Token> refreshAccessToken() {
        String refreshToken = token.getRefreshToken();
        if (refreshToken != null) {
            return RXUtils.handleResponseBody(oauthClient.getAccessToken(queryDecoder.decode(
                    new RefreshTokenQuery(oAuthSettings, refreshToken)
            ))).map(newToken -> setToken(newToken).setRefreshToken(refreshToken));
        } else {
            return getClientCredentials();
        }
    }

}
