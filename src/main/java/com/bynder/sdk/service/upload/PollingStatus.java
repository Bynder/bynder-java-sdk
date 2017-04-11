package com.bynder.sdk.service.upload;

/**
 * Created by diegobarrerarodriguez on 10/04/17.
 */
public class PollingStatus {

    private int mAttemptsRemaining;
    private boolean isDone;
    private boolean isSuccessful;

    public PollingStatus(int attempts) {
        mAttemptsRemaining = attempts - 1;
        isDone = false;
        isSuccessful = false;
    }

    public boolean nextAttempt() {
        mAttemptsRemaining--;
        return mAttemptsRemaining > 0;
    }

    public void setDone(boolean success) {
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
