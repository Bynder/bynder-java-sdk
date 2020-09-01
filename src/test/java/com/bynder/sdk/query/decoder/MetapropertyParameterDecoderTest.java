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

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link MetapropertyParameterDecoder} class method.
 */
public class MetapropertyParameterDecoderTest {

    public static final String PARAMETER_NAME = "property";
    public static final String METAPROPERTY_NAME = "name";
    public static final String EXPECTED_OPTION_NAME = "option";
    public static final String EXPECTED_PARAMETER_NAME = PARAMETER_NAME + "_" + METAPROPERTY_NAME;

    @Test
    public void decodeReturnsMetapropertyParameterFormat() {
        Map<String, String> parameters = new MetapropertyParameterDecoder()
            .decode(PARAMETER_NAME, new HashMap<String, String>() {{
                put(METAPROPERTY_NAME, EXPECTED_OPTION_NAME);
            }});

        assertEquals(EXPECTED_OPTION_NAME, parameters.get(EXPECTED_PARAMETER_NAME));
    }
}
