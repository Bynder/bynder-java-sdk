package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.configuration.OAuthSettings;
import com.bynder.sdk.model.Collection;
import com.bynder.sdk.query.collection.*;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.collection.CollectionService;
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

public class CollectionsSample {
    private static final Logger LOG = LoggerFactory.getLogger(CollectionsSample.class);

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

        // Initialize collection service
        CollectionService collectionService = client.getCollectionService();

        // get collections with limit
        CollectionQuery collectionQuery = new CollectionQuery().setLimit(20);
        List<Collection> collections = collectionService.getCollections(collectionQuery).blockingSingle().body();
        if (collections != null && !collections.isEmpty()) {
            for (Collection collectionResult : collections) {
                LOG.info("Collection ID: " + collectionResult.getId());
                LOG.info("Collection Name: " + collectionResult.getName());
                LOG.info("Collection Description: " + collectionResult.getDescription());
                LOG.info("Collection Media Count: " + collectionResult.getMediaCount());
                LOG.info("Collection Date Created: " + collectionResult.getDateCreated());
            }
        }

        // get collection info
        String collectionInfoId = appProperties.getProperty("GET_COLLECTION_INFO_ID");
        CollectionInfoQuery collectionInfoQuery = new CollectionInfoQuery(collectionInfoId);
        Collection collectionInfo = collectionService.getCollectionInfo(collectionInfoQuery).blockingSingle().body();
        if (collectionInfo != null) {
            LOG.info("Collection Info Name: " + collectionInfo.getName());
            LOG.info("Collection Info Description: " + collectionInfo.getDescription());
        }

        // create collection
        Random random = new Random();
        int randomInt = random.nextInt(100);
        String newCollectionName = "New Collection" + randomInt;
        LOG.info("new collection name: " + newCollectionName);
        CollectionCreateQuery createCollectionQuery = new CollectionCreateQuery(newCollectionName);
        collectionService.createCollection(createCollectionQuery).blockingSingle();

        // get collections with added collection
        List<Collection> updatedCollections = collectionService.getCollections(collectionQuery).blockingSingle().body();
        if (updatedCollections != null && !updatedCollections.isEmpty()) {
            for (Collection collectionResult : updatedCollections) {
                LOG.info("Collection ID: " + collectionResult.getId());
                LOG.info("Collection Name: " + collectionResult.getName());
                LOG.info("Collection Description: " + collectionResult.getDescription());
                LOG.info("Collection Media Count: " + collectionResult.getMediaCount());
                LOG.info("Collection Date Created: " + collectionResult.getDateCreated());
            }
        }

        // share collection to recipients
        String shareCollectionId = appProperties.getProperty("SHARE_COLLECTION_ID");
        LOG.info("sharing collection id: " + shareCollectionId);
        String collectionShareRecipient = appProperties.getProperty("COLLECTION_SHARE_RECIPIENT");
        String[] shareCollectionRecipients = new String[] {collectionShareRecipient};

        // allow recipient to view, login required false, send email with message "test"
        CollectionShareQuery collectionShareQuery = new CollectionShareQuery(shareCollectionId, shareCollectionRecipients, CollectionRecipientRight.VIEW)
                .setLoginRequired(false)
                .setSendMail(true)
                .setMessage("test");
        collectionService.shareCollection(collectionShareQuery);

        // addMediaToCollection
        String addMediaToCollectionId = appProperties.getProperty("ADD_MEDIA_TO_COLLECTION_COLLECTION_ID");
        String mediaIdToAdd = appProperties.getProperty("ADD_MEDIA_TO_COLLECTION_MEDIA_ID");
        String[] addMediaIds = new String[] {mediaIdToAdd};

        LOG.info("adding media ids: " + mediaIdToAdd);
        CollectionAddMediaQuery collectionAddMediaQuery = new CollectionAddMediaQuery(addMediaToCollectionId, addMediaIds);
        collectionService.addMediaToCollection(collectionAddMediaQuery).blockingSingle();


        // get info about collection that had media added
        CollectionInfoQuery addMediaCollectionInfoQuery = new CollectionInfoQuery(addMediaToCollectionId);
        List<String> mediaIdsFromCollection = collectionService.getCollectionMediaIds(addMediaCollectionInfoQuery).blockingSingle().body();
        for (String mediaId : mediaIdsFromCollection) {
            LOG.info("media id: " + mediaId);
        }

        // removeMediaFromCollection
        String removeFromCollectionId = appProperties.getProperty("REMOVE_FROM_COLLECTION_ID");
        String mediaIdToRemove = appProperties.getProperty("REMOVE_MEDIA_ID_FROM_COLLECTION");
        String[] removeMediaIds = new String[] {mediaIdToRemove};
        LOG.info("removing media ids: " + mediaIdToRemove);
        CollectionRemoveMediaQuery collectionRemoveMediaQuery = new CollectionRemoveMediaQuery(removeFromCollectionId, removeMediaIds);
        collectionService.removeMediaFromCollection(collectionRemoveMediaQuery).blockingSingle();

        // get updated media ids from collection after removal
        CollectionInfoQuery removeMediaCollectionInfoQuery = new CollectionInfoQuery(removeFromCollectionId);
        List<String> mediaIdsFromCollectionAfterRemoval = collectionService.getCollectionMediaIds(removeMediaCollectionInfoQuery).blockingSingle().body();
        for (String mediaId : mediaIdsFromCollectionAfterRemoval) {
            LOG.info("media id: " + mediaId);
        }
    }
}
