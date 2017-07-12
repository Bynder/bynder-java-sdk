/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.model.MediaType;

/**
 * Query to specify if we want to get metaproperties with media count or not.
 */
public class MetapropertyQuery {

    /**
     * This property has to be set to 1 (TRUE) for the API to calculate and include the media count
     * for each metaproperty option in the response.
     */
    @ApiField(name = "count")
    private Boolean count;
    /**
     * Filters the count results by media asset type. It only makes sense to be defined if the count
     * parameter was set to 1. Possible values: image, document, audio, video.
     */
    @ApiField(name = "type")
    private MediaType type;

    public Boolean getCount() {
        return count;
    }

    public MetapropertyQuery setCount(final Boolean count) {
        this.count = count;
        return this;
    }

    public MediaType getType() {
        return type;
    }

    public MetapropertyQuery setType(final MediaType type) {
        this.type = type;
        return this;
    }
}
