/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.Map;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;

/**
 * Model to represent the S3 file information returned in the response of
 * {@link BynderApi#getUploadInformation(Map)}. This model is only and should be only used when
 * uploading a file.
 */
public class S3File {

    /**
     * Upload id.
     */
    @SerializedName("uploadid")
    private String uploadId;
    /**
     * Target id.
     */
    @SerializedName("targetid")
    private String targetId;

    public String getUploadId() {
        return uploadId;
    }

    public String getTargetId() {
        return targetId;
    }
}
