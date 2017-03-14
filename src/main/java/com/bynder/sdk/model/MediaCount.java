/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.List;

public class MediaCount {

    private Count count;
    private List<Media> media;

    public Count getCount() {
        return count;
    }

    public List<Media> getMedia() {
        return media;
    }

    @Override
    public String toString() {
        return "MediaCount [count=" + count + ", media=" + media + "]";
    }
}
