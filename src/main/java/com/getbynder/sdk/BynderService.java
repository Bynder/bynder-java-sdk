package com.getbynder.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.domain.UserAccessData;
import com.getbynder.sdk.util.BooleanTypeAdapter;
import com.getbynder.sdk.util.ConfigProperties;
import com.getbynder.sdk.util.ErrorMessages;
import com.getbynder.sdk.util.SecretProperties;
import com.getbynder.sdk.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 *
 * @author daniel.sequeira
 */
public class BynderService {

    private final String BASE_URL = ConfigProperties.getInstance().getProperty("BASE_URL");

    private static final String LOGIN_PATH = ConfigProperties.getInstance().getProperty("LOGIN_PATH");
    private static final String CATEGORIES_PATH = ConfigProperties.getInstance().getProperty("CATEGORIES_PATH");
    private static final String MEDIA_PATH = ConfigProperties.getInstance().getProperty("MEDIA_PATH");
    private static final String METAPROPERTIES_PATH = ConfigProperties.getInstance().getProperty("METAPROPERTIES_PATH");
    private static final String LIMIT_PARAMETER = ConfigProperties.getInstance().getProperty("LIMIT_PARAMETER");
    private static final String OFFSET_PARAMETER = ConfigProperties.getInstance().getProperty("OFFSET_PARAMETER");
    private static final String VERSIONS_PARAMETER = ConfigProperties.getInstance().getProperty("VERSIONS_PARAMETER");
    private static final String METAPROPERTY_ID_PARAMETER = ConfigProperties.getInstance().getProperty("METAPROPERTY_ID_PARAMETER");
    private static final String KEYWORD_PARAMETER = ConfigProperties.getInstance().getProperty("KEYWORD_PARAMETER");
    private static final String METAPROPERTIES_PARAMETER = ConfigProperties.getInstance().getProperty("METAPROPERTIES_PARAMETER");
    private static final String COUNT_PARAMETER = ConfigProperties.getInstance().getProperty("COUNT_PARAMETER");
    private static final String MEDIA_TYPE_PARAMETER = ConfigProperties.getInstance().getProperty("MEDIA_TYPE_PARAMETER");
    private static final String MEDIA_TYPE_IMAGE = ConfigProperties.getInstance().getProperty("MEDIA_TYPE_IMAGE");
    private static final String MEDIA_PATH_ID = ConfigProperties.getInstance().getProperty("MEDIA_PATH_ID");

    private final String CONSUMER_KEY = SecretProperties.getInstance().getProperty("CONSUMER_KEY");
    private final String CONSUMER_SECRET = SecretProperties.getInstance().getProperty("CONSUMER_SECRET");

    private final String baseUrl;
    private final UserAccessData userAccessData;

    public BynderService() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException, URISyntaxException {
        this.baseUrl = BASE_URL;
        this.userAccessData = new UserAccessData(CONSUMER_KEY, SecretProperties.getInstance().getProperty("TOKEN_KEY"), SecretProperties.getInstance().getProperty("TOKEN_SECRET"), true);
    }

    public BynderService(final String username, final String password) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException, URISyntaxException {
        this.baseUrl = BASE_URL;
        this.userAccessData = getUserAccessData(username, password);
    }

    public BynderService(final String baseUrl, final String username, final String password) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException, URISyntaxException {
        this.baseUrl = baseUrl;
        this.userAccessData = getUserAccessData(username, password);
    }

    public UserAccessData getUserAccessData(final String username, final String password) throws OAuthMessageSignerException, OAuthExpectationFailedException,
    OAuthCommunicationException, ClientProtocolException, IOException, URISyntaxException {

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("consumerId", CONSUMER_KEY));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

        // create an HTTP request to a protected resource
        URI loginUri = Utils.createRequestURI(new URL(baseUrl), LOGIN_PATH, params);

        HttpPost request = Utils.createPostRequest(CONSUMER_KEY, CONSUMER_SECRET, null, loginUri, params);

