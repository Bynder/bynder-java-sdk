/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.model.MediaType;
import com.bynder.sdk.query.decoder.ApiField;
import com.bynder.sdk.query.decoder.BooleanParameterDecoder;
import com.bynder.sdk.query.decoder.MetapropertyParameterDecoder;
import com.bynder.sdk.query.decoder.StringArrayParameterDecoder;
import java.util.Map;

/**
 * Query to filter media results.
 */
public class MediaQuery {

    /**
     * The type of the media asset. Possible values: image, document, audio, video.
     */
    @ApiField
    private MediaType type;
    /**
     * Keyword that the media asset has to have to appear in the results. Search filenames, tags,
     * extensions, collection names, guidelines, brandstore, campaigns in workflow, enriched PDFs,
     * word documents.
     */
    @ApiField
    private String keyword;
    /**
     * This property has to be set to 1 (TRUE) for the API to retrieved only media assets marked as
     * public.
     */
    @ApiField(decoder = BooleanParameterDecoder.class)
    private Boolean isPublic;
    /**
     * Whether to fetch the media item information or not.
     */
    @ApiField(decoder = BooleanParameterDecoder.class)
    private Boolean includeMediaItems;
    /**
     * Limit of results per request. Maximum: 1000. Default: 50.
     */
    @ApiField
    private Integer limit;
    /**
     * Page to be retrieved.
     */
    @ApiField
    private Integer page;
    /**
     * Metaproperty option ids that the media asset needs to have at least one of.
     */
    @ApiField(name = "propertyOptionId", decoder = StringArrayParameterDecoder.class)
    private String[] propertyOptionIds;
    /**
     * Desired order for the returned list of results.
     */
    @ApiField
    private OrderBy orderBy;
    /**
     * Metaproperty option ids that the media asset has to have.
     */
    @ApiField(name = "property", decoder = MetapropertyParameterDecoder.class)
    private Map<String, String> metapropertyOptions;

    public MediaType getType() {
        return type;
    }

    public MediaQuery setType(final MediaType type) {
        this.type = type;
        return this;
    }

    public String getKeyword() {
        return keyword;
    }

    public MediaQuery setKeyword(final String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public MediaQuery setIsPublic(final Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public Boolean getIncludeMediaItems() {
        return includeMediaItems;
    }

    public MediaQuery setIncludeMediaItems(final Boolean includeMediaItems) {
        this.includeMediaItems = includeMediaItems;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public MediaQuery setLimit(final Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public MediaQuery setPage(final Integer page) {
        this.page = page;
        return this;
    }

    public String[] getPropertyOptionIds() {
        return propertyOptionIds;
    }

    public MediaQuery setPropertyOptionIds(final String... propertyOptionIds) {
        this.propertyOptionIds = propertyOptionIds;
        return this;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public MediaQuery setOrderBy(final OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public Map<String, String> getMetapropertyOptions() {
        return metapropertyOptions;
    }

    public MediaQuery setMetapropertyOptions(final Map<String, String> metapropertyOptions) {
        this.metapropertyOptions = metapropertyOptions;
        return this;
    }
}
