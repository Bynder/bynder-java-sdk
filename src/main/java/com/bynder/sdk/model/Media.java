/**
 * Copyright (c) Bynder. All rights reserved.
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

    public Media() {}

    public Media(final String id, final String name, final String description, final String copyright, final Boolean archive, final String dateCreated, final String dateModified,
            final String datePublished, final String type, final String brandId, final int height, final int width, final String orientation, final long fileSize, final List<String> tags,
            final List<String> extensions, final List<String> videoPreviewURLs, final List<String> propertyOptions, final Map<String, String> thumbnails, final List<MediaItem> mediaItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.copyright = copyright;
        this.archive = archive;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.datePublished = datePublished;
        this.type = type;
        this.brandId = brandId;
        this.height = height;
        this.width = width;
        this.orientation = orientation;
        this.fileSize = fileSize;
        this.tags = tags;
        this.extensions = extensions;
        this.videoPreviewURLs = videoPreviewURLs;
        this.propertyOptions = propertyOptions;
        this.thumbnails = thumbnails;
        this.mediaItems = mediaItems;
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
