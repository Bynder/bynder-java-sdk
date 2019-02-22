/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.api;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.Collection;
import com.bynder.sdk.model.Derivative;
import com.bynder.sdk.model.DownloadUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.Smartfilter;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.model.Usage;
import com.bynder.sdk.model.upload.FinaliseResponse;
import com.bynder.sdk.model.upload.PollStatus;
import com.bynder.sdk.model.upload.SaveMediaResponse;
import com.bynder.sdk.model.upload.UploadRequest;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Interface of the Bynder API to handle the HTTP communication.
 */
public interface BynderApi {

    /**
     * Gets list of the account derivatives.
     *
     * @return {@link Observable} with list of {@link Derivative}.
     */
    @GET("/api/v4/account/derivatives/")
    Observable<Response<List<Derivative>>> getDerivatives();

    /**
     * Gets list of the brands.
     *
     * @return {@link Observable} with list of {@link Brand}.
     */
    @GET("/api/v4/brands/")
    Observable<Response<List<Brand>>> getBrands();

    /**
     * Gets list of the tags.
     *
     * @return {@link Observable} with list of {@link Tag}.
     */
    @GET("/api/v4/tags/")
    Observable<Response<List<Tag>>> getTags();

    /**
     * Gets map of the metaproperties. The key of the map returned is the name of the metaproperty.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with Map of metaproperties.
     */
    @GET("/api/v4/metaproperties/")
    Observable<Response<Map<String, Metaproperty>>> getMetaproperties(
        @QueryMap Map<String, String> params);

    /**
     * Gets a list of media assets filtered by parameters.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with list of {@link Media}.
     */
    @GET("/api/v4/media/")
    Observable<Response<List<Media>>> getMediaList(@QueryMap Map<String, String> params);

    /**
     * Gets all the media information for a specific media id.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with {@link Media} information.
     */
    @GET("/api/v4/media/")
    Observable<Response<Media>> getMediaInfo(@QueryMap Map<String, String> params);

    /**
     * Modifies the media metadata for a specific media id.
     *
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("/api/v4/media/")
    Observable<Response<Void>> modifyMedia(@FieldMap Map<String, String> params);

    /**
     * Deletes a media asset.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @DELETE("/api/v4/media/")
    Observable<Response<Void>> deleteMedia(@QueryMap Map<String, String> params);

    /**
     * Gets the download file URL for a specific media id.
     *
     * @param mediaId Media id of which we want to get the download URL.
     * @return {@link Observable} with the {@link DownloadUrl} information of the media.
     */
    @GET("/api/v4/media/{id}/download/")
    Observable<Response<DownloadUrl>> getMediaDownloadUrl(@Path("id") String mediaId);

    /**
     * Gets the download file URL for specific media item id.
     *
     * @param mediaId Media id of which the media item belongs to.
     * @param mediaItemId Media item id of which we want to get the download URL.
     * @return {@link Observable} with the {@link DownloadUrl} information of the media item.
     */
    @GET("/api/v4/media/{id}/download/{itemId}/")
    Observable<Response<DownloadUrl>> getMediaDownloadUrl(@Path("id") String mediaId,
        @Path("itemId") String mediaItemId);

    /**
     * Creates a usage record for a media asset.
     *
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with {@link Usage} information.
     */
    @FormUrlEncoded
    @POST("/api/media/usage/")
    Observable<Response<Usage>> createUsage(@FieldMap Map<String, String> params);

    /**
     * Gets all the media assets usage records.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with list of {@link Usage}.
     */
    @GET("/api/media/usage/")
    Observable<Response<List<Usage>>> getUsage(@QueryMap Map<String, String> params);

    /**
     * Deletes a usage record of a media asset.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @DELETE("/api/media/usage/")
    Observable<Response<Void>> deleteUsage(@QueryMap Map<String, String> params);

    /**
     * Gets list of the smartfilters.
     *
     * @return {@link Observable} with List of smartfilters.
     */
    @GET("/api/v4/smartfilters/")
    Observable<Response<List<Smartfilter>>> getSmartfilters();

