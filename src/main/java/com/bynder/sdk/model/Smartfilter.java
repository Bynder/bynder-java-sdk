/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Smartfilter model returned by {@link BynderApi#getSmartfilters()}.
 */
public class Smartfilter {

    /**
     * Id of the smartfilter.
     */
    private String id;
    /**
     * Labels of the smartfilter.
     */
    @SerializedName(value = "labels")
    private Map<String, String> labels;
    /**
     * Metaproperties that are part of the smartfilter.
     */
    private List<String> metaproperties;

    public String getId() {
        return id;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public List<String> getMetaproperties() {
        return metaproperties;
    }
}
