/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

public class MediaInfoQuery {

    private String mediaId;
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
