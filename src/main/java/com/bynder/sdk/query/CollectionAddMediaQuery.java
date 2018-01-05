/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import java.util.List;

/**
 * Query to add media assets to a collection.
 */
public class CollectionAddMediaQuery {

    /**
     * Id of the collection to which we want to add media.
     */
    private final String collectionId;

    /**
     * List with the media assets ids to be added.
     */
    @ApiField(name = "data", conversionType = ConversionType.JSON_FIELD)
    private final List<String> mediaIds;

    public CollectionAddMediaQuery(final String collectionId, final List<String> mediaIds) {
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
