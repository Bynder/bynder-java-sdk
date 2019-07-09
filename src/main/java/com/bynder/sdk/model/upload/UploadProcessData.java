/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

import com.bynder.sdk.model.upload.UploadRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Model to represent the upload process data of a file being uploaded to Bynder.
 */
public class UploadProcessData {

    /**
     * File being uploaded.
     */
    private final File file;
    /**
     * Input bytes of the file being uploaded.
     */
    private final FileInputStream fileInputStream;
    /**
     * Upload authorisation information.
     */
    private final UploadRequest uploadRequest;
    /**
     * Max chunk size.
     */
    private final int maxChunkSize;
    /**
     * Total number of chunks.
     */
    private final int numberOfChunks;
    /**
     * Number of the chunk be uploaded.
     */
    private int chunkNumber;

    public UploadProcessData(final File file, final FileInputStream fileInputStream,
        final UploadRequest uploadRequest, final int maxChunkSize) {
        this.file = file;
        this.fileInputStream = fileInputStream;
        this.uploadRequest = uploadRequest;
        this.maxChunkSize = maxChunkSize;
        this.numberOfChunks = (int) ((file.length() + maxChunkSize - 1) / maxChunkSize);
        this.chunkNumber = 0;
    }

    public File getFile() {
        return file;
    }

    public UploadRequest getUploadRequest() {
        return uploadRequest;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

    public int getNumberOfChunks() {
        return numberOfChunks;
    }

    public void incrementChunk() {
        chunkNumber++;
    }

    public boolean isCompleted() {
        return chunkNumber == numberOfChunks;
    }

    public byte[] getBuffer() throws IOException {
        int length = Math.min(maxChunkSize, fileInputStream.available());
        byte[] buffer = new byte[length];
        fileInputStream.read(buffer);
        return buffer;
    }
}
