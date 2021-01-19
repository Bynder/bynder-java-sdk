/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model.oauth;

/**
 * Enum to represent the authorization grant type.
 */
public enum GrantType {

    AUTHORIZATION_CODE("authorization_code"),
    CLIENT_CREDENTIALS("client_credentials"),
    REFRESH_TOKEN("refresh_token");

    private final String name;

    GrantType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
