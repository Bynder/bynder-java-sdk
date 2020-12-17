/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model returned by {@link BynderApi#saveMedia} and {@link BynderApi#saveMediaVersion}.
 */
public class SaveMediaResponse {

    /**
     * Media id.
     */
    @SerializedName(value = "mediaid")
    private String mediaId;
    /**
     * Batch id.
     */
    private String batchId;
    /**
     * Success flag.
     */
    private Boolean success;
    /**
     * Media items locations.
     */
    @SerializedName(value = "mediaitems")
    private List<SaveMediaItem> mediaItems;

    public String getMediaId() {
        return mediaId;
    }

    public String getBatchId() {
        return batchId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<SaveMediaItem> getMediaItems() {
        return mediaItems;
    }
}
