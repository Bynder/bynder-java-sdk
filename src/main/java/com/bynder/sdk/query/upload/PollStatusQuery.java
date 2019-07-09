/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.upload;

import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.StringArrayParameterDecoder;

/**
 * Query with the information to poll if asset has finished converting.
 */
public class PollStatusQuery {

    /**
     * Import ids of a finalised file.
     */
    @ApiField(decoder = StringArrayParameterDecoder.class)
    private final String[] items;

    public PollStatusQuery(final String[] items) {
        this.items = items;
    }

    public String[] getItems() {
        return items;
    }
}
