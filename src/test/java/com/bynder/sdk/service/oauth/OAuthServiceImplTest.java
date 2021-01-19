/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 *
 * JUnit framework component copyright (c) 2002-2017 JUnit. All Rights Reserved. Licensed under
 * Eclipse Public License - v 1.0. You may obtain a copy of the License at
 * https://www.eclipse.org/legal/epl-v10.html.
 */
package com.bynder.sdk.service.oauth;

import com.bynder.sdk.api.OAuthApi;
import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.oauth.AccessTokenQuery;
import com.bynder.sdk.query.oauth.ClientCredentialsQuery;
import com.bynder.sdk.query.oauth.RefreshTokenQuery;
import io.reactivex.Single;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import retrofit2.Response;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests the {@link OAuthServiceImpl} class methods.
 */
@RunWith(MockitoJUnitRunner.class)
public class OAuthServiceImplTest {

    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_SECRET = "clientSecret";
    private static final String BASE_URL = "https://example.bynder.com";
    private static final String REDIRECT_URI = "https://localhost/callback";
    private static final String STATE = "this is the state";
    private static final List<String> SCOPES = Arrays.asList("scope1", "scope2", "scope3");
    private static final String CODE = "code";
    private static final String REFRESH_TOKEN_STRING = "refresh_token";

    @Mock
    private OAuthApi oauthClient;
    @Mock
    private Token accessToken;
    @Mock
    private Token clientCredentialsToken;
    @Mock
    private Token refreshToken;
    @Mock
    private Map<String, String> accessTokenParams;
    @Mock
    private Map<String, String> clientCredentialsParams;
    @Mock
    private Map<String, String> refreshTokenParams;
    @Mock
    private QueryDecoder queryDecoder;

    private OAuthService oAuthService;

    @Before
    public void setUp() throws Exception {
        oAuthService = new OAuthServiceImpl(
                new Configuration.Builder(
                        BASE_URL,
                        new OAuthSettings.Builder(CLIENT_ID, CLIENT_SECRET)
                                .setRedirectUri(REDIRECT_URI)
                                .setScopes(SCOPES)
                                .build()
                ).build(),
                oauthClient,
                queryDecoder
        );

        when(queryDecoder.decode(any(AccessTokenQuery.class)))
                .thenReturn(accessTokenParams);
        when(queryDecoder.decode(any(ClientCredentialsQuery.class)))
                .thenReturn(clientCredentialsParams);
        when(queryDecoder.decode(any(RefreshTokenQuery.class)))
                .thenReturn(refreshTokenParams);

        when(oauthClient.getAccessToken(accessTokenParams))
                .thenReturn(Single.just(Response.success(accessToken)));
        when(oauthClient.getAccessToken(clientCredentialsParams))
                .thenReturn(Single.just(Response.success(clientCredentialsToken)));
        when(oauthClient.getAccessToken(refreshTokenParams))
                .thenReturn(Single.just(Response.success(refreshToken)));

        when(accessToken.getRefreshToken())
                .thenReturn(REFRESH_TOKEN_STRING);
        when(clientCredentialsToken.getRefreshToken())
                .thenReturn(REFRESH_TOKEN_STRING);
        when(refreshToken.setRefreshToken(anyString()))
                .thenReturn(refreshToken);
    }

    @Test
    public void getAuthorizationUrl() throws Exception {
        URL authorizationUrl = oAuthService.getAuthorizationUrl(STATE);
        assertNotNull(authorizationUrl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAuthorizationUrlWithNullState() throws Exception {
        oAuthService.getAuthorizationUrl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAuthorizationUrlWithEmptyState() throws Exception {
        oAuthService.getAuthorizationUrl("");
    }

    @Test
    public void getAccessToken() {
        Token actualAccessToken = oAuthService.getAccessToken(CODE).blockingGet();

        assertEquals(accessToken, actualAccessToken);
        verify(oauthClient).getAccessToken(accessTokenParams);

        Token actualRefreshToken = oAuthService.refreshAccessToken().blockingGet();

        assertEquals(refreshToken, actualRefreshToken);
        verify(oauthClient).getAccessToken(refreshTokenParams);
    }

    @Test
    public void getClientCredentials() {
        Token actualClientCredentialsToken = oAuthService.getClientCredentials().blockingGet();

        assertEquals(clientCredentialsToken, actualClientCredentialsToken);
        verify(oauthClient).getAccessToken(clientCredentialsParams);

        Token actualRefreshToken = oAuthService.refreshAccessToken().blockingGet();

        assertEquals(refreshToken, actualRefreshToken);
        verify(oauthClient).getAccessToken(refreshTokenParams);
    }

}