    /**
     * Gets list of the collections.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with list of {@link Collection}.
     */
    @GET("/api/v4/collections/")
    Observable<Response<List<Collection>>> getCollections(@QueryMap Map<String, String> params);

    /**
     * Gets all the collection information for a specific collection id.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with {@link Collection} information.
     */
    @GET("/api/v4/collections/")
    Observable<Response<Collection>> getCollectionInfo(@QueryMap Map<String, String> params);

    /**
     * Creates a collection.
     *
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("/api/v4/collections/")
    Observable<Response<Void>> createCollection(@FieldMap Map<String, String> params);

    /**
     * Deletes a collection.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @DELETE("/api/v4/collections/")
    Observable<Response<Void>> deleteCollection(@QueryMap Map<String, String> params);

    /**
     * Gets a list of the media assets ids of a collection.
     *
     * @param collectionId Collection id of which we want to get the media assets ids.
     * @return {@link Observable} with list of media assets ids.
     */
    @GET("/api/v4/collections/{id}/media/")
    Observable<Response<List<String>>> getCollectionMediaIds(@Path("id") String collectionId);

    /**
     * Adds media assets to a collection.
     *
     * @param collectionId Collection id to which we want to add media assets.
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("/api/v4/collections/{id}/media/")
    Observable<Response<Void>> addMediaToCollection(@Path("id") String collectionId,
        @FieldMap Map<String, String> params);

    /**
     * Removes media assets from a collection.
     *
     * @param collectionId Collection id from which we want to remove media assets.
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @DELETE("/api/v4/collections/{id}/media/")
    Observable<Response<Void>> removeMediaFromCollection(@Path("id") String collectionId,
        @QueryMap Map<String, String> params);

    /**
     * Shares a collection.
     *
     * @param collectionId Id of the collection we want to share.
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("/api/v4/collections/{id}/share/")
    Observable<Response<Void>> shareCollection(@Path("id") String collectionId,
        @FieldMap Map<String, String> params);

    /**
     * Initialises a file upload with Bynder and returns authorisation information to allow
     * uploading to the Amazon S3 bucket-endpoint.
     *
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with {@link UploadRequest} authorisation information.
     */
    @FormUrlEncoded
    @POST("/api/upload/init/")
    Observable<Response<UploadRequest>> getUploadInformation(@FieldMap Map<String, String> params);

    /**
     * Gets the URL of the Amazon S3 bucket-endpoint in the region closest to the server. This URL
     * is used to upload parts to Amazon.
     *
     * @return {@link Observable} with the URL.
     */
    @GET("/api/upload/endpoint/")
    Observable<Response<String>> getClosestS3Endpoint();

    /**
     * Registers an uploaded chunk in Bynder.
     *
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with the {@link Response}.
     */
    @FormUrlEncoded
    @POST("/api/v4/upload/")
    Observable<Response<Void>> registerChunk(@FieldMap Map<String, String> params);

    /**
     * Finalises a completely uploaded file.
     *
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with {@link FinaliseResponse} information.
     */
    @FormUrlEncoded
    @POST("/api/v4/upload/")
    Observable<Response<FinaliseResponse>> finaliseUpload(@FieldMap Map<String, String> params);

    /**
     * Gets poll processing status of finalised files.
     *
     * @param params {@link QueryMap} with parameters.
     * @return {@link Observable} with {@link PollStatus} information.
     */
    @GET("/api/v4/upload/poll/")
    Observable<Response<PollStatus>> getPollStatus(@QueryMap Map<String, String> params);

    /**
     * Saves a new media asset in Bynder. If media id is specified in the query a new version of the
     * asset will be saved. Otherwise a new asset will be saved.
     *
     * @param params {@link FieldMap} with parameters.
     * @return {@link Observable} with the {@link SaveMediaResponse} information.
     */
    @FormUrlEncoded
    @POST("/api/v4/media/save/")
    Observable<Response<SaveMediaResponse>> saveMedia(@FieldMap Map<String, String> params);
}
