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

/**
 * Tests the {@link CollectionInfoQuery} class methods.
 */
public class CollectionInfoQueryTest {

    public static final String EXPECTED_COLLECTION_ID = "id";

    @Test
    public void initializeCollectionInfoQuery() {
        CollectionInfoQuery collectionInfoQuery = new CollectionInfoQuery(EXPECTED_COLLECTION_ID);
        assertEquals(EXPECTED_COLLECTION_ID, collectionInfoQuery.getCollectionId());
    }
}
