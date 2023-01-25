/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.asset;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.*;
import com.bynder.sdk.model.upload.SaveMediaResponse;
import com.bynder.sdk.model.upload.UploadAdditionalMediaResponse;
import com.bynder.sdk.model.upload.UploadProgress;
import com.bynder.sdk.query.*;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.upload.UploadQuery;
import com.bynder.sdk.service.upload.FileUploader;
import io.reactivex.Observable;
import retrofit2.Response;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link AssetService}.
 */
public class AssetServiceImpl implements AssetService {

    /**
     * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
     */
    private final BynderApi bynderApi;
    /**
     * Instance of {@link QueryDecoder} to decode query objects into API parameters.
     */
    private final QueryDecoder queryDecoder;
    /**
     * Instance to upload files to Bynder.
     */
    private final FileUploader fileUploader;

    /**
     * Initialises a new instance of the class.
     *
     * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
     * @param queryDecoder Query decoder.
     */
    public AssetServiceImpl(final BynderApi bynderApi, final QueryDecoder queryDecoder) {
        this.bynderApi = bynderApi;
        this.queryDecoder = queryDecoder;
        this.fileUploader = new FileUploader(bynderApi, queryDecoder);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<List<Brand>>> getBrands() {
        return bynderApi.getBrands();
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<List<Tag>>> getTags() {
        return bynderApi.getTags();
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<Map<String, Metaproperty>>> getMetaproperties(
        final MetapropertyQuery metapropertyQuery) {
        Map<String, String> params = queryDecoder.decode(metapropertyQuery);
        return bynderApi.getMetaproperties(params);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<List<Media>>> getMediaList(final MediaQuery mediaQuery) {
        Map<String, String> params = queryDecoder.decode(mediaQuery);
        return bynderApi.getMediaList(params);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<Media>> getMediaInfo(final MediaInfoQuery mediaInfoQuery) {
        Map<String, String> params = queryDecoder.decode(mediaInfoQuery);
        return bynderApi.getMediaInfo(params);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<Void>> modifyMedia(final MediaModifyQuery mediaModifyQuery) {
        Map<String, String> params = queryDecoder.decode(mediaModifyQuery);
        return bynderApi.modifyMedia(params);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<Void>> deleteMedia(final MediaDeleteQuery mediaDeleteQuery) {
        Map<String, String> params = queryDecoder.decode(mediaDeleteQuery);
        return bynderApi.deleteMedia(params);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<DownloadUrl>> getMediaDownloadUrl(
        final MediaDownloadQuery mediaDownloadQuery) {
        if (mediaDownloadQuery.getMediaItemId() == null) {
            return bynderApi.getMediaDownloadUrl(mediaDownloadQuery.getMediaId());
        } else {
            return bynderApi.getMediaDownloadUrl(mediaDownloadQuery.getMediaId(),
                mediaDownloadQuery.getMediaItemId());
        }
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<Usage>> createUsage(final UsageCreateQuery usageCreateQuery) {
        Map<String, String> params = queryDecoder.decode(usageCreateQuery);
        return bynderApi.createUsage(params);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<List<Usage>>> getUsage(final UsageQuery usageQuery) {
        Map<String, String> params = queryDecoder.decode(usageQuery);
        return bynderApi.getUsage(params);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<Void>> deleteUsage(final UsageDeleteQuery usageDeleteQuery) {
        Map<String, String> params = queryDecoder.decode(usageDeleteQuery);
        return bynderApi.deleteUsage(params);
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<Response<List<Smartfilter>>> getSmartfilters() {
        return bynderApi.getSmartfilters();
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<SaveMediaResponse> uploadFile(final UploadQuery uploadQuery) {
        return fileUploader.uploadFile(uploadQuery).toObservable();
    }

    @Override
    public Observable<UploadAdditionalMediaResponse> uploadAdditionalFile(final UploadQuery uploadQuery) {
        return fileUploader.uploadAdditionalFile(uploadQuery).toObservable();
    }

    /**
     * Check {@link AssetService} for more information.
     */
    @Override
    public Observable<UploadProgress> uploadFileWithProgress(final UploadQuery uploadQuery) {
        return fileUploader.uploadFileWithProgress(uploadQuery);
    }
}
