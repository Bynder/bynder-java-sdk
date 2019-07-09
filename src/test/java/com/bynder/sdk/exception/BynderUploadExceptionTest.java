/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 *
 * JUnit framework component copyright (c) 2002-2017 JUnit. All Rights Reserved. Licensed under
 * Eclipse Public License - v 1.0. You may obtain a copy of the License at
 * https://www.eclipse.org/legal/epl-v10.html.
 */
package com.bynder.sdk.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the {@link BynderUploadException} class.
 */
public class BynderUploadExceptionTest {

    public static final String EXPECTED_MESSAGE = "message";

    @Test
    public void throwBynderUploadException() {
        try {
            throw new BynderUploadException(EXPECTED_MESSAGE);
        } catch (BynderUploadException e) {
            assertEquals(EXPECTED_MESSAGE, e.getMessage());
        }
    }
}
