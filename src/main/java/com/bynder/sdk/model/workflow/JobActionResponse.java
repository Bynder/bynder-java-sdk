/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

public class JobActionResponse {

    @SerializedName(value = "job_id")
    private String id;

    private String status;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
