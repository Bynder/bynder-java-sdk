/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.query.decoder.ApiField;

/**
 * Query to filter tag results.
 */
public class TagQuery {

    /**
     * Maximum number of results
     */
    @ApiField
    private Integer limit;
    /**
     * Offset page for results: return the N-th set of limit-results.
     */
    @ApiField
    private Integer page;
    /**
     * Order of the returned list of tags..
     */
    @ApiField
    private String orderBy;
    /**
     * Search on matching names
     */
    @ApiField
    private String keyword;
    /**
     * Minimum media count that the returned tags should have.
     */
    @ApiField
    private Integer mincount;


    public Integer getLimit() {
        return limit;
    }

    public TagQuery setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public TagQuery setPage(Integer page) {
        this.page = page;
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public TagQuery setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getKeyword() {
        return keyword;
    }

    public TagQuery setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Integer getMincount() {
        return mincount;
    }

    public TagQuery setMincount(Integer mincount) {
        this.mincount = mincount;
        return this;
    }
}
