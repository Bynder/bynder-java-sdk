/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

/**
 * Enum used to define how API results should be ordered.
 */
public enum Order {

    ASC("asc"), DESC("desc");

    private final String name;

    Order(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
