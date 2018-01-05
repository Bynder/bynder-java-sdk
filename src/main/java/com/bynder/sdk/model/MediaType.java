/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to represent type of media.
 */
public enum MediaType {
    @SerializedName("document") DOCUMENT("document"), @SerializedName("image") IMAGE(
        "image"), @SerializedName("video") VIDEO("video"), @SerializedName("audio") AUDIO("audio");

    private final String type;

    private MediaType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
