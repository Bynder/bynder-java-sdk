/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to specify the media from which we want the download URL.
 */
public class MediaDownloadQuery {

    /**
     * Media id.
     */
    private String mediaId;
    /**
     * Media item id. If null the URL returned will be the URL of the original media.
     */
    private String mediaItemId;

    public MediaDownloadQuery(final String mediaId, final String mediaItemId) {
        this.mediaId = mediaId;
        this.mediaItemId = mediaItemId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getMediaItemId() {
        return mediaItemId;
    }
}
