/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;

/**
 * Media model returned by {@link BynderApi#getMediaList(Map)} and
 * {@link BynderApi#getMediaInfo(Map)}.
 */
public class Media {

    /**
     * Media id.
     */
    private String id;
    /**
     * Media name.
     */
    private String name;
    /**
     * Media description.
     */
    private String description;
    /**
     * Copyright of the media.
     */
    private String copyright;
    /**
     * Media archive status.
     */
    private Boolean archive;
    /**
     * Date created.
     */
    private String dateCreated;
    /**
     * Date modified.
     */
    private String dateModified;
    /**
     * Date published.
     */
    private String datePublished;
    /**
     * Media type. Possible values are: image, document, audio and video.
     */
    private MediaType type;
    /**
     * Id of the brand the media belongs to.
     */
    private String brandId;
    /**
     * Height of the original media file.
     */
    private int height;
    /**
     * Width of the original media file.
     */
    private int width;
    /**
     * Orientation of the original media file.
     */
    private String orientation;
    /**
     * File size of the original media file in bytes.
     */
    private long fileSize;
    /**
     * Media public status.
     */
    private Boolean isPublic;
    /**
     * Media original URL.
     */
    private String original;
    /**
     * Tags of the media.
     */
    private List<String> tags;
    /**
     * Extension of the media file.
     */
    @SerializedName(value = "extension")
    private List<String> extensions;
    /**
     * Video preview URLs.
     */
    private List<String> videoPreviewURLs;
    /**
     * Property options assigned to the media.
     */
    private List<String> propertyOptions = new ArrayList<>();
    /**
     * Generated thumbnails for the media.
     */
    private Map<String, String> thumbnails;

    private Map<String, Double> activeOriginalFocusPoint;

    /**
     * Media items for the media. Including derivatives, additional and original. To get this
     * information we have to call {@link BynderApi#getMediaInfo(Map)} with the media id and
     * versions equal to true.
     */
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

    public MediaType getType() {
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

    public Boolean isPublic() {
        return isPublic;
    }

    public String getOriginal() {
        return original;
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

    public Map<String, Double> getActiveOriginalFocusPoint() {
        return activeOriginalFocusPoint;
    }

}
