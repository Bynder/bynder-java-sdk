/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

import com.bynder.sdk.api.BynderApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Model returned by {@link BynderApi#finaliseUpload(Map)}.
 */
public class FinaliseResponse {
    private static final Logger LOG = LoggerFactory.getLogger(FinaliseResponse.class);

    /**
     * Import id of the upload. Needed to poll and save media.
     */
    private String importId;

    public String getImportId() {
        LOG.info("getImportId from FinaliseResponse Import ID: " + importId);
        return importId;
    }
}
