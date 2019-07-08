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
package com.bynder.sdk.query.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.bynder.sdk.model.oauth.GrantType;
import com.bynder.sdk.model.oauth.Scope;

import java.net.URI;
import org.junit.Test;

/**
 * Tests the {@link TokenQuery} class methods.
 */
public class TokenQueryTest {

    public static final String EXPECTED_CLIENT_ID = "clientId";
    public static final String EXPECTED_CLIENT_SECRET = "clientSecret";
    public static final String EXPECTED_REDIRECT_URI = "https://redirecturi.bynder.com";
    public static final GrantType EXPECTED_GRANT_TYPE = GrantType.AUTHORIZATION_CODE;
    public static final String EXPECTED_SCOPE = Scope.OPEN_ID.toString();
    public static final String EXPECTED_CODE = "code";
    public static final String EXPECTED_REFRESH_TOKEN = "refreshToken";

    @Test
    public void initializeTokenQueryWithoutRefreshToken() throws Exception {
        TokenQuery tokenQuery = new TokenQuery(EXPECTED_CLIENT_ID, EXPECTED_CLIENT_SECRET,
            new URI(EXPECTED_REDIRECT_URI), EXPECTED_GRANT_TYPE, EXPECTED_SCOPE, EXPECTED_CODE);

        assertEquals(EXPECTED_CLIENT_ID, tokenQuery.getClientId());
        assertEquals(EXPECTED_CLIENT_SECRET, tokenQuery.getClientSecret());
        assertEquals(EXPECTED_REDIRECT_URI, tokenQuery.getRedirectUri().toString());
        assertEquals(EXPECTED_GRANT_TYPE, tokenQuery.getGrantType());
        assertEquals(EXPECTED_CODE, tokenQuery.getCode());
        assertNull(tokenQuery.getRefreshToken());
    }

    @Test
    public void initializeTokenQueryWithoutCode() throws Exception {
        TokenQuery tokenQuery = new TokenQuery(EXPECTED_CLIENT_ID, EXPECTED_CLIENT_SECRET,
            EXPECTED_GRANT_TYPE, EXPECTED_REFRESH_TOKEN);

        assertEquals(EXPECTED_CLIENT_ID, tokenQuery.getClientId());
        assertEquals(EXPECTED_CLIENT_SECRET, tokenQuery.getClientSecret());
        assertEquals(EXPECTED_GRANT_TYPE, tokenQuery.getGrantType());
        assertEquals(EXPECTED_REFRESH_TOKEN, tokenQuery.getRefreshToken());
        assertNull(tokenQuery.getRedirectUri());
        assertNull(tokenQuery.getCode());
    }
}
