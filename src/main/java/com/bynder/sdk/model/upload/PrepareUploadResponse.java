package com.bynder.sdk.model.upload;

import com.bynder.sdk.api.BynderApi;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Response body of {@link BynderApi#prepareUpload}
 */
public class PrepareUploadResponse {

    @SerializedName(value = "file_id")
    private UUID fileId;

    public UUID getFileId() {
        return fileId;
    }

}
