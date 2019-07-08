/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.upload;

import com.bynder.sdk.api.BynderApi;
import java.util.HashSet;
import java.util.Map;

/**
 * Model returned by {@link BynderApi#getPollStatus(Map)}. This model is only and should be only
 * used when uploading a file.
 */
public class PollStatus {

    /**
     * Returns the items for which the conversion succeeded.
     */
    private HashSet<String> itemsDone;
    /**
     * Returns the items for which the conversion failed.
     */
    private HashSet<String> itemsFailed;
    /**
     * Returns the items for which the conversion was rejected.
     */
    private HashSet<String> itemsRejected;

    public HashSet<String> getItemsDone() {
        return itemsDone;
    }

    public HashSet<String> getItemsFailed() {
        return itemsFailed;
    }

    public HashSet<String> getItemsRejected() {
        return itemsRejected;
    }
}
