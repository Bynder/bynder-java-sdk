package com.getbynder.api.domain;

import org.json.JSONObject;

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

    public Category(final JSONObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.description = jsonObject.getString("description");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
}
