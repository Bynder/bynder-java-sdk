package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Tag;
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

public class TagsSample {
    private static final Logger LOG = LoggerFactory.getLogger(TagsSample.class);

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

        // get tags and media count for each tag
        List<Tag> assetTags = assetService.getTags().blockingSingle().body();
        if (assetTags != null && !assetTags.isEmpty()) {
            for (Tag assetTag : assetTags) {
                LOG.info("Asset Tag ID: " + assetTag.getId());
                LOG.info("Asset Tag: " + assetTag.getTag());
                LOG.info("Asset Tag Media Count: " + assetTag.getMediaCount());
            }
        }
    }
}
