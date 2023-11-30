package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Usage;
import com.bynder.sdk.query.UsageCreateQuery;
import com.bynder.sdk.query.UsageDeleteQuery;
import com.bynder.sdk.query.UsageQuery;
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
public class UsageSample {
    private static final Logger LOG = LoggerFactory.getLogger(UsageSample.class);

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

        // create usage
        String mediaIdForAssetUsage = appProperties.getProperty("MEDIA_ID_FOR_ASSET_USAGE");
        String integrationIdForAssetUsage = appProperties.getProperty("INTEGRATION_ID_FOR_ASSET_USAGE");

        UsageCreateQuery usageCreateQuery = new UsageCreateQuery(integrationIdForAssetUsage, mediaIdForAssetUsage);
        Usage createdAssetUsage = assetService.createUsage(usageCreateQuery).blockingSingle().body();
        if (createdAssetUsage != null) {
            LOG.info("Asset Usage ID: " + createdAssetUsage.getId());
            LOG.info("Asset Usage Asset ID: " + createdAssetUsage.getAssetId());
        }

        // get asset usages
        UsageQuery usageQuery= new UsageQuery().setAssetId(mediaIdForAssetUsage);
        List<Usage> assetUsages = assetService.getUsage(usageQuery).blockingSingle().body();

        if (assetUsages != null && !assetUsages.isEmpty()) {
            String deleteAssetUsageId = "";
            for (Usage assetUsage : assetUsages) {
                LOG.info("Asset Usage ID: " + assetUsage.getId());
                deleteAssetUsageId = assetUsage.getAssetId();
                LOG.info(assetUsage.getAssetId());
            }

            // delete asset usage
            UsageDeleteQuery usageDeleteQuery = new UsageDeleteQuery(integrationIdForAssetUsage, deleteAssetUsageId);
            LOG.info("Deleting asset usage id: " + deleteAssetUsageId);
            assetService.deleteUsage(usageDeleteQuery);
        }
    }
}
