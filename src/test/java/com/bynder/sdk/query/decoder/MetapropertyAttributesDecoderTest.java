package com.bynder.sdk.query.decoder;

import com.bynder.sdk.query.MetapropertyAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MetapropertyAttribu
        tesDecoderTest {

    public static final String PARAMETER_NAME = "metaproperty";
    public static final String METAPROPERTY_UUID = "00000000-0000-0000-0000000000000000";
    public static final String EXPECTED_OPTION_NAME = "option";
    public static final String EXPECTED_PARAMETER_NAME = PARAMETER_NAME + "." + METAPROPERTY_UUID;

    @Test
    public void decodeReturnsMetapropertyAttributeFormat() {
        List<MetapropertyAttribute> metapropertyAttributes = new ArrayList<>();
        metapropertyAttributes.add(new MetapropertyAttribute(METAPROPERTY_UUID, new String[]{EXPECTED_OPTION_NAME}));

        Map<String, String> parameters = new MetapropertyAttributesDecoder().decode(PARAMETER_NAME, metapropertyAttributes);

        assertEquals(1, parameters.size());
        assertEquals(EXPECTED_OPTION_NAME, parameters.get(EXPECTED_PARAMETER_NAME));
    }
}