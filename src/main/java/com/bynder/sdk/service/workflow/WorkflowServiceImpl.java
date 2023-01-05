package com.bynder.sdk.service.workflow;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.model.workflow.*;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.workflow.*;
import io.reactivex.Observable;
import retrofit2.Response;

import java.util.List;
import java.util.Map;

public class WorkflowServiceImpl implements WorkflowService {

	private final BynderApi bynderApi;

	private final QueryDecoder queryDecoder;

	public WorkflowServiceImpl(final BynderApi bynderApi, final QueryDecoder queryDecoder) {
		this.bynderApi = bynderApi;
		this.queryDecoder = queryDecoder;
	}

	@Override
	public Observable<Response<List<Campaign>>> getAllCampaigns() {
		return bynderApi.getAllCampaigns();
	}

	@Override
	public Observable<Response<Campaign>> getCampaign(CampaignQuery campaignQuery) {
		return bynderApi.getCampaign(campaignQuery.getCampaignId());
	}

	@Override
	public Observable<Response<CampaignId>> createCampaign(CampaignDataQuery campaignDataQuery) {
		return bynderApi.createCampaign(campaignDataQuery);
	}

	@Override
	public Observable<Response<CampaignId>> modifyCampaign(CampaignQuery campaignQuery, CampaignDataQuery campaignDataQuery) {
		return bynderApi.modifyCampaign(campaignQuery.getCampaignId(), campaignDataQuery);
	}

	@Override
	public Observable<Response<Void>> deleteCampaign(CampaignQuery campaignQuery) {
		return bynderApi.deleteCampaign(campaignQuery.getCampaignId());
	}

	@Override
	public Observable<Response<JobPreset>> getJobPreset(JobPresetQuery jobPresetQuery) {
		return bynderApi.getJobPreset(jobPresetQuery.getId());
	}

	@Override
	public Observable<Response<List<Job>>> getJobs(JobQuery jobQuery) {
		Map<String, String> params = queryDecoder.decode(jobQuery);
		return bynderApi.getJobs(params);
	}

	@Override
	public Observable<Response<List<Job>>> getCampaignJobs(JobQuery jobQuery) {
		Map<String, String> params = queryDecoder.decode(jobQuery);
		return bynderApi.getCampaignJobs(jobQuery.getId(), params);
	}

	@Override
	public Observable<Response<Job>> getJob(JobQuery jobQuery) {
		return bynderApi.getJob(jobQuery.getId());
	}

	@Override
	public Observable<Response<List<JobMedia>>> getJobMedia(JobQuery jobQuery) {
		return bynderApi.getJobMedia(jobQuery.getId());
	}

	@Override
	public Observable<Response<JobActionResponse>> createJob(JobCreateQuery jobCreateQuery) {
		return bynderApi.createJob(jobCreateQuery);
	}

	@Override
	public Observable<Response<JobActionResponse>> modifyJob(JobModifyQuery jobModifyQuery) {
		return bynderApi.modifyJob(jobModifyQuery.getId(), jobModifyQuery);
	}

	@Override
	public Observable<Response<Void>> deleteJob(JobQuery jobQuery) {
		return bynderApi.deleteJob(jobQuery.getId());
	}

	@Override
	public Observable<Response<List<WorkflowMetaproperty>>> getAllWorkflowMetaproperties() {
		return bynderApi.getAllWorkflowMetaproperties();
	}

	@Override
	public Observable<Response<WorkflowMetaproperty>> getWorkflowMetaproperty(
			WorkflowMetapropertyQuery workflowMetapropertyQuery) {
		return bynderApi.getWorkflowMetaproperty(workflowMetapropertyQuery.getMetapropertyId());
	}

	@Override
	public Observable<Response<List<WorkflowUser>>> getAllWorkflowUsers() {
		return bynderApi.getAllWorkflowUsers();
	}

	@Override
	public Observable<Response<List<WorkflowGroup>>> getAllWorkflowGroups() {
		return bynderApi.getAllWorkflowGroups();
	}

	@Override
	public Observable<Response<WorkflowGroup>> getWorkflowGroup(WorkflowGroupQuery workflowGroupQuery) {
		return bynderApi.getWorkflowGroup(workflowGroupQuery.getGroupId());
	}

	@Override
	public Observable<Response<WorkflowGroup>> createWorkflowGroup(WorkflowGroupDataQuery workflowGroupDataQuery) {
		return bynderApi.createWorkflowGroup(workflowGroupDataQuery);
	}

	@Override
	public Observable<Response<WorkflowGroup>> updateWorkflowGroup(WorkflowGroupQuery workflowGroupQuery, WorkflowGroupDataQuery workflowGroupDataQuery) {
		return bynderApi.updateWorkflowGroup(workflowGroupQuery.getGroupId(), workflowGroupDataQuery);
	}

	@Override
	public Observable<Response<Void>> deleteWorkflowGroup(WorkflowGroupQuery workflowGroupQuery) {
		return bynderApi.deleteWorkflowGroup(workflowGroupQuery.getGroupId());
	}
}
