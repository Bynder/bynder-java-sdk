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

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link StringArrayParameterDecoder} class method.
 */
public class StringArrayParameterDecoderTest {

    public static final String PARAMETER_NAME = "name";
    public static final String EXPECTED_ITEM_1 = "item1";
    public static final String EXPECTED_ITEM_2 = "item2";

    @Test
    public void decodeReturnsJoinedArray() {
        Map<String, String> parameters = new StringArrayParameterDecoder()
            .decode(PARAMETER_NAME, new String[]{EXPECTED_ITEM_1, EXPECTED_ITEM_2});

        assertEquals(EXPECTED_ITEM_1 + "," + EXPECTED_ITEM_2, parameters.get(PARAMETER_NAME));
    }
}
