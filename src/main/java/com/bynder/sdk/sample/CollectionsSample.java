package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Collection;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.collection.CollectionService;
import com.bynder.sdk.query.collection.CollectionQuery;
import com.bynder.sdk.query.collection.CollectionCreateQuery;
import com.bynder.sdk.util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.Properties;

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

        // get collections with limit and page
        CollectionQuery collectionQuery = new CollectionQuery().setLimit(10).setPage(1);
        List<Collection> collections = collectionService.getCollections(collectionQuery).blockingSingle().body();
        for (Collection collectionResult : collections) {
            LOG.info("collection: ");
            LOG.info(collectionResult.getId());
            LOG.info(collectionResult.getName());
            LOG.info(collectionResult.getDescription());
        }

        // add collection
        CollectionCreateQuery createCollectionQuery = new CollectionCreateQuery("New Collection 1234");
        collectionService.createCollection(createCollectionQuery).blockingSingle();

        // get collections with added collection
        List<Collection> updatedCollections = collectionService.getCollections(collectionQuery).blockingSingle().body();
        for (Collection collectionResult : collections) {
            LOG.info("collection: ");
            LOG.info(collectionResult.getId());
            LOG.info(collectionResult.getName());
            LOG.info(collectionResult.getDescription());
        }

        // TODO addMediaToCollection
        // TODO removeMediaFromCollection
        // TODO shareCollection
    }
}
