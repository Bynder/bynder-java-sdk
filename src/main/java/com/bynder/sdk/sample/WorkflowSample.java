/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.sample;

import com.bynder.sdk.configuration.Configuration;
import com.bynder.sdk.configuration.HttpConnectionSettings;
import com.bynder.sdk.model.workflow.*;
import com.bynder.sdk.query.workflow.*;
import com.bynder.sdk.service.BynderClient;
import com.bynder.sdk.service.workflow.WorkflowService;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Sample class to display some of the SDK functionality.
 */
public class WorkflowSample {

    private static final Logger log = LoggerFactory.getLogger(WorkflowSample.class);

    private static final String TEST_CAMPAIGN_ID = "8c05e9f0acb7467d906e5ce2793d66b8";
//    private static final String TEST_CAMPAIGN_ID_NEW = "8bbeb6b32856474597a52d6170919c65";

    public static void main(final String[] args) throws URISyntaxException, IOException {
        // Loads app.properties file under src/main/resources
        AppProperties appProperties = new AppProperties();

        // create configuration builder
        HttpConnectionSettings httpConnectionSettings = new HttpConnectionSettings();
//        httpConnectionSettings.setLoggingInterceptorEnabled(true);

        Configuration.Builder configBuilder = new Configuration.Builder(new URL(appProperties.getProperty("BASE_URL")));
        configBuilder.setPermanentToken(appProperties.getProperty("PERMANENT_TOKEN"));
        configBuilder.setHttpConnectionSettings(httpConnectionSettings);

        // Initialize BynderClient with a permanent token
        BynderClient client = BynderClient.Builder.create(configBuilder.build());

        // Get workflow service
        WorkflowService workflowService = client.getWorkflowService();

        // Call the API
//        Response<List<Campaign>> response = workflowService.getAllCampaigns().blockingSingle();

        CampaignQuery campaignQuery = new CampaignQuery(TEST_CAMPAIGN_ID);
        Response<Campaign> response = workflowService.getCampaign(campaignQuery).blockingSingle();

//        CampaignDataQuery campaignDataQuery = new CampaignDataQuery("Test Campaign via API", "APIT1",
//                "329e7b02-1aa9-4fab-9d59-fa69c70d33ca");
//        campaignDataQuery.setDescription("Test via API");
//        Response<CampaignId> response = workflowService.createCampaign(campaignDataQuery).blockingSingle();

//        Response<CampaignId> response = null;
//        CampaignQuery campaignQuery = new CampaignQuery(TEST_CAMPAIGN_ID_NEW);
//        Response<Campaign> responseGet = workflowService.getCampaign(campaignQuery).blockingSingle();
//        if (responseGet.isSuccessful()) {
//            assert responseGet.body() != null;
//
//            CampaignDataQuery campaignDataQuery = new CampaignDataQuery(responseGet.body().getName(),
//                    responseGet.body().getKey(), responseGet.body().getResponsibleId());
//            campaignDataQuery.setDescription(responseGet.body().getDescription() + " MODIFIED");
//            Map<String, String> campaignMetaproperties = responseGet.body().getCampaignMetaproperties();
//            campaignMetaproperties.put("4fbe87e1d2c0431388515ff39ce827b8", "6B6BECC6-92EA-4295-AD14A09D0F21D3C2");
//            campaignDataQuery.setCampaignMetaproperties(campaignMetaproperties);
//            response = workflowService.updateCampaign(campaignQuery, campaignDataQuery).blockingSingle();
//        }

//        CampaignQuery campaignQuery = new CampaignQuery(TEST_CAMPAIGN_ID_NEW);
//        Response<Void> response = workflowService.deleteCampaign(campaignQuery).blockingSingle();

//        Response<List<WorkflowMetaproperty>> response = workflowService.getAllWorkflowMetaproperties().blockingSingle();

//        WorkflowMetapropertyQuery workflowMetapropertyQuery = new WorkflowMetapropertyQuery("f1ee794c-363f-44f8-ae56-8f6e18460a31");
//        Response<WorkflowMetaproperty> response = workflowService.getWorkflowMetaproperty(workflowMetapropertyQuery).blockingSingle();

//        Response<List<WorkflowUser>> response = workflowService.getAllWorkflowUsers().blockingSingle();

//        Response<List<WorkflowGroup>> response = workflowService.getAllWorkflowGroups().blockingSingle();

//        WorkflowGroupQuery workflowGroupQuery = new WorkflowGroupQuery("59344c68-345b-4cb9-b4b8-afbf4a134fe2");
//        Response<WorkflowGroup> response = workflowService.getWorkflowGroup(workflowGroupQuery).blockingSingle();

//        WorkflowGroupDataQuery workflowGroupDataQuery = new WorkflowGroupDataQuery("Test API SDK");
//        Response<WorkflowGroup> response = workflowService.createWorkflowGroup(workflowGroupDataQuery).blockingSingle();

//        WorkflowGroupQuery workflowGroupQuery = new WorkflowGroupQuery("176b8fa9-77e5-487b-9f4f-acc9ff4408c3");
//        WorkflowGroupDataQuery workflowGroupDataQuery = new WorkflowGroupDataQuery("Test API SDK");
//        List<String> groupUserIds = new ArrayList<>();
//        groupUserIds.add("da8cf14e-b733-4615-a1c5-66565263ff3b");
//        groupUserIds.add("ffaf572b-6c8c-40e1-8659-764046a680bc");
//        workflowGroupDataQuery.setUserIds(groupUserIds);
//        Response<WorkflowGroup> response = workflowService.updateWorkflowGroup(workflowGroupQuery, workflowGroupDataQuery).blockingSingle();

//        WorkflowGroupQuery workflowGroupQuery = new WorkflowGroupQuery("176b8fa9-77e5-487b-9f4f-acc9ff4408c3");
//        Response<Void> response = workflowService.deleteWorkflowGroup(workflowGroupQuery).blockingSingle();

        if (response != null) {
            log.info(new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
            if (response.isSuccessful()) {
                assert response.body() != null;

//                for (Campaign campaign : response.body()) {
//                    log.info("id={}. name={}", campaign.getId(), campaign.getName());
//                }

                Campaign campaign = response.body();
                log.info("id={}, key={}, name={}", campaign.getId(), campaign.getKey(), campaign.getName());

//                CampaignId campaignId = response.body();
//                log.info("id={}", campaignId.getId());

//                for (WorkflowMetaproperty workflowMetaproperty : response.body()) {
//                    log.info("id={}, entity={}. type={}. label={}", workflowMetaproperty.getId(),
//                            workflowMetaproperty.getEntity(), workflowMetaproperty.getType(),
//                            workflowMetaproperty.getLabel());
//                }

//                WorkflowMetaproperty workflowMetaproperty = response.body();
//                log.info("id={}, entity={}. type={}. label={}", workflowMetaproperty.getId(),
//                            workflowMetaproperty.getEntity(), workflowMetaproperty.getType(),
//                            workflowMetaproperty.getLabel());

//                for (WorkflowUser workflowUser : response.body()) {
//                    log.info("id={}. name={}", workflowUser.getId(), workflowUser.getFullName());
//                }

//                for (WorkflowGroup group : response.body()) {
//                    log.info("id={},name={}", group.getId(), group.getName());
//                    for (WorkflowUser user : group.getUsers()) {
//                        log.info("- {}", user.getFullName());
//                    }
//                }

//                WorkflowGroup group = response.body();
//                log.info("id={},name={}", group.getId(), group.getName());
//                for (WorkflowUser user : group.getUsers()) {
//                    log.info("- {}", user.getFullName());
//                }

//                WorkflowGroup group = response.body();
//                log.info("id={}", group.getId());
            }
            else {
                assert response.errorBody() != null;
                log.error(response.errorBody().string());
            }
        }
    }
}