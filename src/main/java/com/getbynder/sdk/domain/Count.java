package com.getbynder.sdk.domain;

import java.util.HashMap;
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
    private Map<String, Map<String, Integer>> metaproperties = new HashMap<String, Map<String, Integer>>();

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

    public Map<String, Map<String, Integer>> getMetaproperties() {
        return metaproperties;
    }

    public void addMetaproperty(final String name, final Map<String, Integer> options) {
        metaproperties.put(name, options);
    }
}
