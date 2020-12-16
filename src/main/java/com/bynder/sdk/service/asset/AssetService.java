/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.asset;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.*;
import com.bynder.sdk.query.*;
import com.bynder.sdk.query.decoder.QueryDecoder;
import io.reactivex.Observable;
import retrofit2.Response;

import java.util.List;
import java.util.Map;

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
     */
    Observable<Response<Usage>> createUsage(UsageCreateQuery usageCreateQuery);

    /**
     * Gets all the media assets usage records.
     *
     * @param usageQuery Information about the asset usage we want to get the information from.
     * @return {@link Observable} with list of {@link Usage}.
     */
    Observable<Response<List<Usage>>> getUsage(UsageQuery usageQuery);

    /**
     * Deletes a usage record of a media asset.
     *
     * @param usageDeleteQuery Information about the asset usage we want to delete.
     * @return {@link Observable} with the request {@link Response} information.
     */
    Observable<Response<Void>> deleteUsage(UsageDeleteQuery usageDeleteQuery);

    /**
     * Get Smartfilters.
     *
     * @return {@link Observable} with List of {@link Smartfilter};
     */
    Observable<Response<List<Smartfilter>>> getSmartfilters();

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
