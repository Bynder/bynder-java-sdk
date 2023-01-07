/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class JobCreateQuery {

    private final String name;

    private String description;

    private String deadline;

    @SerializedName(value = "campaignID")
    private final String campaignID;

    @SerializedName(value = "accountableID")
    private final String accountableId;

    @SerializedName(value = "presetID")
    private final String presetId;

    private Map<String, String> jobMetaproperties;

    private List<StageCreateQuery> stages;

    public JobCreateQuery(String name, String campaignId, String accountableId, String presetId) {
        this.name = name;
        this.campaignID = campaignId;
        this.accountableId = accountableId;
        this.presetId = presetId;
    }

    public String getName() {
        return name;
    }

    public String getCampaignID() {
        return campaignID;
    }

    public String getAccountableId() {
        return accountableId;
    }

    public String getPresetId() {
        return presetId;
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

    public Map<String, String> getJobMetaproperties() {
        return jobMetaproperties;
    }

    public void setJobMetaproperties(Map<String, String> jobMetaproperties) {
        this.jobMetaproperties = jobMetaproperties;
    }

    public List<StageCreateQuery> getStages() {
        return stages;
    }

    public void setStages(List<StageCreateQuery> stages) {
        this.stages = stages;
    }
}
