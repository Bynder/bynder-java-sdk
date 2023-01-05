package com.bynder.sdk.query.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class JobModifyQuery {

    private String id;

    private String name;

    private String description;

    private String deadline;

    @SerializedName(value = "campaignID")
    private String campaignId;

    @SerializedName(value = "accountableID")
    private String accountableId;

    private Map<String, String> jobMetaproperties;

    private StageModifyQuery activeStage;

    private StageModifyQuery nextStage;

    private StageModifyQuery previousStage;

    public JobModifyQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getAccountableId() {
        return accountableId;
    }

    public void setAccountableId(String accountableId) {
        this.accountableId = accountableId;
    }

    public Map<String, String> getJobMetaproperties() {
        return jobMetaproperties;
    }

    public void setJobMetaproperties(Map<String, String> jobMetaproperties) {
        this.jobMetaproperties = jobMetaproperties;
    }

    public StageModifyQuery getActiveStage() {
        return activeStage;
    }

    public void setActiveStage(StageModifyQuery activeStage) {
        this.activeStage = activeStage;
    }

    public StageModifyQuery getNextStage() {
        return nextStage;
    }

    public void setNextStage(StageModifyQuery nextStage) {
        this.nextStage = nextStage;
    }

    public StageModifyQuery getPreviousStage() {
        return previousStage;
    }

    public void setPreviousStage(StageModifyQuery previousStage) {
        this.previousStage = previousStage;
    }
}
