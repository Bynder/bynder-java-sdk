/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.model.upload.UploadAdditionalMediaResponse;
import com.bynder.sdk.query.MediaModifyQuery;
import com.bynder.sdk.query.MetapropertyAttribute;
import com.bynder.sdk.query.upload.UploadQuery;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.asset.AssetService;
import com.bynder.sdk.util.Utils;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UploadSample {

    private static final Logger LOG = LoggerFactory.getLogger(UploadSample.class);

    public static void main(final String[] args) throws IOException {

        Properties appProperties = Utils.loadConfig("app");

        // Initialize BynderClient with a permanent token
        BynderClient client = BynderClient.Builder.create(
            new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")))
                .setPermanentToken(appProperties.getProperty("PERMANENT_TOKEN")).build());

        AssetService assetService = client.getAssetService();

        List<Observable<String>> assetsUploaderList = new ArrayList<>();
        for (UploadAsset uploadAsset : scanUploadAssets()) {
            assetsUploaderList.add(handleUploadAsset(assetService, uploadAsset));
        }

        Observable.zip(assetsUploaderList, objects -> {
            LOG.info("Finished all uploads");
            return objects;
        }).blockingSubscribe(mediaIds -> {
            for (Object mediaId : mediaIds) {
                LOG.info("Uploaded media with id: " + mediaId);
            }
        });
    }

    private static List<UploadAsset> scanUploadAssets() throws IOException {
        List<UploadAsset> uploadAssets = new ArrayList<>();

        File rootFolder = new File("sampleFiles/uploadAssets");
        for (File assetFolder : Objects.requireNonNull(rootFolder.listFiles())) {
            if (assetFolder.isDirectory()) {
                UploadAsset uploadAsset = new UploadAsset();

                for (File assetFileContent : Objects.requireNonNull(assetFolder.listFiles())) {
                    if (assetFileContent.isDirectory()) {
                        if ("original".equals(assetFileContent.getName())) {
                            for (File assetFile : Objects.requireNonNull(assetFileContent.listFiles())) {
                                if (!".DS_Store".equals(assetFile.getName())) {
                                    if (uploadAsset.originalAssetPath != null) {
                                        LOG.error("original folder contains more than 1 file, path" + assetFileContent.getPath());
                                        uploadAsset.originalAssetPath = null;
                                        break;
                                    }
                                    uploadAsset.originalAssetPath = assetFile.getPath();
                                }
                            }
                        }
                        else if ("additional".equals(assetFileContent.getName())) {
                            for (File assetFile : Objects.requireNonNull(assetFileContent.listFiles())) {
                                if (!".DS_Store".equals(assetFile.getName())) {
                                    uploadAsset.additionalAssetPaths.add(assetFile.getPath());
                                }
                            }
                        }
                    }
                    else if (assetFileContent.isFile()) {
                        if ("metadata.txt".equals(assetFileContent.getName())) {
                            try (BufferedReader reader = new BufferedReader(new FileReader(assetFileContent))) {
                                String fileLine;
                                while ((fileLine = reader.readLine()) != null) {
                                    String[] metadata = fileLine.split(":");
                                    if ("assetName".equals(metadata[0])) {
                                        uploadAsset.assetName = metadata[1];
                                    }
                                    else if ("brandId".equals(metadata[0])) {
                                        uploadAsset.brandId = metadata[1];
                                    }
                                    if ("metaproperty".equals(metadata[0])) {
                                        uploadAsset.metaproperties.add(metadata[1]);
                                    }
                                    if ("fileNameMapping".equals(metadata[0])) {
                                        StringTokenizer mappings = new StringTokenizer(metadata[1], ";");
                                        while (mappings.hasMoreElements()) {
                                            String[] fileNameMap = mappings.nextToken().split("=");
                                            uploadAsset.fileNameMapping.put(fileNameMap[0], fileNameMap[1]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (uploadAsset.originalAssetPath != null) {
                    uploadAsset.sourceFolderName = assetFolder.getName();
                    uploadAssets.add(uploadAsset);
                }
            }
        }

        return uploadAssets;
    }

    private static Observable<String> handleUploadAsset(AssetService assetService, UploadAsset uploadAsset) {
        LOG.info("Start upload of asset: " + uploadAsset.sourceFolderName);

        UploadQuery originalAssetUpload = new UploadQuery(uploadAsset.originalAssetPath, uploadAsset.brandId);
        String originalFileName = uploadAsset.fileNameMapping.get(originalAssetUpload.getFilename());
        if (originalFileName != null) {
            originalAssetUpload.setFileName(originalFileName);
        }
        if (uploadAsset.assetName != null) {
            originalAssetUpload.setAssetName(uploadAsset.assetName);
        }
        for (String metaproperty : uploadAsset.metaproperties) {
            String[] metapropertyValue = metaproperty.split("=");
            originalAssetUpload.addMetaproperty(metapropertyValue[0], metapropertyValue[1].split(","));
        }
        return assetService.uploadFile(originalAssetUpload).flatMap(response -> {
                    LOG.info("Uploaded original: " + uploadAsset.originalAssetPath + ", assigned id=" +  response.getMediaId());
                    return Observable.just(response.getMediaId());
                }).flatMap(mediaId -> {
                    if (!uploadAsset.additionalAssetPaths.isEmpty()) {
                        List<Observable<UploadAdditionalMediaResponse>> assetsUploaderList = new ArrayList<>();
                        for (String path : uploadAsset.additionalAssetPaths) {
                            LOG.info("Start upload of additional: " + path + " for asset=" + uploadAsset.assetName);
                            UploadQuery addAssetUpload = new UploadQuery(path, uploadAsset.brandId).setMediaId(mediaId);
                            String addFileName = uploadAsset.fileNameMapping.get(addAssetUpload.getFilename());
                            if (addFileName != null) {
                                addAssetUpload.setFileName(addFileName);
                            }
                            assetsUploaderList.add(assetService.uploadAdditionalFile(addAssetUpload).onErrorReturn(error -> {
                                LOG.error("An error has occurred for additional media of asset:" + uploadAsset.assetName + ", error=" + error.toString());
                                return new UploadAdditionalMediaResponse();
                            }));
                        }

                        Observable.zip(assetsUploaderList, objects -> {
                            LOG.info("Finished upload of additional media of asset: " + uploadAsset.assetName);
                            return objects;
                        }).blockingSubscribe(responses -> {
                            for (Object response : responses) {
                                UploadAdditionalMediaResponse additionalMediaResponse = (UploadAdditionalMediaResponse) response;
                                LOG.info("Uploaded additional media for " + uploadAsset.assetName + " with item id: " + additionalMediaResponse.getItemId());
                            }
                        });
                    }
                    return Observable.just(mediaId);
                }).onErrorReturn(error -> {
                    LOG.error("An error has occurred for asset:" + uploadAsset.assetName + ", error=" + error.toString());
                    return "ERROR for asset:" + uploadAsset.assetName;
                });
    }

    private static class UploadAsset {
        private String sourceFolderName;
        private String assetName;
        private String brandId;
        private final List<String> metaproperties;
        private final Map<String, String> fileNameMapping;
        private String originalAssetPath;
        private final List<String> additionalAssetPaths;

        private UploadAsset() {
            this.sourceFolderName = null;
            this.assetName = null;
            this.metaproperties = new ArrayList<>();
            this.fileNameMapping = new HashMap<>();
            this.originalAssetPath = null;
            this.additionalAssetPaths = new ArrayList<>();
        }
    }
}