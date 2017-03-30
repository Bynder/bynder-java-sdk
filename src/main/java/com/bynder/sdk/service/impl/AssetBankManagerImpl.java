/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.query.AddMetapropertyToMediaQuery;
import com.bynder.sdk.query.MediaDownloadQuery;
import com.bynder.sdk.query.MediaInfoQuery;
import com.bynder.sdk.query.MediaPropertiesQuery;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.MetapropertyQuery;
import com.bynder.sdk.query.UploadQuery;
import com.bynder.sdk.service.AssetBankManager;
import com.bynder.sdk.service.exception.BynderUploadException;
import com.bynder.sdk.service.upload.FileUploader;
import com.bynder.sdk.util.Utils;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Implementation of {@link AssetBankManager}.
 */
public class AssetBankManagerImpl implements AssetBankManager {

    /**
     * Instance of {@link BynderApi} which handles the HTTP communication with the Bynder API.
     */
    private final BynderApi bynderApi;
    /**
     * Instance to upload files to Bynder.
     */
    private final FileUploader fileUploader;

    /**
     * Initializes a new instance of the class.
     *
     * @param bynderApi Instance to handle the HTTP communication with the Bynder API.
     */
    public AssetBankManagerImpl(final BynderApi bynderApi) {
        this.bynderApi = bynderApi;
        this.fileUploader = new FileUploader(bynderApi);
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<List<Brand>>> getBrands() {
        return bynderApi.getBrands();
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<List<Tag>>> getTags() {
        return bynderApi.getTags();
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<Map<String, Metaproperty>>> getMetaproperties(final MetapropertyQuery metapropertyQuery) {
        Map<String, String> params = Utils.getApiParameters(metapropertyQuery);
        return bynderApi.getMetaproperties(params);
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<List<Media>>> getMediaList(final MediaQuery mediaQuery) {
        Map<String, String> params = Utils.getApiParameters(mediaQuery);
        return bynderApi.getMediaList(params);
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<Media>> getMediaInfo(final MediaInfoQuery mediaInfoQuery) {
        Map<String, String> params = Utils.getApiParameters(mediaInfoQuery);
        return bynderApi.getMediaInfo(mediaInfoQuery.getMediaId(), params);
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<DownloadUrl>> getMediaDownloadUrl(final MediaDownloadQuery mediaDownloadQuery) {
        if (mediaDownloadQuery.getMediaItemId() == null) {
            return bynderApi.getMediaDownloadUrl(mediaDownloadQuery.getMediaId());
        } else {
            return bynderApi.getMediaDownloadUrl(mediaDownloadQuery.getMediaId(), mediaDownloadQuery.getMediaItemId());
        }
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<Void>> setMediaProperties(final MediaPropertiesQuery mediaPropertiesQuery) {
        Map<String, String> params = Utils.getApiParameters(mediaPropertiesQuery);
        return bynderApi.setMediaProperties(mediaPropertiesQuery.getMediaId(), params);
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<Void>> addMetapropertyToMedia(final AddMetapropertyToMediaQuery addMetapropertyToMediaQuery) {
        Map<String, String> params = new HashMap<>();
        params.put("id", addMetapropertyToMediaQuery.getMediaId());
        params.put(String.format("metaproperty.%s", addMetapropertyToMediaQuery.getMetapropertyId()), StringUtils.join(addMetapropertyToMediaQuery.getOptionsIds(), Utils.STR_COMMA));

        return bynderApi.addMetapropertyToMedia(params);
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Response<Void> uploadFile(final UploadQuery uploadQuery) throws BynderUploadException, InterruptedException {
        return fileUploader.uploadFile(uploadQuery);
    }
}
