package com.getbynder.sdk.api;

import java.util.List;
import java.util.Map;

import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.MediaCount;
import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.domain.Tag;
import com.getbynder.sdk.domain.UserAccessData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author daniel.sequeira
 */
public interface BynderApi {

    @FormUrlEncoded
    @POST("v4/users/login/")
    Call<UserAccessData> login(@Field("username") String username, @Field("password") String password);

    @POST("v4/oauth/request_token/")
    Call<String> getRequestToken();

    @POST("v4/oauth/access_token/")
    Call<String> getAccessToken();

    @GET("v4/categories/")
    Call<List<Category>> getCategories();

    @GET("v4/tags/")
    Call<List<Tag>> getTags();

    @GET("v4/metaproperties/")
    Call<Map<String, Metaproperty>> getMetaproperties();

    @GET("v4/media/")
    Call<List<MediaAsset>> getMediaAssets(@Query("type") String type, @Query("keyword") String keyword, @Query("limit") Integer limit, @Query("page") Integer page,
            @Query("propertyOptionId") String propertyOptionId);

    @GET("v4/media/{id}/")
    Call<MediaAsset> getMediaAssetById(@Path("id") String id, @Query("versions") Boolean versions);

    @GET("v4/media/?type=image")
    Call<List<MediaAsset>> getImageAssets(@Query("keyword") String keyword, @Query("limit") Integer limit, @Query("page") Integer page, @Query("propertyOptionId") String propertyOptionId);

    @GET("v4/media/?type=image")
    Call<List<MediaAsset>> getImageAssetsByMetapropertyId(@Query("propertyOptionId") String propertyOptionId);

    @GET("v4/media/?type=image&count=1")
    Call<MediaCount> getImageAssetsCount();

    @FormUrlEncoded
    @POST("v4/media/{id}/")
    Call<Void> setMediaAssetProperties(@Path("id") String id, @Field("name") String name, @Field("description") String description, @Field("copyright") String copyright,
            @Field("archive") Boolean archive, @Field("datePublished") String datePublished);

    @FormUrlEncoded
    @POST("v4/media/")
    Call<Void> addMetapropertyToAsset(@Field("id") String id, @FieldMap Map<String, String> metapropertyOptions);
}
