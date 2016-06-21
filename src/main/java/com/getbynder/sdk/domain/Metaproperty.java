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

    public Metaproperty(final String id, final String name, final String label, final List<Metaproperty> options, final int mediaCount) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.options = options;
        this.mediaCount = mediaCount;
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

    @Override
    public String toString() {
        return "Metaproperty [id=" + id + ", name=" + name + ", label=" + label + ", mediaCount=" + mediaCount + "]";
    }
}
