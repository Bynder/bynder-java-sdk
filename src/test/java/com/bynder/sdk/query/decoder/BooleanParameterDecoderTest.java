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

import static org.junit.Assert.assertEquals;

import java.util.Map;
import org.junit.Test;

/**
 * Tests the {@link BooleanParameterDecoder} class method.
 */
public class BooleanParameterDecoderTest {

    public static final String PARAMETER_NAME = "name";
    public static final String EXPECTED_TRUE_VALUE = "1";
    public static final String EXPECTED_FALSE_VALUE = "0";


    @Test
    public void decodeReturnsInteger() {
        Map<String, String> parameters = new BooleanParameterDecoder()
            .decode(PARAMETER_NAME, Boolean.TRUE);
        assertEquals(EXPECTED_TRUE_VALUE, parameters.get(PARAMETER_NAME));

        parameters = new BooleanParameterDecoder().decode(PARAMETER_NAME, Boolean.FALSE);
        assertEquals(EXPECTED_FALSE_VALUE, parameters.get(PARAMETER_NAME));
    }
}
