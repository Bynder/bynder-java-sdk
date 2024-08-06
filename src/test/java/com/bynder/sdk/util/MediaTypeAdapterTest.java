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
package com.bynder.sdk.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.bynder.sdk.model.Media;
import com.google.gson.JsonParser;

/**
 * Tests the {@link BooleanTypeAdapter} class methods.
 */
public class MediaTypeAdapterTest {
    
    
    /**
     * Given JSON
     * 
     * {
     *   "id": "FF5DB884-5665-4127-88D0116D8EB379FE",
     *   "isPublic": 0,
     *   "property_Articlenumber": "16773",
     *   "property_Language": [
     *     "Neutral"
     *   ]
     * }
     * 
     */
    private final String givenApiResponse = "{ \"id\": \"FF5DB884-5665-4127-88D0116D8EB379FE\", \"isPublic\": 0, \"property_Articlenumber\": \"16773\", \"property_Language\": [ \"Neutral\" ]}";


    /**
     * Tests that
     * {@link MediaTypeAdapter#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)}
     * correctly converts all "property_" prefixed json fields into a custom Map<String,List<String>> when deserializing the Json response returned by the
     * API.
     */
    @Test
    public void deserializeWithMediaTypeAdapter() {
        MediaTypeAdapter mediaTypeAdapter = new MediaTypeAdapter();

        Media actualMedia = mediaTypeAdapter.deserialize(JsonParser.parseString(givenApiResponse), null, null);
       
        // common default field like ID
        assertEquals("FF5DB884-5665-4127-88D0116D8EB379FE", actualMedia.getId());
        
        // boolean using BooleanTypeAdapter inside MediaTypeAdapter
        assertEquals(Boolean.FALSE, actualMedia.isPublic());
        
        // new custom "property_"  which is returned as string from API#
        
        assertNotNull(actualMedia.getCustomMetaproperties());
        assertTrue(actualMedia.getCustomMetaproperties().containsKey("Articlenumber"));
        assertEquals(Arrays.asList("16773"), actualMedia.getCustomMetaproperties().get("Articlenumber"));
        
        // new custom "property_" which is returned as array from API
        assertNotNull(actualMedia.getCustomMetaproperties());
        assertTrue(actualMedia.getCustomMetaproperties().containsKey("Language"));
        assertEquals(Arrays.asList("Neutral"), actualMedia.getCustomMetaproperties().get("Language"));
    }
}
