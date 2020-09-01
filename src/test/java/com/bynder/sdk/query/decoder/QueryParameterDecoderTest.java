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

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link QueryDecoder} and {@link ParameterDecoder} class methods.
 */
public class QueryParameterDecoderTest {

    public static final String EXPECTED_ID = "123";
    public static final String EXPECTED_NAME = "name";
    public static final String DESCRIPTION = "description";

    private QueryDecoder queryDecoder;

    @Before
    public void setUp() {
        queryDecoder = new QueryDecoder();
    }

    @Test
    public void decodeNullObjectReturnsEmptyMap() {
        assertEquals(0, queryDecoder.decode(null).size());
    }

    @Test
    public void decodeObjectOnlyReturnsParamsWithApiFieldAndNotNull() {
        QueryObject object = new QueryObject(EXPECTED_ID, EXPECTED_NAME, DESCRIPTION);
        Map<String, String> parameters = queryDecoder.decode(object);
        assertEquals(2, parameters.size());
        assertEquals(EXPECTED_ID, parameters.get("id"));
        assertEquals(EXPECTED_NAME, parameters.get("name"));
    }

    static class QueryObject {

        @ApiField(name = "id")
        private String queryId;

        @ApiField(decoder = MockDecoder.class)
        private String name;

        @ApiField
        private String additional;

        private String description;


        QueryObject(String id, String name, String description) {
            this.queryId = id;
            this.name = name;
            this.description = description;
        }
    }

    static class MockDecoder implements ParameterDecoder<String, String> {

        @Override
        public Map<String, String> decode(String name, String value) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put(name, value);
            return parameters;
        }
    }

}
