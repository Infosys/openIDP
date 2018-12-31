/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.controller.jobservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.infy.entities.triggerinputs.Steps;
import org.infy.entities.triggerinputs.TriggerInputs;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.businessapi.EmailSender;
import org.infy.idp.businessapi.JobsAdditionalInfo;
import org.infy.idp.businessapi.JobsBL;
import org.infy.idp.businessapi.JobsManagementBL;
import org.infy.idp.businessapi.SubscriptionBL;
import org.infy.idp.entities.jobs.DownloadArtifactInputs;
import org.infy.idp.entities.jobs.History;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.JobsBuilds;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.PipelineDetail;
import org.infy.idp.entities.jobs.Pipelines;
import org.infy.idp.entities.jobs.TestPlans;
import org.infy.idp.entities.jobs.TestSuits;
import org.infy.idp.entities.jobs.UserRolesPermissions;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.entities.models.ResourceResponse;
import org.infy.idp.entities.triggerparameter.ApproveBuildParams;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = JobService.class)
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ActiveProfiles("mvc")
public class JobServiceTest {

	@Autowired
	@InjectMocks
	private JobService jobService;

	@Mock
	private OAuth2Authentication authBean;

	@Mock
	private JobsBL jobsBLBean;
	@Mock
	private JobsManagementBL jobsmgmtBL;
	@Mock
	private JobsAdditionalInfo jobsaddInfo;
	
	@Mock
	private EmailSender emailBean;

	@Mock
	private SubscriptionBL subscriptionBL;

