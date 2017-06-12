package com.bynder.sdk.service.upload;

/**
 * Created by diegobarrerarodriguez on 09/06/2017.
 */

/**
 * Model to represent the progress in Bytes of an Asset upload.
 */
public class ByteProgress {
    /**
     * The bytes already transmitted.
     */
    private long transmittedBytes;
    /**
     * The total bytes of the file.
     */
    private long totalBytes;

    /**
     *
     * @param bytes The size in bytes of the file being uploaded.
     */
    public ByteProgress(long bytes) {
        totalBytes = bytes;
        transmittedBytes = 0;
    }

    /**
     * Add progress.
     *
     * @param bytes The bytes uploaded in the last chunk.
     */
    public void addProgress(long bytes)
    {
        transmittedBytes += bytes;
    }

    public long getTransmittedBytes() {
        return transmittedBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }
}