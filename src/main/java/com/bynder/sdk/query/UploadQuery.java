/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

public class UploadQuery {

    private String filepath;
    private String brandId;
    private String mediaId;

    public UploadQuery(final String filepath, final String brandId, final String mediaId) {
        this.filepath = filepath;
        this.brandId = brandId;
        this.mediaId = mediaId;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getMediaId() {
        return mediaId;
    }
}
