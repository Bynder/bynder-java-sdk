/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.oauth;

/**
 * Enum to represent the authorization scope.
 */
public enum Scope {

    OPEN_ID("openid"), OFFLINE("offline");

    private final String name;

    Scope(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }}
