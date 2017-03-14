/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class UtilsTest {

    private final String NAME = "name";
    private final String TEST_FAILED_EXCEPTION_NOT_THROWN = "%s failed: Exception was not thrown";

    private boolean exceptionThrown;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        exceptionThrown = false;
    }

    @Test
    public void buildMapFromResponseTest() {
        String keyA = "keyA";
        String keyB = "keyB";
        String valueA = "valueA";
        String valueB = "valueB";

        StringBuilder stringBuilder = new StringBuilder(keyA).append(Utils.STR_EQUALS).append(valueA).append(Utils.STR_AND).append(keyB).append(Utils.STR_EQUALS).append(valueB);
        String response = stringBuilder.toString();

        Map<String, String> responseMap = Utils.buildMapFromResponse(response);
        assertNotNull(responseMap);
        assertEquals(valueA, responseMap.get(keyA));
        assertEquals(valueB, responseMap.get(keyB));
    }

    @Test
    public void checkNotNullSuccessTest() {
        Utils.checkNotNull(NAME, "value");
    }

    @Test
    public void checkNotNullFailTest() {
        try {
            Utils.checkNotNull(NAME, null);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().startsWith(NAME));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }

        exceptionThrown = false;
        try {
            Utils.checkNotNull(NAME, "");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().startsWith(NAME));
            exceptionThrown = true;
        } finally {
            if (!exceptionThrown) {
                Assert.fail(String.format(TEST_FAILED_EXCEPTION_NOT_THROWN, testName.getMethodName()));
            }
        }
    }
}
