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
public enum OrderBy {

    DATE_CREATED_ASC("dateCreated asc"), DATE_CREATED_DESC("dateCreated desc"), DATE_MODIFIED_ASC(
        "dateModified asc"), DATE_MODIFIED_DESC("dateModified desc"), NAME_ASC(
        "name asc"), NAME_DESC("name desc");

    private final String name;

    OrderBy(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }}
