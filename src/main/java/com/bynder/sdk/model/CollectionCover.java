/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.List;

/**
 * Model to represent the cover of a {@link Collection}.
 */
public class CollectionCover {

    /**
     * Thumbnail URL.
     */
    private String thumbnail;
    /**
     * Thumbnails URLs.
     */
    private List<String> thumbnails;
    /**
     * Cover large version URL.
     */
    private String large;

    public String getThumbnail() {
        return thumbnail;
    }

    public List<String> getThumbnails() {
        return thumbnails;
    }

    public String getLarge() {
        return large;
    }
}
