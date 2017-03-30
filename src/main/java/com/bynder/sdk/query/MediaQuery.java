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
     * Metaproperty option ids that the media asset has to have.
     */
    @ApiField
    private List<String> propertyOptionId;

    public MediaQuery(final MediaType type, final String keyword, final Integer limit, final Integer page, final List<String> propertyOptionId) {
        this.type = type;
        this.keyword = keyword;
        this.limit = limit;
        this.page = page;
        this.propertyOptionId = propertyOptionId;
    }

    public MediaType getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPage() {
        return page;
    }

    public List<String> getPropertyOptionId() {
        return propertyOptionId;
    }
}
