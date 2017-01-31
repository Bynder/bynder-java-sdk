/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadFileUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.query.UploadQuery;
import com.bynder.sdk.service.AssetBankManager;
import com.bynder.sdk.service.BynderServiceCall;
import com.bynder.sdk.service.exception.BynderUploadException;
import com.bynder.sdk.service.upload.FileUploader;
import com.bynder.sdk.util.Utils;

import retrofit2.Call;

public class AssetBankManagerImpl implements AssetBankManager {

    private final BynderApi bynderApi;
    private final FileUploader fileUploader;

    public AssetBankManagerImpl(final BynderApi bynderApi) {
        this.bynderApi = bynderApi;
        this.fileUploader = new FileUploader(bynderApi);
    }

    @Override
    public BynderServiceCall<List<Brand>> getBrands() {
        Call<List<Brand>> call = bynderApi.getBrands();
        return Utils.createServiceCall(call);
    }

    @Override
    public BynderServiceCall<Map<String, Metaproperty>> getMetaproperties() {
        Call<Map<String, Metaproperty>> call = bynderApi.getMetaproperties();
        return Utils.createServiceCall(call);
    }

    @Override
    public BynderServiceCall<List<Media>> getMediaList(final String type, final String keyword, final Integer limit, final Integer page, final List<String> propertyOptionIds) {
        Call<List<Media>> call = bynderApi.getMediaList(type, keyword, limit, page, StringUtils.join(propertyOptionIds, Utils.STR_COMMA));
        return Utils.createServiceCall(call);
    }

    @Override
    public BynderServiceCall<Media> getMediaInfo(final String id, final Boolean versions) {
        Call<Media> call = bynderApi.getMediaInfo(id, versions);
        return Utils.createServiceCall(call);
    }

    @Override
    public BynderServiceCall<DownloadFileUrl> getDownloadFileUrl(final String id) {
        Call<DownloadFileUrl> call = bynderApi.getDownloadFileUrl(id);
        return Utils.createServiceCall(call);
    }

    @Override
    public void uploadFile(final UploadQuery uploadQuery) throws BynderUploadException, IOException, InterruptedException, RuntimeException {
        fileUploader.uploadFile(uploadQuery);
    }
}
