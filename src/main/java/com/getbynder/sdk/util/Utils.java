package com.getbynder.sdk.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.getbynder.sdk.domain.UserAccessData;
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

/**
 *
 * @author daniel.sequeira
 */
public final class Utils {

    //separators
    public static final String STR_AND = "&";
    public static final String STR_COMMA = ",";
    public static final String STR_EQUALS = "=";
    public static final String STR_QUESTION = "?";
    public static final String STR_SLASH = "/";
    public static final String STR_SPACE = " ";

    private Utils() {
        //prevent instantiation
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

            //the value needs to be decoded because the method OAuth.toHeaderElement will encode it again
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

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(userAccessData.getTokenKey(), userAccessData.getTokenSecret());

        // sign the request and return the encoded url
        String inputUrl = consumer.sign(url);

        return getOAuthHeaderFromUrl(new URL(inputUrl));
    }

    public static boolean isDateFormatValid(final String date) {

        //The valid datetime format is ISO8601
        try {
            DatatypeConverter.parseDateTime(date);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    public static HttpPost createPostRequest(final String consumerKey, final String consumerSecret, final UserAccessData userAccessData, final URI requestUri, final List<BasicNameValuePair> params) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        HttpPost request = new HttpPost(requestUri);

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);

        if (userAccessData != null) {
            consumer.setTokenWithSecret(userAccessData.getTokenKey(), userAccessData.getTokenSecret());
        } else {
            consumer.setTokenWithSecret(SecretProperties.getInstance().getProperty("ACCESS_TOKEN"), SecretProperties.getInstance().getProperty("ACCESS_TOKEN_SECRET"));
        }

        // set the parameters into the request
        request.setEntity(new UrlEncodedFormEntity(params));

        // sign the request
        consumer.sign(request);

        return request;
    }

    public static Response getRequestResponse(final String url, final String oauthHeader) {

        Client client = ClientBuilder.newClient();

        return client.target(url).request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", oauthHeader).get();
    }

    public static int getTotalCountFromJson(final String jsonResponse) {
        JsonElement jsonElement = new JsonParser().parse(jsonResponse);
        JsonObject  jsonObject = jsonElement.getAsJsonObject();

        return jsonObject.get("count").getAsJsonObject().get("total").getAsInt();
    }
}
