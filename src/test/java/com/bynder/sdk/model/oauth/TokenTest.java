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
package com.bynder.sdk.model.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link Token} class methods.
 */
public class TokenTest {

    public static final String EXPECTED_ACCESS_TOKEN = "accessToken";
    public static final int EXPECTED_EXPIRES_IN = 3600;
    public static final String EXPECTED_TOKEN_ID = "tokenId";
    public static final String EXPECTED_TOKEN_TYPE = "tokenType";
    public static final String EXPECTED_SCOPE = "scope";
    public static final String EXPECTED_REFRESH_TOKEN = "refreshToken";

    private Token token;

    @Before
    public void setUp() {
        token = new Token(EXPECTED_ACCESS_TOKEN, EXPECTED_EXPIRES_IN, EXPECTED_TOKEN_ID,
            EXPECTED_TOKEN_TYPE, EXPECTED_SCOPE, EXPECTED_REFRESH_TOKEN);
    }

    @Test
    public void definedInitializationValuesOfToken() {
        assertEquals(EXPECTED_ACCESS_TOKEN, token.getAccessToken());
        assertEquals(EXPECTED_EXPIRES_IN, token.getExpiresIn());
        assertEquals(EXPECTED_TOKEN_ID, token.getTokenId());
        assertEquals(EXPECTED_TOKEN_TYPE, token.getTokenType());
        assertEquals(EXPECTED_SCOPE, token.getScope());
        assertEquals(EXPECTED_REFRESH_TOKEN, token.getRefreshToken());
        assertNull(token.getAccessTokenExpiration());
    }

    @Test
    public void setAccessTokenExpirationDate() {
        token.setAccessTokenExpiration();
        Calendar currentDate = Calendar.getInstance();

        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(token.getAccessTokenExpiration());
        expirationDate.add(Calendar.SECOND, -EXPECTED_EXPIRES_IN);
        assertEquals(currentDate.YEAR, expirationDate.YEAR);
        assertEquals(currentDate.MONTH, expirationDate.MONTH);
        assertEquals(currentDate.DAY_OF_MONTH, expirationDate.DAY_OF_MONTH);
        assertEquals(currentDate.HOUR, expirationDate.HOUR);
        assertEquals(currentDate.MINUTE, expirationDate.MINUTE);
        assertEquals(currentDate.SECOND, expirationDate.SECOND);
    }
}
