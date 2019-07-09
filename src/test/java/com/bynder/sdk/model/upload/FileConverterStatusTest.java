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
package com.bynder.sdk.model.upload;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link FileConverterStatus} class methods.
 */
public class FileConverterStatusTest {

    public static final int EXPECTED_ATTEMPTS = 3;

    private FileConverterStatus fileConverterStatus;

    @Before
    public void setUp() {
        fileConverterStatus = new FileConverterStatus(EXPECTED_ATTEMPTS);
    }

    @Test
    public void defaultInitializationValuesOfFileConverterStatus() {
        assertFalse(fileConverterStatus.isDone());
        assertFalse(fileConverterStatus.isSuccessful());
    }

    @Test
    public void executeFileConverterStatusNextAttempt() {
        assertTrue(fileConverterStatus.nextAttempt());
        assertFalse(fileConverterStatus.nextAttempt());
    }

    @Test
    public void setSuccessfulFileConverterStatus() {
        fileConverterStatus.setDone(true);
        assertTrue(fileConverterStatus.isDone());
        assertTrue(fileConverterStatus.isSuccessful());
    }

    @Test
    public void setUnsuccessfulFileConverterStatus() {
        fileConverterStatus.setDone(false);
        assertTrue(fileConverterStatus.isDone());
        assertFalse(fileConverterStatus.isSuccessful());
    }
}
