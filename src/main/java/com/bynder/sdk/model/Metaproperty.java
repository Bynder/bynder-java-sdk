/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bynder.sdk.api.BynderApi;

/**
 * Metaproperty model returned by {@link BynderApi#getMetaproperties(Map)}.
 */
public class Metaproperty {

    /**
     * Id of the metaproperty.
     */
    private String id;
    /**
     * Name of the metaproperty.
     */
    private String name;
    /**
     * Label of the metaproperty.
     */
    private String label;
    /**
     * Child metaproperty options.
     */
    private List<MetapropertyOption> options = new ArrayList<>();
    /**
     * True if metaproperty option has filterable turned on.
     */
    private Boolean isFilterable;
    /**
     * Order in which the metaproperty should appear.
     */
    private int zindex;
    /**
     * Metaproperty type.
     */
    private String type;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public List<MetapropertyOption> getOptions() {
        return options;
    }

    public Boolean isFilterable() {
        return isFilterable;
    }

    public int getZindex() {
        return zindex;
    }

    public String getType() {
        return type;
    }
}
