/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Query to filter collection results.
 */
public class CollectionQuery {

    /**
     * Keyword that the collection has to have to appear in the results. It searches collection
     * names and descriptions.
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
     * Desired order for the returned collection results.
     */
    @ApiField(name = "orderBy")
    private CollectionOrderType orderBy;

    public String getKeyword() {
        return keyword;
    }

    public CollectionQuery setKeyword(final String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public CollectionQuery setLimit(final Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public CollectionQuery setPage(final Integer page) {
        this.page = page;
        return this;
    }

    public CollectionOrderType getOrderBy() {
        return orderBy;
    }

    public CollectionQuery setOrderBy(final CollectionOrderType orderBy) {
        this.orderBy = orderBy;
        return this;
    }
}
