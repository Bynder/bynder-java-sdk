/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.ArrayList;
import java.util.List;

import com.bynder.sdk.api.BynderApi;

/**
 * Metaproperty model returned by {@link BynderApi#getMetaproperties(Boolean)}.
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

    @Override
    public String toString() {
        return "Metaproperty [id=" + id + ", name=" + name + ", label=" + label + ", options=" + options + ", isFilterable=" + isFilterable + ", zindex=" + zindex + "]";
    }
}
