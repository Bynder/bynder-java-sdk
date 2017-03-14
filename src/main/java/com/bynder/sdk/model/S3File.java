/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.google.gson.annotations.SerializedName;

public class S3File {

    @SerializedName("uploadid")
    private String uploadId;
    @SerializedName("targetid")
    private String targetId;

    public String getUploadId() {
        return uploadId;
    }

    public String getTargetId() {
        return targetId;
    }
}
