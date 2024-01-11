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
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link DeletedMediaQuery} class methods.
 */
public class DeletedMediaQueryTest {

    public static final int EXPECTED_INTEGER = 1;
    public static final Order EXPECTED_ORDER = Order.ASC;
    public static final OrderField EXPECTED_FIELD = OrderField.NAME;
    public static final String EXPECTED_DATE = "2021-12-25T10:30:00Z";

    private DeletedMediaQuery deletedMediaQuery;

    @Before
    public void setUp() {
        this.deletedMediaQuery = new DeletedMediaQuery();
    }

    @Test
    public void initializeEmptyMediaQuery() {
        assertNull(deletedMediaQuery.getLimit());
        assertNull(deletedMediaQuery.getPage());
        assertNull(deletedMediaQuery.getDateRemoved());
        assertNull(deletedMediaQuery.getField());
        assertNull(deletedMediaQuery.getOrder());
    }

    @Test
    public void setValuesForMediaQuery() {
        deletedMediaQuery.setLimit(EXPECTED_INTEGER);
        assertEquals(EXPECTED_INTEGER, deletedMediaQuery.getLimit().intValue());

        deletedMediaQuery.setPage(EXPECTED_INTEGER);
        assertEquals(EXPECTED_INTEGER, deletedMediaQuery.getPage().intValue());

        deletedMediaQuery.setDateRemoved(EXPECTED_DATE);
        assertEquals(EXPECTED_DATE, deletedMediaQuery.getDateRemoved());

        deletedMediaQuery.setField(EXPECTED_FIELD);
        assertEquals(EXPECTED_FIELD, deletedMediaQuery.getField());

        deletedMediaQuery.setOrder(EXPECTED_ORDER);
        assertEquals(EXPECTED_ORDER, deletedMediaQuery.getOrder());
    }
}
