package com.bynder.sdk.service.upload;

import com.bynder.sdk.model.SaveMediaResponse;

/**
 * Model to represent the progress of an Asset upload.
 */
public class UploadProgress {
    /**
     * Whether the upload has finished or not.
     */
    private boolean finished = false;
    /**
     * The SaveMediaResponse of the upload, filled in when the upload is finished.
     */
    private SaveMediaResponse saveMediaResponse;
    /**
     * The number of chunks already successfully uploaded.
     */
    private int uploadedChunks;
    /**
     * The bytes already transmitted.
     */
    private long transmittedBytes;
    /**
     * The total bytes of the file.
     */
    private long totalBytes;

    public UploadProgress(long totalBytes) {
        this.uploadedChunks = 0;
        this.totalBytes = totalBytes;
        this.transmittedBytes = 0;
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

    /**
     * Adds a chunk of the specified size to the progress of the upload.
     *
     * @param bytes The size in bytes of the chunk successfully uploaded.
     */
    void addProgress(long bytes) {
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
