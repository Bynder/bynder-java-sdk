package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.MetapropertyOption;
import com.bynder.sdk.query.MetapropertyQuery;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MetapropertiesSample {
    private static final Logger LOG = LoggerFactory.getLogger(MetapropertiesSample.class);

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

        // get metaproperties
        MetapropertyQuery metapropertyQuery = new MetapropertyQuery();

        Map<String, Metaproperty> metapropertiesMap = assetService.getMetaproperties(metapropertyQuery).blockingSingle().body();

        if (metapropertiesMap != null) {
            for (Map.Entry<String, Metaproperty> metapropertyEntry : metapropertiesMap.entrySet()) {
                LOG.info("current metaproperty");
                LOG.info("Key: " + metapropertyEntry.getKey());
                Metaproperty currentMetaproperty = metapropertyEntry.getValue();

                LOG.info("ID: " + currentMetaproperty.getId());
                LOG.info("Name: " + currentMetaproperty.getName());
                LOG.info("Label: " + currentMetaproperty.getLabel());
                LOG.info("Type: " + currentMetaproperty.getType());
                List<MetapropertyOption> metapropertyOptionList = currentMetaproperty.getOptions();

                // metaproperty options if found
                if (metapropertyOptionList != null && !metapropertyOptionList.isEmpty()) {
                    for (MetapropertyOption metapropertyOption : metapropertyOptionList) {
                        LOG.info("Metaproperty Option ID: " + metapropertyOption.getId());
                        LOG.info("Metaproperty Option Label: " + metapropertyOption.getLabel());
                        LOG.info("Metaproperty Name: " + metapropertyOption.getName());
                    }
                }
            }
        }
    }
}
