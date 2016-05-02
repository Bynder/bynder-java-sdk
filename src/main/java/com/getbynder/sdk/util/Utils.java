package com.getbynder.sdk.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.getbynder.sdk.domain.UserAccessData;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import okhttp3.OkHttpClient;
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

    public static URI createRequestURI(final URL url, final String relativePath) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();

        builder.setScheme(url.getProtocol()).setHost(url.getHost()).setPath(url.getPath().concat(relativePath));

        return builder.build();
    }

    public static URI createRequestURI(final URL url, final String relativePath, final List<BasicNameValuePair> params) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();

        builder.setScheme(url.getProtocol()).setHost(url.getHost()).setPath(url.getPath().concat(relativePath));

        for (BasicNameValuePair pair : params) {
            builder.setParameter(pair.getName(), pair.getValue());
        }

        return builder.build();
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

        checkNotNull("CONSUMER_KEY", consumerKey, true);
        checkNotNull("CONSUMER_SECRET", consumerSecret, true);

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

        checkNotNull("CONSUMER_KEY", consumerKey, true);
        checkNotNull("CONSUMER_SECRET", consumerSecret, true);

        HttpPost request = new HttpPost(requestUri);

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);

        if (userAccessData != null) {
            consumer.setTokenWithSecret(userAccessData.getTokenKey(), userAccessData.getTokenSecret());
        } else {
            // it means this is the post request to login to the api so the request tokens must be used
            String requestTokenKey = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_KEY");
            String requestTokenSecret = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_SECRET");

            checkNotNull("REQUEST_TOKEN_KEY", requestTokenKey, true);
            checkNotNull("REQUEST_TOKEN_SECRET", requestTokenSecret, true);

            consumer.setTokenWithSecret(requestTokenKey, requestTokenSecret);
        }

        // set the parameters into the request
        request.setEntity(new UrlEncodedFormEntity(params));

        // sign the request
        consumer.sign(request);

        return request;
    }

    public static String sendPostRequest(final HttpPost request, final String errorMessage) throws IOException {

        String responseBody = "";

        // send the post request
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                // if request was unsuccessful
                if (Arrays.asList(HttpStatus.SC_INTERNAL_SERVER_ERROR, HttpStatus.SC_NOT_FOUND).contains(response.getStatusLine().getStatusCode())) {
                    throw new HttpResponseException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
                } else if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new HttpResponseException(response.getStatusLine().getStatusCode(), errorMessage);
                }

                // if successful, return the response body
                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {
                    responseBody = EntityUtils.toString(resEntity);
                }
            }
        }

        return responseBody;
    }

    public static Response getRequestResponse(final String url, final String oauthHeader) throws HttpResponseException {

        Client client = ClientBuilder.newClient();

        Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", oauthHeader).get();

        // if request was unsuccessful
        if (response.getStatusInfo().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpResponseException(response.getStatusInfo().getStatusCode(), response.getStatusInfo().getReasonPhrase());
        }

        return response;
    }

    public static int getTotalCountFromJson(final String jsonResponse) {

        JsonElement jsonElement = new JsonParser().parse(jsonResponse);
        JsonObject  jsonObject = jsonElement.getAsJsonObject();

        return jsonObject.get("count").getAsJsonObject().get("total").getAsInt();
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

    public static void checkNotNull(final String name, final Object value, final boolean isPropertyFromFile) {

        String errorMessage = conditionalSetter(isPropertyFromFile, ErrorMessages.PROPERTY_NOT_DEFINED, ErrorMessages.NULL_PARAMETER);

        if (value == null) {
            throw new IllegalArgumentException(String.format(errorMessage, name));
        } else if (value.getClass().equals(String.class)) {
            if (StringUtils.isEmpty((String) value)) {
                throw new IllegalArgumentException(String.format(errorMessage, name));
            }
        }
    }

    // same function as a ternary operator
    public static <T> T conditionalSetter(final boolean condition, final T valueIfTrue, final T valueIfFalse) {

        if (condition) {
            return valueIfTrue;
        }
        return valueIfFalse;
    }

    public static <T> T createApiService(final Class<T> apiClass, final boolean forLogin) {

        String tokenKey;
        String tokenSecret;

        if (forLogin) {
            tokenKey = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_KEY");
            tokenSecret = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_SECRET");
        } else {
            tokenKey = SecretProperties.getInstance().getProperty("ACCESS_TOKEN_KEY");
            tokenSecret = SecretProperties.getInstance().getProperty("ACCESS_TOKEN_SECRET");
        }

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(SecretProperties.getInstance().getProperty("CONSUMER_KEY"), SecretProperties.getInstance().getProperty("CONSUMER_SECRET"));
        consumer.setTokenWithSecret(tokenKey, tokenSecret);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.interceptors().clear();
        httpClient.addInterceptor(new SigningInterceptor(consumer));

        // Increase timeout
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();

        Builder retrofitBuilder = new Builder().baseUrl(ConfigProperties.getInstance().getProperty("BASE_URL")).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create()));

        Retrofit retrofitBynderApi = retrofitBuilder.client(client).build();

        return retrofitBynderApi.create(apiClass);
    }
}
