/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import java.util.HashSet;

public class PollStatus {

    private HashSet<String> itemsFailed;
    private HashSet<String> itemsDone;

    public HashSet<String> getItemsFailed() {
        return itemsFailed;
    }

    public HashSet<String> getItemsDone() {
        return itemsDone;
    }
}
