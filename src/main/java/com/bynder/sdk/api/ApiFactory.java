/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.api;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.util.BooleanTypeAdapter;
import com.bynder.sdk.util.StringConverterFactory;
import com.bynder.sdk.util.Utils;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Factory to create API clients.
 */
public class ApiFactory {

    /**
     * Prevents the instantiation of the class.
     */
    private ApiFactory() {}

    /**
     * Creates an implementation of the Bynder API endpoints defined in the {@link BynderApi}
     * interface.
     *
     * @param configuration {@link Configuration} settings for the HTTP communication with Bynder.
     * @return Implementation instance of the {@link BynderApi} interface.
     */
    public static BynderApi createBynderClient(final Configuration configuration) {
        return new Retrofit.Builder()
                .baseUrl(configuration.getBaseUrl().toString())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
                                .create())
                )
                .client(createOkHttpClient(configuration))
                .build().create(BynderApi.class);
    }

    /**
     * Creates an implementation of the Bynder OAuth2 endpoints defined in the {@link OAuthApi}
     * interface.
     *
     * @param baseUrl Bynder portal base URL.
     * @return Implementation instance of the {@link OAuthApi} interface.
     */
    public static OAuthApi createOAuthClient(final String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(OAuthApi.class);
    }

    /**
     * Creates an instance of {@link OkHttpClient}.
     *
     * @param configuration Configuration settings for the HTTP communication with Bynder.
     * @return {@link OkHttpClient} instance used for API requests.
     */
    private static OkHttpClient createOkHttpClient(final Configuration configuration) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (configuration.getPermanentToken() == null) {
            setOAuthInterceptor(httpClientBuilder, configuration);
        } else {
            setPermanentTokenInterceptor(httpClientBuilder, configuration);
        }
        setHttpConnectionSettings(httpClientBuilder, configuration);
        addUserAgentHeader(httpClientBuilder);
        return httpClientBuilder.build();
    }

    /**
     * Sets the OAuth interceptor for the HTTP client. This interceptor will handle adding the
     * access token to the request header and refreshing it when it expires.
     *
     * @param httpClientBuilder Builder instance of the HTTP client.
     * @param configuration {@link Configuration} settings for the HTTP communication with Bynder.
     */
    private static void setOAuthInterceptor(
            final Builder httpClientBuilder,
            final Configuration configuration
    ) {
        httpClientBuilder.addInterceptor(chain -> {
            if (configuration.getOAuthSettings().getToken() == null) {
                throw new RuntimeException("Token is not defined in Configuration");
            }

            // check if access token is expiring in the next 15 seconds
            if (Utils.isDateExpiring(configuration.getOAuthSettings().getToken().getAccessTokenExpiration(), 15)) {
                // refresh the access token
                configuration.getOAuthSettings().callback(
                        BynderClient.Builder.create(configuration)
                                .getOAuthService()
                                .refreshAccessToken()
                                .blockingSingle()
                );
            }

            return chain.proceed(addAuthHeader(
                    chain.request(),
                    configuration.getOAuthSettings().getToken().getAccessToken()
            ));
        });
    }

    /**
     * Sets the permanent token interceptor for the HTTP client. This interceptor will handle adding
     * the permanent token to the request header.
     *
     * @param httpClientBuilder Builder instance of the HTTP client.
     * @param configuration {@link Configuration} settings for the HTTP communication with Bynder.
    */
    private static void setPermanentTokenInterceptor(
            final Builder httpClientBuilder,
            final Configuration configuration
    ) {
        httpClientBuilder.addInterceptor(chain -> chain.proceed(
                addAuthHeader(chain.request(), configuration.getPermanentToken())
        ));
    }

    /**
     * Sets the HTTP connection settings for the HTTP client.
     *
     * @param httpClientBuilder Builder instance of the HTTP client.
     * @param configuration HTTP connection settings for the HTTP communication with
     * Bynder.
     */
    private static void setHttpConnectionSettings(
            final Builder httpClientBuilder,
            final Configuration configuration
    ) {
        HttpConnectionSettings httpConnectionSettings = configuration.getHttpConnectionSettings();

        if (httpConnectionSettings.isLoggingInterceptorEnabled()) {
            httpClientBuilder.addInterceptor(
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            );
        }

        if (httpConnectionSettings.getCustomInterceptor() != null) {
            httpClientBuilder.addInterceptor(httpConnectionSettings.getCustomInterceptor());
        }

        httpClientBuilder
                .retryOnConnectionFailure(httpConnectionSettings.isRetryOnConnectionFailure())
                .readTimeout(httpConnectionSettings.getReadTimeoutSeconds(), TimeUnit.SECONDS)
                .connectTimeout(httpConnectionSettings.getConnectTimeoutSeconds(), TimeUnit.SECONDS)
                .writeTimeout(httpConnectionSettings.getConnectTimeoutSeconds(), TimeUnit.SECONDS);

        if (httpConnectionSettings.getSslContext() != null
                && httpConnectionSettings.getTrustManager() != null) {
            httpClientBuilder.sslSocketFactory(
                    httpConnectionSettings.getSslContext().getSocketFactory(),
                    httpConnectionSettings.getTrustManager()
            );
        }
    }

    /**
     * Add the POM artifact ID (name) and version to the User-Agent header of the request.
     *
     * @param httpClientBuilder Builder instance of the HTTP client.
     */
    private static void addUserAgentHeader(final Builder httpClientBuilder) {
        httpClientBuilder.addInterceptor(chain -> {
            String name;
            String version;
            try {
                final Properties sdkInfo = Utils.loadConfig("bynder-sdk");
                name = sdkInfo.getProperty("name");
                version = sdkInfo.getProperty("version");
            } catch (IOException e) {
                name = "bynder-java-sdk";
                version = "unknown";
            }
            return chain.proceed(addHeader(
                    chain.request(),
                    "User-Agent",
                    String.format("%s/%s", name, version)
            ));
        });
    }

    /**
     * Adds a header to a request.
     *
     * @param request the request
     * @param key header key
     * @param value header value
     * @return the request with the added header
     */
    private static Request addHeader(
            final Request request,
            final String key,
            final String value
    ) {
        return request
                .newBuilder()
                .header(key, value)
                .build();
    }

    /**
     * Adds an Authentication header with a Bearer token to a request.
     *
     * @param request the request
     * @param token bearer token
     * @return the request with the added header
     */
    private static Request addAuthHeader(
            final Request request,
            final String token
    ) {
        return addHeader(
                request,
                "Authorization",
                String.format("%s %s", "Bearer", token)
        );
    }

}
