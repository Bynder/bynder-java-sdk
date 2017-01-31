/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadFileUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.query.UploadQuery;
import com.bynder.sdk.service.exception.BynderUploadException;

public interface AssetBankManager {

    BynderServiceCall<List<Brand>> getBrands();

    BynderServiceCall<Map<String, Metaproperty>> getMetaproperties();

    BynderServiceCall<List<Media>> getMediaList(String type, String keyword, Integer limit, Integer page, List<String> propertyOptionIds);

    BynderServiceCall<Media> getMediaInfo(String id, Boolean versions);

    BynderServiceCall<DownloadFileUrl> getDownloadFileUrl(String id);

    void uploadFile(UploadQuery uploadQuery) throws BynderUploadException, IOException, InterruptedException, RuntimeException;
}
