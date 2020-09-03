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
package com.bynder.sdk.configuration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link Configuration} class methods.
 */
public class ConfigurationTest {

    public static final String EXPECTED_BASE_URL = "https://baseurl.bynder.com";
    public static final String EXPECTED_CLIENT_ID = "clientId";
    public static final String EXPECTED_CLIENT_SECRET = "clientSecret";
    public static final String EXPECTED_REDIRECT_URI = "https://redirecturi.bynder.com";

    @Mock
    private HttpConnectionSettings httpConnectionSettings;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildConfigurationWithoutCallback() throws Exception {
        Configuration configuration = new Configuration.Builder(new URL(EXPECTED_BASE_URL))
            .setOAuthSettings(new OAuthSettings(EXPECTED_CLIENT_ID, EXPECTED_CLIENT_SECRET,
            new URI(EXPECTED_REDIRECT_URI)))
            .setHttpConnectionSettings(httpConnectionSettings).build();

        assertEquals(EXPECTED_BASE_URL, configuration.getBaseUrl().toString());
        assertEquals(EXPECTED_CLIENT_ID, configuration.getOAuthSettings().getClientId());
        assertEquals(EXPECTED_CLIENT_SECRET, configuration.getOAuthSettings().getClientSecret());
        assertEquals(EXPECTED_REDIRECT_URI, configuration.getOAuthSettings().getRedirectUri().toString());
        assertEquals(httpConnectionSettings, configuration.getHttpConnectionSettings());
    }
}
