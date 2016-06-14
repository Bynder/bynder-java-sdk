package com.getbynder.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

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

    private final String ACCESS_TOKEN_FAILED = "Could not obtain access token. Reason: %s %s";
    private final String LOGIN_REQUEST_FAILED = "Could not login to the API. Reason: %s %s";
    private final String REQUEST_TOKEN_FAILED = "Could not obtain request token. Reason: %s %s";

    private final int DEFAULT_LIMIT = 50;

    private BynderApi bynderApi;

    public BynderApiService() {
        Utils.checkNotNull("ACCESS_TOKEN_KEY", SecretProperties.getInstance().getProperty("ACCESS_TOKEN_KEY"));
        Utils.checkNotNull("ACCESS_TOKEN_SECRET", SecretProperties.getInstance().getProperty("ACCESS_TOKEN_SECRET"));

        bynderApi = Utils.createApiService(BynderApi.class, BASE_URL, SecretProperties.getInstance().getProperty("ACCESS_TOKEN_KEY"), SecretProperties.getInstance().getProperty("ACCESS_TOKEN_SECRET"));
    }

    public BynderApiService(final String username, final String password) throws IOException {
        UserAccessData userAccessData = login(username, password);
        bynderApi = Utils.createApiService(BynderApi.class, BASE_URL, userAccessData.getTokenKey(), userAccessData.getTokenSecret());
    }

    public BynderApiService(final String baseUrl, final String username, final String password) throws IOException {
        UserAccessData userAccessData = login(username, password);
        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, userAccessData.getTokenKey(), userAccessData.getTokenSecret());
    }

    public UserAccessData login(final String username, final String password) throws IOException {
        Utils.checkNotNull("username", username);
        Utils.checkNotNull("password", password);
        Utils.checkNotNull("REQUEST_TOKEN_KEY", REQUEST_TOKEN_KEY);
        Utils.checkNotNull("REQUEST_TOKEN_SECRET", REQUEST_TOKEN_SECRET);

        BynderApi bynderApiForLogin = Utils.createApiService(BynderApi.class, BASE_URL, REQUEST_TOKEN_KEY, REQUEST_TOKEN_SECRET);
        Response<UserAccessData> response = bynderApiForLogin.login(username, password).execute();

        Utils.validateResponse(response, LOGIN_REQUEST_FAILED);

        return response.body();
    }

    public Map<String, String> getRequestToken() throws IOException {
        BynderApi bynderApiForRequestToken = Utils.createApiService(BynderApi.class, BASE_URL, null, null);
        Response<String> response = bynderApiForRequestToken.getRequestToken().execute();

        Utils.validateResponse(response, REQUEST_TOKEN_FAILED);

        return Utils.buildMapFromResponse(response.body());
    }

    public Map<String, String> getAccessToken(final String requestTokenKey, final String requestTokenSecret) throws IOException {
        Utils.checkNotNull("requestTokenKey", requestTokenKey);
        Utils.checkNotNull("requestTokenSecret", requestTokenSecret);

        BynderApi bynderApiForAccessToken = Utils.createApiService(BynderApi.class, BASE_URL, requestTokenKey, requestTokenSecret);
        Response<String> response = bynderApiForAccessToken.getRequestToken().execute();

        Utils.validateResponse(response, ACCESS_TOKEN_FAILED);

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

        Map<String, Metaproperty> metaproperties = response.body();
        for (Entry<String, Metaproperty> entry : metaproperties.entrySet()) {
            if (entry.getValue().getOptions().size() > 0) {
                updateOptionsMediaCount(entry.getValue().getOptions());
            }
        }

        return metaproperties;
    }

    private void updateOptionsMediaCount(final List<Metaproperty> options) throws IOException {
        for (Metaproperty option : options) {
            option.setMediaCount(getImageAssetsTotal(null, Arrays.asList(option.getId())));
            if (option.getOptions() != null && option.getOptions().size() > 0) {
                updateOptionsMediaCount(option.getOptions());
            }
        }
    }

    public List<MediaAsset> getMediaAssets(final String type, final String keyword, final Integer limit, final Integer page, final String propertyOptionId) throws IOException {
        Response<List<MediaAsset>> response = bynderApi.getMediaAssets(type, keyword, limit, page, propertyOptionId).execute();
        return response.body();
    }

    public MediaAsset getMediaAssetById(final String id, final Boolean versions) throws IOException {
        Utils.checkNotNull("id", id);

        Response<MediaAsset> response = bynderApi.getMediaAssetById(id, versions).execute();
        return response.body();
    }

    public List<MediaAsset> getImageAssets(final String keyword, final Integer limit, final Integer page) throws IOException {
        Response<List<MediaAsset>> response = bynderApi.getImageAssets(keyword, limit, page, null).execute();
        return response.body();
    }

    // get the N-th set of limit-results of images assets that contain all the metaproperties ids inside the propertyOptionIds list
    public List<MediaAsset> getImageAssets(final String keyword, final Integer limit, final Integer page, final List<String> propertyOptionIds) throws IOException {
        // if propertyOptions is null or empty call getImageAssets
        if (propertyOptionIds == null || propertyOptionIds.size() == 0) {
            return getImageAssets(keyword, limit, page);
        }

        List<MediaAsset> imageAssets = new ArrayList<>();
        List<MediaAsset> imageAssetsOffset = new ArrayList<>();

        // number of the first N image assets that shall be ignored
        int ignoreCount = limit * (page - 1);
        int offset = 1;

        // the condition 'offset == 1' is included in the while loop just for its first iteration
        // if imageAssets already has the same number of elements as the asked limit and the imageAssetsOffset retrieved is empty, stop the while
        while (offset == 1 || (imageAssets.size() < limit && imageAssetsOffset.size() != 0)) {

            imageAssetsOffset = getImageAssets(keyword, DEFAULT_LIMIT, offset);

            for (MediaAsset imageAsset : imageAssetsOffset) {
                // if imageAssets already has the same number of elements as the asked limit, break the for loop
                if (imageAssets.size() >= limit) {
                    break;
                }

                if (imageAsset.getPropertyOptions() != null && imageAsset.getPropertyOptions().containsAll(propertyOptionIds)) {
                    // decrement ignoreCount and if it is already less than zero add the imageAsset to the list
                    ignoreCount--;
                    if (ignoreCount < 0) {
                        imageAssets.add(imageAsset);
                    }
                }
            }
            offset++;
        }

        return imageAssets;
    }

    public List<MediaAsset> getImageAssetsByMetapropertyId(final String propertyOptionId) throws IOException {
        Utils.checkNotNull("propertyOptionId", propertyOptionId);

        Response<List<MediaAsset>> response = bynderApi.getImageAssetsByMetapropertyId(propertyOptionId).execute();
        return response.body();
    }

    public int getImageAssetsTotal() throws IOException {
        Response<MediaCount> response = bynderApi.getImageAssetsCount().execute();
        MediaCount mediaCount = response.body();
        return mediaCount.getCount().getTotal();
    }

    public int getImageAssetsTotal(final String keyword, final List<String> propertyOptionIds) throws IOException {
        int total = 0;
        List<MediaAsset> imageAssetsOffset = new ArrayList<>();
        int offset = 1;

        // the condition 'offset == 1' is included in the while loop just for its first iteration
        while (offset == 1 || imageAssetsOffset.size() == DEFAULT_LIMIT) {

            imageAssetsOffset = getImageAssets(keyword, DEFAULT_LIMIT, offset);

            if (propertyOptionIds != null) {
                for (MediaAsset imageAsset : imageAssetsOffset) {
                    if (imageAsset.getPropertyOptions() != null && imageAsset.getPropertyOptions().containsAll(propertyOptionIds)) {
                        total++;
                    }
                }
            } else {
                total += imageAssetsOffset.size();
            }
            offset++;
        }

        return total;
    }

    public int setMediaAssetProperties(final String id, final String name, final String description, final String copyright, final Boolean archive, final String datePublished) throws IOException {
        Utils.checkNotNull("id", id);

        Response<Void> response = bynderApi.setMediaAssetProperties(id, name, description, copyright, archive, datePublished).execute();
        return response.code();
    }

    public int addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds) throws IOException {

        Utils.checkNotNull("assetId", assetId);
        Utils.checkNotNull("metapropertyId", metapropertyId);

        Map<String, String> metapropertyOptions = new HashMap<>();
        metapropertyOptions.put(String.format("metaproperty.%s", metapropertyId), StringUtils.join(optionsIds, Utils.STR_COMMA));

        Response<Void> response = bynderApi.addMetapropertyToAsset(assetId, metapropertyOptions).execute();
        return response.code();
    }
}
