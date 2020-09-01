package com.bynder.sdk.query.upload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.bynder.sdk.query.MetapropertyAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the {@link UploadQuery} class methods.
 */
public class UploadQueryTest {

    public static final String EXPECTED_FILE_PATH = "filePath";
    public static final String EXPECTED_BRAND_ID = "brandId";
    public static final String EXPECTED_MEDIA_ID = "mediaId";
    public static final String EXPECTED_METAPROPERTY_ID = "metapropertyId";
    public static final String EXPECTED_OPTION_NAME = "optionName";
    public static final Boolean EXPECTED_AUDIT = Boolean.TRUE;
    public static final List<MetapropertyAttribute> EXPECTED_METAPROPERTIES = new ArrayList<>();
    public static final MetapropertyAttribute EXPECTED_METAPROPERTY = new MetapropertyAttribute(EXPECTED_METAPROPERTY_ID, new String[]{EXPECTED_OPTION_NAME});
    static {
        EXPECTED_METAPROPERTIES.add(EXPECTED_METAPROPERTY);
    }


    @Test
    public void initializeUploadQuery() {
        UploadQuery uploadQuery = new UploadQuery(EXPECTED_FILE_PATH, EXPECTED_BRAND_ID);
        uploadQuery.setMediaId(EXPECTED_MEDIA_ID);
        uploadQuery.setAudit(EXPECTED_AUDIT);
        uploadQuery.addMetaproperty(EXPECTED_METAPROPERTY_ID, EXPECTED_OPTION_NAME);

        assertTrue(EXPECTED_METAPROPERTY.equals(uploadQuery.getMetaproperties().get(0)));
        assertEquals(EXPECTED_FILE_PATH, uploadQuery.getFilepath());
        assertEquals(EXPECTED_BRAND_ID, uploadQuery.getBrandId());
        assertEquals(EXPECTED_MEDIA_ID, uploadQuery.getMediaId());
        assertEquals(EXPECTED_AUDIT, uploadQuery.isAudit());
    }
}
