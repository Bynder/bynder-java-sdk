/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
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

public class StringArrayParameterDecoderTest {

    @Test
    public void decodeReturnsJoinedArray() {
        Map<String, String> params = new StringArrayParameterDecoder()
            .decode("key", new String[]{"item1", "item2"});

        assertEquals("item1,item2", params.get("key"));
    }
}
