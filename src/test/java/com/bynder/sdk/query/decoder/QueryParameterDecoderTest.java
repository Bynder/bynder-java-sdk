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

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class QueryParameterDecoderTest {

    @Test
    public void decodeNullObjectReturnsEmptyMap() {
        assertEquals(0, new QueryDecoder().decode(null).size());
    }

    @Test
    public void decodeObjectOnlyReturnsParamsWithApiFieldAndNotNull() {
        QueryObject object = new QueryObject("12", "BCN", "Sun");
        Map<String, String> params = new QueryDecoder().decode(object);
        assertEquals(2, params.size());
        assertEquals("12", params.get("_id"));
        assertEquals("BCN", params.get("name"));
    }

    static class QueryObject {

        @ApiField(name = "_id")
        private String id;

        @ApiField(decoder = MockDecoder.class)
        private String name;

        @ApiField
        private String additional;

        private String description;


        QueryObject(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
    }

    static class MockDecoder implements ParameterDecoder<String, String> {

        @Override
        public Map<String, String> decode(String key, String value) {
            Map<String, String> params = new HashMap<>();
            params.put(key, value);
            return params;
        }
    }

}
