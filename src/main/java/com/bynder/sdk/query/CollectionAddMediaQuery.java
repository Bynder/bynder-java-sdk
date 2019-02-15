/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.JsonParameterDecoder;
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
    @ApiField(name = "data", decoder = JsonParameterDecoder.class)
    private final String[] mediaIds;

    public CollectionAddMediaQuery(final String collectionId, final String[] mediaIds) {
        this.collectionId = collectionId;
        this.mediaIds = mediaIds;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public String[] getMediaIds() {
        return mediaIds;
    }
}
