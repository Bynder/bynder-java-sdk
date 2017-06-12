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
     * The Byte progress of the upload.
     */
    private ByteProgress progress;
    /**
     * The SaveMediaResponse of the upload, filled in when the upload is finished.
     */
    private SaveMediaResponse saveMediaResponse;
    /**
     * The number of chunks already successfully uploaded.
     */
    private int uploadedChunks;

    public UploadProgress(long totalBytes) {
        progress = new ByteProgress(totalBytes);
        uploadedChunks = 0;
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
        progress.addProgress(bytes);
        uploadedChunks++;
    }

    /**
     * Whether all Bytes of the Asset have been uploaded.
     * Not the same as isFinished().
     *
     * @return boolean indicating whether chunk transmission is finished.
     */
    public boolean areChunksFinished() {
        return progress.getTransmittedBytes() == progress.getTotalBytes();
    }

    public int getUploadedChunks() {
        return uploadedChunks;
    }

    public long getTransmittedBytes() {
        return progress.getTransmittedBytes();
    }

    public long getTotalBytes() {
        return progress.getTotalBytes();
    }
}
