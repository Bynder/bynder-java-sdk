package com.bynder.sdk.query.upload;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UploadQueryTest {

    public static final String EXPECTED_FILE_PATH = "filePath";
    public static final String EXPECTED_BRAND_ID = "brandId";
    public static final String EXPECTED_MEDIA_ID = "mediaId";
    public static final Boolean EXPECTED_AUDIT = Boolean.TRUE;

    @Test
    public void initializeUploadQuery() {
        UploadQuery uploadQuery = new UploadQuery(EXPECTED_FILE_PATH, EXPECTED_BRAND_ID);
        uploadQuery.setMediaId(EXPECTED_MEDIA_ID);
        uploadQuery.setAudit(EXPECTED_AUDIT);

        assertEquals(EXPECTED_FILE_PATH, uploadQuery.getFilepath());
        assertEquals(EXPECTED_BRAND_ID, uploadQuery.getBrandId());
        assertEquals(EXPECTED_MEDIA_ID, uploadQuery.getMediaId());
        assertEquals(EXPECTED_AUDIT, uploadQuery.isAudit());
    }
}
