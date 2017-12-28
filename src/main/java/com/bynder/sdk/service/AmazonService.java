/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import com.bynder.sdk.model.UploadRequest;
import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Interface to upload file parts to Amazon.
 */
public interface AmazonService {

    /**
     * Uploads a file part to Amazon.
     *
     * @param filename Name of the file to be uploaded.
     * @param uploadRequest Upload request information.
     * @param chunkNumber Number of the chunk to be uploaded.
     * @param fileContent Content of the file to be uploaded.
     * @param numberOfChunks Total number of chunks.
     * @return {@link Observable} with the request {@link Response} information.
     */
    Observable<Response<Void>> uploadPartToAmazon(String filename, UploadRequest uploadRequest,
        int chunkNumber, byte[] fileContent, int numberOfChunks);
}
