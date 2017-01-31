/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.api;

import java.util.List;
import java.util.Map;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.Category;
import com.bynder.sdk.model.DownloadFileUrl;
import com.bynder.sdk.model.FinaliseResponse;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.MediaCount;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.PollStatus;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.model.UploadRequest;
import com.bynder.sdk.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BynderApi {

    @FormUrlEncoded
    @POST("v4/users/login/")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @POST("v4/oauth/request_token/")
    Call<String> getRequestToken();

    @POST("v4/oauth/access_token/")
    Call<String> getAccessToken();

    @GET("v4/brands/")
    Call<List<Brand>> getBrands();

    @GET("v4/categories/")
    Call<List<Category>> getCategories();

    @GET("v4/tags/")
    Call<List<Tag>> getTags();

    @GET("v4/metaproperties/?count=1")
    Call<Map<String, Metaproperty>> getMetaproperties();

    @GET("v4/media/")
    Call<List<Media>> getMediaList(@Query("type") String type, @Query("keyword") String keyword, @Query("limit") Integer limit, @Query("page") Integer page,
            @Query("propertyOptionId") String propertyOptionId);

    @GET("v4/media/?count=1")
    Call<MediaCount> getMediaWithCount();

    @GET("v4/media/{id}/")
    Call<Media> getMediaInfo(@Path("id") String id, @Query("versions") Boolean versions);

    @GET("v4/media/?type=image")
    Call<List<Media>> getImageAssets(@Query("keyword") String keyword, @Query("limit") Integer limit, @Query("page") Integer page, @Query("propertyOptionId") String propertyOptionId);

    @GET("v4/media/?type=image&count=1")
    Call<MediaCount> getImageAssetsWithCount(@Query("keyword") String keyword, @Query("limit") Integer limit, @Query("page") Integer page, @Query("propertyOptionId") String propertyOptionId);

    @GET("v4/media/?type=image")
    Call<List<Media>> getImageAssetsByMetapropertyId(@Query("propertyOptionId") String propertyOptionId);

    @GET("v4/media/?type=image&count=1")
    Call<MediaCount> getImageAssetsCount();

    @FormUrlEncoded
    @POST("v4/media/{id}/")
    Call<Void> setMediaAssetProperties(@Path("id") String id, @Field("name") String name, @Field("description") String description, @Field("copyright") String copyright,
            @Field("archive") Boolean archive, @Field("datePublished") String datePublished);

    @FormUrlEncoded
    @POST("v4/media/")
    Call<Void> addMetapropertyToAsset(@Field("id") String id, @FieldMap Map<String, String> metapropertyOptions);

    @FormUrlEncoded
    @POST("upload/init/")
    Call<UploadRequest> getUploadInformation(@Field("filename") String filename);

    @GET("upload/endpoint/")
    Call<String> getClosestS3Endpoint();

    @FormUrlEncoded
    @POST("v4/upload/{id}/")
    Call<Void> registerChunk(@Path("id") String id, @Field("chunkNumber") int chunkNumber, @Field("targetid") String targetId, @Field("filename") String filename);

    @FormUrlEncoded
    @POST("v4/upload/{id}/")
    Call<FinaliseResponse> finaliseUploaded(@Path("id") String id, @Field("targetid") String targetId, @Field("s3_filename") String s3Filename, @Field("chunks") int chunks);

    @GET("v4/upload/poll/")
    Call<PollStatus> pollStatus(@Query("items") String items);

    @FormUrlEncoded
    @POST("v4/media/save/{importId}/")
    Call<Void> saveMedia(@Path("importId") String importId, @Field("brandId") String brandId, @Field("name") String name);

    @FormUrlEncoded
    @POST("v4/media/{id}/save/{importId}/")
    Call<Void> saveMediaVersion(@Path("id") String id, @Path("importId") String importId);

    @GET("v4/media/{id}/download/")
    Call<DownloadFileUrl> getDownloadFileUrl(@Path("id") String id);
}
