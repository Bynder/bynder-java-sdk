package com.bynder.sdk.query.upload;

import com.bynder.sdk.query.MetapropertyAttribute;

import java.util.ArrayList;
import java.util.List;

public class NewAssetUploadQuery extends UploadQuery {

    /**
     * Brand id where we want to store the file.
     */
    private final String brandId;

    /**
     * Flags if the media asset should be sent to the waiting room.
     */
    private Boolean audit;

    /**
     * list of metaproperties and options to set on the asset upon upload.
     */
    private List<MetapropertyAttribute> metaproperties;

    public NewAssetUploadQuery(String filepath, String brandId) {
        super(filepath);
        this.brandId = brandId;
    }

    public String getBrandId() {
        return brandId;
    }

    public Boolean isAudit() {
        return audit;
    }

    public UploadQuery setAudit(final Boolean audit) {
        this.audit = audit;
        return this;
    }

    public List<MetapropertyAttribute> getMetaproperties() {
        return metaproperties;
    }

    public UploadQuery addMetaproperty(String metapropertyUuid, String optionName) {
        if(this.metaproperties == null) {
            this.metaproperties = new ArrayList<>();
        }

        this.metaproperties.add(new MetapropertyAttribute(metapropertyUuid, new String[]{optionName}));

        return this;
    }

}
