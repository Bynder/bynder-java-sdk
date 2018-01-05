/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to delete a media asset from Bynder.
 */
public class MediaDeleteQuery {

    /**
     * Id of the media asset to be deleted.
     */
    @ApiField(name = "id")
    private final String mediaId;

    public MediaDeleteQuery(final String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }
}
