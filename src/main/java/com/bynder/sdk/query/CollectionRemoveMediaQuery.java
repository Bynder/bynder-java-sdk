/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import java.util.List;

/**
 * Query to remove media assets from a collection.
 */
public class CollectionRemoveMediaQuery {

    /**
     * Id of the collection from which we want to remove media.
     */
    private String collectionId;

    /**
     * List with the media assets ids to be removed.
     */
    @ApiField(name = "deleteIds", conversionType = ConversionType.LIST_FIELD)
    private List<String> mediaIds;

    public CollectionRemoveMediaQuery(final String collectionId, final List<String> mediaIds) {
        this.collectionId = collectionId;
        this.mediaIds = mediaIds;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public List<String> getMediaIds() {
        return mediaIds;
    }
}
