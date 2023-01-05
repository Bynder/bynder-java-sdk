package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

public class Stage {

    private String id;

    private String status;

    private int position;

    private WorkflowUser responsibleUser;

    @SerializedName(value = "responsible_group")
    private WorkflowGroup responsibleGroup;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getPosition() {
        return position;
    }

    public WorkflowUser getResponsibleUser() {
        return responsibleUser;
    }

    public WorkflowGroup getResponsibleGroup() {
        return responsibleGroup;
    }
}
