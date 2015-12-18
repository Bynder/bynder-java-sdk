package com.getbynder.api;

import java.io.IOException;
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
import com.getbynder.api.domain.MediaAsset;
import com.getbynder.api.domain.Metaproperty;
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

    private static final String LOGIN_PATH = BynderProperties.getInstance().getProperty("LOGIN_PATH");
    private static final String CATEGORIES_PATH = BynderProperties.getInstance().getProperty("CATEGORIES_PATH");
    private static final String IMAGE_ASSETS_PATH = BynderProperties.getInstance().getProperty("IMAGE_ASSETS_PATH");
    private static final String MEDIA_PATH = BynderProperties.getInstance().getProperty("MEDIA_PATH");
    private static final String METAPROPERTIES_PATH = BynderProperties.getInstance().getProperty("METAPROPERTIES_PATH");

    private static final String CONSUMER_KEY = BynderProperties.getInstance().getProperty("CONSUMER_KEY");
    private static final String CONSUMER_SECRET = BynderProperties.getInstance().getProperty("CONSUMER_SECRET");
    private static final String ACCESS_TOKEN = BynderProperties.getInstance().getProperty("ACCESS_TOKEN");
    private static final String ACCESS_TOKEN_SECRET = BynderProperties.getInstance().getProperty("ACCESS_TOKEN_SECRET");

    private final String baseUrl;
    private final UserAccessData userAccessData;

    public BynderService(final String baseUrl, final String username, final String password) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException, URISyntaxException {
        this.baseUrl = baseUrl;
        this.userAccessData = getUserAccessData(username, password);
    }

    public UserAccessData getUserAccessData(final String username, final String password) throws OAuthMessageSignerException, OAuthExpectationFailedException,
    OAuthCommunicationException, ClientProtocolException, IOException, URISyntaxException {

        // create a consumer object and configure it with the access token and token secret obtained from the service provider
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("consumerId", CONSUMER_KEY));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

        // create an HTTP request to a protected resource
        URI loginUri = Utils.createRequestURI(new URL(baseUrl), LOGIN_PATH, params);

        HttpPost request = new HttpPost(loginUri);

        // set the parameters into the request
        request.setEntity(new UrlEncodedFormEntity(params));

        // sign the request
        consumer.sign(request);

        // send the request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        // if request was unsuccessful
        if(response.getStatusLine().getStatusCode() != 200){
            throw new HttpResponseException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
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

    public List<Category> getCategories() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, UnsupportedEncodingException, MalformedURLException {

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

    public List<ImageAsset> getImageAssets() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

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

    public List<ImageAsset> getImageAssets(final int limit, final int offset) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

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

    public int getImageAssetCount() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

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

    public List<MediaAsset> getMediaAssets() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        String apiGetImageAssetsUrl = baseUrl+MEDIA_PATH;

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsUrl);

        Client client = ClientBuilder.newClient();

        Response response = client.target(apiGetImageAssetsUrl)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", oauthHeader)
                .get();

        JSONArray responseJsonArray = new JSONArray(response.readEntity(String.class));

        return Utils.createMediaAssetListFromJSONArray(responseJsonArray);
    }

    public int setMediaAssetProperties(final MediaAsset mediaAsset) throws URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException  {

        if(mediaAsset.getId() == null){
            throw new IllegalArgumentException("Asset ID shall not be null");
        }

        List<BasicNameValuePair> params = new ArrayList<>();

        if(mediaAsset.getName() != null) {
            params.add(new BasicNameValuePair("name", mediaAsset.getName()));
        }

        if(mediaAsset.getDescription() != null) {
            params.add(new BasicNameValuePair("description", mediaAsset.getDescription()));
        }

        if(mediaAsset.getCopyright() != null) {
            params.add(new BasicNameValuePair("copyright", mediaAsset.getCopyright()));
        }

        if(mediaAsset.getArchive() != null) {
            params.add(new BasicNameValuePair("archive", mediaAsset.getArchive().toString()));
        }

        if(mediaAsset.getPublicationDate() != null) {
            if(Utils.isDateFormatValid(mediaAsset.getPublicationDate())){
                params.add(new BasicNameValuePair("datePublished", mediaAsset.getPublicationDate()));
            } else {
                throw new IllegalArgumentException(String.format("Invalid format for inserted publication date: %s, should be ISO8601-format [yyyy-MM-dd'T'HH:mm:ss'Z']", mediaAsset.getPublicationDate()));
            }
        }

        StringBuilder stringBuilder = new StringBuilder(MEDIA_PATH);
        stringBuilder.append(mediaAsset.getId());
        stringBuilder.append("/");

        String relativePath = stringBuilder.toString();

        // create an HTTP request to a protected resource
        URI requestUri = Utils.createRequestURI(new URL(baseUrl), relativePath, params);

        HttpPost request = Utils.createPostRequest(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, requestUri, params);

        // send the request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        if(response.getStatusLine().getStatusCode() == 404) {
            throw new HttpResponseException(response.getStatusLine().getStatusCode(), String.format("Check if Asset ID: %s exists", mediaAsset.getId()));
        }

        return response.getStatusLine().getStatusCode();
    }

    public List<Metaproperty> getAllMetaproperties() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        String apiGetAllMetapropertiesUrl = baseUrl+METAPROPERTIES_PATH;

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetAllMetapropertiesUrl);

        Client client = ClientBuilder.newClient();

        Response response = client.target(apiGetAllMetapropertiesUrl)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", oauthHeader)
                .get();

        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        return Utils.createMetapropertyListFromJSONObject(jsonObject);
    }

    public int addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds) throws URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException {

        List<BasicNameValuePair> params = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder("metaproperty.");
        stringBuilder.append(metapropertyId);

        String paramName = stringBuilder.toString();

        params.add(new BasicNameValuePair("id", assetId));

        stringBuilder = new StringBuilder();

        for(String optionId : optionsIds) {
            stringBuilder.append(optionId);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);

        String paramValues = stringBuilder.toString();

        params.add(new BasicNameValuePair(paramName, paramValues));

        // create an HTTP request to a protected resource
        URI requestUri = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH);

        HttpPost request = Utils.createPostRequest(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, requestUri, params);

        // send the request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        return response.getStatusLine().getStatusCode();
    }

}
