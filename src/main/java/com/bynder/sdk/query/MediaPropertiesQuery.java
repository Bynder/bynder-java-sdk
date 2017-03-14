/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

public class MediaPropertiesQuery {

    private String mediaId;
    private String name;
    private String description;
    private String copyright;
    private Boolean archive;
    private String datePublished;

    public MediaPropertiesQuery(final String mediaId, final String name, final String description, final String copyright, final Boolean archive, final String datePublished) {
        this.mediaId = mediaId;
        this.name = name;
        this.description = description;
        this.copyright = copyright;
        this.archive = archive;
        this.datePublished = datePublished;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCopyright() {
        return copyright;
    }

    public Boolean getArchive() {
        return archive;
    }

    public String getDatePublished() {
        return datePublished;
    }
}
