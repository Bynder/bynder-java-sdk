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

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Tests the {@link MediaModifyQuery} class methods.
 */
public class MediaModifyQueryTest {
    public static final String EXPECTED_MEDIA_ID = "id";
    public static final Boolean EXPECTED_LIMITED = true;
    public static final String EXPECTED_LIMITED_DATE = "2014-12-25T10:30:00Z";

    @Test
    public void initializeMediaModifyQueryTest() {
        MediaModifyQuery mediaModifyQuery = new MediaModifyQuery(EXPECTED_MEDIA_ID);
        mediaModifyQuery.setLimited(EXPECTED_LIMITED);
        assertEquals(EXPECTED_LIMITED, mediaModifyQuery.getLimited());

        mediaModifyQuery.setLimitedDate(EXPECTED_LIMITED_DATE);
        assertEquals(EXPECTED_LIMITED_DATE, mediaModifyQuery.getLimitedDate());

    }
}
