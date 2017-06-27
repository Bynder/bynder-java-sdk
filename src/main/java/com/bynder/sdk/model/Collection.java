/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.net.URL;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;

/**
 * Collection model returned by {@link BynderApi#getCollections(java.util.Map)} and
 * {@link BynderApi#getCollectionInfo(java.util.Map)}.
 */
public class Collection {

    /**
     * Collection id.
     */
    private String id;
    /**
     * Collection name.
     */
    private String name;
    /**
     * Collection description.
     */
    private String description;
    /**
     * Id of the user that created the collection.
     */
    private String userId;
    /**
     * Link to collection.
     */
    private URL link;
    /**
     * Number of assets in the collection.
     */
    @SerializedName(value = "collectionCount")
    private int mediaCount;
    /**
     * Collection view count.
     */
    private int views;
    /**
     * Collection public status.
     */
    @SerializedName(value = "IsPublic")
    private Boolean isPublic;
    /**
     * Date created.
     */
    private String dateCreated;
    /**
     * Date modified.
     */
    private String dateModified;
    /**
     * Collection cover.
     */
    private CollectionCover cover;
    /**
     * Thumbnail URL of the collection.
     */
    private URL thumbnail;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public URL getLink() {
        return link;
    }

    public URL getThumbnail() {
        return thumbnail;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public int getViews() {
        return views;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public CollectionCover getCover() {
        return cover;
    }
}
