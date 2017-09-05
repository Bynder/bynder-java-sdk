package com.bynder.sdk.model;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;

/**
 * Configuration holder for HTTP connection related settings for the connection to Bynder.
 */
public class HttpConnectionSettings {

    public static final int DEFAULT_TIMEOUT_SECONDS = 30;

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
    /**
     * Read timeout in seconds.
     */
    private int readTimeoutSeconds;
    /**
     * Connect timeout in seconds.
     */
    private int connectTimeoutSeconds;
    /**
     * Retry connection in case it fails the first time.
     */
    private boolean retryOnConnectionFailure;

    public HttpConnectionSettings(final SSLContext sslContext, final X509TrustManager trustManager, final Interceptor customInterceptor, final int readTimeoutSeconds, final int connectTimeoutSeconds,
            final boolean retryOnConnectionFailure) {
        this.sslContext = sslContext;
        this.trustManager = trustManager;
        this.customInterceptor = customInterceptor;
        this.readTimeoutSeconds = readTimeoutSeconds;
        this.connectTimeoutSeconds = connectTimeoutSeconds;
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }

    /**
     * Empty constructor: allows quick access to default settings.
     */
    public HttpConnectionSettings() {
        this.readTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
        this.connectTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
        this.retryOnConnectionFailure = true;
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
}
