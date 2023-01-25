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
import java.util.Map;

/**
 * Model returned by {@link BynderApi#saveMedia(Map)}.
 */
public class UploadAdditionalMediaResponse {

    /**
     * Media id.
     */
    @SerializedName(value = "itemId")
    private String itemId;

    public String getItemId() {
        return itemId;
    }
}
