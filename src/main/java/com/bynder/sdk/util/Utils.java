/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpResponseException;

import com.bynder.sdk.model.Count;
import com.bynder.sdk.service.BynderServiceCall;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public final class Utils {

    // check not null error message
    private static final String errorMessage = "%s shall not be null.";

    // separators
    public static final String STR_AND = "&";
    public static final String STR_COMMA = ",";
    public static final String STR_EQUALS = "=";
    public static final String STR_SPACE = " ";

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

        Retrofit retrofit =
                new Builder().baseUrl(baseUrl).addConverterFactory(new StringConverterFactory())
                        .addConverterFactory(GsonConverterFactory
                                .create(new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).registerTypeAdapter(Count.class, new CountTypeAdapter()).create()))
                        .client(client).build();

        return retrofit.create(apiInterface);
    }

    public static <T> void validateResponse(final Response<T> response, final String errorMessage) throws HttpResponseException {
        if (!response.isSuccessful()) {
            throw new HttpResponseException(response.code(), String.format(errorMessage, Integer.toString(response.code()), response.message()));
        }
    }

    public static <T> BynderServiceCall<T> createServiceCall(final Call<T> call) {
        return new BynderServiceCall<T>() {
            @Override
            public T execute() throws RuntimeException {
                try {
                    Response<T> response = call.execute();
                    return response.body();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Observable<T> executeAsync() {
                return Observable.create(new OnSubscribe<T>() {
                    @Override
                    public void call(final Subscriber<? super T> subscriber) {
                        call.enqueue(new Callback<T>() {
                            @Override
                            public void onResponse(final Call<T> call, final Response<T> response) {
                                subscriber.onNext(response.body());
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onFailure(final Call<T> call, final Throwable t) {
                                subscriber.onError(t);
                            }
                        });
                    }
                });
            }
        };
    }
}
