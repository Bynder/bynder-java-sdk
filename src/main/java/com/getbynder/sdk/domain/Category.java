package com.getbynder.sdk.domain;

/**
 *
 * @author daniel.sequeira
 */
public class Category {

    private String id;
    private String name;
    private String description;

    public Category(final String id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
}
