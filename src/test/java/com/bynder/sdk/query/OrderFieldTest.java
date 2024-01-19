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
package com.bynder.sdk.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the {@link OrderField} enum.
 */
public class OrderFieldTest {

    @Test
    public void enumValuesForOrderField() {
        assertEquals("media.dateCreated", OrderField.DATE_CREATED.toString());
        assertEquals("media.dateRemoved", OrderField.DATE_REMOVED.toString());
        assertEquals("media.name", OrderField.NAME.toString());
        assertEquals("media.username", OrderField.USERNAME.toString());
    }
}
