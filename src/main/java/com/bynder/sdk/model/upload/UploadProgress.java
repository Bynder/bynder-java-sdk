/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

import com.bynder.sdk.model.upload.SaveMediaResponse;

/**
 * Model to represent the progress of an Asset upload.
 */
public class UploadProgress {

    /**
     * The total bytes of the file.
     */
    private final long totalBytes;
    /**
     * Whether the upload has finished or not.
     */
    private boolean finished;
    /**
     * The SaveMediaResponse of the upload, filled in when the upload is finished.
     */
    private SaveMediaResponse saveMediaResponse;
    /**
     * The Id of uploaded additional file, filled, when upload is finished.
     */
    private FinaliseAdditionalFileResponse finaliseAdditionalFileResponse;
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
        this.finished = false;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public SaveMediaResponse getSaveMediaResponse() {
        return saveMediaResponse;
    }

    public void setSaveMediaResponse(SaveMediaResponse saveMediaResponse) {
        this.saveMediaResponse = saveMediaResponse;
    }

    public FinaliseAdditionalFileResponse getFinaliseAdditionalFileResponse() {
        return finaliseAdditionalFileResponse;
    }

    public void setFinaliseAdditionalFileResponse(FinaliseAdditionalFileResponse finaliseAdditionalFileResponse) {
        this.finaliseAdditionalFileResponse = finaliseAdditionalFileResponse;
    }

    /**
     * Adds a chunk of the specified size to the progress of the upload.
     *
     * @param bytes The size in bytes of the chunk successfully uploaded.
     */
    public void addProgress(long bytes) {
        transmittedBytes += bytes;
        uploadedChunks++;
    }

    /**
     * Whether all Bytes of the Asset have been uploaded.
     * Not the same as isFinished().
     *
     * @return boolean indicating whether chunk transmission is finished.
     */
    public boolean areChunksFinished() {
        return transmittedBytes == totalBytes;
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
