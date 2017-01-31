package com.bynder.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Category;
import com.bynder.sdk.model.Count;
import com.bynder.sdk.model.MediaCount;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.util.Constants;
import com.bynder.sdk.util.Utils;

import retrofit2.Response;

/**
 *
 * @deprecated
 */
@Deprecated
public class BynderApiService {

    private final String baseUrl;
    private final String consumerKey;
    private final String consumerSecret;

    private final String ACCESS_TOKEN_FAILED = "Could not obtain access token. Reason: %s %s";
    private final String LOGIN_REQUEST_FAILED = "Could not login to the API. Reason: %s %s";
    private final String REQUEST_TOKEN_FAILED = "Could not obtain request token. Reason: %s %s";

    private final int DEFAULT_LIMIT = 50;

    private final BynderApi bynderApi;

    public BynderApiService() {
        this.baseUrl = Constants.BASE_URL;
        this.consumerKey = Constants.CONSUMER_KEY;
        this.consumerSecret = Constants.CONSUMER_SECRET;

        bynderApi = Utils.createApiService(BynderApi.class, Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, Constants.ACCESS_TOKEN_KEY, Constants.ACCESS_TOKEN_SECRET);
    }

    public BynderApiService(final String baseUrl, final String consumerKey, final String consumerSecret, final String accessTokenKey, final String accessTokenSecret) {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;

        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, accessTokenKey, accessTokenSecret);
    }

    public BynderApiService(final String baseUrl, final String consumerKey, final String consumerSecret, final String requestTokenKey, final String requestTokenSecret, final String username,
            final String password) throws IOException {
        this.baseUrl = baseUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;

        UserAccessData userAccessData = login(requestTokenKey, requestTokenSecret, username, password);
        bynderApi = Utils.createApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, userAccessData.getTokenKey(), userAccessData.getTokenSecret());
    }

    public UserAccessData login(final String requestTokenKey, final String requestTokenSecret, final String username, final String password) throws IOException {
        BynderApi bynderApiForLogin = Utils.createApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, requestTokenKey, requestTokenSecret);
        Response<UserAccessData> response = bynderApiForLogin.login(username, password).execute();

        Utils.validateResponse(response, LOGIN_REQUEST_FAILED);

        return response.body();
    }

    public Map<String, String> getRequestToken() throws IOException {
        BynderApi bynderApiForRequestToken = Utils.createApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, null, null);
        Response<String> response = bynderApiForRequestToken.getRequestToken().execute();

        Utils.validateResponse(response, REQUEST_TOKEN_FAILED);

        Map<String, String> requestToken = Utils.buildMapFromResponse(response.body());

        StringBuilder stringBuilder = new StringBuilder(baseUrl).append("v4/oauth/authorise/?oauth_token=").append(requestToken.get("oauth_token")).append("&callback=http://localhost:8891/");
        requestToken.put("loginpage_url", stringBuilder.toString());

        return requestToken;
    }

    public String getAuthoriseUrl(final String oauthToken, final String callbackUrl) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(baseUrl).append("v4/oauth/authorise/?oauth_token=").append(oauthToken).append("&callback=").append(callbackUrl);
        return stringBuilder.toString();
    }

    public Map<String, String> getAccessToken(final String requestTokenKey, final String requestTokenSecret) throws IOException {
        Utils.checkNotNull("requestTokenKey", requestTokenKey);
        Utils.checkNotNull("requestTokenSecret", requestTokenSecret);

        BynderApi bynderApiForAccessToken = Utils.createApiService(BynderApi.class, baseUrl, consumerKey, consumerSecret, requestTokenKey, requestTokenSecret);
        Response<String> response = bynderApiForAccessToken.getAccessToken().execute();

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

        return metaproperties;
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

    // get the N-th set of limit-results of images assets that contain all the metaproperties ids
    // inside the propertyOptionIds list
    public List<MediaAsset> getImageAssetsForLazyLoading(final String keyword, final Integer limit, final Integer page, final List<String> propertyOptionIds) throws IOException {
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
        // if imageAssets already has the same number of elements as the asked limit and the
        // imageAssetsOffset retrieved is empty, stop the while
        while (offset == 1 || (imageAssets.size() < limit && imageAssetsOffset.size() != 0)) {

            imageAssetsOffset = getImageAssets(keyword, DEFAULT_LIMIT, offset);

            for (MediaAsset imageAsset : imageAssetsOffset) {
                // if imageAssets already has the same number of elements as the asked limit, break
                // the for loop
                if (imageAssets.size() >= limit) {
                    break;
                }

                if (imageAsset.getPropertyOptions() != null && imageAsset.getPropertyOptions().containsAll(propertyOptionIds)) {
                    // decrement ignoreCount and if it is already less than zero add the imageAsset
                    // to the list
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

    public MediaCount getImageAssetsWithCount(final String keyword, final Integer limit, final Integer page) throws IOException {
        Response<MediaCount> response = bynderApi.getImageAssetsWithCount(keyword, limit, page, null).execute();
        return response.body();
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

    public int getMediaAssetsTotal(final String keyword, final List<String> propertyOptionIds) throws IOException {
        int total = 0;
        List<MediaAsset> mediaAssetsOffset = new ArrayList<>();
        int offset = 1;

        // the condition 'offset == 1' is included in the while loop just for its first iteration
        while (offset == 1 || mediaAssetsOffset.size() == DEFAULT_LIMIT) {

            mediaAssetsOffset = getMediaAssets(null, keyword, DEFAULT_LIMIT, offset, null);

            if (propertyOptionIds != null) {
                for (MediaAsset mediaAsset : mediaAssetsOffset) {
                    if (mediaAsset.getPropertyOptions() != null && mediaAsset.getPropertyOptions().containsAll(propertyOptionIds)) {
                        total++;
                    }
                }
            } else {
                total += mediaAssetsOffset.size();
            }
            offset++;
        }

        return total;
    }

    public Map<String, Integer> getImageAssetsMetapropertyCount(final String keyword, final List<String> propertyOptionIds) throws IOException {
        List<MediaAsset> imageAssetsOffset = new ArrayList<>();
        Map<String, Integer> metapropertiesMediaCount = new HashMap<>();
        int offset = 1;

        // the condition 'offset == 1' is included in the while loop just for its first iteration
        while (offset == 1 || imageAssetsOffset.size() == DEFAULT_LIMIT) {

            imageAssetsOffset = getImageAssets(keyword, DEFAULT_LIMIT, offset);

            for (MediaAsset imageAsset : imageAssetsOffset) {
                if ((propertyOptionIds == null) || (imageAsset.getPropertyOptions() != null && imageAsset.getPropertyOptions().containsAll(propertyOptionIds))) {
                    for (String propertyOptionId : imageAsset.getPropertyOptions()) {
                        if (metapropertiesMediaCount.containsKey(propertyOptionId)) {
                            int mediaCount = metapropertiesMediaCount.get(propertyOptionId);
                            mediaCount++;
                            metapropertiesMediaCount.put(propertyOptionId, mediaCount);
                        } else {
                            metapropertiesMediaCount.put(propertyOptionId, 1);
                        }
                    }
                }
            }
            offset++;
        }

        return metapropertiesMediaCount;
    }

    public Count getImageAssetsCount() throws IOException {
        Response<MediaCount> response = bynderApi.getImageAssetsCount().execute();
        return response.body().getCount();
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
