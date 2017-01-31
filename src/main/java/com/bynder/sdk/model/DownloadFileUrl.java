/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.net.URI;

import com.google.gson.annotations.SerializedName;

public class DownloadFileUrl {

    @SerializedName(value = "s3_file")
    private URI s3File;

    public URI getS3File() {
        return s3File;
    }
}
