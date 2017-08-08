package com.bynder.sdk.model;

import okhttp3.Interceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * Created by benzahler on 21.07.17.
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

    private int readTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
    private int connectTimeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
    private boolean retryOnConnectionFailure = true;

    public HttpConnectionSettings(SSLContext sslContext, X509TrustManager trustManager, Interceptor
            customInterceptor, int readTimeoutSeconds, int connectTimeoutSeconds, boolean retryOnConnectionFailure) {
        this.sslContext = sslContext;
        this.trustManager = trustManager;
        this.customInterceptor = customInterceptor;
        this.readTimeoutSeconds = readTimeoutSeconds;
        this.connectTimeoutSeconds = connectTimeoutSeconds;
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }

    public HttpConnectionSettings() {
    }

    public SSLContext getSslContext() { return sslContext; }

    public X509TrustManager getTrustManager() { return trustManager; }

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
