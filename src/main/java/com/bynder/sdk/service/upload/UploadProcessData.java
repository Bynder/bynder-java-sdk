/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.bynder.sdk.model.UploadRequest;

public class UploadProcessData {

    private int numberOfChunks;
    private FileInputStream fis;
    private int chunkNumber = 0;
    private UploadRequest uploadRequest;
    private File file;
    private int maxChunkSize;

    public UploadProcessData(final File file, FileInputStream fis, final int maxChunkSize, final UploadRequest uploadRequest) {
        this.file = file;
        this.fis = fis;
        this.maxChunkSize = maxChunkSize;
        this.uploadRequest = uploadRequest;
        this.numberOfChunks = (Math.toIntExact(file.length()) + maxChunkSize - 1) / maxChunkSize;
    }

    public void incrementChunk() {
        chunkNumber++;
    }

    public boolean isCompleted() {
        return chunkNumber == numberOfChunks;
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

    public byte[] getBuffer() throws IOException {
        int length = Math.min(maxChunkSize,fis.available());
        byte[] buffer = new byte[length];
        fis.available();

        fis.read(buffer);
        return buffer;
    }

    public int getNumberOfChunks() {
        return numberOfChunks;
    }
}