        // send the post request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        // if request was unsuccessful
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new HttpResponseException(response.getStatusLine().getStatusCode(), ErrorMessages.LOGIN_REQUEST_FAILED);
        }

        // if successful, return the response body
        HttpEntity resEntity = response.getEntity();
        String responseBody = "";

        if (resEntity != null) {
            responseBody = EntityUtils.toString(resEntity);
        }

        //close this stream
        httpClient.close();

        UserAccessData userAccessData = new Gson().fromJson(responseBody, UserAccessData.class);

        return userAccessData;
    }

    public List<Category> getCategories() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, UnsupportedEncodingException, MalformedURLException, URISyntaxException {

        String apiGetCategoriesUrl = Utils.createRequestURI(new URL(baseUrl), CATEGORIES_PATH).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetCategoriesUrl);

        Response response = Utils.getRequestResponse(apiGetCategoriesUrl, oauthHeader);

        Type collectionType = new TypeToken<List<Category>>(){}.getType();
        List<Category> categories = new Gson().fromJson(response.readEntity(String.class), collectionType);

        return categories;
    }

    public List<MediaAsset> getAllImageAssets() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException, URISyntaxException {

        int total = getImageAssetsTotal();

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(MEDIA_TYPE_PARAMETER, MEDIA_TYPE_IMAGE));
        params.add(new BasicNameValuePair(LIMIT_PARAMETER, Integer.toString(total)));

        String apiGetAllImageAssetsUrl = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH, params).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetAllImageAssetsUrl);

        Response response = Utils.getRequestResponse(apiGetAllImageAssetsUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> allImageAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return allImageAssets;
    }

    public List<MediaAsset> getImageAssets(final int limit, final int offset) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException, URISyntaxException {

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(MEDIA_TYPE_PARAMETER, MEDIA_TYPE_IMAGE));
        params.add(new BasicNameValuePair(LIMIT_PARAMETER, Integer.toString(limit)));
        params.add(new BasicNameValuePair(OFFSET_PARAMETER, Integer.toString(offset)));

        String apiGetImageAssetsUrl = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH, params).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsUrl);

        Response response = Utils.getRequestResponse(apiGetImageAssetsUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> imageAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return imageAssets;
    }

    public List<MediaAsset> getImageAssetsByKeyword(final String keyword) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException, URISyntaxException {

        if(keyword == null || keyword.isEmpty()) {
            return getAllImageAssets();
        }

        int total = getImageAssetsTotal();

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(MEDIA_TYPE_PARAMETER, MEDIA_TYPE_IMAGE));
        params.add(new BasicNameValuePair(KEYWORD_PARAMETER, keyword));
        params.add(new BasicNameValuePair(LIMIT_PARAMETER, Integer.toString(total)));

        String apiGetImageAssetsUrl = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH, params).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsUrl);

        Response response = Utils.getRequestResponse(apiGetImageAssetsUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> imageAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return imageAssets;
    }

    public List<MediaAsset> getImageAssetsByMetapropertyId(final String metapropertyId) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException, URISyntaxException {

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(MEDIA_TYPE_PARAMETER, MEDIA_TYPE_IMAGE));
        params.add(new BasicNameValuePair(METAPROPERTIES_PARAMETER, metapropertyId));

        String apiGetImageAssetsUrl = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH, params).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsUrl);

        Response response = Utils.getRequestResponse(apiGetImageAssetsUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> imageAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return imageAssets;
    }

    public int getImageAssetsTotal() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException, URISyntaxException {

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(MEDIA_TYPE_PARAMETER, MEDIA_TYPE_IMAGE));
        params.add(new BasicNameValuePair(COUNT_PARAMETER, "1"));

        String apiGetImageAssetsTotalUrl = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH, params).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsTotalUrl);

        Response response = Utils.getRequestResponse(apiGetImageAssetsTotalUrl, oauthHeader);

        return Utils.getTotalCountFromJson(response.readEntity(String.class));
    }

    public int getMediaAssetsTotal() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException, URISyntaxException {

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(COUNT_PARAMETER, "1"));

        String apiGetMediaAssetsTotalUrl = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH, params).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetMediaAssetsTotalUrl);

        Response response = Utils.getRequestResponse(apiGetMediaAssetsTotalUrl, oauthHeader);

        return Utils.getTotalCountFromJson(response.readEntity(String.class));
    }

    public List<MediaAsset> getAllMediaAssets() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException, URISyntaxException {

        int total = getMediaAssetsTotal();

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(LIMIT_PARAMETER, Integer.toString(total)));

        String apiGetAllMediaAssetsUrl = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH, params).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetAllMediaAssetsUrl);

        Response response = Utils.getRequestResponse(apiGetAllMediaAssetsUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> allMediaAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return allMediaAssets;
    }

    public MediaAsset getMediaAssetById(final String id, final Boolean includeVersions) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException, HttpResponseException, URISyntaxException {

        int versionsValue = 0;
        if(includeVersions != null) {
            versionsValue = includeVersions ? 1 : 0;
        }

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(VERSIONS_PARAMETER, Integer.toString(versionsValue)));

        String apiGetMediaAssetByIdUrl = Utils.createRequestURI(new URL(baseUrl), String.format(MEDIA_PATH_ID, id), params).toString();

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetMediaAssetByIdUrl);

        Response response = Utils.getRequestResponse(apiGetMediaAssetByIdUrl, oauthHeader);

        // if request was unsuccessful
        if (response.getStatusInfo().getStatusCode() != 200) {
            throw new HttpResponseException(response.getStatusInfo().getStatusCode(), ErrorMessages.MEDIA_ASSET_ID_NOT_FOUND);
        }

        Type type = new TypeToken<MediaAsset>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        MediaAsset mediaAsset = gson.fromJson(response.readEntity(String.class), type);

        return mediaAsset;
    }

    public int setMediaAssetProperties(final MediaAsset mediaAsset) throws URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException, IllegalArgumentException, IllegalAccessException {

        if (mediaAsset.getId() == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_MEDIA_ASSET_ID);
        }

        List<BasicNameValuePair> params = mediaAsset.getFieldsNameValuePairs();

        // create an HTTP request to a protected resource
        URI requestUri = Utils.createRequestURI(new URL(baseUrl), String.format(MEDIA_PATH_ID, mediaAsset.getId()), params);

        HttpPost request = Utils.createPostRequest(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, requestUri, params);

        // send the post request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() == 404) {
            throw new HttpResponseException(response.getStatusLine().getStatusCode(), ErrorMessages.MEDIA_ASSET_ID_NOT_FOUND);
        }

        return response.getStatusLine().getStatusCode();
    }

    public List<Metaproperty> getAllMetaproperties() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        String apiGetAllMetapropertiesUrl = baseUrl.concat(METAPROPERTIES_PATH);

        String oauthHeader = Utils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetAllMetapropertiesUrl);

        Response response = Utils.getRequestResponse(apiGetAllMetapropertiesUrl, oauthHeader);

        JsonObject jsonObject = new JsonParser().parse(response.readEntity(String.class)).getAsJsonObject();

        Set<Entry<String,JsonElement>> elements = jsonObject.entrySet();

        List<Metaproperty> allMetaproperties = new ArrayList<>();

        for (Entry<String,JsonElement> element: elements) {
            Metaproperty metaproperty = new Gson().fromJson(element.getValue(), Metaproperty.class);
            allMetaproperties.add(metaproperty);
        }

        return allMetaproperties;
    }

    public int addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds) throws URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException {

        List<BasicNameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("id", assetId));

        String paramName = String.format(METAPROPERTY_ID_PARAMETER, metapropertyId);

        String paramValues = StringUtils.join(optionsIds, ',');

        params.add(new BasicNameValuePair(paramName, paramValues));

        // create an HTTP request to a protected resource
        URI requestUri = Utils.createRequestURI(new URL(baseUrl), MEDIA_PATH);

        HttpPost request = Utils.createPostRequest(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, requestUri, params);

        // send the post request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        return response.getStatusLine().getStatusCode();
    }

}
