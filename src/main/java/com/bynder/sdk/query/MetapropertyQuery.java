/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.model.MediaType;
import com.bynder.sdk.query.decoder.ApiField;

/**
 * Query to specify if we want to get metaproperties with media count or not.
 */
public class MetapropertyQuery {

    /**
     * This property has to be set to 1 (TRUE) for the API to calculate and include the media count
     * for each metaproperty option in the response.
     */
    @ApiField
    private Boolean count;
    /**
     * Filters the count results by media asset type. It only makes sense to be defined if the count
     * parameter was set to 1. Possible values: image, document, audio, video.
     */
    @ApiField
    private MediaType type;
    /**
     * Make it possible to get metaproperties without options, to avoid possible timeout if the number
     *  of metapropertiy/options is very large (e.g. with Stockholm National Museum taxonomies)
     * @author Colin Manning - zetcom
     */
    @ApiField
    private Boolean options;
    /**
     * A collection id of a Metaproperty, to get the information for specific metaproperties
     * Most common use case will be to fetch a single metaproperty,
     * but we need to use "ids" and not "id" to get a Map back or else the API breaks
     * @author Colin Manning - zetcom
     */
    @ApiField
    private String ids;

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

    public Boolean getOptions() {
        return options;
    }

    public MetapropertyQuery setOptions(final Boolean options) {
        this.options = options;
        return this;
    }
    public String getIds() {
        return ids;
    }

    public MetapropertyQuery setIds(final String ids) {
        this.ids = ids;
        return this;
    }
}
