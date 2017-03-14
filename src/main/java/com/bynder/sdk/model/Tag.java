/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

public class Tag {

    private String id;
    private String tag;
    private int mediaCount;

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    @Override
    public String toString() {
        return "Tag [id=" + id + ", tag=" + tag + ", mediaCount=" + mediaCount + "]";
    }
}
