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

import okhttp3.Interceptor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link HttpConnectionSettings} class methods.
 */
public class HttpConnectionSettingsTest {

    public static final int EXPECTED_TIMEOUT_SECONDS = 30;
    public static final boolean EXPECTED_RETRY_ON_CONNECTION_FAILURE = true;
    public static final boolean EXPECTED_LOGGING_INTERCEPTOR_ENABLED = false;

    @Mock
    private SSLContext sslContext;
    @Mock
    private X509TrustManager x509TrustManager;
    @Mock
    private Interceptor interceptor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void initializeHttpConnectionSettingsWithoutArguments() {
        HttpConnectionSettings httpConnectionSettings = new HttpConnectionSettings();

        assertEquals(EXPECTED_TIMEOUT_SECONDS, httpConnectionSettings.getReadTimeoutSeconds());
        assertEquals(EXPECTED_TIMEOUT_SECONDS, httpConnectionSettings.getConnectTimeoutSeconds());
        assertEquals(EXPECTED_RETRY_ON_CONNECTION_FAILURE,
            httpConnectionSettings.isRetryOnConnectionFailure());
        assertEquals(EXPECTED_LOGGING_INTERCEPTOR_ENABLED,
            httpConnectionSettings.isLoggingInterceptorEnabled());
        assertNull(httpConnectionSettings.getSslContext());
        assertNull(httpConnectionSettings.getTrustManager());
        assertNull(httpConnectionSettings.getCustomInterceptor());
    }

    @Test
    public void initializeHttpConnectionSettingsWithoutRetryOnConnectionFailure() {
        HttpConnectionSettings httpConnectionSettings = new HttpConnectionSettings(sslContext,
            x509TrustManager, interceptor, EXPECTED_TIMEOUT_SECONDS, EXPECTED_TIMEOUT_SECONDS,
            EXPECTED_RETRY_ON_CONNECTION_FAILURE);

        assertEquals(sslContext, httpConnectionSettings.getSslContext());
        assertEquals(x509TrustManager, httpConnectionSettings.getTrustManager());
        assertEquals(interceptor, httpConnectionSettings.getCustomInterceptor());
        assertEquals(EXPECTED_TIMEOUT_SECONDS, httpConnectionSettings.getReadTimeoutSeconds());
        assertEquals(EXPECTED_TIMEOUT_SECONDS, httpConnectionSettings.getConnectTimeoutSeconds());
        assertEquals(EXPECTED_RETRY_ON_CONNECTION_FAILURE,
            httpConnectionSettings.isRetryOnConnectionFailure());
        assertEquals(EXPECTED_LOGGING_INTERCEPTOR_ENABLED,
            httpConnectionSettings.isLoggingInterceptorEnabled());
    }

    @Test
    public void initializeHttpConnectionSettings() {
        HttpConnectionSettings httpConnectionSettings = new HttpConnectionSettings(sslContext,
            x509TrustManager, interceptor, EXPECTED_TIMEOUT_SECONDS, EXPECTED_TIMEOUT_SECONDS,
            EXPECTED_RETRY_ON_CONNECTION_FAILURE, EXPECTED_LOGGING_INTERCEPTOR_ENABLED);

        assertEquals(sslContext, httpConnectionSettings.getSslContext());
        assertEquals(x509TrustManager, httpConnectionSettings.getTrustManager());
        assertEquals(interceptor, httpConnectionSettings.getCustomInterceptor());
        assertEquals(EXPECTED_TIMEOUT_SECONDS, httpConnectionSettings.getReadTimeoutSeconds());
        assertEquals(EXPECTED_TIMEOUT_SECONDS, httpConnectionSettings.getConnectTimeoutSeconds());
        assertEquals(EXPECTED_RETRY_ON_CONNECTION_FAILURE,
            httpConnectionSettings.isRetryOnConnectionFailure());
        assertEquals(EXPECTED_LOGGING_INTERCEPTOR_ENABLED,
            httpConnectionSettings.isLoggingInterceptorEnabled());
    }
}
