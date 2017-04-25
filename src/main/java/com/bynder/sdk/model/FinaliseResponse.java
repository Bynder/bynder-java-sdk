/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.Map;

import com.bynder.sdk.api.BynderApi;

/**
 * Model returned by {@link BynderApi#finaliseUpload(Map)}.
 */
public class FinaliseResponse {

    /**
     * Import id of the upload. Needed to poll and save media.
     */
    private String importId;

    public String getImportId() {
        return importId;
    }
}
