package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Brand;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BrandsSample {
    private static final Logger LOG = LoggerFactory.getLogger(BrandsSample.class);

    public static void main(final String[] args) throws URISyntaxException, IOException {
        /**
         * Loads app.properties file under src/main/resources
         */
        Properties appProperties = Utils.loadConfig("app");


        // Initialize BynderClient with a permanent token
        BynderClient client = BynderClient.Builder.create(
                new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")))
                        .setPermanentToken(appProperties.getProperty("PERMANENT_TOKEN")).build());

        // Initialize asset service
        AssetService assetService = client.getAssetService();

        // Call the API to request for brands
        List<Brand> brands = assetService.getBrands().blockingSingle().body();
        for (Brand brand : brands) {
            LOG.info(brand.getName());
            LOG.info(brand.getDescription());
        }
    }
}
