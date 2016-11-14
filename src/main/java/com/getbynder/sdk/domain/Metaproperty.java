package com.getbynder.sdk.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel.sequeira
 */
public class Metaproperty {

    private String id;
    private String name;
    private String label;
    private List<Metaproperty> options = new ArrayList<>();
    private int mediaCount;
    private Boolean isFilterable;
    private int zindex;

    public Metaproperty(final String id, final String name, final String label, final List<Metaproperty> options, final int mediaCount, final Boolean isFilterable, final int zindex) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.options = options;
        this.mediaCount = mediaCount;
        this.isFilterable = isFilterable;
        this.zindex = zindex;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public List<Metaproperty> getOptions() {
        return options;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(final int mediaCount) {
        this.mediaCount = mediaCount;
    }

    public Boolean isFilterable() {
        return isFilterable;
    }

    public int getZindex() {
        return zindex;
    }

    @Override
    public String toString() {
        return "Metaproperty [id=" + id + ", name=" + name + ", label=" + label + ", mediaCount=" + mediaCount + ", isFilterable=" + isFilterable + ", zindex=" + zindex + "]";
    }
}
