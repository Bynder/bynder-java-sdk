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
package com.bynder.sdk.model.upload;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * Tests the {@link UploadProgress} class methods.
 */
public class UploadProgressTest {

    public static final long EXPECTED_TOTAL_BYTES = 1;

    @Mock
    private SaveMediaResponse saveMediaResponse;
    private UploadProgress uploadProgress;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.uploadProgress = new UploadProgress(EXPECTED_TOTAL_BYTES);
    }

    @Test
    public void initializeUploadProgress() {
        assertEquals(EXPECTED_TOTAL_BYTES, uploadProgress.getTotalBytes());
        assertEquals(0, uploadProgress.getUploadedChunks());
        assertEquals(0, uploadProgress.getTransmittedBytes());
        assertFalse(uploadProgress.isFinished());
        assertFalse(uploadProgress.areChunksFinished());
        assertNull(uploadProgress.getSaveMediaResponse());

        uploadProgress.setSaveMediaResponse(saveMediaResponse);
        assertEquals(saveMediaResponse, uploadProgress.getSaveMediaResponse());
    }

    @Test
    public void addProgressAndFinishUploadProgress() {
        uploadProgress.addProgress(1);
        assertEquals(1, uploadProgress.getUploadedChunks());
        assertEquals(1, uploadProgress.getTransmittedBytes());
        assertTrue(uploadProgress.areChunksFinished());

        uploadProgress.setFinished(true);
        assertTrue(uploadProgress.isFinished());
    }
}
