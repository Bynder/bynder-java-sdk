/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to specify the metaproperty options to be added to a media asset.
 */
public class AddMetapropertyToMediaQuery {

    /**
     * Id of the media for which we want to add metaproperty options.
     */
    private String mediaId;
    /**
     * Id of the metaproperty to which the metaproperty options belong to.
     */
    private String metapropertyId;
    /**
     * Metaproperty options ids.
     */
    private String[] optionsIds;

    public AddMetapropertyToMediaQuery(final String mediaId, final String metapropertyId, final String[] optionsIds) {
        this.mediaId = mediaId;
        this.metapropertyId = metapropertyId;
        this.optionsIds = optionsIds;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getMetapropertyId() {
        return metapropertyId;
    }

    public String[] getOptionsIds() {
        return optionsIds;
    }
}
