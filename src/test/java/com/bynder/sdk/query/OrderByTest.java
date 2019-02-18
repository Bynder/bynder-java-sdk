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
 * Tests the {@link OrderBy} enum.
 */
public class OrderByTest {

    @Test
    public void enumValuesForOrderBy() {
        assertEquals("dateCreated asc", OrderBy.DATE_CREATED_ASC.toString());
        assertEquals("dateCreated desc", OrderBy.DATE_CREATED_DESC.toString());
        assertEquals("dateModified asc", OrderBy.DATE_MODIFIED_ASC.toString());
        assertEquals("dateModified desc", OrderBy.DATE_MODIFIED_DESC.toString());
        assertEquals("name asc", OrderBy.NAME_ASC.toString());
        assertEquals("name desc", OrderBy.NAME_DESC.toString());
    }
}
