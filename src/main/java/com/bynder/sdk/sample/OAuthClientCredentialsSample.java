package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.Brand;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.MediaType;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.OrderBy;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.service.oauth.OAuthService;
import com.bynder.sdk.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class OAuthClientCredentialsSample {
    private static final Logger LOG = LoggerFactory.getLogger(MediaSample.class);

    public static void main(final String[] args) throws IOException, URISyntaxException {
        /**
         * Loads app.properties file under src/main/resources
         */
        Properties appProperties = Utils.loadConfig("app");

        // Initialize BynderClient with OAuth
        OAuthSettings oAuthSettings = new OAuthSettings(appProperties.getProperty("CLIENT_ID"), appProperties.getProperty("CLIENT_SECRET"), null);
        BynderClient client = BynderClient.Builder.create(
                new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")))
                        .setOAuthSettings(oAuthSettings)
                        .setHttpConnectionSettings(new HttpConnectionSettings()).build());
        List<String> scopes = Arrays.asList("offline", "asset:read", "asset:write", "asset.usage:read",
                "asset.usage:write", "collection:read", "collection:write", "meta.assetbank:read",
                "meta.assetbank:write", "meta.workflow:read");

        // Initialize OAuthService
        OAuthService oauthService = client.getOAuthService();

        // use client credentials
        oauthService.getAccessTokenClientCredentials(scopes).blockingSingle();

        // Initialize asset service
        AssetService assetService = client.getAssetService();

        // Call the API to request for brands
        List<Brand> brands = assetService.getBrands().blockingSingle().body();
        if (brands != null && !brands.isEmpty()) {
            for (Brand brand : brands) {
                LOG.info("Brand ID: " + brand.getId());
                LOG.info("Brand Name: " + brand.getName());
                LOG.info("Brand Description: " + brand.getDescription());
            }
        }

        // Call the API to request for media assets
        MediaQuery mediaQuery =  new MediaQuery().setType(MediaType.IMAGE).setOrderBy(OrderBy.NAME_DESC).setLimit(10).setPage(1);
        List<Media> mediaList = assetService.getMediaList(mediaQuery).blockingSingle().body();
        if (mediaList != null && !mediaList.isEmpty()) {
            for (Media media : mediaList) {
                LOG.info("Media ID: " + media.getId());
                LOG.info("Media Name: " + media.getName());
            }
        }

        System.exit(0);
    }
}
