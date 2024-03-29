/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

public class StagePreset {

    @SerializedName(value = "ID")
    private String id;

    private String name;

    private String type;

    private String description;

    private Integer position;

    @SerializedName(value = "responsibleID")
    private String responsibleId;

    @SerializedName(value = "responsibleGroupID")
    private String responsibleGroupId;

    @SerializedName(value = "restrictToGroupID")
    private String restrictToGroupIDd;

    private String editableBy;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPosition() {
        return position;
    }

    public String getResponsibleId() {
        return responsibleId;
    }

    public String getResponsibleGroupId() {
        return responsibleGroupId;
    }

    public String getRestrictToGroupIDd() {
        return restrictToGroupIDd;
    }

    public String getEditableBy() {
        return editableBy;
    }
}
