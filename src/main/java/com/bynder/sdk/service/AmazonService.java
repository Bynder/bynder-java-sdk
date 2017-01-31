/**
 * Copyright (c) Bynder. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service;

import com.bynder.sdk.model.UploadRequest;

public interface AmazonService {

    BynderServiceCall<Void> uploadPartToAmazon(String filename, UploadRequest uploadRequest, int chunkNumber, byte[] fileContent, int numberOfChunks);
}
