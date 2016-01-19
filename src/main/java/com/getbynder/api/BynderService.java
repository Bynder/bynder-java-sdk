package com.getbynder.api;

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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.getbynder.api.domain.Category;
import com.getbynder.api.domain.MediaAsset;
import com.getbynder.api.domain.Metaproperty;
import com.getbynder.api.domain.UserAccessData;
import com.getbynder.api.util.ApiUtils;
import com.getbynder.api.util.BooleanTypeAdapter;
import com.getbynder.api.util.ConfigProperties;
import com.getbynder.api.util.ErrorMessages;
import com.getbynder.api.util.SecretProperties;
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

    private static final String LOGIN_PATH = ConfigProperties.getInstance().getProperty("LOGIN_PATH");
    private static final String CATEGORIES_PATH = ConfigProperties.getInstance().getProperty("CATEGORIES_PATH");
    private static final String IMAGE_ASSETS_PATH = ConfigProperties.getInstance().getProperty("IMAGE_ASSETS_PATH");
    private static final String MEDIA_PATH = ConfigProperties.getInstance().getProperty("MEDIA_PATH");
    private static final String METAPROPERTIES_PATH = ConfigProperties.getInstance().getProperty("METAPROPERTIES_PATH");
    private static final String LIMIT_PARAMETER = ConfigProperties.getInstance().getProperty("LIMIT_PARAMETER");
    private static final String OFFSET_PARAMETER = ConfigProperties.getInstance().getProperty("OFFSET_PARAMETER");
    private static final String VERSIONS_PARAMETER = ConfigProperties.getInstance().getProperty("VERSIONS_PARAMETER");
    private static final String METAPROPERTY_ID_PARAMETER = ConfigProperties.getInstance().getProperty("METAPROPERTY_ID_PARAMETER");

    private final String CONSUMER_KEY = SecretProperties.getInstance().getProperty("CONSUMER_KEY");
    private final String CONSUMER_SECRET = SecretProperties.getInstance().getProperty("CONSUMER_SECRET");

    private final String baseUrl;
    private final UserAccessData userAccessData;

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
        URI loginUri = ApiUtils.createRequestURI(new URL(baseUrl), LOGIN_PATH, params);

        HttpPost request = ApiUtils.createPostRequest(CONSUMER_KEY, CONSUMER_SECRET, null, loginUri, params);

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

    public List<Category> getCategories() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, UnsupportedEncodingException, MalformedURLException {

        String apiGetCategoriesUrl = baseUrl.concat(CATEGORIES_PATH);

        String oauthHeader = ApiUtils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetCategoriesUrl);

        Response response = ApiUtils.getRequestResponse(apiGetCategoriesUrl, oauthHeader);

        Type collectionType = new TypeToken<List<Category>>(){}.getType();
        List<Category> categories = new Gson().fromJson(response.readEntity(String.class), collectionType);

        return categories;
    }

    public List<MediaAsset> getAllImageAssets() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        String apiGetAllImageAssetsUrl = baseUrl.concat(IMAGE_ASSETS_PATH);

        String oauthHeader = ApiUtils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetAllImageAssetsUrl);

        Response response = ApiUtils.getRequestResponse(apiGetAllImageAssetsUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> allImageAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return allImageAssets;
    }

    public List<MediaAsset> getImageAssets(final int limit, final int offset) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append(IMAGE_ASSETS_PATH);
        stringBuilder.append(LIMIT_PARAMETER);
        stringBuilder.append(limit);
        stringBuilder.append(OFFSET_PARAMETER);
        stringBuilder.append(offset);

        String apiGetImageAssetsUrl = stringBuilder.toString();

        String oauthHeader = ApiUtils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsUrl);

        Response response = ApiUtils.getRequestResponse(apiGetImageAssetsUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> imageAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return imageAssets;
    }

    public int getImageAssetsTotal() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        String apiGetImageAssetsTotalUrl = baseUrl.concat(IMAGE_ASSETS_PATH);

        String oauthHeader = ApiUtils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetImageAssetsTotalUrl);

        Response response = ApiUtils.getRequestResponse(apiGetImageAssetsTotalUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> imageAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return imageAssets.size();
    }

    public List<MediaAsset> getAllMediaAssets() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        String apiGetAllMediaAssetsUrl = baseUrl.concat(MEDIA_PATH);

        String oauthHeader = ApiUtils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetAllMediaAssetsUrl);

        Response response = ApiUtils.getRequestResponse(apiGetAllMediaAssetsUrl, oauthHeader);

        Type collectionType = new TypeToken<List<MediaAsset>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        List<MediaAsset> allMediaAssets = gson.fromJson(response.readEntity(String.class), collectionType);

        return allMediaAssets;
    }

    public MediaAsset getMediaAssetById(final String id, final Boolean includeVersions) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, MalformedURLException {

        int versionsValue = 0;
        if(includeVersions != null) {
            versionsValue = includeVersions ? 1 : 0;
        }

        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append(MEDIA_PATH);
        stringBuilder.append(id);
        stringBuilder.append(VERSIONS_PARAMETER);
        stringBuilder.append(versionsValue);

        String apiGetMediaAssetByIdUrl = stringBuilder.toString();

        String oauthHeader = ApiUtils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetMediaAssetByIdUrl);

        Response response = ApiUtils.getRequestResponse(apiGetMediaAssetByIdUrl, oauthHeader);

        Type type = new TypeToken<MediaAsset>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter()).create();
        MediaAsset mediaAsset = gson.fromJson(response.readEntity(String.class), type);

        return mediaAsset;
    }


    public int setMediaAssetProperties(final MediaAsset mediaAsset) throws URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException  {

        if (mediaAsset.getId() == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_MEDIA_ASSET_ID);
        }

        List<BasicNameValuePair> params = new ArrayList<>();

        //check which parameters to update
        if (mediaAsset.getName() != null) {
            params.add(new BasicNameValuePair("name", mediaAsset.getName()));
        }

        if (mediaAsset.getDescription() != null) {
            params.add(new BasicNameValuePair("description", mediaAsset.getDescription()));
        }

        if (mediaAsset.getCopyright() != null) {
            params.add(new BasicNameValuePair("copyright", mediaAsset.getCopyright()));
        }

        if (mediaAsset.getArchive() != null) {
            params.add(new BasicNameValuePair("archive", mediaAsset.getArchive().toString()));
        }

        if (mediaAsset.getPublicationDate() != null) {
            if (ApiUtils.isDateFormatValid(mediaAsset.getPublicationDate())) {
                params.add(new BasicNameValuePair("datePublished", mediaAsset.getPublicationDate()));
            } else {
                throw new IllegalArgumentException(ErrorMessages.INVALID_PUBLICATION_DATE_FORMAT);
            }
        }

        StringBuilder stringBuilder = new StringBuilder(MEDIA_PATH);
        stringBuilder.append(mediaAsset.getId());
        stringBuilder.append("/");

        String relativePath = stringBuilder.toString();

        // create an HTTP request to a protected resource
        URI requestUri = ApiUtils.createRequestURI(new URL(baseUrl), relativePath, params);

        HttpPost request = ApiUtils.createPostRequest(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, requestUri, params);

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

        String oauthHeader = ApiUtils.createOAuthHeader(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, apiGetAllMetapropertiesUrl);

        Response response = ApiUtils.getRequestResponse(apiGetAllMetapropertiesUrl, oauthHeader);

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

        StringBuilder stringBuilder = new StringBuilder(METAPROPERTY_ID_PARAMETER);
        stringBuilder.append(metapropertyId);

        String paramName = stringBuilder.toString();

        params.add(new BasicNameValuePair("id", assetId));

        stringBuilder = new StringBuilder();

        for (String optionId : optionsIds) {
            stringBuilder.append(",");
            stringBuilder.append(optionId);
        }
        stringBuilder.deleteCharAt(0);

        String paramValues = stringBuilder.toString();

        params.add(new BasicNameValuePair(paramName, paramValues));

        // create an HTTP request to a protected resource
        URI requestUri = ApiUtils.createRequestURI(new URL(baseUrl), MEDIA_PATH);

        HttpPost request = ApiUtils.createPostRequest(CONSUMER_KEY, CONSUMER_SECRET, userAccessData, requestUri, params);

        // send the post request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        return response.getStatusLine().getStatusCode();
    }

}
