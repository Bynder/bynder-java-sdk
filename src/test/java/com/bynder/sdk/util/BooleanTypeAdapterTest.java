/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 *
 * JUnit framework component copyright (c) 2002-2017 JUnit. All Rights Reserved. Licensed under
 * Eclipse Public License - v 1.0. You may obtain a copy of the License at
 * https://www.eclipse.org/legal/epl-v10.html.
 */
package com.bynder.sdk.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gson.JsonParser;

/**
 * Tests the {@link BooleanTypeAdapter} class methods.
 */
public class BooleanTypeAdapterTest {

    /**
     * Tests that
     * {@link BooleanTypeAdapter#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)}
     * correctly converts integers booleans when deserializing the Json response returned by the
     * API.
     */
    @Test
    public void deserializeTest() {
        BooleanTypeAdapter booleanTypeAdapter = new BooleanTypeAdapter();

        // Integers
        Boolean value = booleanTypeAdapter.deserialize(new JsonParser().parse("1"), null, null);
        assertTrue(value);

        value = booleanTypeAdapter.deserialize(new JsonParser().parse("0"), null, null);
        assertFalse(value);

        // Booleans
        value = booleanTypeAdapter.deserialize(new JsonParser().parse("true"), null, null);
        assertTrue(value);

        value = booleanTypeAdapter.deserialize(new JsonParser().parse("false"), null, null);
        assertFalse(value);

        // Invalid values
        value = booleanTypeAdapter.deserialize(new JsonParser().parse("-1"), null, null);
        assertNull(value);

        value = booleanTypeAdapter.deserialize(new JsonParser().parse("3"), null, null);
        assertNull(value);

        value = booleanTypeAdapter.deserialize(new JsonParser().parse("string"), null, null);
        assertNull(value);
    }
}
