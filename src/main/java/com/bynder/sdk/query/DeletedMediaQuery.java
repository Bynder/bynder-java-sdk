/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import com.bynder.sdk.query.decoder.ApiField;

/**
 * Query to filter deleted media results.
 */
public class DeletedMediaQuery {

    /**
     * Limit of results per request. Example: 20. Default: 10.
     */
    @ApiField
    private Integer limit;
    /**
     * Page to be retrieved.
     */
    @ApiField
    private Integer page;
    /**
     * Retrieve assets removed after this date.
     * You can only retrieve the removed assets from the last 30 days.
     */
    @ApiField
    private String dateRemoved;
    /**
     * Desired field to sort the returned list of results.
     */
    @ApiField
    private OrderField field;
    /**
     * Desired order for the returned list of results.
     */
    @ApiField
    private Order order;

    public Integer getLimit() {
        return limit;
    }

    public DeletedMediaQuery setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public DeletedMediaQuery setPage(Integer page) {
        this.page = page;
        return this;
    }

    public String getDateRemoved() {
        return dateRemoved;
    }

    public DeletedMediaQuery setDateRemoved(String dateRemoved) {
        this.dateRemoved = dateRemoved;
        return this;
    }

    public OrderField getField() {
        return field;
    }

    public DeletedMediaQuery setField(OrderField field) {
        this.field = field;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public DeletedMediaQuery setOrder(Order order) {
        this.order = order;
        return this;
    }

}
