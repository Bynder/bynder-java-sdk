/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

import java.util.Map;

import com.bynder.sdk.api.BynderApi;

/**
 * Model returned by {@link BynderApi#finaliseAdditionalFileUpload(String, String, Map)}.
 */
public class FinaliseAdditionalFileResponse {

    /**
     * Item id of the upload.
     */
    private String itemId;

    public String getItemId() {
        return itemId;
    }
}
