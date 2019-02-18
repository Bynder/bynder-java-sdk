/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.asset;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.DownloadUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.Smartfilter;
import com.bynder.sdk.model.Tag;
import com.bynder.sdk.model.Usage;
import com.bynder.sdk.model.upload.SaveMediaResponse;
import com.bynder.sdk.model.upload.UploadProgress;
import com.bynder.sdk.query.MediaDeleteQuery;
import com.bynder.sdk.query.MediaDownloadQuery;
import com.bynder.sdk.query.MediaInfoQuery;
import com.bynder.sdk.query.MediaModifyQuery;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.MetapropertyQuery;
import com.bynder.sdk.query.UsageCreateQuery;
import com.bynder.sdk.query.UsageDeleteQuery;
import com.bynder.sdk.query.UsageQuery;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.upload.UploadQuery;
import com.bynder.sdk.util.Utils;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
import retrofit2.Response;

/**
 * Interface to represent operations that can be done to the Bynder Asset Bank.
 */
public interface AssetService {

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
     */
    Observable<Response<Map<String, Metaproperty>>> getMetaproperties(
        MetapropertyQuery metapropertyQuery);

    /**
     * Gets a list of media using query information.
     *
     * @param mediaQuery Information to correctly filter/paginate media.
     * @return {@link Observable} with list of {@link Media}.
     */
    Observable<Response<List<Media>>> getMediaList(MediaQuery mediaQuery);

    /**
     * Gets all the information for a specific media. This is needed to get the media items of a
     * media asset.
     *
     * @param mediaInfoQuery Information about the media we want to get the information from.
     * @return {@link Observable} with {@link Media} information.
     */
    Observable<Response<Media>> getMediaInfo(MediaInfoQuery mediaInfoQuery);

    /**
     * Modifies the metadata of a media asset.
     *
     * @param mediaModifyQuery Information with the media asset metadata new values to be
     * modified.
     * @return {@link Observable} with the request {@link Response} information.
     */
    Observable<Response<Void>> modifyMedia(MediaModifyQuery mediaModifyQuery);

    /**
     * Deletes a media asset.
     *
     * @param mediaDeleteQuery Information to identify the media asset we want to delete.
     * @return {@link Observable} with the request {@link Response} information.
     */
    Observable<Response<Void>> deleteMedia(MediaDeleteQuery mediaDeleteQuery);

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
     * @throws IllegalAccessException Check {@link Utils} convertField method for more
     * information.
     */
    Observable<Response<Usage>> createUsage(UsageCreateQuery usageCreateQuery);

    /**
     * Gets all the media assets usage records.
     *
     * @param usageQuery Information about the asset usage we want to get the information from.
     * @return {@link Observable} with list of {@link Usage}.
     * @throws IllegalAccessException Check {@link Utils} convertField method for more
     * information.
     */
    Observable<Response<List<Usage>>> getUsage(UsageQuery usageQuery);

    /**
     * Deletes a usage record of a media asset.
     *
     * @param usageDeleteQuery Information about the asset usage we want to delete.
     * @return {@link Observable} with the request {@link Response} information.
     * @throws IllegalAccessException Check {@link Utils} convertField method for more
     * information.
     */
    Observable<Response<Void>> deleteUsage(UsageDeleteQuery usageDeleteQuery);

    /**
     * Get Smartfilters.
     *
     * @return {@link Observable} with List of {@link Smartfilter};
     */
    Observable<Response<List<Smartfilter>>> getSmartfilters();

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

    /**
     * Builder class used to create a new instance of {@link AssetService}.
     */
    class Builder {

        private Builder() {
        }

        public static AssetService create(final BynderApi bynderApi,
            final QueryDecoder queryDecoder) {
            return new AssetServiceImpl(bynderApi, queryDecoder);
        }
    }
}
