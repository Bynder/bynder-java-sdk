/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import java.util.List;

/**
 * Query with the information to poll if asset has finished converting.
 */
public class PollStatusQuery {

    /**
     * Import ids of a finalised file.
     */
    @ApiField(name = "items", conversionType = ConversionType.LIST_FIELD)
    private final List<String> items;

    public PollStatusQuery(final List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }
}
