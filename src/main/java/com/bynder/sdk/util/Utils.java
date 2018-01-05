/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import com.bynder.sdk.model.Credentials;
import com.bynder.sdk.model.HttpConnectionSettings;
import com.bynder.sdk.query.ApiField;
import com.bynder.sdk.query.ConversionType;
import com.bynder.sdk.query.MetapropertyField;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang.StringUtils;
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

    /**
     * String separators.
     */
    public static final String STR_AND = "&";
    public static final String STR_COMMA = ",";
    public static final String STR_EQUALS = "=";

    /**
     * Prevents the instantiation of the class.
     */
    private Utils() {
    }

    /**
     * Builds a {@link Map} from a API response string containing a key and value separated by a
     * &amp;.
     *
     * @param response Response string returned by the API.
     * @return {@link Map} with key and value pair.
     */
    public static Map<String, String> buildMapFromResponse(final String response) {
        Map<String, String> map = new HashMap<>();
        String[] keyValuePairs = response.split(STR_AND);

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(STR_EQUALS);
            if (keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            } else {
                throw new InvalidParameterException();
            }
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
     * @return {@link OkHttpOAuthConsumer} instance to create the HTTP client.
     */
    private static OkHttpOAuthConsumer createHttpOAuthConsumer(final String consumerKey,
        final String consumerSecret, final String tokenKey, final String tokenSecret) {
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
     * @param httpConnectionSettings Settings for the HTTP connection to Bynder.
     * @return {@link OkHttpClient} instance used for API requests.
     */
    private static OkHttpClient createHttpClient(final OkHttpOAuthConsumer consumer,
        final HttpConnectionSettings httpConnectionSettings) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().clear();
        httpClient.addInterceptor(new SigningInterceptor(consumer));

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        if (httpConnectionSettings.getCustomInterceptor() != null) {
            httpClient.addInterceptor(httpConnectionSettings.getCustomInterceptor());
        }

        httpClient.retryOnConnectionFailure(httpConnectionSettings.isRetryOnConnectionFailure());
        httpClient.readTimeout(httpConnectionSettings.getReadTimeoutSeconds(), TimeUnit.SECONDS);
        httpClient
            .connectTimeout(httpConnectionSettings.getConnectTimeoutSeconds(), TimeUnit.SECONDS);

        if (httpConnectionSettings.getSslContext() != null
            && httpConnectionSettings.getTrustManager() != null) {
            httpClient.sslSocketFactory(httpConnectionSettings.getSslContext().getSocketFactory(),
                httpConnectionSettings.getTrustManager());
        }
        return httpClient.build();
    }

    /**
     * Creates an implementation of the API endpoints defined by the service interface.
     *
     * @param <T> Class type of the API interface.
     * @param apiInterface API interface class.
     * @param baseUrl Domain URL where we want to point the API calls.
     * @param credentials Token credentials to call the API.
     * @param httpConnectionSettings Settings for the http connection to Bynder
     * @return Instance of the API interface class implementation.
     */
    public static <T> T createApiService(final Class<T> apiInterface, final URL baseUrl,
        final Credentials credentials, final HttpConnectionSettings httpConnectionSettings) {
        OkHttpOAuthConsumer oauthConsumer = createHttpOAuthConsumer(credentials.getConsumerKey(),
            credentials.getConsumerSecret(), credentials.getToken(), credentials.getTokenSecret());
        OkHttpClient httpClient = createHttpClient(oauthConsumer, httpConnectionSettings);

        Builder builder = new Builder();
        builder.baseUrl(baseUrl.toString());
        builder.addConverterFactory(new StringConverterFactory());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create(
            new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
                .create()));
        builder.client(httpClient);

        Retrofit retrofit = builder.build();

        return retrofit.create(apiInterface);
    }

    /**
     * Given a query object this method gets its API parameters. The parameters are basically the
     * fields of the query object that have {@link ApiField} annotation.
     *
     * @param query Query object.
     * @return Map with parameters name/value pairs to send to the API.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    public static Map<String, String> getApiParameters(final Object query)
        throws IllegalAccessException {
        Map<String, String> params = new HashMap<>();
        Field[] fields = query.getClass().getDeclaredFields();

        for (Field field : fields) {
            convertField(field, query, params);
        }
        return params;
    }

    /**
     * Method called for each field in a query object. It extracts the different fields with
     * {@link ApiField} annotation and, if needed, converts it according to the conversion type
     * defined.
     *
     * @param field Field information.
     * @param query Query object.
     * @param params Parameters name/value pairs to send to the API.
     * @throws IllegalAccessException If the Field object is inaccessible.
     */
    private static void convertField(final Field field, final Object query,
        final Map<String, String> params) throws IllegalAccessException {
        field.setAccessible(true);
        ApiField apiField = field.getAnnotation(ApiField.class);

        if (field.get(query) != null && apiField != null) {
            if (apiField.conversionType() == ConversionType.NONE) {
                params.put(apiField.name(), field.get(query).toString());
            } else {
                if (apiField.conversionType() == ConversionType.METAPROPERTY_FIELD) {
                    MetapropertyField metapropertyField = (MetapropertyField) field.get(query);
                    params.put(String
                            .format("%s.%s", apiField.name(), metapropertyField.getMetapropertyId
                                ()),
                        StringUtils.join(metapropertyField.getOptionsIds(), Utils.STR_COMMA));
                } else if (apiField.conversionType() == ConversionType.LIST_FIELD) {
                    List<?> listField = (List<?>) field.get(query);
                    params.put(apiField.name(), StringUtils.join(listField, Utils.STR_COMMA));
                } else if (apiField.conversionType() == ConversionType.JSON_FIELD) {
                    List<?> listField = (List<?>) field.get(query);
                    Gson gson = new Gson();
                    params.put(apiField.name(), gson.toJson(listField));
                } else if (apiField.conversionType() == ConversionType.BOOLEAN_FIELD) {
                    Boolean booleanField = (Boolean) field.get(query);
                    params.put(apiField.name(), booleanField ? "1" : "0");
                }
            }
        }
        field.setAccessible(false);
    }
}
