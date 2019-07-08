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

import static org.junit.Assert.assertNotNull;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Tests the {@link ApiFactory} class methods.
 */
public class ApiFactoryTest {

    public static String BASE_URL = "https://example.bynder.com";

    @Mock
    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(configuration.getBaseUrl()).thenReturn(new URL(BASE_URL));
        Mockito.when(configuration.getHttpConnectionSettings())
            .thenReturn(new HttpConnectionSettings());
    }

    @Test
    public void createBynderClient() {
        BynderApi bynderApi = ApiFactory.createBynderClient(configuration);
        assertNotNull(bynderApi);
    }

    @Test
    public void createAmazonS3Client() {
        AmazonS3Api amazonS3Api = ApiFactory.createAmazonS3Client(BASE_URL);
        assertNotNull(amazonS3Api);
    }

    @Test
    public void createOAuthClient() {
        OAuthApi oAuthApi = ApiFactory.createOAuthClient(BASE_URL);
        assertNotNull(oAuthApi);
    }
}
