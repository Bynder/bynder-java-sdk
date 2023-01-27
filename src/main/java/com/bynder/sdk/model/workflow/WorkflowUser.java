/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.bynder.sdk.model.User;
import com.google.gson.annotations.SerializedName;

public class WorkflowUser {

    @SerializedName(value = "ID")
    private String id;

    private String fullName;

    private User bynderUser;

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public User getBynderUser() {
        return bynderUser;
    }
}
