package com.getbynder.sdk.domain;

import java.util.Map;

/**
 *
 * @author daniel.sequeira
 */
public class Count {

    private Map<String, String> brandId;
    private Map<String, String> subBrandId;
    private Map<String, String> categoryId;
    private Map<String, String> tags;
    private Map<String, String> type;
    private Map<String, String> orientation;
    private Integer total;

    public Count(final Map<String, String> brandId, final Map<String, String> subBrandId, final Map<String, String> categoryId, final Map<String, String> tags, final Map<String, String> type, final Map<String, String> orientation, final Integer total) {
        this.brandId = brandId;
        this.subBrandId = subBrandId;
        this.categoryId = categoryId;
        this.tags = tags;
        this.type = type;
        this.orientation = orientation;
        this.total = total;
    }

    public Map<String, String> getBrandId() {
        return brandId;
    }

    public Map<String, String> getSubBrandId() {
        return subBrandId;
    }

    public Map<String, String> getCategoryId() {
        return categoryId;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public Map<String, String> getType() {
        return type;
    }

    public Map<String, String> getOrientation() {
        return orientation;
    }

    public Integer getTotal() {
        return total;
    }
}
