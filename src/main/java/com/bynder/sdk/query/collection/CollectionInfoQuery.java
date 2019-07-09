/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.collection;

import com.bynder.sdk.query.decoder.ApiField;

/**
 * Query to get collection information.
 */
public class CollectionInfoQuery {

    /**
     * Id of the collection from which we want to retrieve information.
     */
    @ApiField(name = "id")
    private final String collectionId;

    public CollectionInfoQuery(final String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionId() {
        return collectionId;
    }
}
