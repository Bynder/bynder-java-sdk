/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.upload;

import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.StringArrayParameterDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Query with the information to poll if asset has finished converting.
 */
public class PollStatusQuery {
    private static final Logger LOG = LoggerFactory.getLogger(PollStatusQuery.class);


    /**
     * Import ids of a finalised file.
     */
    @ApiField(decoder = StringArrayParameterDecoder.class)
    private final String[] items;

    public PollStatusQuery(final String[] items) {
        this.items = new String[items.length];
        for(int i = 0; i < items.length; i++) {
            LOG.info("PollStatusQuery item: " + items[i]);
            this.items[i] = items[i].replace("/", "");
        }
    }

    public String[] getItems() {
        return items;
    }
}
