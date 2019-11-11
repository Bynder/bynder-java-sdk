/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.api;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.exception.BynderRuntimeException;
import com.bynder.sdk.model.oauth.Token;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.oauth.OAuthService;
import com.bynder.sdk.util.BooleanTypeAdapter;
import com.bynder.sdk.util.StringConverterFactory;
import com.bynder.sdk.util.Utils;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(configuration.getBaseUrl().toString())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(new StringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()));

        Retrofit retrofit = retrofitBuilder.client(createOkHttpClient(configuration)).build();
        return retrofit.create(BynderApi.class);
    }

    /**
     * Creates an implementation of the Bynder OAuth2 endpoints defined in the {@link OAuthApi}
     * interface.
     *
     * @param bucket AWS bucket URL.
     * @return Implementation instance of the {@link OAuthApi} interface.
     */
    public static AmazonS3Api createAmazonS3Client(final String bucket) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(bucket)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(AmazonS3Api.class);
    }

    /**
     * Creates an implementation of the Amazon S3 endpoints defined in the {@link AmazonS3Api}
     * interface.
     *
     * @param baseUrl Bynder portal base URL.
     * @return Implementation instance of the {@link OAuthApi} interface.
     */
    public static OAuthApi createOAuthClient(final String baseUrl) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(OAuthApi.class);
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

        HttpConnectionSettings httpConnectionSettings = configuration.getHttpConnectionSettings();
        setHttpConnectionSettings(httpClientBuilder, httpConnectionSettings);

        return httpClientBuilder.build();
    }

    /**
     * Sets the OAuth interceptor for the HTTP client. This interceptor will handle adding the
     * access token to the request header and refreshing it when it expires.
     *
     * @param httpClientBuilder Builder instance of the HTTP client.
     * @param configuration {@link Configuration} settings for the HTTP communication with Bynder.
     */
    private static void setOAuthInterceptor(final Builder httpClientBuilder,
        final Configuration configuration) {
        httpClientBuilder.addInterceptor(new Interceptor() {

            @Override
            public Response intercept(final Chain chain) throws IOException {
                if (configuration.getOAuthSettings().getToken() == null) {
                    throw new BynderRuntimeException("Token is not defined in Configuration");
                }

                // check if access token is expiring in the next 15 seconds
                if (Utils.isDateExpiring(configuration.getOAuthSettings().getToken().getAccessTokenExpiration(), 15)) {
                    // refresh the access token
                    OAuthService oAuthService = BynderClient.Builder.create(configuration)
                        .getOAuthService();
                    Token token = oAuthService.refreshAccessToken().blockingSingle();

                    // trigger callback method
                    configuration.getOAuthSettings().callback(token);
                }

                String headerValue = String
                    .format("%s %s", "Bearer", configuration.getOAuthSettings().getToken().getAccessToken());

                Request.Builder requestBuilder = chain.request().newBuilder()
                    .header("Authorization", headerValue);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
    }

    /**
     * Sets the permanent token interceptor for the HTTP client. This interceptor will handle adding
     * the permanent toekn to the request header.
     *
     * @param httpClientBuilder Builder instance of the HTTP client.
     * @param configuration     {@link Configuration} settings for the HTTP communication with Bynder.
    */
    private static void setPermanentTokenInterceptor(final Builder httpClientBuilder,
        final Configuration configuration) {
        httpClientBuilder.addInterceptor(new Interceptor() {

            @Override
            public Response intercept(final Chain chain) throws IOException {
                String headerValue = String.format("%s %s", "Bearer", configuration.getPermanentToken());

                Request.Builder requestBuilder =
                    chain.request().newBuilder().header("Authorization", headerValue);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
    }

    /**
     * Sets the HTTP connection settings for the HTTP client.
     *
     * @param httpClientBuilder Builder instance of the HTTP client.
     * @param httpConnectionSettings HTTP connection settings for the HTTP communication with
     * Bynder.
     */
    private static void setHttpConnectionSettings(final Builder httpClientBuilder,
        final HttpConnectionSettings httpConnectionSettings) {
        if (httpConnectionSettings.isLoggingInterceptorEnabled()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        if (httpConnectionSettings.getCustomInterceptor() != null) {
            httpClientBuilder.addInterceptor(httpConnectionSettings.getCustomInterceptor());
        }

        httpClientBuilder
            .retryOnConnectionFailure(httpConnectionSettings.isRetryOnConnectionFailure());
        httpClientBuilder
            .readTimeout(httpConnectionSettings.getReadTimeoutSeconds(), TimeUnit.SECONDS);
        httpClientBuilder
            .connectTimeout(httpConnectionSettings.getConnectTimeoutSeconds(), TimeUnit.SECONDS);
        httpClientBuilder
            .writeTimeout(httpConnectionSettings.getConnectTimeoutSeconds(), TimeUnit.SECONDS);

        if (httpConnectionSettings.getSslContext() != null
            && httpConnectionSettings.getTrustManager() != null) {
            httpClientBuilder
                .sslSocketFactory(httpConnectionSettings.getSslContext().getSocketFactory(),
                    httpConnectionSettings.getTrustManager());
        }
    }
}