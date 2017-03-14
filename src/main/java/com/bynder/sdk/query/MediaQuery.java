/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import java.util.List;

import com.bynder.sdk.model.MediaType;

public class MediaQuery {

    private MediaType type;
    private String keyword;
    private Integer limit;
    private Integer page;
    private List<String> propertyOptionId;
    private Boolean count;

    public MediaQuery(final MediaType type, final String keyword, final Integer limit, final Integer page, final List<String> propertyOptionId, final Boolean count) {
        this.type = type;
        this.keyword = keyword;
        this.limit = limit;
        this.page = page;
        this.propertyOptionId = propertyOptionId;
        this.count = count;
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

    public Boolean getCount() {
        return count;
    }
}
