package com.bynder.sdk.service.upload;

/**
 * Created by diegobarrerarodriguez on 10/04/17.
 */
public class PollingStatus {

    private int mAttemptsRemaining;

    public PollingStatus(int attempts){
        mAttemptsRemaining = attempts - 1;
    }

    public boolean nextAttempt(){
        mAttemptsRemaining--;
        return mAttemptsRemaining > 0;
    }
}
