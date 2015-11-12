package com.getbynder.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.getbynder.api.domain.Category;
import com.getbynder.api.domain.ImageAsset;
import com.getbynder.api.domain.UserAccessData;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 *
 * @author daniel.sequeira
 */
public class BynderService {

    private static final String LOGIN_PATH = "users/login/";
    private static final String CATEGORIES_PATH = "categories/";
    private static final String IMAGE_ASSETS_PATH = "media/?type=image";

    private static final String CONSUMER_KEY = "9F770A57-71DD-4EB0-AD4F8FEACBFB3F55";
    private static final String CONSUMER_SECRET = "xwbg1098";
    private static final String ACCESS_TOKEN = "553D7FCD-625D-4690-8D5533787EA36282";
    private static final String ACCESS_TOKEN_SECRET = "284F5ED19214EA8E51EE046BBBF507E9F34C28A7";

    private final String baseUrl;
    private final String username;
    private final String password;
    private final UserAccessData userAccessData;

    public BynderService(final String baseUrl, final String username, final String password) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.userAccessData = getUserAccessData();
    }

    public UserAccessData getUserAccessData() throws OAuthMessageSignerException, OAuthExpectationFailedException,
    OAuthCommunicationException, ClientProtocolException, IOException {

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

        // create an HTTP request to a protected resource
        String loginUri = Utils.getLoginUri(baseUrl+LOGIN_PATH, "consumerId", CONSUMER_KEY, "username", username, "password", password);
        HttpPost request = new HttpPost(loginUri);

        // sign the request
        consumer.sign(request);

        // set the parameters into the request
        request.setEntity(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("username", username),
                new BasicNameValuePair("password", password))));

        // send the request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        // if request was unsuccessful
        if(response.getStatusLine().getStatusCode() != 200){
            throw new HttpResponseException(response.getStatusLine().getStatusCode(), "The request was unsuccessful");
        }

        // if successful, return the response body
        HttpEntity resEntity = response.getEntity();
        String responseBody = "";

        if (resEntity != null) {
            responseBody = EntityUtils.toString(resEntity);
        }

        //close this stream
        httpClient.close();

        // parse the response string into a JSON object
        JSONObject responseObj = new JSONObject(responseBody);

        return new UserAccessData(responseObj);
    }

    public List<Category> getCategories() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, UnsupportedEncodingException {

        String apiGetCategoriesUrl = baseUrl+CATEGORIES_PATH;

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetCategoriesUrl);

        Client client = ClientBuilder.newClient();

        Response response = client.target(apiGetCategoriesUrl)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", oauthHeader)
                .get();

        JSONArray responseJsonArray = new JSONArray(response.readEntity(String.class));

        List<Category> categories = new ArrayList<>();

        for(int i=0; i<responseJsonArray.length(); i++) {
            categories.add(new Category((JSONObject) responseJsonArray.get(i)));
        }

        return categories;
    }

    public List<ImageAsset> getImageAssets() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        String apiGetImageAssetsUrl = baseUrl+IMAGE_ASSETS_PATH;

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsUrl);

        Client client = ClientBuilder.newClient();

        Response response = client.target(apiGetImageAssetsUrl)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", oauthHeader)
                .get();

        JSONArray responseJsonArray = new JSONArray(response.readEntity(String.class));

        return Utils.createImageAssetListFromJSONArray(responseJsonArray);
    }

    public List<ImageAsset> getImageAssets(final int limit, final int offset) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        StringBuilder stringBuilder = new StringBuilder(baseUrl+IMAGE_ASSETS_PATH);
        stringBuilder.append("&limit=");
        stringBuilder.append(limit);
        stringBuilder.append("&page=");
        stringBuilder.append(offset);

        String apiGetImageAssetsUrl = stringBuilder.toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsUrl);

        Client client = ClientBuilder.newClient();

        Response response = client.target(apiGetImageAssetsUrl)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", oauthHeader)
                .get();

        JSONArray responseJsonArray = new JSONArray(response.readEntity(String.class));

        return Utils.createImageAssetListFromJSONArray(responseJsonArray);
    }

    public int getImageAssetCount() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        String apiGetImageAssetsUrl = baseUrl+IMAGE_ASSETS_PATH;

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsUrl);

        Client client = ClientBuilder.newClient();

        Response response = client.target(apiGetImageAssetsUrl)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", oauthHeader)
                .get();

        JSONArray responseJsonArray = new JSONArray(response.readEntity(String.class));

        return responseJsonArray.length();
    }

}
