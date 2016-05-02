package com.getbynder.sdk;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.getbynder.sdk.api.BynderApi;
import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.domain.Tag;
import com.getbynder.sdk.domain.UserAccessData;
import com.getbynder.sdk.util.Utils;

import retrofit2.Response;

public class BynderApiService {

    private BynderApi bynderApi;

    public BynderApiService() {
        bynderApi = Utils.createApiService(BynderApi.class, false);
    }

    public Response<UserAccessData> login(final String username, final String password) throws IOException {
        bynderApi = Utils.createApiService(BynderApi.class, true);
        return bynderApi.login(username, password).execute();
    }

    public Response<List<MediaAsset>> getMediaAssets(final String type, final String keyword, final Integer limit, final Integer offset, final String propertyOptionId) throws IOException {
        return bynderApi.getMediaAssets(type, keyword, limit, offset, propertyOptionId).execute();
    }

    public Response<Map<String, Metaproperty>> getMetaproperties() throws IOException {
        return bynderApi.getMetaproperties().execute();
    }

    public Response<List<Category>> getCategories() throws IOException {
        return bynderApi.getCategories().execute();
    }

    public Response<List<Tag>> getTags() throws IOException {
        return bynderApi.getTags().execute();
    }

    public Response<MediaAsset> getMediaAssetById(final String id, final Boolean versions) throws IOException {
        return bynderApi.getMediaAssetById(id ,versions).execute();
    }
}
