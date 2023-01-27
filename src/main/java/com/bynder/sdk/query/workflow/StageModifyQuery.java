/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.workflow;

import com.bynder.sdk.model.workflow.StageStatus;

public class StageModifyQuery {

    private final String id;

    private StageStatus status;

    public StageModifyQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public StageStatus getStatus() {
        return status;
    }

    public void setStatus(StageStatus status) {
        this.status = status;
    }
}
