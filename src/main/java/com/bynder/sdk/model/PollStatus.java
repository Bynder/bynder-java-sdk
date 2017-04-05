/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.HashSet;

import com.bynder.sdk.api.BynderApi;

/**
 * Model returned by {@link BynderApi#getPollStatus(String)} (String)}. This model is only and should be only
 * used when uploading a file.
 */
public class PollStatus {

    /**
     * Returns the items for which the conversion failed.
     */
    private HashSet<String> itemsFailed;
    /**
     * Returns the items for which the conversion succeeded.
     */
    private HashSet<String> itemsDone;

    public HashSet<String> getItemsFailed() {
        return itemsFailed;
    }

    public HashSet<String> getItemsDone() {
        return itemsDone;
    }
}
