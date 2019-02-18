package com.bynder.sdk.query.upload;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the {@link PollStatusQuery} class methods.
 */
public class PollStatusQueryTest {

    public static final String[] EXPECTED_ITEMS = new String[]{"item1", "item2"};

    @Test
    public void initializePollStatusQuery() {
        PollStatusQuery pollStatusQuery = new PollStatusQuery(EXPECTED_ITEMS);

        assertEquals(2, pollStatusQuery.getItems().length);
        assertEquals("item1", pollStatusQuery.getItems()[0]);
        assertEquals("item2", pollStatusQuery.getItems()[1]);
    }
}
