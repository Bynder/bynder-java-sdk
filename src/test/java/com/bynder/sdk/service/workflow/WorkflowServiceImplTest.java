package com.bynder.sdk.service.workflow;

import com.bynder.sdk.api.BynderApi;
import com.bynder.sdk.query.decoder.QueryDecoder;
import com.bynder.sdk.query.workflow.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WorkflowServiceImplTest {

    public static String EMPTY_STRING = "";

    @Mock
    private QueryDecoder queryDecoder;
    @Mock
    private BynderApi bynderApi;
    private WorkflowService workflowService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        workflowService = WorkflowService.Builder.create(bynderApi, queryDecoder);
    }

    @Test
    public void getAllCampaigns() {
        workflowService.getAllCampaigns();

        verify(bynderApi, times(1)).getAllCampaigns();
    }

    @Test
    public void getCampaign() {
        CampaignQuery campaignQuery = new CampaignQuery(EMPTY_STRING);
        workflowService.getCampaign(campaignQuery);

        verify(bynderApi, times(1)).getCampaign(anyString());
    }

    @Test
    public void createCampaign() {
        CampaignDataQuery campaignDataQuery = new CampaignDataQuery(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
        workflowService.createCampaign(campaignDataQuery);

        verify(bynderApi, times(1)).createCampaign(campaignDataQuery);
    }

    @Test
    public void updateCampaign() {
        CampaignQuery campaignQuery = new CampaignQuery(EMPTY_STRING);
        CampaignDataQuery campaignDataQuery = new CampaignDataQuery(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
        workflowService.modifyCampaign(campaignQuery, campaignDataQuery);

        verify(bynderApi, times(1)).modifyCampaign(EMPTY_STRING, campaignDataQuery);
    }

    @Test
    public void deleteCampaign() {
        CampaignQuery campaignQuery = new CampaignQuery(EMPTY_STRING);
        workflowService.deleteCampaign(campaignQuery);

        verify(bynderApi, times(1)).deleteCampaign(anyString());
    }

    @Test
    public void getJobPreset() {
        JobPresetQuery jobPresetQuery = new JobPresetQuery(EMPTY_STRING);
        workflowService.getJobPreset(jobPresetQuery);

        verify(bynderApi, times(1)).getJobPreset(anyString());
    }

    @Test
    public void getJobs() {
        JobQuery jobQuery = new JobQuery();
        workflowService.getJobs(jobQuery);

        verify(bynderApi, times(1)).getJobs(anyMap());
        verify(queryDecoder, times(1)).decode(jobQuery);
    }

    @Test
    public void getCampaignJobs() {
        JobQuery jobQuery = new JobQuery(EMPTY_STRING);
        workflowService.getCampaignJobs(jobQuery);

        verify(bynderApi, times(1)).getCampaignJobs(anyString(), anyMap());
        verify(queryDecoder, times(1)).decode(jobQuery);
    }

    @Test
    public void getJob() {
        JobQuery jobQuery = new JobQuery(EMPTY_STRING);
        workflowService.getJob(jobQuery);

        verify(bynderApi, times(1)).getJob(anyString());
    }

    @Test
    public void getJobMedia() {
        JobQuery jobQuery = new JobQuery(EMPTY_STRING);
        workflowService.getJobMedia(jobQuery);

        verify(bynderApi, times(1)).getJobMedia(anyString());
    }

    @Test
    public void createJob() {
        JobCreateQuery jobCreateQuery = new JobCreateQuery(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
        workflowService.createJob(jobCreateQuery);

        verify(bynderApi, times(1)).createJob(jobCreateQuery);
    }

    @Test
    public void modifyJob() {
        JobModifyQuery jobModifyQuery = new JobModifyQuery(EMPTY_STRING);
        workflowService.modifyJob(jobModifyQuery);

        verify(bynderApi, times(1)).modifyJob(EMPTY_STRING, jobModifyQuery);
    }

    @Test
    public void deleteJob() {
        JobQuery jobQuery = new JobQuery(EMPTY_STRING);
        workflowService.deleteJob(jobQuery);

        verify(bynderApi, times(1)).deleteJob(EMPTY_STRING);
    }

    @Test
    public void getAllWorkflowMetaproperties() {
        workflowService.getAllWorkflowMetaproperties();

        verify(bynderApi, times(1)).getAllWorkflowMetaproperties();
    }

    @Test
    public void getWorkflowMetaproperty() {
        WorkflowMetapropertyQuery workflowMetapropertyQuery = new WorkflowMetapropertyQuery(EMPTY_STRING);
        workflowService.getWorkflowMetaproperty(workflowMetapropertyQuery);

        verify(bynderApi, times(1)).getWorkflowMetaproperty(anyString());
    }

    @Test
    public void getAllWorkflowUsers() {
        workflowService.getAllWorkflowUsers();

        verify(bynderApi, times(1)).getAllWorkflowUsers();
    }

    @Test
    public void getAllWorkflowGroups() {
        workflowService.getAllWorkflowGroups();

        verify(bynderApi, times(1)).getAllWorkflowGroups();
    }

    @Test
    public void getWorkflowGroup() {
        WorkflowGroupQuery workflowGroupQuery = new WorkflowGroupQuery(EMPTY_STRING);
        workflowService.getWorkflowGroup(workflowGroupQuery);

        verify(bynderApi, times(1)).getWorkflowGroup(anyString());
    }

    @Test
    public void createWorkflowGroup() {
        WorkflowGroupDataQuery workflowGroupDataQuery = new WorkflowGroupDataQuery(EMPTY_STRING);
        workflowService.createWorkflowGroup(workflowGroupDataQuery);

        verify(bynderApi, times(1)).createWorkflowGroup(workflowGroupDataQuery);
    }

    @Test
    public void updateWorkflowGroup() {
        WorkflowGroupQuery workflowGroupQuery = new WorkflowGroupQuery(EMPTY_STRING);
        WorkflowGroupDataQuery workflowGroupDataQuery = new WorkflowGroupDataQuery(EMPTY_STRING);
        workflowService.updateWorkflowGroup(workflowGroupQuery, workflowGroupDataQuery);

        verify(bynderApi, times(1)).updateWorkflowGroup(EMPTY_STRING, workflowGroupDataQuery);
    }

    @Test
    public void deleteWorkflowGroup() {
        WorkflowGroupQuery workflowGroupQuery = new WorkflowGroupQuery(EMPTY_STRING);
        workflowService.deleteWorkflowGroup(workflowGroupQuery);

        verify(bynderApi, times(1)).deleteWorkflowGroup(anyString());
    }
}
