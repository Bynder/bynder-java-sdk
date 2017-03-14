/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

public class AddMetapropertyToMediaQuery {

    private String mediaId;
    private String metapropertyId;
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
