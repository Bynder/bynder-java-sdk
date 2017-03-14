/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

public enum MediaType {
    DOCUMENT("document"), IMAGE("image"), VIDEO("video"), AUDIO("audio");

    private final String type;

    private MediaType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
