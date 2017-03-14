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

import org.apache.commons.lang.StringUtils;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public final class Utils {

    // check not null error message
    private static final String errorMessage = "%s shall not be null.";

    // separators
    public static final String STR_AND = "&";
    public static final String STR_COMMA = ",";
    public static final String STR_EQUALS = "=";

    private Utils() {
        // prevent instantiation
    }

    public static Map<String, String> buildMapFromResponse(final String response) {
        Map<String, String> map = new HashMap<>();
        String[] keyValuePairs = response.split(STR_AND);

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(STR_EQUALS);
            map.put(keyValue[0], keyValue[1]);
        }

        return map;
    }

    public static void checkNotNull(final String name, final Object value) {
        if (value == null) {
            throw new IllegalArgumentException(String.format(errorMessage, name));
        } else if (value.getClass().equals(String.class)) {
            if (StringUtils.isEmpty((String) value)) {
                throw new IllegalArgumentException(String.format(errorMessage, name));
            }
        }
    }

    private static OkHttpOAuthConsumer createHttpOAuthConsumer(final String consumerKey, final String consumerSecret, final String tokenKey, final String tokenSecret) {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(consumerKey, consumerSecret);

        if (tokenKey != null && tokenSecret != null) {
            consumer.setTokenWithSecret(tokenKey, tokenSecret);
        }

        return consumer;
    }

    private static OkHttpClient createHttpClient(final OkHttpOAuthConsumer consumer) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().clear();
        httpClient.addInterceptor(new SigningInterceptor(consumer));

        // increase timeout
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        return httpClient.build();
    }

    public static <T> T createApiService(final Class<T> apiInterface, final String baseUrl, final String consumerKey, final String consumerSecret, final String tokenKey, final String tokenSecret) {
        OkHttpOAuthConsumer consumer = createHttpOAuthConsumer(consumerKey, consumerSecret, tokenKey, tokenSecret);
        OkHttpClient client = createHttpClient(consumer);

        Retrofit retrofit = new Builder().baseUrl(baseUrl).addConverterFactory(new StringConverterFactory()).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create())).client(client).build();

        return retrofit.create(apiInterface);
    }
}
