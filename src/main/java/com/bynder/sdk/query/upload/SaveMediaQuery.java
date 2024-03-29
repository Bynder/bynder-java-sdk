/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.upload;

import com.bynder.sdk.query.MetapropertyAttribute;
import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.MetapropertyAttributesDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Query with the information to save a media asset.
 */
public class SaveMediaQuery {

    /**
     * Import id of a finalised and processed upload to save.
     */
    @ApiField
    private final String importId;

    /**
     * Brand id to save the media asset to.
     */
    @ApiField
    private String brandId;

    /**
     * Name of the media asset.
     */
    @ApiField
    private String name;

    /**
     * Media id for which to save the new version.
     */
    @ApiField(name = "id")
    private String mediaId;

    /**
     * Flags if the media asset should be sent to the waiting room.
     */
    @ApiField
    private Boolean audit;

    /**
     * Comma-separated list of tags to be set on the asset.
     */
    @ApiField
    private String tags;

    /**
     * Dictionary with (metaproperty) options to set on the asset upon upload.
     */
    @ApiField(name = "metaproperty", decoder = MetapropertyAttributesDecoder.class)
    private List<MetapropertyAttribute> metaproperties;

    public SaveMediaQuery(final String importId) {
        this.importId = importId;
        this.metaproperties = new ArrayList<>();
    }

    public SaveMediaQuery setBrandId(final String brandId) {
        this.brandId = brandId;
        return this;
    }

    public SaveMediaQuery setName(final String name) {
        this.name = name;
        return this;
    }

    public SaveMediaQuery setMediaId(final String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public SaveMediaQuery setAudit(final Boolean audit) {
        this.audit = audit;
        return this;
    }

    public SaveMediaQuery setTags(final String tags) {
        this.tags = tags;
        return this;
    }

    public SaveMediaQuery setMetaproperties(List<MetapropertyAttribute> metaproperties) {
        this.metaproperties = metaproperties;
        return this;
    }

    public SaveMediaQuery addMetaproperty(final MetapropertyAttribute metaproperty) {
        this.metaproperties.add(metaproperty);
        return this;
    }

}
