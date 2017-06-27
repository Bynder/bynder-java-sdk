/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Enum to represent the permission rights for sharing a collection.
 */
public enum CollectionRecipientRight {
    VIEW("view"), EDIT("edit");

    private final String right;

    private CollectionRecipientRight(final String right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return right;
    }
}
