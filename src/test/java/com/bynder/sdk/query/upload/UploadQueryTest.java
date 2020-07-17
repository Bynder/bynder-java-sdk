package com.bynder.sdk.query.upload;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the {@link UploadQuery} class methods.
 */
public class UploadQueryTest {

    public static final String EXPECTED_FILE_PATH = "filePath";
    public static final String EXPECTED_BRAND_ID = "brandId";
    public static final String EXPECTED_MEDIA_ID = "mediaId";
    public static final Boolean EXPECTED_AUDIT = Boolean.TRUE;
    public static final String EXPECTED_METAPROPERTY = "metaproperty.id1=value1,metaproperty.id2=value2";

    @Test
    public void initializeUploadQuery() {
        UploadQuery uploadQuery = new UploadQuery(EXPECTED_FILE_PATH, EXPECTED_BRAND_ID);
        uploadQuery.setMediaId(EXPECTED_MEDIA_ID);
        uploadQuery.setAudit(EXPECTED_AUDIT);
        uploadQuery.setMetaproperty(EXPECTED_METAPROPERTY);

        assertEquals(EXPECTED_FILE_PATH, uploadQuery.getFilepath());
        assertEquals(EXPECTED_BRAND_ID, uploadQuery.getBrandId());
        assertEquals(EXPECTED_MEDIA_ID, uploadQuery.getMediaId());
        assertEquals(EXPECTED_AUDIT, uploadQuery.isAudit());
        assertEquals(EXPECTED_METAPROPERTY, uploadQuery.getMetaproperty());
    }
}
