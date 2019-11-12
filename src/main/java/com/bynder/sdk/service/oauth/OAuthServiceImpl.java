/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.oauth;

import com.bynder.sdk.api.OAuthApi;
import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.model.oauth.ResponseType;
import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.oauth.TokenQuery;
import com.bynder.sdk.util.Utils;
import io.reactivex.Observable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import retrofit2.Response;

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
    /**
     * Configuration settings needed to get the OAuth info of the SDK client.
     */
    private Configuration configuration;

    /**
     * Initialises a new instance of the class.
     *
     * @param configuration Configuration settings.
     * @param oauthClient OAuth2 client instance.
     */
    OAuthServiceImpl(final Configuration configuration, final OAuthApi oauthClient,
        final QueryDecoder queryDecoder) {
        this.configuration = configuration;
        this.oauthClient = oauthClient;
        this.queryDecoder = queryDecoder;
    }

    /**
     * Check {@link OAuthService} for more information.
     */
    @Override
    public URL getAuthorizationUrl(final String state, final List<String> scopes)
        throws MalformedURLException, UnsupportedEncodingException, IllegalArgumentException {
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException(state);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(configuration.getBaseUrl());
        stringBuilder.append("/v6/authentication/oauth2/auth");
        stringBuilder.append("?client_id=")
            .append(Utils.encodeParameterValue(configuration.getOAuthSettings().getClientId()));
        stringBuilder.append("&redirect_uri=")
            .append(Utils.encodeParameterValue(configuration.getOAuthSettings().getRedirectUri().toString()));
        stringBuilder.append("&response_type=")
            .append(Utils.encodeParameterValue(ResponseType.CODE.toString()));
        stringBuilder.append("&scope=")
            .append(Utils.encodeParameterValue(String.join(" ", scopes)));
        stringBuilder.append("&state=").append(Utils.encodeParameterValue(state));

        return new URL(stringBuilder.toString());
    }

    /**
     * Check {@link OAuthService} for more information.
     */
    @Override
    public Observable<Token> getAccessToken(final String code, final List<String> scopes) {
        TokenQuery tokenQuery = new TokenQuery(configuration.getOAuthSettings().getClientId(),
            configuration.getOAuthSettings().getClientSecret(), configuration.getOAuthSettings().getRedirectUri(),
            GrantType.AUTHORIZATION_CODE, String.join(" ", scopes), code);

        Map<String, String> params = queryDecoder.decode(tokenQuery);
        Observable<Response<Token>> accessTokenObservable = oauthClient.getAccessToken(params);

        return accessTokenObservable.map(response -> {
            Token token = response.body();
            token.setAccessTokenExpiration();
            configuration.getOAuthSettings().setToken(token);
            return token;
        });
    }

    /**
     * Check {@link OAuthService} for more information.
     */
    @Override
    public Observable<Token> refreshAccessToken() {
        TokenQuery tokenQuery = new TokenQuery(configuration.getOAuthSettings().getClientId(),
            configuration.getOAuthSettings().getClientSecret(), GrantType.REFRESH_TOKEN,
            configuration.getOAuthSettings().getToken().getRefreshToken());

        Map<String, String> params = queryDecoder.decode(tokenQuery);
        Observable<Response<Token>> refreshTokenObservable = oauthClient.getAccessToken(params);

        return refreshTokenObservable.map(response -> {
            Token token = response.body();
            token.setAccessTokenExpiration();
            configuration.getOAuthSettings().refreshToken(token);
            return token;
        });
    }
}