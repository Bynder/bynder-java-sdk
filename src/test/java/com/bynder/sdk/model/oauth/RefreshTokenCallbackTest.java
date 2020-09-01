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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link RefreshTokenCallback} class.
 */
public class RefreshTokenCallbackTest {

    public static String MOCK_STRING = "";
    public static int MOCK_INT = 0;

    private Token token;
    private RefreshTokenCallback callback;

    @Before
    public void setUp() {
        this.token = new Token(MOCK_STRING, MOCK_INT, MOCK_STRING, MOCK_STRING, MOCK_STRING);

        this.callback = new RefreshTokenCallback() {
            @Override
            public void execute(Token token) {
                token.setAccessTokenExpiration();
            }
        };
    }

    @Test
    public void executeRefreshTokenCallback() {
        assertNull(token.getAccessTokenExpiration());
        callback.execute(token);
        assertNotNull(token.getAccessTokenExpiration());
    }
}
