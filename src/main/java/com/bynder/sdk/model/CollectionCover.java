/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.net.URL;
import java.util.List;

/**
 * Model to represent the cover of a {@link Collection}.
 */
public class CollectionCover {

    /**
     * Thumbnail URL.
     */
    private URL thumbnail;
    /**
     * Thumbnails URLs.
     */
    private List<URL> thumbnails;
    /**
     * Cover large version URL.
     */
    private URL large;

    public URL getThumbnail() {
        return thumbnail;
    }

    public List<URL> getThumbnails() {
        return thumbnails;
    }

    public URL getLarge() {
        return large;
    }
}
