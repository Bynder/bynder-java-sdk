/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.upload;

/**
 * Model to represent the conversion status of a file being uploaded to Bynder.
 */
public class FileConverterStatus {

    /**
     * Attempts remaining to wait for the file to be converted.
     */
    private int attemptsRemaining;
    /**
     * True if the file conversion, successfully or not, is done.
     */
    private boolean isDone;
    /**
     * Success status of the file conversion.
     */
    private boolean isSuccessful;

    public FileConverterStatus(final int attempts) {
        attemptsRemaining = attempts - 1;
        isDone = false;
        isSuccessful = false;
    }

    public boolean nextAttempt() {
        attemptsRemaining--;
        return attemptsRemaining > 0;
    }

    public void setDone(final boolean success) {
        isDone = true;
        isSuccessful = success;
    }

    public boolean isDone() {
        return isDone;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
