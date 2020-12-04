/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 *
 * JUnit framework component copyright (c) 2002-2017 JUnit. All Rights Reserved. Licensed under
 * Eclipse Public License - v 1.0. You may obtain a copy of the License at
 * https://www.eclipse.org/legal/epl-v10.html.
 */
package com.bynder.sdk.util;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Tests the {@link Utils} class methods.
 */
public class UtilsTest {

    @Test
    public void encodeParameterValueTest() throws UnsupportedEncodingException {
        String value = "example value";
        String encodedValue = Utils.encodeParameterValue(value);
        assertNotNull(encodedValue);
        assertNotEquals(encodedValue, value);
        assertEquals(encodedValue.length(), value.length());

        String decodedValue = URLDecoder.decode(encodedValue, StandardCharsets.UTF_8.name());
        assertNotNull(decodedValue);
        assertTrue(decodedValue.equals(value));
    }

    @Test
    public void isDateExpiringTest() {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.SECOND, 10);

        boolean isExpired = Utils.isDateExpiring(expirationDate.getTime(), 15);
        assertTrue(isExpired);

        expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.SECOND, 20);

        isExpired = Utils.isDateExpiring(expirationDate.getTime(), 15);
        assertFalse(isExpired);
    }

    @Test(expected = IOException.class)
    public void testLoadConfigFileNotFOund() throws IOException {
        Properties config = Utils.loadConfig("non-existing");
    }

    @Test
    public void testLoadConfig() throws IOException {
        Properties config = Utils.loadConfig("config");
        assertEquals("value", config.getProperty("key"));
    }

}
