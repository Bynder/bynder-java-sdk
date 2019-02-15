/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.StringArrayParameterDecoder;

/**
 * Query to remove media assets from a collection.
 */
public class CollectionRemoveMediaQuery {

    /**
     * Id of the collection from which we want to remove media.
     */
    private final String collectionId;

    /**
     * String array with the media assets ids to be removed.
     */
    @ApiField(name = "deleteIds", decoder = StringArrayParameterDecoder.class)
    private final String[] mediaIds;

    public CollectionRemoveMediaQuery(final String collectionId, final String[] mediaIds) {
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
