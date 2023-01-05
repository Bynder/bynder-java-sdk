package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

public class WorkflowMetapropertyOption {

    @SerializedName(value = "ID")
    private String id;

    @SerializedName(value = "metapropertyID")
    private String metapropertyId;

    private String label;

    private int position;

    private Boolean isDefault;

    private String dateCreated;

    @SerializedName(value = "created_by")
    private WorkflowUser createdBy;

    public String getId() {
        return id;
    }

    public String getMetapropertyId() {
        return metapropertyId;
    }

    public String getLabel() {
        return label;
    }

    public int getPosition() {
        return position;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public WorkflowUser getCreatedBy() {
        return createdBy;
    }
}