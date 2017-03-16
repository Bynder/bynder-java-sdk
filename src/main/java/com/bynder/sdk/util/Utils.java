/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Final class that provides methods to help handling API requests and responses.
 */
public final class Utils {

    // separators
    public static final String STR_AND = "&";
    public static final String STR_COMMA = ",";
    public static final String STR_EQUALS = "=";

    /**
     * Prevents the instantiation of the class.
     */
    private Utils() {}

    public static Map<String, String> buildMapFromResponse(final String response) {
        Map<String, String> map = new HashMap<>();
        String[] keyValuePairs = response.split(STR_AND);

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(STR_EQUALS);
            map.put(keyValue[0], keyValue[1]);
        }

        return map;
    }

    /**
     * Creates an instance of {@link OkHttpOAuthConsumer}.
     *
     * @param consumerKey Consumer key.
     * @param consumerSecret Consumer secret.
     * @param tokenKey Token key.
     * @param tokenSecret Token secret.
     *
     * @return {@link OkHttpOAuthConsumer} instance to create the HTTP client.
     */
    private static OkHttpOAuthConsumer createHttpOAuthConsumer(final String consumerKey, final String consumerSecret, final String tokenKey, final String tokenSecret) {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(consumerKey, consumerSecret);

        if (tokenKey != null && tokenSecret != null) {
            consumer.setTokenWithSecret(tokenKey, tokenSecret);
        }

        return consumer;
    }

    /**
     * Creates an instance of {@link OkHttpClient}.
     *
     * @param consumer {@link OkHttpOAuthConsumer} instance.
     *
     * @return {@link OkHttpClient} instance used for API requests.
     */
    private static OkHttpClient createHttpClient(final OkHttpOAuthConsumer consumer) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().clear();
        httpClient.addInterceptor(new SigningInterceptor(consumer));

        // increase timeout
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        return httpClient.build();
    }

    /**
     * Creates an implementation of the API endpoints defined by the service interface.
     *
     * @param apiInterface API interface class.
     * @param baseUrl Domain URL where we want to point the API calls.
     * @param consumerKey Consumer key.
     * @param consumerSecret Consumer secret.
     * @param tokenKey Token key.
     * @param tokenSecret Token secret.
     *
     * @return Instance of the API interface class implementation.
     */
    public static <T> T createApiService(final Class<T> apiInterface, final String baseUrl, final String consumerKey, final String consumerSecret, final String tokenKey, final String tokenSecret) {
        OkHttpOAuthConsumer consumer = createHttpOAuthConsumer(consumerKey, consumerSecret, tokenKey, tokenSecret);
        OkHttpClient client = createHttpClient(consumer);

        Retrofit retrofit = new Builder().baseUrl(baseUrl).addConverterFactory(new StringConverterFactory()).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create())).client(client).build();

        return retrofit.create(apiInterface);
    }
}
