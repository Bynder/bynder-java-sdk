/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.SaveMediaResponse;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.model.Usage;
import com.bynder.sdk.query.MediaDeleteQuery;
import com.bynder.sdk.query.MediaDownloadQuery;
import com.bynder.sdk.query.MediaInfoQuery;
import com.bynder.sdk.query.MediaPropertiesQuery;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.MetapropertyQuery;
import com.bynder.sdk.query.UploadQuery;
import com.bynder.sdk.query.UsageCreateQuery;
import com.bynder.sdk.query.UsageDeleteQuery;
import com.bynder.sdk.query.UsageQuery;
import com.bynder.sdk.service.upload.UploadProgress;
import com.bynder.sdk.util.Utils;
import io.reactivex.Observable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
     * metaproperty options or not.
     * @return {@link Observable} with Map of String, {@link Metaproperty} key/value pairs.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    Observable<Response<Map<String, Metaproperty>>> getMetaproperties(
        MetapropertyQuery metapropertyQuery) throws IllegalAccessException;

    /**
     * Gets a list of media using query information.
     *
     * @param mediaQuery Information to correctly filter/paginate media.
     * @return {@link Observable} with list of {@link Media}.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    Observable<Response<List<Media>>> getMediaList(MediaQuery mediaQuery)
        throws IllegalAccessException;

    /**
     * Gets all the information for a specific media. This is needed to get the media items of a
     * media asset.
     *
     * @param mediaInfoQuery Information about the media we want to get the information from.
     * @return {@link Observable} with {@link Media} information.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    Observable<Response<Media>> getMediaInfo(MediaInfoQuery mediaInfoQuery)
        throws IllegalAccessException;

    /**
     * Updates the properties of a media asset.
     *
     * @param mediaPropertiesQuery Information with the media asset properties new values to be
     * updated.
     * @return {@link Observable} with the request {@link Response} information.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    Observable<Response<Void>> setMediaProperties(MediaPropertiesQuery mediaPropertiesQuery)
        throws IllegalAccessException;

    /**
     * Deletes a media asset.
     *
     * @param mediaDeleteQuery Information to identify the media asset we want to delete.
     * @return {@link Observable} with the request {@link Response} information.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    Observable<Response<Void>> deleteMedia(MediaDeleteQuery mediaDeleteQuery)
        throws IllegalAccessException;

    /**
     * Gets the download file URL for a specific media asset file. If the media item id was not
     * specified, it will return the download URL of the media specified by media id.
     *
     * @param mediaDownloadQuery Information with the media we want to get the URL from.
     * @return {@link Observable} with the {@link DownloadUrl} information of the media asset file.
     */
    Observable<Response<DownloadUrl>> getMediaDownloadUrl(MediaDownloadQuery mediaDownloadQuery);

    /**
     * Creates a usage record for a media asset.
     *
     * @param usageCreateQuery Information about the asset usage we want to create.
     * @return {@link Observable} with {@link Usage} information.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    Observable<Response<Usage>> createUsage(UsageCreateQuery usageCreateQuery)
        throws IllegalAccessException;

    /**
     * Gets all the media assets usage records.
     *
     * @param usageQuery Information about the asset usage we want to get the information from.
     * @return {@link Observable} with list of {@link Usage}.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    Observable<Response<List<Usage>>> getUsage(UsageQuery usageQuery) throws IllegalAccessException;

    /**
     * Deletes a usage record of a media asset.
     *
     * @param usageDeleteQuery Information about the asset usage we want to delete.
     * @return {@link Observable} with the request {@link Response} information.
     * @throws IllegalAccessException Check {@link Utils#convertField(Field, Object, Map)} for more
     * information.
     */
    Observable<Response<Void>> deleteUsage(UsageDeleteQuery usageDeleteQuery)
        throws IllegalAccessException;

    /**
     * Uploads a file with the information specified in the query parameter.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @return {@link Observable} with the {@link SaveMediaResponse} information.
     */
    Observable<SaveMediaResponse> uploadFile(UploadQuery uploadQuery);

    /**
     * Uploads a file with Progress Report.
     *
     * @param uploadQuery Upload query with the information to upload the file.
     * @return {@link Observable} with the {@link UploadProgress} information.
     */
    Observable<UploadProgress> uploadFileWithProgress(UploadQuery uploadQuery);
}
