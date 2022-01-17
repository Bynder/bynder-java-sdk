/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.MetapropertyAttributesDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Query to specify the media metadata to be modified.
 */
public class MediaModifyQuery {

    /**
     * Id of the media for which we want to modify its metadata.
     */
    @ApiField(name = "id")
    private final String mediaId;

    /**
     * Name new value.
     */
    @ApiField
    private String name;

    /**
     * Description new value.
     */
    @ApiField
    private String description;

    /**
     * Copyright new value.
     */
    @ApiField
    private String copyright;

    /**
     * Archive new status.
     */
    @ApiField
    private Boolean archive;

    /**
     * Date published new value.
     */
    @ApiField
    private String datePublished;

    /**
     * Limited new status.
     */
    @ApiField
    private Boolean limited;

    /**
     * Date limited new value.
     */
    @ApiField
    private String limitedDate;

    /**
     * Tags new value.
     */
    @ApiField
    private String tags;

    /**
     * Dictionary with (metaproperty) options to set on the asset.
     */
    @ApiField(name = "metaproperty", decoder = MetapropertyAttributesDecoder.class)
    private List<MetapropertyAttribute> metaproperties;

    public MediaModifyQuery(final String mediaId) {
        this.mediaId = mediaId;
        this.metaproperties = new ArrayList<>();
    }

    public MediaModifyQuery setName(final String name) {
        this.name = name;
        return this;
    }

    public MediaModifyQuery setDescription(final String description) {
        this.description = description;
        return this;
    }

    public MediaModifyQuery setCopyright(final String copyright) {
        this.copyright = copyright;
        return this;
    }

    public MediaModifyQuery setArchive(final Boolean archive) {
        this.archive = archive;
        return this;
    }

    public MediaModifyQuery setDatePublished(final String datePublished) {
        this.datePublished = datePublished;
        return this;
    }

    public MediaModifyQuery setMetaproperties(List<MetapropertyAttribute> metaproperties) {
        this.metaproperties = metaproperties;
        return this;
    }

    public Boolean getLimited() {
        return limited;
    }

    public String getLimitedDate() {
        return limitedDate;
    }

    public MediaModifyQuery setLimited(final Boolean limited) {
        this.limited = limited;
        return this;
    }

    public MediaModifyQuery setLimitedDate(final String limitedDate) {
        this.limitedDate = limitedDate;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public MediaModifyQuery setTags(final List<String> tags) {
        this.tags = String.join(",", tags);
        return this;
    }

    /**
     * @deprecated use {@link #addMetaproperty(MetapropertyAttribute)} instead.
     */
    @Deprecated
    public MediaModifyQuery setMetaproperty(final MetapropertyAttribute metaproperty) {
        return addMetaproperty(metaproperty);
    }

    public MediaModifyQuery addMetaproperty(final MetapropertyAttribute metaproperty) {
        this.metaproperties.add(metaproperty);
        return this;
    }

}
