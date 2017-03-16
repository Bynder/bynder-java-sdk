/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.io.IOException;
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
        this.fileUploader = FileUploader.create(bynderApi);
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
        return bynderApi.getMetaproperties(metapropertyQuery.getCount());
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<List<Media>>> getMediaList(final MediaQuery mediaQuery) {
        return bynderApi.getMediaList(mediaQuery.getType() == null ? null : mediaQuery.getType().toString(), mediaQuery.getKeyword(), mediaQuery.getLimit(), mediaQuery.getPage(),
                StringUtils.join(mediaQuery.getPropertyOptionId(), Utils.STR_COMMA));
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<Media>> getMediaInfo(final MediaInfoQuery mediaInfoQuery) {
        return bynderApi.getMediaInfo(mediaInfoQuery.getMediaId(), mediaInfoQuery.getVersions());
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
        return bynderApi.setMediaProperties(mediaPropertiesQuery.getMediaId(), mediaPropertiesQuery.getName(), mediaPropertiesQuery.getDescription(), mediaPropertiesQuery.getCopyright(),
                mediaPropertiesQuery.getArchive(), mediaPropertiesQuery.getDatePublished());
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public Observable<Response<Void>> addMetapropertyToMedia(final AddMetapropertyToMediaQuery addMetapropertyToMediaQuery) {
        Map<String, String> metapropertyOptions = new HashMap<>();
        metapropertyOptions.put(String.format("metaproperty.%s", addMetapropertyToMediaQuery.getMetapropertyId()), StringUtils.join(addMetapropertyToMediaQuery.getOptionsIds(), Utils.STR_COMMA));

        return bynderApi.addMetapropertyToMedia(addMetapropertyToMediaQuery.getMediaId(), metapropertyOptions);
    }

    /**
     * Check {@link AssetBankManager} for more information.
     */
    @Override
    public void uploadFile(final UploadQuery uploadQuery) throws BynderUploadException, IOException, InterruptedException, RuntimeException {
        fileUploader.uploadFile(uploadQuery);
    }
}
