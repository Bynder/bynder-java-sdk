/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to create collections.
 */
public class CollectionCreateQuery {

    /**
     * Name of the collection.
     */
    @ApiField(name = "name")
    private final String name;

    /**
     * Description of the collection.
     */
    @ApiField(name = "description")
    private String description;

    public CollectionCreateQuery(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CollectionCreateQuery setDescription(final String description) {
        this.description = description;
        return this;
    }
}
