/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import java.util.List;

import com.bynder.sdk.model.MediaType;

/**
 * Query to filter media results.
 */
public class MediaQuery {

    /**
     * The type of the media asset. Possible values: image, document, audio, video.
     */
    @ApiField(name = "type")
    private MediaType type;
    /**
     * Keyword that the media asset has to have to appear in the results. Search filenames, tags,
     * extensions, collection names, guidelines, brandstore, campaigns in workflow, enriched PDFs,
     * word documents.
     */
    @ApiField(name = "keyword")
    private String keyword;
    /**
     * Limit of results per request. Maximum: 1000. Default: 50.
     */
    @ApiField(name = "limit")
    private Integer limit;
    /**
     * Page to be retrieved.
     */
    @ApiField(name = "page")
    private Integer page;
    /**
     * Metaproperty option ids that the media asset has to have.
     */
    @ApiField(name = "propertyOptionId", conversionType = ConversionType.LIST_FIELD)
    private List<String> propertyOptionIds;

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

    public List<String> getPropertyOptionIds() {
        return propertyOptionIds;
    }

    public MediaQuery setPropertyOptionIds(final List<String> propertyOptionIds) {
        this.propertyOptionIds = propertyOptionIds;
        return this;
    }
}
