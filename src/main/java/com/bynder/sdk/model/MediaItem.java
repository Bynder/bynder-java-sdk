/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class MediaItem {

    private String id;
    @SerializedName(value = "fileName")
    private String name;
    private String type;
    private String dateCreated;
    private int height;
    private int width;
    private long size;
    private int version;
    private Boolean active;
    private Map<String, String> thumbnails;

    public MediaItem(final String id, final String name, final String type, final String dateCreated, final int height, final int width, final long size, final int version, final Boolean active,
            final Map<String, String> thumbnails) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.dateCreated = dateCreated;
        this.height = height;
        this.width = width;
        this.size = size;
        this.version = version;
        this.active = active;
        this.thumbnails = thumbnails;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public long getSize() {
        return size;
    }

    public int getVersion() {
        return version;
    }

    public Boolean isActive() {
        return active;
    }

    public Map<String, String> getThumbnails() {
        return thumbnails;
    }

    @Override
    public String toString() {
        return "MediaItem [id=" + id + ", name=" + name + ", type=" + type + ", dateCreated=" + dateCreated + ", height=" + height + ", width=" + width + ", size=" + size + ", version=" + version
                + ", active=" + active + ", thumbnails=" + thumbnails + "]";
    }
}
