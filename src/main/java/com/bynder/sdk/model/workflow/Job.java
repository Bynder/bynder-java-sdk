package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Job {

    private String id;

    private String name;

    private String deadline;

    private String description;

    private String dateCreated;

    private Boolean basedOnPreset;

    @SerializedName(value = "presetID")
    private String presetId;

    private String dateModified;

    @SerializedName(value = "campaignID")
    private String campaignId;

    @SerializedName(value = "accountableID")
    private String accountableId;

    @SerializedName(value = "createdByID")
    private String createdById;

    private Map<String, String> jobMetaproperties;

    @SerializedName(value = "job_previous_stage")
    private Stage previousStage;

    @SerializedName(value = "job_active_stage")
    private Stage activeStage;

    @SerializedName(value = "job_next_stage")
    private Stage nextStage;

    @SerializedName(value = "job_stages")
    private List<Stage> stages;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public Boolean getBasedOnPreset() {
        return basedOnPreset;
    }

    public String getPresetId() {
        return presetId;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getAccountableId() {
        return accountableId;
    }

    public String getCreatedById() {
        return createdById;
    }

    public Map<String, String> getJobMetaproperties() {
        return jobMetaproperties;
    }

    public Stage getPreviousStage() {
        return previousStage;
    }

    public Stage getActiveStage() {
        return activeStage;
    }

    public Stage getNextStage() {
        return nextStage;
    }

    public List<Stage> getStages() {
        return stages;
    }
}
