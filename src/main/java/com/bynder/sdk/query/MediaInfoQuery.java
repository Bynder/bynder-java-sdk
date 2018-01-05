/*
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
    @ApiField(name = "id")
    private final String mediaId;
    /**
     * This property has to be set to 1 (TRUE) for the API response to include the media items of
     * the media asset.
     */
    @ApiField(name = "versions")
    private Boolean versions;

    public MediaInfoQuery(final String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public Boolean getVersions() {
        return versions;
    }

    public MediaInfoQuery setVersions(final Boolean versions) {
        this.versions = versions;
        return this;
    }
}
