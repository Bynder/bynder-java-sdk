/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.service.workflow;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.workflow.*;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.workflow.*;
import io.reactivex.Observable;
import retrofit2.Response;

import java.util.List;

public interface WorkflowService {

    Observable<Response<List<Campaign>>> getAllCampaigns();

    Observable<Response<Campaign>> getCampaign(CampaignQuery campaignQuery);

    Observable<Response<CampaignId>> createCampaign(CampaignDataQuery campaignDataQuery);

    Observable<Response<CampaignId>> modifyCampaign(CampaignQuery campaignQuery, CampaignDataQuery campaignDataQuery);

    Observable<Response<Void>> deleteCampaign(CampaignQuery campaignQuery);

    Observable<Response<JobPresetContainer>> getJobPreset(JobPresetQuery jobPresetQuery);

    Observable<Response<List<Job>>> getJobs(JobQuery jobQuery);

    Observable<Response<List<Job>>> getCampaignJobs(JobQuery jobQuery);

    Observable<Response<Job>> getJob(JobQuery jobQuery);

    Observable<Response<List<JobMedia>>> getJobMedia(JobQuery jobQuery);

    Observable<Response<JobActionResponse>> createJob(JobCreateQuery jobCreateQuery);

    Observable<Response<JobActionResponse>> modifyJob(JobModifyQuery jobModifyQuery);

    Observable<Response<Void>> deleteJob(JobQuery jobQuery);

    Observable<Response<List<WorkflowMetaproperty>>> getAllWorkflowMetaproperties();

    Observable<Response<WorkflowMetaproperty>> getWorkflowMetaproperty(WorkflowMetapropertyQuery workflowMetapropertyQuery);

    Observable<Response<List<WorkflowUser>>> getAllWorkflowUsers();

    Observable<Response<List<WorkflowGroup>>> getAllWorkflowGroups();

    Observable<Response<WorkflowGroup>> getWorkflowGroup(WorkflowGroupQuery workflowGroupQuery);

    Observable<Response<WorkflowGroup>> createWorkflowGroup(WorkflowGroupDataQuery workflowGroupDataQuery);

    Observable<Response<WorkflowGroup>> updateWorkflowGroup(WorkflowGroupQuery workflowGroupQuery, WorkflowGroupDataQuery workflowGroupDataQuery);

    Observable<Response<Void>> deleteWorkflowGroup(WorkflowGroupQuery workflowGroupQuery);

    /**
     * Builder class used to create a new instance of {@link WorkflowService}.
     */
    class Builder {

        private Builder() {
        }

        public static WorkflowService create(final BynderApi bynderApi, final QueryDecoder queryDecoder) {
            return new WorkflowServiceImpl(bynderApi, queryDecoder);
        }
    }
}
