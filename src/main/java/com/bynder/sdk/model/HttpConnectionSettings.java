package com.bynder.sdk.model;

import okhttp3.Interceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * Configuration holder for http connection related settings.
 */
public class HttpConnectionSettings {

    public static final int DEFAULT_TIMEOUT_SECONDS = 30;

    /**
     * SSL Context for the connection to Bynder.
     * Allows for example to send a client SSL certificate.
     * Can only be used if a trustManager is provided.
     */
    private SSLContext sslContext;
    /**
     * SSL trust manager to be used for the connection to Bynder.
     */
    private X509TrustManager trustManager;
    /**
     * A custom OkHttp Interceptor. Can be used to transform URLs to an ESB.
     */
    private Interceptor customInterceptor;

    /**
     * Read timeout in seconds for http connections to bynder.
     */
    private int readTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
    /**
     * Connect timeout in seconds for http connections to bynder
     */
    private int connectTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
    /**
     * Retry connection to bynder if it fails the first time
     */
    private boolean retryOnConnectionFailure = true;

    public HttpConnectionSettings(final SSLContext sslContext, final X509TrustManager trustManager, final Interceptor
            customInterceptor, final int readTimeoutSeconds, final int connectTimeoutSeconds, final boolean
            retryOnConnectionFailure) {
        this.sslContext = sslContext;
        this.trustManager = trustManager;
        this.customInterceptor = customInterceptor;
        this.readTimeoutSeconds = readTimeoutSeconds;
        this.connectTimeoutSeconds = connectTimeoutSeconds;
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }

    /**
     * Empty constructor, allows quick access to default settings.
     */
    public HttpConnectionSettings() {}

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
