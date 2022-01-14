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

import com.bynder.sdk.model.MediaType;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link MediaQuery} class methods.
 */
public class MediaQueryTest {

    public static final MediaType EXPECTED_MEDIA_TYPE = MediaType.IMAGE;
    public static final String EXPECTED_KEYWORD = "keyword";
    public static final int EXPECTED_INTEGER = 1;
    public static final Boolean EXPECTED_BOOLEAN = Boolean.TRUE;
    public static final String EXPECTED_OPTION1 = "option1";
    public static final String EXPECTED_OPTION2 = "option2";
    public static final OrderBy EXPECTED_ORDER = OrderBy.DATE_CREATED_ASC;
    public static final String EXPECTED_METAPROPERTY_NAME = "metapropertyName";
    public static final String EXPECTED_METAPROPERTY_OPTION = "metapropertyOption";
    public static final String EXPECTED_TAGS = "tag1,tag2";

    private MediaQuery mediaQuery;

    @Before
    public void setUp() {
        this.mediaQuery = new MediaQuery();
    }

    @Test
    public void initializeEmptyMediaQuery() {
        assertNull(mediaQuery.getType());
        assertNull(mediaQuery.getKeyword());
        assertNull(mediaQuery.getIsPublic());
        assertNull(mediaQuery.getLimit());
        assertNull(mediaQuery.getPage());
        assertNull(mediaQuery.getPropertyOptionIds());
        assertNull(mediaQuery.getOrderBy());
        assertNull(mediaQuery.getMetapropertyOptions());
    }

    @Test
    public void setValuesForMediaQuery() {
        mediaQuery.setType(EXPECTED_MEDIA_TYPE);
        assertEquals(EXPECTED_MEDIA_TYPE, mediaQuery.getType());

        mediaQuery.setKeyword(EXPECTED_KEYWORD);
        assertEquals(EXPECTED_KEYWORD, mediaQuery.getKeyword());

        mediaQuery.setIsPublic(EXPECTED_BOOLEAN);
        assertEquals(EXPECTED_BOOLEAN, mediaQuery.getIsPublic());

        mediaQuery.setLimited(EXPECTED_BOOLEAN);
        assertEquals(EXPECTED_BOOLEAN, mediaQuery.getLimited());

        mediaQuery.setLimit(EXPECTED_INTEGER);
        assertEquals(EXPECTED_INTEGER, mediaQuery.getLimit().intValue());

        mediaQuery.setTags(Arrays.asList("tag1", "tag2"));
        assertEquals(EXPECTED_TAGS, mediaQuery.getTags());

        mediaQuery.setPage(EXPECTED_INTEGER);
        assertEquals(EXPECTED_INTEGER, mediaQuery.getPage().intValue());

        mediaQuery.setPropertyOptionIds(EXPECTED_OPTION1, EXPECTED_OPTION2);
        assertEquals(2, mediaQuery.getPropertyOptionIds().length);
        assertEquals(EXPECTED_OPTION1, mediaQuery.getPropertyOptionIds()[0]);
        assertEquals(EXPECTED_OPTION2, mediaQuery.getPropertyOptionIds()[1]);

        mediaQuery.setOrderBy(EXPECTED_ORDER);
        assertEquals(EXPECTED_ORDER, mediaQuery.getOrderBy());

        mediaQuery.setMetapropertyOptions(new HashMap<String, String>() {{
            put(EXPECTED_METAPROPERTY_NAME, EXPECTED_METAPROPERTY_OPTION);
        }});
        assertEquals(1, mediaQuery.getMetapropertyOptions().size());
        assertEquals(EXPECTED_METAPROPERTY_OPTION,
            mediaQuery.getMetapropertyOptions().get(EXPECTED_METAPROPERTY_NAME));
    }
}
