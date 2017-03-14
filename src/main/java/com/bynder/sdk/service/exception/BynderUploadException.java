/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.exception;

public class BynderUploadException extends Exception {

    private static final long serialVersionUID = -2460334649402315079L;

    public BynderUploadException(final String message) {
        super(message);
    }
}
