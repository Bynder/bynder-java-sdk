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
import com.bynder.sdk.util.Utils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Response;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

/**
 * Tests the {@link OAuthServiceImpl} class methods.
 */
public class OAuthServiceImplTest {

    private final String EXPECTED_CLIENT_ID = "clientId";
    private final String EXPECTED_CLIENT_SECRET = "clientSecret";
    private final String EXPECTED_BASE_URL = "https://example.bynder.com";
    private final String EXPECTED_REDIRECT_URI = "https://localhost/callback";
    private final String EXPECTED_STATE = "this is the state";
    private final List<String> EXPECTED_SCOPES = Arrays.asList("scope");
    private final String EMPTY_STRING = "";

    @Mock
    private OAuthApi oauthClient;
    @Mock
    private Token token;
    @Mock
    private QueryDecoder queryDecoder;
    @Mock
    private Configuration configuration;
    @Mock
    private OAuthSettings oAuthSettings;

    private OAuthService oAuthService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(oauthClient.getAccessToken(anyMap())).thenReturn(Observable.just(Response.success(token)));
        when(configuration.getBaseUrl()).thenReturn(new URL(EXPECTED_BASE_URL));
        when(configuration.getOAuthSettings()).thenReturn(oAuthSettings);
        when(configuration.getOAuthSettings().getClientId()).thenReturn(EXPECTED_CLIENT_ID);
        when(configuration.getOAuthSettings().getRedirectUri()).thenReturn(new URI(EXPECTED_REDIRECT_URI));
        when(configuration.getOAuthSettings().getClientSecret()).thenReturn(EXPECTED_CLIENT_SECRET);
        when(configuration.getOAuthSettings().getToken()).thenReturn(token);
        oAuthService = OAuthService.Builder.create(configuration, oauthClient, queryDecoder);
    }

    @Test
    public void getAuthorizationUrl() throws Exception {
        URL authorizationUrl = oAuthService.getAuthorizationUrl(EXPECTED_STATE, EXPECTED_SCOPES);
        assertNotNull(authorizationUrl);
        assertEquals(new URL(EXPECTED_BASE_URL).getHost(), authorizationUrl.getHost());
        assertTrue(authorizationUrl.toString().contains(EXPECTED_CLIENT_ID));
        assertTrue(authorizationUrl.toString().contains(Utils.encodeParameterValue(EXPECTED_REDIRECT_URI)));
        assertTrue(authorizationUrl.toString().contains(Utils.encodeParameterValue(EXPECTED_STATE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAuthorizationUrlWithNullState() throws Exception {
        oAuthService.getAuthorizationUrl(null, EXPECTED_SCOPES);
    }

    @Test
    public void getAccessToken() {
        oAuthService.getAccessToken(EMPTY_STRING, EXPECTED_SCOPES);

        verify(oauthClient, times(1)).getAccessToken(anyMap());
        verify(queryDecoder, times(1)).decode(any());
    }

    @Test
    public void getRefreshToken() {
        oAuthService.refreshAccessToken();

        verify(oauthClient, times(1)).getAccessToken(anyMap());
        verify(queryDecoder, times(1)).decode(any());
    }
}
