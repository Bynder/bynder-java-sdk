/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

/**
 * Model to represent the progress of an Asset upload.
 */
public class UploadProgress {

    /**
     * The total bytes of the file.
     */
    private final long totalBytes;

    /**
     * The number of chunks already successfully uploaded.
     */
    private int uploadedChunks;

    /**
     * The bytes already transmitted.
     */
    private long transmittedBytes;

    public UploadProgress(long totalBytes) {
        this.uploadedChunks = 0;
        this.totalBytes = totalBytes;
        this.transmittedBytes = 0;
    }

    /**
     * Adds a chunk of the specified size to the progress of the upload.
     *
     * @param bytes The size in bytes of the chunk successfully uploaded.
     */
    public UploadProgress addProgress(int bytes) {
        transmittedBytes += bytes;
        uploadedChunks++;
        return this;
    }

    public int getUploadedChunks() {
        return uploadedChunks;
    }

    public long getTransmittedBytes() {
        return transmittedBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }
}
