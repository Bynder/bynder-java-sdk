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
package com.bynder.sdk.query.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link CollectionQuery} class methods.
 */
public class CollectionQueryTest {

    public static final String EXPECTED_KEYWORD = "keyword";
    public static final int EXPECTED_INTEGER = 1;
    public static final CollectionOrderType EXPECTED_ORDER_BY =
        CollectionOrderType.DATE_CREATED_ASC;

    @Test
    public void initializeEmptyCollectionQuery() {
        CollectionQuery collectionQuery = new CollectionQuery();

        assertNull(collectionQuery.getKeyword());
        assertNull(collectionQuery.getLimit());
        assertNull(collectionQuery.getPage());
        assertNull(collectionQuery.getOrderBy());
    }

    @Test
    public void setValuesForCollectionQuery() {
        CollectionQuery collectionQuery = new CollectionQuery();

        collectionQuery.setKeyword(EXPECTED_KEYWORD);
        assertEquals(EXPECTED_KEYWORD, collectionQuery.getKeyword());

        collectionQuery.setLimit(EXPECTED_INTEGER);
        assertEquals(EXPECTED_INTEGER, collectionQuery.getLimit().intValue());

        collectionQuery.setPage(EXPECTED_INTEGER);
        assertEquals(EXPECTED_INTEGER, collectionQuery.getPage().intValue());

        collectionQuery.setOrderBy(EXPECTED_ORDER_BY);
        assertEquals(EXPECTED_ORDER_BY, collectionQuery.getOrderBy());
    }
}
