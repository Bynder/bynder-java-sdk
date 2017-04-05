/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

import java.io.File;

import com.bynder.sdk.model.UploadRequest;

public class UploadProcessData {

    private int numberOfChunks;
    private byte[] buffer;
    private int chunkNumber = 0;
    private UploadRequest uploadRequest;
    private File file;
//    private String mediaId;
//    private String brandId;

    public UploadProcessData(/*final String mediaId, final String brandId, */final File file, final byte[] buffer, final int maxChunkSize, final UploadRequest uploadRequest) {
//        this.mediaId = mediaId;
//        this.brandId = brandId;
        this.file = file;
        this.buffer = buffer;
        this.uploadRequest = uploadRequest;
        this.numberOfChunks = (Math.toIntExact(file.length()) + maxChunkSize - 1) / maxChunkSize;
    }

    public void incrementChunk() {
        chunkNumber++;
    }

    public boolean isCompleted() {
        return chunkNumber == numberOfChunks;
    }

//    public String getMediaId() {
//        return mediaId;
//    }
//
//    public String getBrandId() {
//        return brandId;
//    }

    public File getFile() {
        return file;
    }

    public UploadRequest getUploadRequest() {
        return uploadRequest;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getNumberOfChunks() {
        return numberOfChunks;
    }
}
