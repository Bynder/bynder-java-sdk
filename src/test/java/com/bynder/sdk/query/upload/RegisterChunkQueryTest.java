package com.bynder.sdk.query.upload;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link RegisterChunkQuery} class methods.
 */
public class RegisterChunkQueryTest {

    public static final String EXPECTED_UPLOAD_ID = "uploadId";
    public static final int EXPECTED_CHUNK_NUMBER = 1;
    public static final String EXPECTED_TARGET_ID = "targetId";
    public static final String EXPECTED_FILENAME = "filename";

    @Test
    public void initializeRegisterChunkQuery() {
        RegisterChunkQuery registerChunkQuery = new RegisterChunkQuery(
                EXPECTED_CHUNK_NUMBER,
                EXPECTED_UPLOAD_ID,
                EXPECTED_TARGET_ID,
                EXPECTED_FILENAME
        );

        assertEquals(EXPECTED_UPLOAD_ID, registerChunkQuery.getUploadId());
        assertEquals(EXPECTED_CHUNK_NUMBER, registerChunkQuery.getChunkNumber());
        assertEquals(EXPECTED_TARGET_ID, registerChunkQuery.getTargetId());
        assertEquals(EXPECTED_FILENAME, registerChunkQuery.getFilename());
    }
}
