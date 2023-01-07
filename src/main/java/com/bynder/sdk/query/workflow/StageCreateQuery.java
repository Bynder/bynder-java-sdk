/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

import com.google.gson.annotations.SerializedName;

public class StageCreateQuery {

    @SerializedName(value = "preset_stage_id")
    private final String presetStageId;

    private String name;

    private String description;

    @SerializedName(value = "responsibleGroupID")
    private String responsibleGroupId;

    @SerializedName(value = "responsibleID")
    private String responsibleId;

    private String deadline;

    public StageCreateQuery(String presetStageId) {
        this.presetStageId = presetStageId;
    }

    public String getPresetStageId() {
        return presetStageId;
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

    public String getResponsibleGroupId() {
        return responsibleGroupId;
    }

    public void setResponsibleGroupId(String responsibleGroupId) {
        this.responsibleGroupId = responsibleGroupId;
    }

    public String getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(String responsibleId) {
        this.responsibleId = responsibleId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
