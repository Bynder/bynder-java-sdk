/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.upload;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.query.decoder.ApiField;

import java.util.Map;

/**
 * Query with the information to register an uploaded chunk in Bynder.
 */
public class RegisterChunkQuery {

    /**
     * Number of the chunk that was uploaded.
     */
    @ApiField
    private final int chunkNumber;

    /**
     * Upload id for the file being uploaded.
     */
    @ApiField(name = "id")
    private final String uploadId;

    /**
     * Target id in the authorisation information returned by the
     * {@link BynderApi#getUploadInformation(Map)}.
     */
    @ApiField(name = "targetid")
    private final String targetId;

    /**
     * S3 filename.
     */
    @ApiField
    private final String filename;

    public RegisterChunkQuery(
            final int chunkNumber,
            final String uploadId,
            final String targetId,
            final String filename
    ) {
        this.chunkNumber = chunkNumber;
        this.uploadId = uploadId;
        this.targetId = targetId;
        this.filename = filename;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

    public String getUploadId() {
        return uploadId;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getFilename() {
        return filename;
    }
}
