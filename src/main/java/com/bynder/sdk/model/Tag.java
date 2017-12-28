/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.bynder.sdk.api.BynderApi;

/**
 * Tag model returned by {@link BynderApi#getTags()}.
 */
public class Tag {

    /**
     * Tag id.
     */
    private String id;
    /**
     * Tag name.
     */
    private String tag;
    /**
     * Media count of assets with the tag.
     */
    private int mediaCount;

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public int getMediaCount() {
        return mediaCount;
    }
}
