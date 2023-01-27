/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WorkflowGroupDataQuery {

    private final String name;

    @SerializedName(value = "user_ids")
    private List<String> userIds;

    public WorkflowGroupDataQuery(String name) {
        this.name = name;
        this.userIds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
