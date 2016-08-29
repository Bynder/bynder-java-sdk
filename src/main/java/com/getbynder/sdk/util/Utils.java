package com.getbynder.sdk.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpResponseException;

import com.getbynder.sdk.domain.Count;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 *
 * @author daniel.sequeira
 */
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

    public static <T> T createApiService(final Class<T> apiClass, final String baseUrl, final String consumerKey, final String consumerSecret, final String tokenKey, final String tokenSecret) {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(consumerKey, consumerSecret);

        if (tokenKey != null && tokenSecret != null) {
            consumer.setTokenWithSecret(tokenKey, tokenSecret);
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().clear();
        httpClient.addInterceptor(new SigningInterceptor(consumer));

        // increase timeout
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();
        Builder retrofitBuilder = new Builder().baseUrl(baseUrl).addConverterFactory(new StringConverterFactory()).addConverterFactory(
                GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).registerTypeAdapter(Count.class, new CountTypeAdapter()).create()));
        Retrofit retrofitBynderApi = retrofitBuilder.client(client).build();

        return retrofitBynderApi.create(apiClass);
    }

    public static <T> void validateResponse(final Response<T> response, final String errorMessage) throws HttpResponseException {
        if (!response.isSuccessful()) {
            throw new HttpResponseException(response.code(), String.format(errorMessage, Integer.toString(response.code()), response.message()));
        }
    }
}
