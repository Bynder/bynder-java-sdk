package com.bynder.sdk.service.upload;

/**
 * Created by diegobarrerarodriguez on 09/06/2017.
 */
public class ByteProgress {
    private long transmittedBytes;
    private long totalBytes;

    public ByteProgress(long bytes) {
        totalBytes = bytes;
        transmittedBytes = 0;
    }

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