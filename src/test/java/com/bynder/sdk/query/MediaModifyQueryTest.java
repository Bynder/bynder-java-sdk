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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link MediaModifyQuery} class methods.
 */
public class MediaModifyQueryTest {

    public static final String EXPECTED_MEDIA_ID = "id";
    public static final String EXPECTED_NEW_STRING_VALUE = "value";
    public static final Boolean EXPECTED_NEW_BOOLEAN_VALUE = Boolean.TRUE;

    @Mock
    private MetapropertyAttribute metapropertyAttribute;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initializeMediaModifyQuery() {
        MediaModifyQuery mediaModifyQuery = new MediaModifyQuery(EXPECTED_MEDIA_ID);
        assertEquals(EXPECTED_MEDIA_ID, mediaModifyQuery.getMediaId());

        mediaModifyQuery.setName(EXPECTED_NEW_STRING_VALUE);
        assertEquals(EXPECTED_NEW_STRING_VALUE, mediaModifyQuery.getName());

        mediaModifyQuery.setDescription(EXPECTED_NEW_STRING_VALUE);
        assertEquals(EXPECTED_NEW_STRING_VALUE, mediaModifyQuery.getDescription());

        mediaModifyQuery.setCopyright(EXPECTED_NEW_STRING_VALUE);
        assertEquals(EXPECTED_NEW_STRING_VALUE, mediaModifyQuery.getCopyright());

        mediaModifyQuery.setArchive(EXPECTED_NEW_BOOLEAN_VALUE);
        assertEquals(EXPECTED_NEW_BOOLEAN_VALUE, mediaModifyQuery.getArchive());

        mediaModifyQuery.setDatePublished(EXPECTED_NEW_STRING_VALUE);
        assertEquals(EXPECTED_NEW_STRING_VALUE, mediaModifyQuery.getDatePublished());

        mediaModifyQuery.setMetaproperty(metapropertyAttribute);
        assertEquals(metapropertyAttribute, mediaModifyQuery.getMetaproperty());
    }
}
