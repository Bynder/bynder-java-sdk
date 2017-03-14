/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private String copyright;
    private Boolean archive;
    private String dateCreated;
    private String dateModified;
    private String datePublished;
    private String type;
    private String brandId;
    private int height;
    private int width;
    private String orientation;
    private long fileSize;
    private List<String> tags;
    @SerializedName(value = "extension")
    private List<String> extensions;
    private List<String> videoPreviewURLs;
    private List<String> propertyOptions = new ArrayList<>();
    private Map<String, String> thumbnails;
    private List<MediaItem> mediaItems;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCopyright() {
        return copyright;
    }

    public Boolean getArchive() {
        return archive;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getType() {
        return type;
    }

    public String getBrandId() {
        return brandId;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getOrientation() {
        return orientation;
    }

    public long getFileSize() {
        return fileSize;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public List<String> getVideoPreviewURLs() {
        return videoPreviewURLs;
    }

    public List<String> getPropertyOptions() {
        return propertyOptions;
    }

    public Map<String, String> getThumbnails() {
        return thumbnails;
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    @Override
    public String toString() {
        return "Media [id=" + id + ", name=" + name + ", description=" + description + ", copyright=" + copyright + ", archive=" + archive + ", dateCreated=" + dateCreated + ", dateModified="
                + dateModified + ", datePublished=" + datePublished + ", type=" + type + ", brandId=" + brandId + ", height=" + height + ", width=" + width + ", orientation=" + orientation
                + ", fileSize=" + fileSize + ", tags=" + tags + ", extensions=" + extensions + ", videoPreviewURLs=" + videoPreviewURLs + ", propertyOptions=" + propertyOptions + ", thumbnails="
                + thumbnails + ", mediaItems=" + mediaItems + "]";
    }
}
