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
package com.bynder.sdk.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link MediaDownloadQuery} class methods.
 */
public class MediaDownloadQueryTest {

    public static final String EXPECTED_MEDIA_ID = "id";
    public static final String EXPECTED_MEDIA_ITEM_ID = "itemId";

    @Test
    public void initializeMediaDownloadQuery() {
        MediaDownloadQuery mediaDownloadQuery = new MediaDownloadQuery(EXPECTED_MEDIA_ID);
        assertEquals(EXPECTED_MEDIA_ID, mediaDownloadQuery.getMediaId());
        assertNull(mediaDownloadQuery.getMediaItemId());

        mediaDownloadQuery.setMediaItemId(EXPECTED_MEDIA_ITEM_ID);
        assertEquals(EXPECTED_MEDIA_ITEM_ID, mediaDownloadQuery.getMediaItemId());
    }
}
