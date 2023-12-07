package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.Derivative;
import com.bynder.sdk.model.DownloadUrl;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.MediaType;

import com.bynder.sdk.query.*;

import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.service.oauth.OAuthService;
import com.bynder.sdk.util.Utils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaSample {
    private static final Logger LOG = LoggerFactory.getLogger(MediaSample.class);

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

        // get derivatives
        List<Derivative> derivatives = client.getDerivatives().blockingSingle().body();
        if (derivatives != null && !derivatives.isEmpty()) {
            for (Derivative derivative : derivatives) {
                LOG.info("Derivative Prefix: " + derivative.getPrefix());
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

        // get media info for single asset
        String mediaIdInfo = appProperties.getProperty("MEDIA_ID_FOR_INFO");
        MediaInfoQuery mediaInfoQuery = new MediaInfoQuery(mediaIdInfo);
        Media foundMedia = assetService.getMediaInfo(mediaInfoQuery).blockingSingle().body();
        if (foundMedia != null) {
            LOG.info("get media info result: ");
            LOG.info("Media ID: " + foundMedia.getId());
            LOG.info("Media Name: " + foundMedia.getName());
            LOG.info("Media Brand ID: " + foundMedia.getBrandId());
        }

        // get media download url
        MediaDownloadQuery mediaDownloadQuery = new MediaDownloadQuery(mediaIdInfo);
        DownloadUrl mediaDownloadUrl = assetService.getMediaDownloadUrl(mediaDownloadQuery).blockingSingle().body();
        if (mediaDownloadUrl != null) {
            LOG.info("Media S3 File: " + mediaDownloadUrl.getS3File().getFile());
            LOG.info("Media S3 URI: " + mediaDownloadUrl.getS3File().toURI());
        }

        // modify name of asset
        String mediaIdForRename = appProperties.getProperty("MEDIA_ID_FOR_RENAME");
        MediaModifyQuery modifyQuery = new MediaModifyQuery(mediaIdForRename)
                .setName("New Name Updated")
                .setDescription("Test Updated Description");
        assetService.modifyMedia(modifyQuery).blockingSingle();

        MediaInfoQuery mediaInfoQueryRename = new MediaInfoQuery(mediaIdForRename);
        Media updatedFoundMedia = assetService.getMediaInfo(mediaInfoQueryRename).blockingSingle().body();
        if (updatedFoundMedia != null) {
            LOG.info("get updated media info result: ");
            LOG.info(updatedFoundMedia.getId());
            LOG.info(updatedFoundMedia.getName());
            LOG.info(updatedFoundMedia.getDescription());
        }

        // remove media
        String mediaToRemove = appProperties.getProperty("MEDIA_ID_FOR_REMOVAL");
        MediaDeleteQuery mediaDeleteQuery = new MediaDeleteQuery(mediaToRemove);
        LOG.info("Removing media id: " + mediaToRemove);
        assetService.deleteMedia(mediaDeleteQuery).blockingSingle();
        System.exit(0);
    }
}
