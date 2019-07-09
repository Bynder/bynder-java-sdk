package com.bynder.sdk.query.upload;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the {@link RequestUploadQuery} class methods.
 */
public class RequestUploadQueryTest {

    public static final String EXPECTED_FILENAME = "filename";

    @Test
    public void initializeRequestUploadQuery() {
        RequestUploadQuery requestUploadQuery = new RequestUploadQuery(EXPECTED_FILENAME);

        assertEquals(EXPECTED_FILENAME, requestUploadQuery.getFilename());
    }
}
