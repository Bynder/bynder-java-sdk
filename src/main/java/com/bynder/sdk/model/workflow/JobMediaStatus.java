/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.workflow;

/**
 * Enum used to define how API results should be ordered.
 */
public enum JobMediaStatus {

    AWAITING_APPROVAL("AwaitingApproval"), PROCESSING("Processing"),
    APPROVED("Approved"), REJECTED("Rejected"),
    FAILED("Failed"), DONE("Done");

    private final String name;

    JobMediaStatus(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
