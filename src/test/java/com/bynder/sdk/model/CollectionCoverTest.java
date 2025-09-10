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
package com.bynder.sdk.model;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the {@link CollectionCover} class.
 */
public class CollectionCoverTest {

    private CollectionCover collectionCover;

    @Before
    public void setUp() {
        collectionCover = new CollectionCover();
    }

    /**
     * Tests that the getter methods return the correct values when the fields are populated
     * with valid absolute URL data.
     */
    @Test
    public void testGettersWithValidData() throws Exception {
        // given
        String thumbnailUrl = "https://example.com/thumbnail.jpg";
        String largeUrl = "https://example.com/large.jpg";
        List<String> thumbnailsList = Arrays.asList("https://example.com/thumb1.jpg", "https://example.com/thumb2.jpg");

        setField(collectionCover, "thumbnail", thumbnailUrl);
        setField(collectionCover, "large", largeUrl);
        setField(collectionCover, "thumbnails", thumbnailsList);

        // when
        String actualThumbnail = collectionCover.getThumbnail();
        String actualLarge = collectionCover.getLarge();
        List<String> actualThumbnails = collectionCover.getThumbnails();

        // then
        assertEquals("getThumbnail() should return the correct thumbnail URL.", thumbnailUrl, actualThumbnail);
        assertEquals("getLarge() should return the correct large URL.", largeUrl, actualLarge);
        assertEquals("getThumbnails() should return the correct list of thumbnail URLs.", thumbnailsList, actualThumbnails);
        assertNotNull("Thumbnails list should not be null.", actualThumbnails);
        assertEquals("Thumbnails list should contain 2 items.", 2, actualThumbnails.size());
    }

    /**
     * Tests that the getter methods return the correct values when fields are populated
     * with relative path data instead of full URLs.
     */
    @Test
    public void testGettersWithRelativePathData() throws Exception {
        // given
        String relativePath = "/files/f1a2b3c4-d5e6-7890-1234-567890abcdef?account_id=DUMMY-ACCOUNT-ID&expiry=1234567890123"
            + "&signature=FakeSignatureValue1A2b3C4d5E6f7G8h9I0jKLMNOp&version=dummy_v1a2b3c4";
        List<String> relativePathList = Collections.singletonList(relativePath);

        setField(collectionCover, "thumbnail", relativePath);
        setField(collectionCover, "thumbnails", relativePathList);

        // when
        String actualThumbnail = collectionCover.getThumbnail();
        List<String> actualThumbnails = collectionCover.getThumbnails();

        // then
        assertEquals("getThumbnail() should return the correct relative path.", relativePath, actualThumbnail);
        assertEquals("getThumbnails() should return the correct list of relative paths.", relativePathList, actualThumbnails);
        assertNotNull("Thumbnails list should not be null.", actualThumbnails);
        assertEquals("Thumbnails list should contain 1 item.", 1, actualThumbnails.size());
    }

    /**
     * Tests that the getter methods return null when the fields have not been set.
     */
    @Test
    public void testGettersWithNullData() {
        // given
        // A new CollectionCover instance has all fields as null by default

        // when
        String actualThumbnail = collectionCover.getThumbnail();
        String actualLarge = collectionCover.getLarge();
        List<String> actualThumbnails = collectionCover.getThumbnails();

        // then
        assertNull("getThumbnail() should be null by default.", actualThumbnail);
        assertNull("getLarge() should be null by default.", actualLarge);
        assertNull("getThumbnails() should be null by default.", actualThumbnails);
    }

    /**
     * Tests that the getThumbnails() method correctly returns an empty list.
     */
    @Test
    public void testGetThumbnailsWithEmptyList() throws Exception {
        // given
        List<String> emptyList = Collections.emptyList();
        setField(collectionCover, "thumbnails", emptyList);

        // when
        List<String> result = collectionCover.getThumbnails();

        // then
        assertNotNull("getThumbnails() should return an empty list, not null.", result);
        assertTrue("getThumbnails() should return an empty list.", result.isEmpty());
    }

    /**
     * Helper method to set a value on a private field of an object.
     *
     * @param targetObject The object on which to set the field.
     * @param fieldName    The name of the private field.
     * @param value        The value to set.
     */
    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

}
