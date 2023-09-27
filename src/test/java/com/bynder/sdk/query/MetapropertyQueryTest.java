package com.bynder.sdk.query;

import com.bynder.sdk.model.MediaType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link MetapropertyQuery} class methods.
 */
public class MetapropertyQueryTest {

    public static final MediaType EXPECTED_MEDIA_TYPE = MediaType.IMAGE;
    public static final int EXPECTED_INTEGER = 1;
    public static final Boolean EXPECTED_BOOLEAN = Boolean.TRUE;

    public static final String EXPECTED_ID_LIST = "1,2,3,4,5";

    private MetapropertyQuery metapropertyQuery;

    @Before
    public void setUp() {
        this.metapropertyQuery = new MetapropertyQuery();
    }

    @Test
    public void initializeEmptyMetapropertyQuery() {
        assertNull(metapropertyQuery.getType());
        assertNull(metapropertyQuery.getCount());
        assertNull(metapropertyQuery.getOptions());
        assertNull(metapropertyQuery.getIds());
    }

    @Test
    public void setValuesForMediaQuery() {
        metapropertyQuery.setType(EXPECTED_MEDIA_TYPE);
        assertEquals(EXPECTED_MEDIA_TYPE, metapropertyQuery.getType());

        metapropertyQuery.setCount(EXPECTED_BOOLEAN);
        assertEquals(EXPECTED_BOOLEAN, metapropertyQuery.getCount());

        metapropertyQuery.setOptions(EXPECTED_BOOLEAN);
        assertEquals(EXPECTED_BOOLEAN, metapropertyQuery.getOptions());

        metapropertyQuery.setIds(EXPECTED_ID_LIST);
        assertEquals(EXPECTED_ID_LIST, metapropertyQuery.getIds());

    }
}
