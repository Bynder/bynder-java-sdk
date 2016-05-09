package com.getbynder.sdk.domain;

import java.util.List;

/**
 *
 * @author daniel.sequeira
 */
public class Metaproperty {

    private String id;
    private String name;
    private String label;
    private List<Metaproperty> options;

    public Metaproperty(final String id, final String name, final String label, final List<Metaproperty> options) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.options = options;
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
}
