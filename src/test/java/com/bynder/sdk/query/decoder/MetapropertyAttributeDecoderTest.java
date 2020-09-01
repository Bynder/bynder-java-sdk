/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 *
 * JUnit framework component copyright (c) 2002-2017 JUnit. All Rights Reserved. Licensed under
 * Eclipse Public License - v 1.0. You may obtain a copy of the License at
 * https://www.eclipse.org/legal/epl-v10.html.
 */
package com.bynder.sdk.query.decoder;

import com.bynder.sdk.query.MetapropertyAttribute;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link MetapropertyAttributeDecoder} class method.
 */
public class MetapropertyAttributeDecoderTest {

    public static final String PARAMETER_NAME = "metaproperty";
    public static final String METAPROPERTY_UUID = "00000000-0000-0000-0000000000000000";
    public static final String EXPECTED_OPTION_NAME = "option";
    public static final String EXPECTED_PARAMETER_NAME = PARAMETER_NAME + "." + METAPROPERTY_UUID;

    @Test
    public void decodeReturnsMetapropertyAttributeFormat() {
        Map<String, String> parameters = new MetapropertyAttributeDecoder().decode(PARAMETER_NAME,
            new MetapropertyAttribute(METAPROPERTY_UUID, new String[]{EXPECTED_OPTION_NAME}));

        assertEquals(EXPECTED_OPTION_NAME, parameters.get(EXPECTED_PARAMETER_NAME));
    }
}
