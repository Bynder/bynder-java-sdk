package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Collection;
import com.bynder.sdk.query.collection.*;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.collection.CollectionService;
import com.bynder.sdk.util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionsSample {
    private static final Logger LOG = LoggerFactory.getLogger(CollectionsSample.class);

    public static void main(final String[] args) throws URISyntaxException, IOException {
        /**
         * Loads app.properties file under src/main/resources
         */
        Properties appProperties = Utils.loadConfig("app");


        // Initialize BynderClient with a permanent token
        BynderClient client = BynderClient.Builder.create(
                new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")))
                        .setPermanentToken(appProperties.getProperty("PERMANENT_TOKEN")).build());

        // Initialize collection service
        CollectionService collectionService = client.getCollectionService();

        // get collections
        CollectionQuery collectionQuery = new CollectionQuery();
        List<Collection> collections = collectionService.getCollections(collectionQuery).blockingSingle().body();
        if (collections != null && !collections.isEmpty()) {
            for (Collection collectionResult : collections) {
                LOG.info("collection: ");
                LOG.info(collectionResult.getId());
                LOG.info(collectionResult.getName());
                LOG.info(collectionResult.getDescription());
            }
        }

        // get collection info
        String collectionInfoId = appProperties.getProperty("GET_COLLECTION_INFO_ID");
        CollectionInfoQuery collectionInfoQuery = new CollectionInfoQuery(collectionInfoId);
        Collection collectionInfo = collectionService.getCollectionInfo(collectionInfoQuery).blockingSingle().body();
        if (collectionInfo != null) {
            LOG.info("collection info name: " + collectionInfo.getName());
            LOG.info("collection description: " + collectionInfo.getDescription());
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
                LOG.info("collection: ");
                LOG.info(collectionResult.getId());
                LOG.info(collectionResult.getName());
                LOG.info(collectionResult.getDescription());
            }
        }

        // TODO resolve collection methods

        // share collection to recipients
        String shareCollectionId = appProperties.getProperty("SHARE_COLLECTION_ID");
        LOG.info("trying to share collection id: " + shareCollectionId);
        String[] shareCollectionRecipients = new String[] {"alex.hong@bynder.com"};
        CollectionRecipientRight recipientRight = CollectionRecipientRight.EDIT;
        CollectionShareQuery collectionShareQuery = new CollectionShareQuery(shareCollectionId, shareCollectionRecipients, recipientRight)
                .setLoginRequired(false)
                .setSendMail(true)
                .setMessage("test");
        collectionService.shareCollection(collectionShareQuery).blockingSingle();

        // addMediaToCollection
        String addMediaToCollectionId = appProperties.getProperty("ADD_MEDIA_TO_COLLECTION_COLLECTION_ID");
        String mediaIdToAdd = appProperties.getProperty("ADD_MEDIA_TO_COLLECTION_MEDIA_ID");
        String[] addMediaIds = new String[] {mediaIdToAdd};

        for (String mediaId : addMediaIds) {
            LOG.info("test: " + mediaId);
        }

        LOG.info("adding media ids: " + mediaIdToAdd);
        CollectionAddMediaQuery collectionAddMediaQuery = new CollectionAddMediaQuery(addMediaToCollectionId, addMediaIds);
        collectionService.addMediaToCollection(collectionAddMediaQuery).blockingSingle();


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

        CollectionInfoQuery removeMediaCollectionInfoQuery = new CollectionInfoQuery(removeFromCollectionId);
        List<String> mediaIdsFromCollectionAfterRemoval = collectionService.getCollectionMediaIds(removeMediaCollectionInfoQuery).blockingSingle().body();
        for (String mediaId : mediaIdsFromCollectionAfterRemoval) {
            LOG.info("media id: " + mediaId);
        }
    }
}
