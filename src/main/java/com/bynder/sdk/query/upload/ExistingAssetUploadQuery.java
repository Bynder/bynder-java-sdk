package com.bynder.sdk.query.upload;

public class ExistingAssetUploadQuery extends UploadQuery {

    /**
     * Media id. If specified it will add the media asset file as new version of the specified
     * media. Otherwise a new media asset will be added to the asset bank.
     */
    private final String mediaId;

    public ExistingAssetUploadQuery(String filepath, String mediaId) {
        super(filepath);
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

}
