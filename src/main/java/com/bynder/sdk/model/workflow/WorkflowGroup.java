/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkflowGroup {

    @SerializedName(value = "ID")
    private String id;

    private String name;

    @SerializedName(value = "createdByID")
    private String createdById;

    private String dateCreated;

    @SerializedName(value = "accountID")
    private String accountId;

    private List<WorkflowUser> users;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatedById() {
        return createdById;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getAccountId() {
        return accountId;
    }

    public List<WorkflowUser> getUsers() {
        return users;
    }
}
