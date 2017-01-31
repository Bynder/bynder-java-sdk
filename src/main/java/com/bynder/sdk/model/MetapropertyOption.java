/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.ArrayList;
import java.util.List;

public class MetapropertyOption {

    private String id;
    private String name;
    private String label;
    private List<MetapropertyOption> options = new ArrayList<>();
    private int mediaCount;
    private Boolean isSelectable;
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

    public int getMediaCount() {
        return mediaCount;
    }

    public Boolean isSelectable() {
        return isSelectable;
    }

    public int getZindex() {
        return zindex;
    }

    @Override
    public String toString() {
        return "MetapropertyOption [id=" + id + ", name=" + name + ", label=" + label + ", options=" + options + ", mediaCount=" + mediaCount + ", isSelectable=" + isSelectable + ", zindex=" + zindex
                + "]";
    }
}
