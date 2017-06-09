package com.bynder.sdk.service.upload;

import com.bynder.sdk.model.SaveMediaResponse;

/**
 * Created by diegobarrerarodriguez on 07/06/2017.
 */
public class UploadProgress {
    private boolean finished = false;
    private ByteProgress progress;
    private SaveMediaResponse saveMediaResponse;
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

    public SaveMediaResponse getSaveMediaItem() {
        return saveMediaResponse;
    }

    public void setSaveMediaItem(SaveMediaResponse saveMediaResponse) {
        this.saveMediaResponse = saveMediaResponse;
    }

    /**
     * Adds a chunk of the specified size to the progress of the upload
     *
     * @param bytes The size in bytes of the chunk successfully uploaded
     */
    void addProgress(long bytes) {
        progress.addProgress(bytes);
        uploadedChunks++;
    }

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
