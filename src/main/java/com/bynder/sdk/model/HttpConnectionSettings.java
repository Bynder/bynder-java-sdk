/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Configuration holder for HTTP connection related settings for the connection to Bynder.
 */
public class HttpConnectionSettings {

    public static final int DEFAULT_TIMEOUT_SECONDS = 30;
    /**
     * Read timeout in seconds.
     */
    private final int readTimeoutSeconds;
    /**
     * Connect timeout in seconds.
     */
    private final int connectTimeoutSeconds;
    /**
     * Retry connection in case it fails the first time.
     */
    private final boolean retryOnConnectionFailure;
    /**
     * Whether or not to enable a {@link HttpLoggingInterceptor} on the HTTP client used, 
     * logging the body of all requests at default log level.
     */
    private final boolean loggingInterceptorEnabled;
    /**
     * SSL Context: allows to send a client SSL certificate. Can only be used if the trustManager
     * was defined.
     */
    private SSLContext sslContext;
    /**
     * SSL Trust Manager.
     */
    private X509TrustManager trustManager;
    /**
     * Custom OkHttp Interceptor: can be used to transform URLs to an ESB.
     */
    private Interceptor customInterceptor;
    
    public HttpConnectionSettings(final SSLContext sslContext, final X509TrustManager trustManager,
        final Interceptor customInterceptor, final int readTimeoutSeconds,
        final int connectTimeoutSeconds, final boolean retryOnConnectionFailure) {
      this(sslContext, trustManager, customInterceptor, readTimeoutSeconds, connectTimeoutSeconds, retryOnConnectionFailure, true);
    }

    public HttpConnectionSettings(final SSLContext sslContext, final X509TrustManager trustManager,
        final Interceptor customInterceptor, final int readTimeoutSeconds,
        final int connectTimeoutSeconds, final boolean retryOnConnectionFailure, boolean includeLoggingInterceptor) {
        this.sslContext = sslContext;
        this.trustManager = trustManager;
        this.customInterceptor = customInterceptor;
        this.readTimeoutSeconds = readTimeoutSeconds;
        this.connectTimeoutSeconds = connectTimeoutSeconds;
        this.retryOnConnectionFailure = retryOnConnectionFailure;
        this.loggingInterceptorEnabled = includeLoggingInterceptor;
    }

    /**
     * Empty constructor: allows quick access to default settings.
     */
    public HttpConnectionSettings() {
        this.readTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
        this.connectTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
        this.retryOnConnectionFailure = true;
        this.loggingInterceptorEnabled = false;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public Interceptor getCustomInterceptor() {
        return customInterceptor;
    }

    public int getReadTimeoutSeconds() {
        return readTimeoutSeconds;
    }

    public int getConnectTimeoutSeconds() {
        return connectTimeoutSeconds;
    }

    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }
    
    public boolean isLoggingInterceptorEnabled() {
        return loggingInterceptorEnabled;
    }
    
}
