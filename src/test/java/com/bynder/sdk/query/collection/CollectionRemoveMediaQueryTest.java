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

import org.junit.Test;

/**
 * Tests the {@link CollectionRemoveMediaQuery} class methods.
 */
public class CollectionRemoveMediaQueryTest {

    public static final String EXPECTED_COLLECTION_ID = "collectionId";
    public static final String[] EXPECTED_MEDIA_IDS = new String[]{"1", "2"};

    @Test
    public void initializeCollectionRemoveMediaQuery() {
        CollectionRemoveMediaQuery collectionRemoveMediaQuery = new CollectionRemoveMediaQuery(
            EXPECTED_COLLECTION_ID, EXPECTED_MEDIA_IDS);

        assertEquals(EXPECTED_COLLECTION_ID, collectionRemoveMediaQuery.getCollectionId());
        assertEquals(EXPECTED_MEDIA_IDS.length, collectionRemoveMediaQuery.getMediaIds().length);
        assertEquals(EXPECTED_MEDIA_IDS[0], collectionRemoveMediaQuery.getMediaIds()[0]);
        assertEquals(EXPECTED_MEDIA_IDS[1], collectionRemoveMediaQuery.getMediaIds()[1]);
    }
}
