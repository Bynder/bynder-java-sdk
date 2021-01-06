package com.bynder.sdk.query.upload;

import com.bynder.sdk.query.decoder.ApiField;

public class FinaliseUploadQuery {

    @ApiField
    private final int chunksCount;

    @ApiField
    private final String fileName;

    @ApiField
    private final long fileSize;

    @ApiField
    private final String sha256;

    public FinaliseUploadQuery(
            final int chunksCount,
            final String fileName,
            final long fileSize,
            final String sha256
    ) {
        this.chunksCount = chunksCount;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.sha256 = sha256;
    }

}
