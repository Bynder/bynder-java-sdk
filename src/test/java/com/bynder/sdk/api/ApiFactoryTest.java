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
package com.bynder.sdk.api;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.service.BynderClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the {@link ApiFactory} class methods.
 */
public class ApiFactoryTest {

    public static String BASE_URL = "https://example.bynder.com";

    @Mock
    private Configuration configuration;
    @Mock
    private BynderClient bynderClient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(configuration.getBaseUrl()).thenReturn(new URL(BASE_URL));
        Mockito.when(configuration.getHttpConnectionSettings())
            .thenReturn(new HttpConnectionSettings());
    }

    @Test
    public void createBynderClient() {
        BynderApi bynderApi = ApiFactory.createBynderApi(configuration, bynderClient);
        assertNotNull(bynderApi);
    }

    @Test
    public void createOAuthClient() {
        OAuthApi oAuthApi = ApiFactory.createOAuthApi(configuration);
        assertNotNull(oAuthApi);
    }
}
