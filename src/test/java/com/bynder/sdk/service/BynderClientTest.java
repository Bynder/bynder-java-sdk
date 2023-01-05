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
package com.bynder.sdk.service;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the {@link BynderClient} class methods.
 */
public class BynderClientTest {

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
    public void buildAndGetServicesBynderClient() {
        BynderClient bynderClient = BynderClient.Builder.create(configuration);

        assertNotNull(bynderClient);
        assertNotNull(bynderClient.getOAuthService());
        assertNotNull(bynderClient.getAssetService());
        assertNotNull(bynderClient.getCollectionService());
        assertNotNull(bynderClient.getWorkflowService());
    }
}
