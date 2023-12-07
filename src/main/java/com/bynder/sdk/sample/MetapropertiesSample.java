package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.Metaproperty;
import com.bynder.sdk.model.MetapropertyOption;
import com.bynder.sdk.query.MetapropertyQuery;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.service.oauth.OAuthService;
import com.bynder.sdk.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class MetapropertiesSample {
    private static final Logger LOG = LoggerFactory.getLogger(MetapropertiesSample.class);

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
        System.exit(0);
    }
}
