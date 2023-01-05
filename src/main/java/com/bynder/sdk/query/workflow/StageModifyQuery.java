package com.bynder.sdk.query.workflow;

import com.bynder.sdk.model.workflow.StageStatus;

public class StageModifyQuery {

    private final String id;

    private StageStatus status;

    public StageModifyQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public StageStatus getStatus() {
        return status;
    }

    public void setStatus(StageStatus status) {
        this.status = status;
    }
}
