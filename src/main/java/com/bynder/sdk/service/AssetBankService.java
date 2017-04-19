/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import java.util.List;
import java.util.Map;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.query.MediaDownloadQuery;
import com.bynder.sdk.query.MediaInfoQuery;
import com.bynder.sdk.query.MediaPropertiesQuery;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.MetapropertyQuery;
import com.bynder.sdk.query.UploadQuery;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Interface to represent operations that can be done to the Bynder Asset Bank.
 */
public interface AssetBankService {

    /**
     * Get Brands.
     *
     * @return {@link Observable} with list of {@link Brand}.
     */
    Observable<Response<List<Brand>>> getBrands();

    /**
     * Get Tags.
     *
     * @return {@link Observable} with list of {@link Tag}.
     */
    Observable<Response<List<Tag>>> getTags();

    /**
     * Get Metaproperties.
     *
     * @param metapropertyQuery Information about if media count should be included in the
     *        metaproperty options or not.
     *
     * @return {@link Observable} with Map of String, {@link Metaproperty} key/value pairs.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    Observable<Response<Map<String, Metaproperty>>> getMetaproperties(MetapropertyQuery metapropertyQuery) throws IllegalArgumentException, IllegalAccessException;

    /**
     * Gets a list of media using query information.
     *
     * @param mediaQuery Information to correctly filter/paginate media.
     *
     * @return {@link Observable} with list of {@link Media}.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    Observable<Response<List<Media>>> getMediaList(MediaQuery mediaQuery) throws IllegalArgumentException, IllegalAccessException;

    /**
     * Gets all the information for a specific media. This is needed to get the media items of a
     * media asset.
     *
     * @param mediaInfoQuery Information about the media we want to get the information from.
     *
     * @return {@link Observable} with {@link Media} information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    Observable<Response<Media>> getMediaInfo(MediaInfoQuery mediaInfoQuery) throws IllegalArgumentException, IllegalAccessException;

    /**
     * Gets the download file URL for a specific media asset file. If the media item id was not
     * specified, it will return the download URL of the media specified by media id.
     *
     * @param mediaDownloadQuery Information with the media we want to get the URL from.
     *
     * @return {@link Observable} with the {@link DownloadUrl} information of the media asset file.
     */
    Observable<Response<DownloadUrl>> getMediaDownloadUrl(MediaDownloadQuery mediaDownloadQuery);

    /**
     * Updates the properties of a media asset.
     *
     * @param mediaPropertiesQuery Information with the media asset properties new values to be
     *        updated.
     *
     * @return {@link Observable} with the request {@link Response} information.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    Observable<Response<Void>> setMediaProperties(MediaPropertiesQuery mediaPropertiesQuery) throws IllegalArgumentException, IllegalAccessException;

    /**
     * Uploads a file to Bynder.
     *
     * @param uploadQuery Information needed to upload the file.
     */
    Observable<Boolean> uploadFile(UploadQuery uploadQuery);
}
