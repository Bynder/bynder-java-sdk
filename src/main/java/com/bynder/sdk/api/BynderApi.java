/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.api;

import java.util.List;
import java.util.Map;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadUrl;
import com.bynder.sdk.model.FinaliseResponse;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.PollStatus;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.model.UploadRequest;
import com.bynder.sdk.model.User;

import io.reactivex.Observable;
import retrofit2.Response;
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
    Observable<Response<User>> login(@Field("username") String username, @Field("password") String password);

    @POST("v4/oauth/request_token/")
    Observable<Response<String>> getRequestToken();

    @POST("v4/oauth/access_token/")
    Observable<Response<String>> getAccessToken();

    @GET("v4/brands/")
    Observable<Response<List<Brand>>> getBrands();

    @GET("v4/tags/")
    Observable<Response<List<Tag>>> getTags();

    @GET("v4/metaproperties/")
    Observable<Response<Map<String, Metaproperty>>> getMetaproperties(@Query("count") Boolean count);

    @GET("v4/media/")
    Observable<Response<List<Media>>> getMediaList(@Query("type") String type, @Query("keyword") String keyword, @Query("limit") Integer limit, @Query("page") Integer page,
            @Query("propertyOptionId") String propertyOptionId, @Query("count") Boolean count);

    @GET("v4/media/{id}/")
    Observable<Response<Media>> getMediaInfo(@Path("id") String id, @Query("versions") Boolean versions);

    @FormUrlEncoded
    @POST("v4/media/{id}/")
    Observable<Response<Void>> setMediaProperties(@Path("id") String id, @Field("name") String name, @Field("description") String description, @Field("copyright") String copyright,
            @Field("archive") Boolean archive, @Field("datePublished") String datePublished);

    @FormUrlEncoded
    @POST("v4/media/")
    Observable<Response<Void>> addMetapropertyToMedia(@Field("id") String mediaId, @FieldMap Map<String, String> metapropertyOptions);

    @FormUrlEncoded
    @POST("upload/init/")
    Observable<Response<UploadRequest>> getUploadInformation(@Field("filename") String filename);

    @GET("upload/endpoint/")
    Observable<Response<String>> getClosestS3Endpoint();

    @FormUrlEncoded
    @POST("v4/upload/{id}/")
    Observable<Response<Void>> registerChunk(@Path("id") String id, @Field("chunkNumber") int chunkNumber, @Field("targetid") String targetId, @Field("filename") String filename);

    @FormUrlEncoded
    @POST("v4/upload/{id}/")
    Observable<Response<FinaliseResponse>> finaliseUploaded(@Path("id") String id, @Field("targetid") String targetId, @Field("s3_filename") String s3Filename, @Field("chunks") int chunks);

    @GET("v4/upload/poll/")
    Observable<Response<PollStatus>> pollStatus(@Query("items") String items);

    @FormUrlEncoded
    @POST("v4/media/save/{importId}/")
    Observable<Response<Void>> saveMedia(@Path("importId") String importId, @Field("brandId") String brandId, @Field("name") String name);

    @FormUrlEncoded
    @POST("v4/media/{id}/save/")
    Observable<Response<Void>> saveMediaVersion(@Path("id") String id, @Field("importId") String importId);

    @GET("v4/media/{id}/download/")
    Observable<Response<DownloadUrl>> getMediaDownloadUrl(@Path("id") String mediaId);

    @GET("v4/media/{id}/download/{itemId}/")
    Observable<Response<DownloadUrl>> getMediaDownloadUrl(@Path("id") String mediaId, @Path("itemId") String mediaItemId);
}
