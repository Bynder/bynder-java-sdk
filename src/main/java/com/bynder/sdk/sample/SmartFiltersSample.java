package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.Smartfilter;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.service.oauth.OAuthService;
import com.bynder.sdk.util.Utils;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartFiltersSample {
    private static final Logger LOG = LoggerFactory.getLogger(SmartFiltersSample.class);

    public static void main(final String[] args) throws URISyntaxException, IOException {
        /**
         * Loads app.properties file under src/main/resources
         */
        Properties appProperties = Utils.loadConfig("app");

        // Initialize BynderClient with OAuth
        OAuthSettings oAuthSettings = new OAuthSettings(appProperties.getProperty("CLIENT_ID"), appProperties.getProperty("CLIENT_SECRET"), new URI(appProperties.getProperty("REDIRECT_URI")));
        BynderClient client = BynderClient.Builder.create(
                new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")))
                        .setOAuthSettings(oAuthSettings)
                        .setHttpConnectionSettings(new HttpConnectionSettings()).build());
        List<String> scopes = Arrays.asList("offline", "asset:read", "asset:write", "asset.usage:read",
                "asset.usage:write", "collection:read", "collection:write", "meta.assetbank:read",
                "meta.assetbank:write", "meta.workflow:read");

        // Initialize OAuthService
        OAuthService oauthService = client.getOAuthService();
        URL authorizationUrl = oauthService.getAuthorizationUrl("state example", scopes);

        // Open browser with authorization URL
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(authorizationUrl.toURI());

        // Ask for the code returned in the redirect URI
        System.out.println("Insert the code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        scanner.close();

        // Get the access token
        oauthService.getAccessToken(code, scopes).blockingSingle();

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
        System.exit(0);
    }
}
