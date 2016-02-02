package com.getbynder.api.domain;

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
    private List<Metaproperty> options;

    public Metaproperty() {
        super();
        this.id = "";
        this.name = "";
        this.label = "";
        this.options = new ArrayList<>();
    }

    public Metaproperty(final String id, final String name, final String label) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.options = new ArrayList<>();
    }

    public Metaproperty(final String id, final String name, final String label, final List<Metaproperty> options) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public List<Metaproperty> getOptions() {
        return options;
    }

    public void setOptions(final List<Metaproperty> options) {
        this.options = options;
    }

    public void addOption(final Metaproperty option) {
        this.options.add(option);
    }

    @Override
    public String toString() {
        return "Metaproperty [id=" + id + ", name=" + name + ", label=" + label + "]";
    }
}
