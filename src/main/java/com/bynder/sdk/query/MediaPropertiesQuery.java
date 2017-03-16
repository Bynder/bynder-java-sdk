/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to specify the media properties to be updated.
 */
public class MediaPropertiesQuery {

    /**
     * Id of the media for which we want to update its properties.
     */
    private String mediaId;
    /**
     * Name new value.
     */
    private String name;
    /**
     * Description new value.
     */
    private String description;
    /**
     * Copyright new value.
     */
    private String copyright;
    /**
     * Archive new status.
     */
    private Boolean archive;
    /**
     * Publication date new value.
     */
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
