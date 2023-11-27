package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.Media;
import com.bynder.sdk.model.MediaType;

import com.bynder.sdk.query.MediaInfoQuery;
import com.bynder.sdk.query.MediaModifyQuery;
import com.bynder.sdk.query.MediaQuery;
import com.bynder.sdk.query.OrderBy;

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

public class MediaSample {
    private static final Logger LOG = LoggerFactory.getLogger(MediaSample.class);

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

        // Call the API to request for media assets
        MediaQuery mediaQuery =  new MediaQuery().setType(MediaType.IMAGE).setOrderBy(OrderBy.NAME_DESC).setLimit(10).setPage(1);
        List<Media> mediaList = assetService.getMediaList(mediaQuery).blockingSingle().body();
        for (Media media : mediaList) {
            LOG.info(media.getId());
            LOG.info(media.getName());
        }

        if (!mediaList.isEmpty()) {
            // get media info for single asset
            String mediaId = mediaList.get(0).getId();
            MediaInfoQuery mediaInfoQuery = new MediaInfoQuery(mediaId);
            Media foundMedia = assetService.getMediaInfo(mediaInfoQuery).blockingSingle().body();
            LOG.info("get media info result: ");
            LOG.info(foundMedia.getId());
            LOG.info(foundMedia.getName());

            // modify name of asset
            MediaModifyQuery modifyQuery = new MediaModifyQuery(mediaId).setName("New Name");
            assetService.modifyMedia(modifyQuery).blockingSingle();

            Media updatedFoundMedia = assetService.getMediaInfo(mediaInfoQuery).blockingSingle().body();
            LOG.info("get updated media info result: ");
            LOG.info(updatedFoundMedia.getId());
            LOG.info(updatedFoundMedia.getName());
        }
    }
}