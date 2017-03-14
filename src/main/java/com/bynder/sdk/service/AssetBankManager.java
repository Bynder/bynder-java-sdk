/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.bynder.sdk.service.exception.BynderUploadException;

import io.reactivex.Observable;
import retrofit2.Response;

public interface AssetBankManager {

    Observable<Response<List<Brand>>> getBrands();

    Observable<Response<List<Tag>>> getTags();

    Observable<Response<Map<String, Metaproperty>>> getMetaproperties(MetapropertyQuery metapropertyQuery);

    Observable<Response<List<Media>>> getMediaList(MediaQuery mediaQuery);

    Observable<Response<Media>> getMediaInfo(MediaInfoQuery mediaInfoQuery);

    Observable<Response<DownloadUrl>> getMediaDownloadUrl(MediaDownloadQuery mediaDownloadQuery);

    Observable<Response<Void>> setMediaProperties(MediaPropertiesQuery mediaPropertiesQuery);

    Observable<Response<Void>> addMetapropertyToMedia(AddMetapropertyToMediaQuery addMetapropertyToMediaQuery);

    void uploadFile(UploadQuery uploadQuery) throws BynderUploadException, IOException, InterruptedException, RuntimeException;
}
