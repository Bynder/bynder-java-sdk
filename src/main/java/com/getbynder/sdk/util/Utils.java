package com.getbynder.sdk.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.getbynder.sdk.domain.UserAccessData;
import com.google.gson.GsonBuilder;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
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

    // separators
    public static final String STR_AND = "&";
    public static final String STR_COMMA = ",";
    public static final String STR_EQUALS = "=";
    public static final String STR_SPACE = " ";

    private Utils() {
        // prevent instantiation
    }

    public static String getOAuthHeaderFromUrl(final URL url) {

        String query = url.getQuery();

        List<BasicNameValuePair> queryPairs = new ArrayList<>();

        String[] pairs = query.split(STR_AND);

        for (String pair : pairs) {
            int idx = pair.indexOf(STR_EQUALS);

            // the value needs to be decoded because the method OAuth.toHeaderElement will encode it again
            queryPairs.add(new BasicNameValuePair(pair.substring(0, idx), OAuth.percentDecode(pair.substring(idx + 1))));
        }

        StringBuilder oauthHeader = new StringBuilder("OAuth ");

        for (BasicNameValuePair nvPair : queryPairs) {
            oauthHeader.append(OAuth.toHeaderElement(nvPair.getName(), nvPair.getValue())).append(STR_COMMA);
        }

        oauthHeader.deleteCharAt(oauthHeader.length()-1);

        return oauthHeader.toString();
    }

    public static String createOAuthHeader(final String consumerKey, final String consumerSecret, final UserAccessData userAccessData, final String url) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        checkNotNull("CONSUMER_KEY", consumerKey);
        checkNotNull("CONSUMER_SECRET", consumerSecret);

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(userAccessData.getTokenKey(), userAccessData.getTokenSecret());

        // sign the request and return the encoded url
        String inputUrl = consumer.sign(url);

        return getOAuthHeaderFromUrl(new URL(inputUrl));
    }

    public static boolean isDateFormatValid(final String date) {

        // The valid datetime format is ISO8601
        try {
            DatatypeConverter.parseDateTime(date);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    public static HttpPost createPostRequest(final String consumerKey, final String consumerSecret, final UserAccessData userAccessData, final URI requestUri, final List<BasicNameValuePair> params) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        checkNotNull("CONSUMER_KEY", consumerKey);
        checkNotNull("CONSUMER_SECRET", consumerSecret);

        HttpPost request = new HttpPost(requestUri);

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);

        if (userAccessData != null) {
            consumer.setTokenWithSecret(userAccessData.getTokenKey(), userAccessData.getTokenSecret());
        } else {
            // it means this is the post request to login to the api so the request tokens must be used
            String requestTokenKey = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_KEY");
            String requestTokenSecret = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_SECRET");

            checkNotNull("REQUEST_TOKEN_KEY", requestTokenKey);
            checkNotNull("REQUEST_TOKEN_SECRET", requestTokenSecret);

            consumer.setTokenWithSecret(requestTokenKey, requestTokenSecret);
        }

        // set the parameters into the request
        request.setEntity(new UrlEncodedFormEntity(params));

        // sign the request
        consumer.sign(request);

        return request;
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

        String errorMessage = "%s shall not be null.";

        if (value == null) {
            throw new IllegalArgumentException(String.format(errorMessage, name));
        } else if (value.getClass().equals(String.class)) {
            if (StringUtils.isEmpty((String) value)) {
                throw new IllegalArgumentException(String.format(errorMessage, name));
            }
        }
    }

    public static <T> T createApiService(final Class<T> apiClass, final String baseUrl, final String tokenKey, final String tokenSecret) {

        String consumerKey = SecretProperties.getInstance().getProperty("CONSUMER_KEY");
        String consumerSecret = SecretProperties.getInstance().getProperty("CONSUMER_SECRET");

        Utils.checkNotNull("CONSUMER_KEY", consumerKey);
        Utils.checkNotNull("CONSUMER_SECRET", consumerSecret);
        Utils.checkNotNull("baseUrl", baseUrl);

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(consumerKey, consumerSecret);

        if (tokenKey != null && tokenSecret != null) {
            consumer.setTokenWithSecret(tokenKey, tokenSecret);
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().clear();
        httpClient.addInterceptor(new SigningInterceptor(consumer));

        // Increase timeout
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();
        Builder retrofitBuilder = new Builder().baseUrl(baseUrl).addConverterFactory(new StringConverterFactory()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create()));
        Retrofit retrofitBynderApi = retrofitBuilder.client(client).build();

        return retrofitBynderApi.create(apiClass);
    }

    public static <T> void validateResponse(final Response<T> response, final String errorMessage) throws HttpResponseException {
        if(response.code() != HttpStatus.SC_OK) {
            throw new HttpResponseException(response.code(), String.format(errorMessage, Integer.toString(response.code()), response.message()));
        }
    }
}
