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

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Tests the {@link UploadProcessData} class methods.
 */
public class UploadProcessDataTest {

    public static final int MAX_CHUNK_SIZE = 1024 * 1024 * 5;
    public static final int EXPECTED_CHUNK_NUMBER = 0;
    public static final int EXPECTED_NUMBER_OF_CHUNKS = 1;
    public static final long FILE_LENGTH = 1000;

    @Mock
    private File file;
    @Mock
    private FileInputStream fileInputStream;
    @Mock
    private UploadRequest uploadRequest;
    private UploadProcessData uploadProcessData;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(file.length()).thenReturn(FILE_LENGTH);

        this.uploadProcessData = new UploadProcessData(file, fileInputStream, uploadRequest,
            MAX_CHUNK_SIZE);
    }

    @Test
    public void initializeUploadProcessData() {
        assertEquals(file, uploadProcessData.getFile());
        assertEquals(uploadRequest, uploadProcessData.getUploadRequest());
        assertEquals(EXPECTED_CHUNK_NUMBER, uploadProcessData.getChunkNumber());
        assertEquals(EXPECTED_NUMBER_OF_CHUNKS, uploadProcessData.getNumberOfChunks());
        assertFalse(uploadProcessData.isCompleted());
    }

    @Test
    public void incrementChunkAndCompleteUploadProcessData() {
        uploadProcessData.incrementChunk();
        assertEquals(EXPECTED_CHUNK_NUMBER + 1, uploadProcessData.getChunkNumber());
        assertTrue(uploadProcessData.isCompleted());
    }
}
