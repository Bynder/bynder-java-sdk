/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.ArrayList;
import java.util.List;

public class Metaproperty {

    private String id;
    private String name;
    private String label;
    private List<MetapropertyOption> options = new ArrayList<>();
    private Boolean isFilterable;
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
