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
import retrofit2.http.QueryMap;

/**
 * Interface of the Bynder API to handle the HTTP communication.
 */
public interface BynderApi {

    /**
     * Logs in to Bynder with a username and password pair.
     *
     * @param params {@link FieldMap} with parameters.
     *
     * @return {@link Observable} with the {@link User} information.
     */
    @FormUrlEncoded
    @POST("v4/users/login/")
    Observable<Response<User>> login(@FieldMap Map<String, String> params);

    /**
     * Gets temporary request token pair.
     *
     * @return {@link Observable} with the request token pair information.
     */
    @POST("v4/oauth/request_token/")
    Observable<Response<String>> getRequestToken();

    /**
     * Gets a temporary access token pair once the user already authorized the request token pair.
     * If successful the request token pair is immediately expired.
     *
     * @return {@link Observable} with the access token pair information.
     */
    @POST("v4/oauth/access_token/")
    Observable<Response<String>> getAccessToken();

    /**
     * Gets list of the brands.
     *
     * @return {@link Observable} with list of {@link Brand}.
     */
    @GET("v4/brands/")
    Observable<Response<List<Brand>>> getBrands();

    /**
     * Gets list of the tags.
     *
     * @return {@link Observable} with list of {@link Tag}.
     */
    @GET("v4/tags/")
    Observable<Response<List<Tag>>> getTags();

    /**
     * Gets map of the metaproperties. The key of the map returned is the name of the metaproperty.
     *
     * @param params {@link QueryMap} with parameters.
     *
     * @return {@link Observable} with Map of metaproperties.
     */
    @GET("v4/metaproperties/")
    Observable<Response<Map<String, Metaproperty>>> getMetaproperties(@QueryMap Map<String, String> params);

    /**
     * Gets a list of media assets filtered by parameters.
     *
     * @param params {@link QueryMap} with parameters.
     *
     * @return {@link Observable} with list of {@link Media}.
     */
    @GET("v4/media/")
    Observable<Response<List<Media>>> getMediaList(@QueryMap Map<String, String> params);

    /**
     * Gets all the media information for a specific media id.
     *
     * @param id Media id from which we want to retrieve information.
     * @param params {@link QueryMap} with parameters.
     *
     * @return {@link Observable} with {@link Media} information.
     */
    @GET("v4/media/{id}/")
    Observable<Response<Media>> getMediaInfo(@Path("id") String id, @QueryMap Map<String, String> params);

    /**
     * Gets the download file URL for a specific media.
     *
     * @param mediaId Media id of which we want to get the download URL.
     *
     * @return {@link Observable} with the {@link DownloadUrl} information of the media.
     */
    @GET("v4/media/{id}/download/")
    Observable<Response<DownloadUrl>> getMediaDownloadUrl(@Path("id") String mediaId);

    /**
     * Gets the download file URL for specific media item id.
     *
     * @param mediaId Media id of which the media item belongs to.
     * @param mediaItemId Media item id of which we want to get the download URL.
     *
     * @return {@link Observable} with the {@link DownloadUrl} information of the media item.
     */
    @GET("v4/media/{id}/download/{itemId}/")
    Observable<Response<DownloadUrl>> getMediaDownloadUrl(@Path("id") String mediaId, @Path("itemId") String mediaItemId);

    /**
     * Updates the media metadata/properties for a specific media id.
     *
     * @param id Media id of which we want to update.
     * @param params {@link FieldMap} with parameters.
     *
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("v4/media/{id}/")
    Observable<Response<Void>> setMediaProperties(@Path("id") String id, @FieldMap Map<String, String> params);

    /**
     * Adds metaproperty options to a media with a specific media id.
     *
     * @param params {@link FieldMap} with parameters.
     *
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("v4/media/")
    Observable<Response<Void>> addMetapropertyToMedia(@FieldMap Map<String, String> params);

    /**
     * Initializes a file upload with Bynder and returns authorization information to allow
     * uploading to the Amazon S3 bucket-endpoint.
     *
     * @param filename Name of the file to be uploaded.
     *
     * @return {@link Observable} with {@link UploadRequest} authorization information.
     */
    @FormUrlEncoded
    @POST("upload/init/")
    Observable<Response<UploadRequest>> getUploadInformation(@Field("filename") String filename);

    /**
     * Gets the URL of the Amazon S3 bucket-endpoint in the region closest to the server. This URL
     * is used to upload parts to Amazon.
     *
     * @return {@link Observable} with the URL.
     */
    @GET("upload/endpoint/")
    Observable<Response<String>> getClosestS3Endpoint();

    /**
     * Registers an uploaded chunk in Bynder.
     *
     * @param id Upload id for the file being uploaded.
     * @param chunkNumber Number of the chunk that was uploaded.
     * @param targetId Target id in the authorization information returned by the
     *        {@link #getUploadInformation(String)}.
     * @param filename Base location of the uploaded chunk. Format: {s3Filename}/p{chunkNumber}.
     *
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("v4/upload/{id}/")
    Observable<Response<Void>> registerChunk(@Path("id") String id, @Field("chunkNumber") int chunkNumber, @Field("targetid") String targetId, @Field("filename") String filename);

    /**
     * Finalizes a completely uploaded file.
     *
     * @param id Upload id for the file being uploaded.
     * @param targetId Target id in the authorization information returned by the
     *        {@link #getUploadInformation(String)}.
     * @param s3Filename Base location of the uploaded file. Format: {s3Filename}/p{chunks}.
     * @param chunks Total number of chunks uploaded.
     *
     * @return {@link Observable} with {@link FinaliseResponse} information.
     */
    @FormUrlEncoded
    @POST("v4/upload/{id}/")
    Observable<Response<FinaliseResponse>> finaliseUploaded(@Path("id") String id, @Field("targetid") String targetId, @Field("s3_filename") String s3Filename, @Field("chunks") int chunks);

    /**
     * Polls processing status of finalized files.
     *
     * @param items Comma separated import ids of a finalized file, as returned by the
     *        {@link #finaliseUploaded(String, String, String, int)}.
     *
     * @return {@link Observable} with {@link PollStatus} information.
     */
    @GET("v4/upload/poll/")
    Observable<Response<PollStatus>> pollStatus(@Query("items") String items);

    /**
     * Saves a new media asset in Bynder.
     *
     * @param importId Import id of a finalized and processed upload to save.
     * @param brandId Brand id to save the media asset to.
     * @param name Name of the media asset.
     *
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("v4/media/save/{importId}/")
    Observable<Response<Void>> saveMedia(@Path("importId") String importId, @Field("brandId") String brandId, @Field("name") String name);

    /**
     * Saves a new version of a media asset in Bynder.
     *
     * @param id Media id for which to save the new version.
     * @param importId Import id of a finalized and processed upload to save.
     *
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("v4/media/{id}/save/")
    Observable<Response<Void>> saveMediaVersion(@Path("id") String id, @Field("importId") String importId);
}
