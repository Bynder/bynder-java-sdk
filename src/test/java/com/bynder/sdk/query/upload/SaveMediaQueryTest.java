package com.bynder.sdk.query.upload;

import static org.junit.Assert.assertEquals;

import com.bynder.sdk.query.MetapropertyAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the {@link SaveMediaQuery} class methods.
 */
public class SaveMediaQueryTest {

    public static final String EXPECTED_IMPORT_ID = "importId";
    public static final String EXPECTED_BRAND_ID = "brandId";
    public static final String EXPECTED_NAME = "name";
    public static final String EXPECTED_MEDIA_ID = "mediaId";
    public static final String EXPECTED_METAPROPERTY_ID = "metapropertyId";
    public static final String EXPECTED_OPTION_NAME = "optionName";
    public static final List<MetapropertyAttribute> EXPECTED_METAPROPERTIES = new ArrayList<>();
    static {
        EXPECTED_METAPROPERTIES.add(new MetapropertyAttribute(EXPECTED_METAPROPERTY_ID, new String[]{EXPECTED_OPTION_NAME}));
    }

    @Test
    public void initializeSaveMediaQuery() {
        SaveMediaQuery saveMediaQuery = new SaveMediaQuery(EXPECTED_IMPORT_ID);
        saveMediaQuery.setBrandId(EXPECTED_BRAND_ID);
        saveMediaQuery.setName(EXPECTED_NAME);
        saveMediaQuery.setMediaId(EXPECTED_MEDIA_ID);
        saveMediaQuery.setMetaproperties(EXPECTED_METAPROPERTIES);

        assertEquals(EXPECTED_METAPROPERTIES, saveMediaQuery.getMetaproperties());
        assertEquals(EXPECTED_IMPORT_ID, saveMediaQuery.getImportId());
        assertEquals(EXPECTED_BRAND_ID, saveMediaQuery.getBrandId());
        assertEquals(EXPECTED_NAME, saveMediaQuery.getName());
        assertEquals(EXPECTED_MEDIA_ID, saveMediaQuery.getMediaId());
    }

}