	/**
	 * executes before test
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * executes after test
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testGetJobsWhenNoPipelinesAreRetrieved() {
		Pipelines pipelines = new Pipelines();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBLBean.getpipelinesByAppNameAndUser("App_0101", authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(pipelines);

		resourceResponse = jobService.getJobs("IDP", "1", "App_0101", authBean);
		assertEquals("No pipeline to show", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetJobsWhenPipelinesAreRetrieved() {
		Pipelines pipelines = new Pipelines();
		List<Pipeline> pipList = new ArrayList<Pipeline>();
		Pipeline pipObj = new Pipeline();
		pipList.add(pipObj);
		pipelines.setPipelines(pipList);
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBLBean.getpipelinesByAppNameAndUser("App_0101", authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(pipelines);

		resourceResponse = jobService.getJobs("IDP", "1", "App_0101", authBean);
		assertEquals("CzcuJBbQSWwHIC8lJegrWzeY7BShXKunm3GeXQ/cdTE=", resourceResponse.getResource());
		assertNotEquals("No pipeline to show", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetExistingAppsWhenNoApplicationsArePresent() {
		Applications apps = new Applications();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBLBean.getExistingApps(authBean.getPrincipal().toString().toLowerCase())).thenReturn(apps);

		resourceResponse = jobService.getExistingApps("IDP", "1", authBean);
		assertEquals("No Application found!!", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetExistingAppsWhenApplicationsArePresent() {
		Applications apps = new Applications();
		Application app = new Application();
		List<Application> appList = new ArrayList<>();
		appList.add(app);
		apps.setApplications(appList);
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBLBean.getExistingApps(authBean.getPrincipal().toString().toLowerCase())).thenReturn(apps);

		resourceResponse = jobService.getExistingApps("IDP", "1", authBean);
		assertNotEquals("No Application found!!", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testSubmitJobForValidJob() {
		String idp = "CzcuJBbQSWwHIC8lJegrWzeY7BShXKunm3GeXQ/cdTE=";
		IDPJob idpdata = new IDPJob();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBLBean.submitJob(idpdata, authBean.getPrincipal().toString().toLowerCase())).thenReturn("");

		resourceResponse = jobService.submitJob("IDP", "1", idp, authBean);
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testApproveJobsForValidJob() {
		ApproveBuildParams approveBuildParams = new ApproveBuildParams();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBLBean.apprRejectJobs(approveBuildParams, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn("");

		resourceResponse = jobService.approveJobs(approveBuildParams, authBean);
		assertEquals("success", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testTriggerJobsForValidJob() {
		TriggerParameters triggerParameters = new TriggerParameters();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBLBean.triggerJobs(triggerParameters, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn("");

		resourceResponse = jobService.triggerJobs(triggerParameters, authBean);
		assertEquals("success", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

//	@Test
//	public void testTriggerIntervalForValidJob() {
//		TriggerInterval triggerInterval = new TriggerInterval();
//		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
//		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
//		Mockito.when(jobsBLBean.submitInterval(triggerInterval, authBean.getPrincipal().toString().toLowerCase()))
//				.thenReturn("");
//
//		resourceResponse = jobService.triggerInterval(triggerInterval, authBean);
//		assertEquals("SUCCESS", resourceResponse.getStatus());
//		assertNull(resourceResponse.getErrorMessage());
//	}

	@Test
	public void testCheckAvailableJobsToTriggerForNoAvailablePipelines() {
		History history = new History();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.checkAvailableJobsToTrigger(authBean.getPrincipal().toString().toLowerCase(), "IDP"))
				.thenReturn(history);

		resourceResponse = jobService.checkAvailableJobsToTrigger(authBean, "IDP");
		assertEquals("No pipelines to trigger", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testCheckAvailableJobsToTriggerForAvailablePipelines() {
		History history = new History();
		PipelineDetail pipeline = new PipelineDetail();
		List<PipelineDetail> pipeList = new ArrayList<>();
		pipeList.add(pipeline);
		history.setPipelineDetails(pipeList);
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.checkAvailableJobsToTrigger(authBean.getPrincipal().toString().toLowerCase(), "IDP"))
				.thenReturn(history);

		resourceResponse = jobService.checkAvailableJobsToTrigger(authBean, "IDP");
		assertNotEquals("No pipelines to trigger", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testTriggerInputsForAvailablePipelines() {
		TriggerJobName tiggerJobName = new TriggerJobName();
		TriggerInputs triggerInputs = new TriggerInputs();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		try {
			Mockito.when(jobsmgmtBL.fecthTriggerOptions(tiggerJobName)).thenReturn(triggerInputs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		resourceResponse = jobService.triggerInputs(tiggerJobName, authBean);
		assertEquals("{}", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetNumberOfBuildsForNoAvailablePipelines() {
		TriggerJobName triggerJobName = new TriggerJobName();
		JobsBuilds jobsBuilds = new JobsBuilds();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.getBuildJobs(triggerJobName)).thenReturn(jobsBuilds);

		resourceResponse = jobService.getNumberOfBuilds("1", triggerJobName, authBean);
		assertEquals("No Access", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetNumberOfBuildsForAvailablePipelines() {
		TriggerJobName triggerJobName = new TriggerJobName();
		JobsBuilds jobsBuilds = new JobsBuilds();
		jobsBuilds.setJobName("jobName");
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.getBuildJobs(triggerJobName)).thenReturn(jobsBuilds);

		resourceResponse = jobService.getNumberOfBuilds("1", triggerJobName, authBean);
		assertEquals("{\"jobName\":\"jobName\"}", resourceResponse.getResource());
		assertNotEquals("No Access", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetPipelineDetailsForNoAvailablePipelines() {
		TriggerJobName triggerJobName = new TriggerJobName();
		Pipeline pipeline = new Pipeline();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		try {
			Mockito.when(jobsmgmtBL.getPipelineDetails(triggerJobName)).thenReturn(pipeline);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		resourceResponse = jobService.getPipelineDetails("IDP", "1", triggerJobName, authBean);
		assertEquals("No Access", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetPipelineDetailsForAvailablePipelines() {
		TriggerJobName triggerJobName = new TriggerJobName();
		Pipeline pipeline = new Pipeline();
		pipeline.setPipelineName("pipelineName");
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		try {
			Mockito.when(jobsmgmtBL.getPipelineDetails(triggerJobName)).thenReturn(pipeline);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		resourceResponse = jobService.getPipelineDetails("IDP", "1", triggerJobName, authBean);
		assertEquals("iBN0OIOipN9Khknq//acVQcCINCKX1/pLOV967q70Y4=", resourceResponse.getResource());
		assertNotEquals("No Access", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testDownloadArtifactForAvailableArtifacts() {
		DownloadArtifactInputs downloadArtifactsInputs = new DownloadArtifactInputs();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.downloadArtifacts(downloadArtifactsInputs)).thenReturn("Response");

		resourceResponse = jobService.downloadArtifact("1", downloadArtifactsInputs, authBean);
		assertEquals("Response", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testDeletePipelineForSuccessfulDeletion() {
		TriggerJobName triggerJobName = new TriggerJobName();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.deletePipeline(triggerJobName)).thenReturn(true);

		resourceResponse = jobService.deletePipeline("1", triggerJobName, authBean);
		assertEquals("success", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testDeletePipelineForUnsuccessfulDeletion() {
		TriggerJobName triggerJobName = new TriggerJobName();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.deletePipeline(triggerJobName)).thenReturn(false);

		resourceResponse = jobService.deletePipeline("1", triggerJobName, authBean);
		assertEquals("error", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testJobCreationSuccessMailForSuccess() {
		TriggerJobName triggerJobName = new TriggerJobName();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(emailBean.jobCreationSuccessMail(triggerJobName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(true);

		resourceResponse = jobService.jobCreationSuccessMail("1", triggerJobName, authBean);
		assertEquals("success", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testJobCreationSuccessMailForFailure() {
		TriggerJobName triggerJobName = new TriggerJobName();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(emailBean.jobCreationSuccessMail(triggerJobName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(false);

		resourceResponse = jobService.jobCreationSuccessMail("1", triggerJobName, authBean);
		assertEquals("error", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetPipelineNamesForApplicationForNoPipelinesAvailable() {
		String appName = "appName";
		String workFlowString = "workFlow";
		Names names = new Names();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.pipelineNamesForApplication(appName, workFlowString,
				authBean.getPrincipal().toString().toLowerCase())).thenReturn(names);

		resourceResponse = jobService.getPipelineNamesForApplication(appName, workFlowString, authBean);
		assertEquals("No Pipelines", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetPipelineNamesForApplicationForPipelinesAvailable() {
		String appName = "appName";
		String workFlowString = "workFlow";
		Names names = new Names();
		List<String> namesList = new ArrayList<>();
		namesList.add("pipeline");
		names.setNames(namesList);
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.pipelineNamesForApplication(appName, workFlowString,
				authBean.getPrincipal().toString().toLowerCase())).thenReturn(names);

		resourceResponse = jobService.getPipelineNamesForApplication(appName, workFlowString, authBean);
		assertEquals("{\"names\":[\"pipeline\"]}", resourceResponse.getResource());
		assertNotEquals("No Pipelines", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetStageViewUrlForValidPipeline() {
		String appName = "appName";
		String pipName = "pipelineName";
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		try {
			Mockito.when(jobsaddInfo.getStageViewUrl(appName, pipName)).thenReturn("jenkinsUrl");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		resourceResponse = jobService.getStageViewUrl(appName, pipName, authBean);
		assertEquals("jenkinsUrl", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetDependencyJobsForNoPipeline() {
		String appName = "appName";
		Pipelines pips = new Pipelines();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.getDependencyPipelines(appName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(pips);

		resourceResponse = jobService.getDependencyJobs("1", appName, authBean);
		assertEquals("No pipeline to show", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetDependencyJobsForAvailablePipelines() {
		String appName = "appName";
		Pipelines pips = new Pipelines();
		List<Pipeline> pipList = new ArrayList<>();
		Pipeline pip = new Pipeline();
		pipList.add(pip);
		pips.setPipelines(pipList);
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.getDependencyPipelines(appName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(pips);

		resourceResponse = jobService.getDependencyJobs("1", appName, authBean);
		assertEquals("CzcuJBbQSWwHIC8lJegrWzeY7BShXKunm3GeXQ/cdTE=", resourceResponse.getResource());
		assertNotEquals("No pipeline to show", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testTriggerSteps() {
		String appName = "appName";
		String pipName = "pipelineName";
		String envName = "envName";
		Steps steps = new Steps();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		try {
			Mockito.when(jobsaddInfo.fecthTriggerSteps(appName, pipName, envName)).thenReturn(steps);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		resourceResponse = jobService.triggerSteps(appName, pipName, envName, authBean);
		assertEquals("{}", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetJobParamDetails() {
		String appName = "appName";
		String pipName = "pipelineName";
		List<JobParam> jobparamList = new ArrayList<>();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		try {
			Mockito.when(jobsmgmtBL.getJobParamDetails(appName, pipName)).thenReturn(jobparamList);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		resourceResponse = jobService.getJobParamDetails(appName, pipName, authBean);
		assertEquals("[]", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}
	@Test
	public void testGetMTMTestPlans() {
		String appName = "appName";
		String pipName = "pipelineName";
		List<TestPlans> test = new ArrayList<>();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.fetchMTMTestPlans(appName, pipName)).thenReturn(test);

		resourceResponse = jobService.getMTMTestPlans(appName, pipName, authBean);
		assertEquals("[]", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetMTMTestSuits() {
		String appName = "appName";
		String pipName = "pipelineName";
		int testPlanId = 1;
		List<TestSuits> test = new ArrayList<>();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.fetchMTMTestSuits(testPlanId, appName, pipName)).thenReturn(test);

		resourceResponse = jobService.getMTMTestSuits(testPlanId, appName, pipName, authBean);
		assertEquals("[]", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetPairName() {
		TriggerJobName jobName = new TriggerJobName();
		Names names = new Names();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.getPairName(jobName)).thenReturn(names);

		resourceResponse = jobService.getPairName(jobName, authBean);
		assertEquals("{}", resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}

	@Test
	public void testGetPipelineRoles() {
		String appName = "appName";
		String pipName = "pipName";
		String userName = "user";
		UserRolesPermissions user = new UserRolesPermissions();
		List<String> pipelinePermissions = new ArrayList<>();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		try {
			Mockito.when(jobsaddInfo.getPipelinePermission(appName, pipName, userName)).thenReturn(pipelinePermissions);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		user.setPermissions(pipelinePermissions);

		resourceResponse = jobService.getPipelineRoles(appName, pipName, userName, authBean);
		assertEquals("0bWRVHtBODrmpi8/RsFVO/XpFgjoa/sie71x3S2ewRrTfgwVB5UxnPB4xVZKLw6p",
				resourceResponse.getResource());
		assertEquals("SUCCESS", resourceResponse.getStatus());
		assertNull(resourceResponse.getErrorMessage());
	}
}