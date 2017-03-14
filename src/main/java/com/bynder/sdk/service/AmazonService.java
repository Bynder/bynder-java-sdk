/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import com.bynder.sdk.model.UploadRequest;

import io.reactivex.Observable;
import retrofit2.Response;

public interface AmazonService {

    Observable<Response<Void>> uploadPartToAmazon(String filename, UploadRequest uploadRequest, int chunkNumber, byte[] fileContent, int numberOfChunks);
}
