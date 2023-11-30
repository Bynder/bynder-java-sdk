package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Smartfilter;
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

public class SmartFiltersSample {
    private static final Logger LOG = LoggerFactory.getLogger(SmartFiltersSample.class);

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

        List<Smartfilter> smartFilters = assetService.getSmartfilters().blockingSingle().body();
        if (smartFilters != null && !smartFilters.isEmpty()) {
            for (Smartfilter smartFilter : smartFilters) {
                LOG.info("Smart Filter ID: " + smartFilter.getId());
                // smart filter metaproperties
                List<String> smartFilterMetaproperties = smartFilter.getMetaproperties();
                if (!smartFilterMetaproperties.isEmpty()) {
                    LOG.info("smart filter metaproperty ids:");
                    for (String metaproperty : smartFilterMetaproperties) {
                        LOG.info("Smart Filter Metaproperty ID: " + metaproperty);
                    }
                }

                // smart filter labels
                Map<String, String> smartFilterLabels = smartFilter.getLabels();
                for (Map.Entry<String, String> entry : smartFilterLabels.entrySet()) {
                    LOG.info("smart filter label: " + entry.getKey() + " " + entry.getValue());
                }
            }
        }
    }
}
