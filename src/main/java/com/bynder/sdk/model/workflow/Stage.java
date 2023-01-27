/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

public class Stage {

    private String id;

    private String status;

    private Integer position;

    private WorkflowUser responsibleUser;

    @SerializedName(value = "responsible_group")
    private WorkflowGroup responsibleGroup;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPosition() {
        return position;
    }

    public WorkflowUser getResponsibleUser() {
        return responsibleUser;
    }

    public WorkflowGroup getResponsibleGroup() {
        return responsibleGroup;
    }
}
