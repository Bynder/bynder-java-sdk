/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

/**
 * Model to represent each media item returned in {@link SaveMediaResponse}.
 */
public class SaveMediaItem {

    /**
     * Original location of the media item.
     */
    private String original;
    /**
     * Destination location of the media item.
     */
    private String destination;

    public String getOriginal() {
        return original;
    }

    public String getDestination() {
        return destination;
    }
}
