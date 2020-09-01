package com.bynder.sdk.query.upload;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link FinaliseUploadQuery} class methods.
 */
public class FinaliseUploadQueryTest {

    public static final String EXPECTED_UPLOAD_ID = "uploadId";
    public static final String EXPECTED_TARGET_ID = "targetId";
    public static final String EXPECTED_S3_FILENAME = "s3Filename";
    public static final int EXPECTED_CHUNKS = 1;

    @Test
    public void initializeFinaliseUploadQuery() {
        FinaliseUploadQuery finaliseUploadQuery = new FinaliseUploadQuery(EXPECTED_UPLOAD_ID,
            EXPECTED_TARGET_ID, EXPECTED_S3_FILENAME, EXPECTED_CHUNKS);

        assertEquals(EXPECTED_UPLOAD_ID, finaliseUploadQuery.getUploadId());
        assertEquals(EXPECTED_TARGET_ID, finaliseUploadQuery.getTargetId());
        assertEquals(EXPECTED_S3_FILENAME, finaliseUploadQuery.getS3Filename());
        assertEquals(EXPECTED_CHUNKS, finaliseUploadQuery.getChunks());
    }
}
