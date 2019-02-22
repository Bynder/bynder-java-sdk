/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;
import java.net.URL;

/**
 * Model returned by {@link BynderApi#getMediaDownloadUrl(String)} and
 * {@link BynderApi#getMediaDownloadUrl(String, String)}.
 */
public class DownloadUrl {

    /**
     * Temporary S3 URL to download the media asset.
     */
    @SerializedName(value = "s3_file")
    private URL s3File;

    public URL getS3File() {
        return s3File;
    }
}
