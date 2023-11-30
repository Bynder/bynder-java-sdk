package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.upload.SaveMediaResponse;
import com.bynder.sdk.query.upload.UploadQuery;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.util.Utils;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadSample {
    private static final Logger LOG = LoggerFactory.getLogger(UploadSample.class);

    public static void main(final String[] args) throws URISyntaxException, IOException {
        /**
         * Loads app.properties file under src/main/resources
         */
        Properties appProperties = Utils.loadConfig("app");

        // Initialize BynderClient with a permanent token
        BynderClient client = BynderClient.Builder.create(
                new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")))
                        .setPermanentToken(appProperties.getProperty("PERMANENT_TOKEN")).build());

        AssetService assetService = client.getAssetService();

        // Upload a file
        // Call the API to request for brands
        String brandId = "";
        List<Brand> brands = assetService.getBrands().blockingSingle().body();
        if (brands != null && !brands.isEmpty()) {
            brandId = brands.get(0).getId();
        }

        String filePath = "src/main/java/com/bynder/sdk/sample/testasset.png";
        LOG.info(filePath);
        UploadQuery uploadQuery = new UploadQuery(filePath, brandId);
        // Add the filename you want to specify in this manner
        uploadQuery.setFileName("testasset.png");
        SaveMediaResponse saveMediaResponse = assetService.uploadFile(uploadQuery).blockingSingle();
        LOG.info(saveMediaResponse.getMediaId());
    }
}
