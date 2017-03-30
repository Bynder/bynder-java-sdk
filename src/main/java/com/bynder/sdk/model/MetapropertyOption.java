/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model to represent metaproperty options.
 */
public class MetapropertyOption {

    /**
     * Id of the metaproperty option.
     */
    private String id;
    /**
     * Name of the metaproperty option.
     */
    private String name;
    /**
     * Label of the metaproperty option.
     */
    private String label;
    /**
     * Child metaproperty options.
     */
    private List<MetapropertyOption> options = new ArrayList<>();
    /**
     * Media count of media assets with the metaproperty option.
     */
    private int mediaCount;
    /**
     * True if metaproperty option has selectable turned on.
     */
    private Boolean isSelectable;
    /**
     * Order in which the metaproperty option should appear.
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

    public int getMediaCount() {
        return mediaCount;
    }

    public Boolean isSelectable() {
        return isSelectable;
    }

    public int getZindex() {
        return zindex;
    }
}
