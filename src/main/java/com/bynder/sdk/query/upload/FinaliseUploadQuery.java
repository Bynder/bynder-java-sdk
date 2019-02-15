/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.upload;

import com.bynder.sdk.query.decoder.ApiField;

/**
 * Query with the information to finalise a completely uploaded file.
 */
public class FinaliseUploadQuery {

    /**
     * Upload id for the file being uploaded.
     */
    @ApiField(name = "id")
    private final String uploadId;

    /**
     * Target id in the authorisation information returned by the
     * {@link #getUploadInformation(String)}.
     */
    @ApiField(name = "targetid")
    private final String targetId;

    /**
     * Base location of the uploaded file.
     */
    @ApiField(name = "s3_filename")
    private final String s3Filename;

    /**
     * Total number of chunks uploaded.
     */
    @ApiField(name = "chunks")
    private final int chunks;

    public FinaliseUploadQuery(final String uploadId, final String targetId,
        final String s3Filename, final int chunks) {
        this.uploadId = uploadId;
        this.targetId = targetId;
        this.s3Filename = s3Filename;
        this.chunks = chunks;
    }

    public String getUploadId() {
        return uploadId;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getS3Filename() {
        return s3Filename;
    }

    public int getChunks() {
        return chunks;
    }
}
