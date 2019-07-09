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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests the {@link CollectionCreateQuery} class methods.
 */
public class CollectionCreateQueryTest {

    public static final String EXPECTED_NAME = "name";
    public static final String EXPECTED_DESCRIPTION = "description";

    @Test
    public void initializeCollectionCreateQuery() {
        CollectionCreateQuery collectionCreateQuery = new CollectionCreateQuery(EXPECTED_NAME);

        assertEquals(EXPECTED_NAME, collectionCreateQuery.getName());
        assertNull(collectionCreateQuery.getDescription());

        collectionCreateQuery.setDescription(EXPECTED_DESCRIPTION);
        assertEquals(EXPECTED_DESCRIPTION, collectionCreateQuery.getDescription());
    }
}
