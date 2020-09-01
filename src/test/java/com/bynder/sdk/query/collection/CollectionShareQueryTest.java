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
 * Tests the {@link CollectionShareQuery} class methods.
 */
public class CollectionShareQueryTest {

    public static final String EXPECTED_COLLECTION_ID = "collectionId";
    public static final String[] EXPECTED_RECIPIENTS = new String[]{"1", "2"};
    public static final CollectionRecipientRight EXPECTED_RECIPIENT_RIGHT =
        CollectionRecipientRight.VIEW;
    public static final Boolean EXPECTED_BOOLEAN = Boolean.TRUE;
    public static final String EXPECTED_DATE = "date";
    public static final String EXPECTED_MESSAGE = "message";

    @Test
    public void initializeCollectionShareQuery() {
        CollectionShareQuery collectionShareQuery = new CollectionShareQuery(EXPECTED_COLLECTION_ID,
            EXPECTED_RECIPIENTS, EXPECTED_RECIPIENT_RIGHT);

        assertEquals(EXPECTED_COLLECTION_ID, collectionShareQuery.getCollectionId());
        assertEquals(EXPECTED_RECIPIENTS.length, collectionShareQuery.getRecipients().length);
        assertEquals(EXPECTED_RECIPIENTS[0], collectionShareQuery.getRecipients()[0]);
        assertEquals(EXPECTED_RECIPIENTS[1], collectionShareQuery.getRecipients()[1]);
        assertEquals(EXPECTED_RECIPIENT_RIGHT, collectionShareQuery.getRight());
        assertNull(collectionShareQuery.getLoginRequired());
        assertNull(collectionShareQuery.getDateStart());
        assertNull(collectionShareQuery.getDateEnd());
        assertNull(collectionShareQuery.getSendMail());
        assertNull(collectionShareQuery.getMessage());

        collectionShareQuery.setLoginRequired(EXPECTED_BOOLEAN);
        assertEquals(EXPECTED_BOOLEAN, collectionShareQuery.getLoginRequired());
        collectionShareQuery.setDateStart(EXPECTED_DATE);
        assertEquals(EXPECTED_DATE, collectionShareQuery.getDateStart());
        collectionShareQuery.setDateEnd(EXPECTED_DATE);
        assertEquals(EXPECTED_DATE, collectionShareQuery.getDateEnd());
        collectionShareQuery.setMessage(EXPECTED_MESSAGE);
        assertEquals(EXPECTED_MESSAGE, collectionShareQuery.getMessage());
    }
}
