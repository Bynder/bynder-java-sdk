package com.getbynder.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import com.getbynder.sdk.api.BynderApi;
import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.MediaCount;
import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.domain.Tag;
import com.getbynder.sdk.domain.UserAccessData;
import com.getbynder.sdk.util.SecretProperties;
import com.getbynder.sdk.util.Utils;

import retrofit2.Response;

/**
 *
 * @author daniel.sequeira
 */
public class BynderApiService {

    private final String BASE_URL = SecretProperties.getInstance().getProperty("BASE_URL");
    private final String REQUEST_TOKEN_KEY = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_KEY");
    private final String REQUEST_TOKEN_SECRET = SecretProperties.getInstance().getProperty("REQUEST_TOKEN_SECRET");

    public final String LOGIN_REQUEST_FAILED = "Could not login to the API. The request was unsuccessful.";
    public final String REQUEST_TOKEN_FAILED = "Could not obtain request token. The request was unsuccessful.";

    private BynderApi bynderApi;

    public BynderApiService() {
        bynderApi = Utils.createApiService(BynderApi.class, BASE_URL, SecretProperties.getInstance().getProperty("ACCESS_TOKEN_KEY"), SecretProperties.getInstance().getProperty("ACCESS_TOKEN_SECRET"));
    }

    public BynderApiService(final String username, final String password) throws IOException {

        Utils.checkNotNull("username", username);
        Utils.checkNotNull("password", password);

        UserAccessData userAccessData = login(username, password);
        bynderApi = Utils.createApiService(BynderApi.class, BASE_URL, userAccessData.getTokenKey(), userAccessData.getTokenSecret());
    }

    public BynderApiService(final String baseUrl, final String username, final String password) throws IOException {

        Utils.checkNotNull("username", username);
        Utils.checkNotNull("password", password);

        UserAccessData userAccessData = login(username, password);
        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, userAccessData.getTokenKey(), userAccessData.getTokenSecret());
    }

    public UserAccessData login(final String username, final String password) throws IOException {

        Utils.checkNotNull("username", username);
        Utils.checkNotNull("password", password);

        bynderApi = Utils.createApiService(BynderApi.class, BASE_URL, REQUEST_TOKEN_KEY, REQUEST_TOKEN_SECRET);
        Response<UserAccessData> response = bynderApi.login(username, password).execute();

        if(response.code() != HttpStatus.SC_OK) {
            throw new HttpResponseException(response.code(), LOGIN_REQUEST_FAILED);
        }

        return response.body();
    }

    public Map<String, String> getRequestToken() throws IOException {
        BynderApi bynderApiForRequestToken = Utils.createApiService(BynderApi.class, BASE_URL, null, null);
        Response<String> response = bynderApiForRequestToken.getRequestToken().execute();

        if(response.code() != HttpStatus.SC_OK) {
            throw new HttpResponseException(response.code(), REQUEST_TOKEN_FAILED);
        }

        return Utils.buildMapFromResponse(response.body());
    }

    public Map<String, String> getAccessToken(final String requestTokenKey, final String requestTokenSecret) throws IOException {

        Utils.checkNotNull("request token key", requestTokenKey);
        Utils.checkNotNull("request token secret", requestTokenSecret);

        BynderApi bynderApiForAccessToken = Utils.createApiService(BynderApi.class, BASE_URL, requestTokenKey, requestTokenSecret);
        Response<String> response = bynderApiForAccessToken.getRequestToken().execute();

        if(response.code() != HttpStatus.SC_OK) {
            throw new HttpResponseException(response.code(), REQUEST_TOKEN_FAILED);
        }

        return Utils.buildMapFromResponse(response.body());
    }

    public List<Category> getCategories() throws IOException {
        Response<List<Category>> response = bynderApi.getCategories().execute();
        return response.body();
    }

    public List<Tag> getTags() throws IOException {
        Response<List<Tag>> response = bynderApi.getTags().execute();
        return response.body();
    }

    public Map<String, Metaproperty> getMetaproperties() throws IOException {
        Response<Map<String, Metaproperty>> response = bynderApi.getMetaproperties().execute();
        return response.body();
    }

    public List<MediaAsset> getMediaAssets(final String type, final String keyword, final Integer limit, final Integer page, final String propertyOptionId) throws IOException {
        Response<List<MediaAsset>> response = bynderApi.getMediaAssets(type, keyword, limit, page, propertyOptionId).execute();
        return response.body();
    }

    public MediaAsset getMediaAssetById(final String id, final Boolean versions) throws IOException {

        Utils.checkNotNull("media asset id", id);

        Response<MediaAsset> response = bynderApi.getMediaAssetById(id, versions).execute();
        return response.body();
    }

    public List<MediaAsset> getImageAssets(final String keyword, final Integer limit, final Integer page) throws IOException {

        Response<List<MediaAsset>> response = bynderApi.getImageAssets(keyword, limit, page).execute();
        return response.body();
    }

    public List<MediaAsset> getImageAssets(final String keyword, final Integer limit, final Integer page, final List<String> propertyOptionIds) throws IOException {

        List<MediaAsset> response = getImageAssets(keyword, limit, page);

        if (propertyOptionIds != null && !propertyOptionIds.isEmpty()) {
            List<MediaAsset> mediaAssets = new ArrayList<>();

            for (MediaAsset mediaAsset : response) {
                if (mediaAsset.getPropertyOptions() != null) {
                    if (mediaAsset.getPropertyOptions().containsAll(propertyOptionIds)) {
                        mediaAssets.add(mediaAsset);
                    }
                }
            }
            return mediaAssets;
        } else {
            return response;
        }
    }

    public List<MediaAsset> getImageAssetsByMetapropertyId(final String propertyOptionId) throws IOException {

        Utils.checkNotNull("metaproperty id", propertyOptionId);

        Response<List<MediaAsset>> response = bynderApi.getImageAssetsByMetapropertyId(propertyOptionId).execute();
        return response.body();
    }

    public int getImageAssetsTotal() throws IOException {
        Response<MediaCount> response = bynderApi.getImageAssetsCount().execute();
        MediaCount mediaCount = response.body();
        return mediaCount.getCount().getTotal();
    }

    public int setMediaAssetProperties(final String id, final String name, final String description, final String copyright, final Boolean archive, final String datePublished) throws IOException {

        Utils.checkNotNull("media asset id", id);

        Response<Void> response = bynderApi.setMediaAssetProperties(id, name, description, copyright, archive, datePublished).execute();
        return response.code();
    }

    public int addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds) throws IOException {

        Utils.checkNotNull("media asset id", assetId);
        Utils.checkNotNull("metaproperty id", metapropertyId);

        Map<String, String> metapropertyOptions = new HashMap<>();
        metapropertyOptions.put(String.format("metaproperty.%s", metapropertyId), StringUtils.join(optionsIds, Utils.STR_COMMA));

        Response<Void> response = bynderApi.addMetapropertyToAsset(assetId, metapropertyOptions).execute();
        return response.code();
    }
}
