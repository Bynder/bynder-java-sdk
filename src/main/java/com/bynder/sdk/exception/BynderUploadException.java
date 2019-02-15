/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.exception;

/**
 * Exception thrown when Upload does not finish within the expected time.
 */
public class BynderUploadException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of the class.
     *
     * @param message Message explaining the exception.
     */
    public BynderUploadException(final String message) {
        super(message);
    }
}
