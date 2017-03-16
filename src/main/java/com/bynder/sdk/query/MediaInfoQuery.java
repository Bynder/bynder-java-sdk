/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to get media information including its media items.
 */
public class MediaInfoQuery {

    /**
     * Id of the media from which we want to retrieve information.
     */
    private String mediaId;
    /**
     * This property has to be set to 1 (TRUE) for the API response to include the media items of
     * the media asset.
     */
    private Boolean versions;

    public MediaInfoQuery(final String mediaId, final Boolean versions) {
        this.mediaId = mediaId;
        this.versions = versions;
    }

    public String getMediaId() {
        return mediaId;
    }

    public Boolean getVersions() {
        return versions;
    }
}
