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
     * SSLContext for the connection to Bynder.
     * Allows for example to send a client SSL cert.
     * Can be only used if a trustManager is provided.
     */
    private SSLContext sslContext;
    /**
     * SSL TrustManager to be used for the connection to Bynder.
     */
    private X509TrustManager trustManager;
    /**
     * A custom OkHttp Interceptor. Can for example be used to transform urls to an ESB.
     */
    private Interceptor customInterceptor;

    //read timeout in seconds for http connections to bynder
    private int readTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
    //connect timeout in seconds for http connections to bynder
    private int connectTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
    //retry connection to bynder if it fails the first time
    private boolean retryOnConnectionFailure = true;

    /**
     * @param sslContext               ssl context to be used for https connections to bynder
     * @param trustManager             ssl trust manager for the https connection to bynder
     * @param customInterceptor        custom ok http interceptor to transform http requests. this can be used to
     *                                 implement
     *                                 connections through a proxy.
     * @param readTimeoutSeconds       read timeout in seconds for http connections to bynder
     * @param connectTimeoutSeconds    connect timeout in seconds for http connections to bynder
     * @param retryOnConnectionFailure retry connection to bynder if it fails the first time
     */
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
     * empty constructor for quick access to default settings.
     */
    public HttpConnectionSettings() {
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
