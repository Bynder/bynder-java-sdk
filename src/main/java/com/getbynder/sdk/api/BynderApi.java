package com.getbynder.sdk.api;

import java.util.List;
import java.util.Map;

import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.MediaAsset;
import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.domain.Tag;
import com.getbynder.sdk.domain.UserAccessData;

import retrofit2.Call;
import retrofit2.http.Field;
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
    @POST("users/login/")
    Call<UserAccessData> login(@Field("username") String username, @Field("password") String password);

    @GET("media/")
    Call<List<MediaAsset>> getMediaAssets(@Query("type") String type, @Query("keyword") String keyword, @Query("limit") Integer limit, @Query("offset") Integer offset, @Query("propertyOptionId") String propertyOptionId);

    @GET("metaproperties/")
    Call<Map<String, Metaproperty>> getMetaproperties();

    @GET("categories/")
    Call<List<Category>> getCategories();

    @GET("tags/")
    Call<List<Tag>> getTags();

    @GET("media/{id}/")
    Call<MediaAsset> getMediaAssetById(@Path("id") String id, @Query("versions") Boolean versions);

    @FormUrlEncoded
    @POST("media/{id}/")
    Call<Void> setMediaAssetProperties(@Path("id") String id, @Query("name") String name, @Query("description") String description, @Query("copyright") String copyright, @Query("archive") Boolean archive, @Query("datePublished") String datePublished);
}
