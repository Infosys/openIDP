/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi.services;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.getjob.GetJob;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.jobs.basicinfo.AdditionalMailRecipients;
import org.infy.idp.entities.jobs.basicinfo.BasicInfo;
import org.infy.idp.entities.jobs.basicinfo.BuildInterval;
import org.infy.idp.entities.jobs.buildinfo.ArtifactToStage;
import org.infy.idp.entities.jobs.buildinfo.BuildInfo;
import org.infy.idp.entities.jobs.code.Code;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.entities.jobs.common.RunScript;
import org.infy.idp.entities.jobs.deployinfo.DeployInfo;
import org.infy.idp.entities.jobs.testinfo.TestInfo;
import org.infy.idp.entities.triggerparameter.Build;
import org.infy.idp.entities.triggerparameter.Deploy;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jtest.AppContext;

/**
 * JobDetailsInsertionServiceTest is a test class for JobDetailsInsertionService
 *
 * @see org.infy.idp.dataapi.services.JobDetailsInsertionService
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class JobDetailsInsertionServiceTest {

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;

	@Spy
	@InjectMocks
	private JobDetailsDL getJobDetails;
	@InjectMocks
	private JobDetailsInsertionService testedObject;

	/**
	 * Constructor for test class.
	 */
	public JobDetailsInsertionServiceTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method
	 * insertApplicationDetails(org.infy.idp.entities.jobs.applicationInfo.
	 * ApplicationInfo).
	 * 
	 */
	@Test
	public void testInsertApplicationDetails0() throws Throwable {
		ApplicationInfo appInfo = new ApplicationInfo();
		appInfo.setApplicationName("appInfo");
		appInfo.setDevelopers("appInfo_1");
		appInfo.setPipelineAdmins("appInfo_2");
		appInfo.setReleaseManager("appInfo_3");
		ArrayList environmentOwnerDetails = new ArrayList();
		appInfo.setEnvironmentOwnerDetails(environmentOwnerDetails);
		ArrayList slavesDetails = new ArrayList();
		appInfo.setSlavesDetails(slavesDetails);

		Integer result = testedObject.insertApplicationDetails(appInfo);

		// jtest.NoSuchValueException thrown
	}

	/**
	 * Test for method
	 * insertApplicationRoles(java.lang.String,java.lang.String,java.lang.String ).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * 
	 */
	@Test
	public void testInsertApplicationRoles0() throws Throwable {

		Integer result = testedObject.insertApplicationRoles("userId", "role", "appName");

		// jtest.NoSuchValueException thrown
	}

	/**
	 * Test for method
	 * insertBuildNumber(java.lang.String,java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * 
	 */
	@Test(expected = Exception.class)
	public void testInsertBuildNumber0() throws Throwable {

		Integer result = testedObject.insertBuildNumber("ApplicationNmae", "pipelineName", "buildnumber");

	}

	/**
	 * Test for method
	 * insertJobdetailsStatus(org.infy.idp.entities.getJob.GetJob,java.lang.Long
	 * ,java.lang.Long).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 * 
	 */
	@Test
	public void testInsertJobdetailsStatus0() throws Throwable {
		GetJob getJob = new GetJob();
		getJob.setJenkinsBuildNumber("string_0");
		getJob.setBuildNumber("string_1");
		getJob.setApplicationName("string_2");
		getJob.setPipelineName("string_3");
		Long ca = new Long(0L);
		Long ct = new Long(1L);

		Integer result = testedObject.insertJobdetailsStatus(getJob, ca, ct);

	}

	/**
	 * Test for method insertPipelineInfo(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * 
	 */
	@Test(expected = Exception.class)
	public void testInsertPipelineInfo0() throws Throwable {

		Long result = testedObject.insertPipelineInfo("pipelineName", "applicationName");

	}

	/**
	 * Test for method insertPipelineJsonDetails(org.infy.idp.entities.jobs.IDPJob).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 */
	@Test(expected = Exception.class)
	public void testInsertPipelineJsonDetails0() throws Throwable {
		IDPJob idp = new IDPJob();
		idp.setSsoName("idp");
		idp.setSsoId("idp_1");
		idp.setErrorCode("idp_2");
		BasicInfo basicInfo = new BasicInfo();
		basicInfo.setGroupName("idp_3");
		basicInfo.setGroupId("idp_4");
		basicInfo.setApplicationName("idp_5");
		basicInfo.setPipelineName("idp_6");
		basicInfo.setBuildServerOS("idp_7");
		basicInfo.setEngine("idp_8");
		BuildInterval buildInterval = new BuildInterval();
		buildInterval.setPollSCM("idp_9");
		buildInterval.setBuildInterval("idp_10");
		buildInterval.setBuildIntervalValue("idp_11");
		basicInfo.setBuildInterval(buildInterval);
		AdditionalMailRecipients additionalMailRecipients = new AdditionalMailRecipients();
		additionalMailRecipients.setApplicationTeam("idp_12");
		additionalMailRecipients.setEmailIds("idp_13");
		basicInfo.setAdditionalMailRecipients(additionalMailRecipients);
		idp.setBasicInfo(basicInfo);
		Code code = new Code();
		ArrayList buildScript = new ArrayList();
		code.setBuildScript(buildScript);
		code.setCategory("idp_14");
		code.setTechnology("idp_15");
		ArrayList scm = new ArrayList();
		code.setScm(scm);
		idp.setCode(code);
		BuildInfo buildInfo = new BuildInfo();
		buildInfo.setJavaModules("idp_18");
		buildInfo.setEjbModules("idp_19");
		buildInfo.setWebModules("idp_20");
		buildInfo.setNexusType("idp_21");
		buildInfo.setNexusUrl("idp_22");
		buildInfo.setUserName("idp_23");
		buildInfo.setPassword("idp_24");
		RunScript postBuildScript = new RunScript();
		postBuildScript.setScriptType("idp_25");
		postBuildScript.setFlattenFilePath("idp_26");
		postBuildScript.setPort(20);
		postBuildScript.setHost("idp_27");
		postBuildScript.setUserName("idp_28");
		postBuildScript.setPassword("idp_29");
		postBuildScript.setScript("idp_30");
		postBuildScript.setPathToFiles("idp_31");
		postBuildScript.setDestinationDir("idp_32");
		postBuildScript.setArchiveLogs("idp_33");
		postBuildScript.setTool("idp_34");
		postBuildScript.setScriptFilePath("idp_35");
		postBuildScript.setTargets("idp_36");
		buildInfo.setPostBuildScript(postBuildScript);
		buildInfo.setBuildtool("idp_37");
		ArtifactToStage artifactToStage = new ArtifactToStage();
		artifactToStage.setFlattenFileStructure("idp_38");
		artifactToStage.setArtifact("idp_39");
		buildInfo.setArtifactToStage(artifactToStage);

		ArrayList modules = new ArrayList();
		buildInfo.setModules(modules);
		idp.setBuildInfo(buildInfo);
		DeployInfo deployInfo = new DeployInfo();
		ArrayList deployEnv = new ArrayList();
		deployInfo.setDeployEnv(deployEnv);
		idp.setDeployInfo(deployInfo);
		TestInfo testInfo = new TestInfo();
		ArrayList testEnv = new ArrayList();
		testInfo.setTestEnv(testEnv);
		idp.setTestInfo(testInfo);

		Integer result = testedObject.insertPipelineJsonDetails(idp, "active");

	}

	/**
	 * Test for method
	 * insertSlaveDetails(org.infy.idp.entities.jobs.applicationInfo.
	 * SlavesDetail,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 */
	@Test(expected = Exception.class)
	public void testInsertSlaveDetails0() throws Throwable {
		SlavesDetail slave = new SlavesDetail();
		slave.setSlaveName("string_0");
		slave.setBuildServerOS("string_1");
		slave.setWorkspacePath("string_2");
		slave.setCreateNewSlave("string_3");
		slave.setLabels("string_4");
		slave.setSSHKeyPath("string_5");
		slave.setSlaveUsage("string_6");
		// Database of testServerHost is used for testing
		Integer result = testedObject.insertSlaveDetails(slave, "DemoAppT");

	}

	/**
	 * Test for method insertTriggerHistory(org.infy.idp.entities.triggerparameter.
	 * TriggerParameters,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 * 
	 */
	@Test
	public void testInsertTriggerHistory0() throws Throwable {
		List<String> testSteps = new ArrayList<>();
		testSteps.add("string_3");
		TriggerParameters triggerParameters = new TriggerParameters();
		triggerParameters.setDashBoardLink("string_0");
		triggerParameters.setErrorCode("string_1");
		triggerParameters.setJobBuildId("string_2");
		triggerParameters.setTestStep((ArrayList<String>) testSteps);
		triggerParameters.setEmailed("string_4");
		triggerParameters.setApplicationName("string_5");
		triggerParameters.setPipelineName("string_6");
		triggerParameters.setReleaseNumber("string_7");
		triggerParameters.setUserName("string_8");
		triggerParameters.setSlaveName("string_9");
		triggerParameters.setEnvSelected("string_10");
		Build build = new Build();
		build.setBranchSelected("string_11");
		ArrayList module = new ArrayList();
		build.setModule(module);
		triggerParameters.setBuild(build);
		Deploy deploy = new Deploy();
		deploy.setVersion("string_12");
		deploy.setArtifactID("string_13");
		deploy.setNexusId("string_14");
		triggerParameters.setDeploy(deploy);
		triggerParameters.setTestSelected("string_15");

		Integer result = testedObject.insertTriggerHistory(triggerParameters);

	}

	/**
	 * Test for method
	 * insertUsers(java.lang.String,java.lang.String,java.lang.Boolean).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * 
	 */
	@Test
	public void testInsertUsers0() throws Throwable {
		Boolean status = new Boolean(true);

		Integer result = testedObject.insertUsers("userId", "emailId", status, 1L);

		// jtest.NoSuchValueException thrown
	}

	/**
	 * Test for method
	 * updateJobdetailsStatus(org.infy.idp.entities.getJob.GetJob,java.lang.Long
	 * ,java.lang.Long).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * 
	 */
	@Test
	public void testUpdateJobdetailsStatus0() throws Throwable {
		GetJob getJob = new GetJob();
		getJob.setJenkinsBuildNumber("string_0");
		getJob.setBuildNumber("string_1");
		getJob.setApplicationName("string_2");
		getJob.setPipelineName("string_3");
		Long ca = new Long(0L);
		Long ct = new Long(1L);

		Integer result = testedObject.updateJobdetailsStatus(getJob, ca, ct);

	}

	/**
	 * Test for method
	 * updateapplicationDetail(org.infy.idp.entities.jobs.applicationInfo.
	 * ApplicationInfo).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 */
	@Test
	public void testUpdateapplicationDetail0() throws Throwable {
		ApplicationInfo appInfo = new ApplicationInfo();
		appInfo.setApplicationName("appInfo");
		appInfo.setDevelopers("appInfo_1");
		appInfo.setPipelineAdmins("appInfo_2");
		appInfo.setReleaseManager("appInfo_3");
		ArrayList environmentOwnerDetails = new ArrayList();
		appInfo.setEnvironmentOwnerDetails(environmentOwnerDetails);
		ArrayList slavesDetails = new ArrayList();
		appInfo.setSlavesDetails(slavesDetails);

		Integer result = testedObject.updateapplicationDetail(appInfo);

	}

	@Test
	public void testInsertOrgInfo() throws Throwable {
		long result = testedObject.insertOrgInfo("Infy", "infosys.com");
	}

	@Test
	public void testInsertUsers() throws Throwable {
		int result = testedObject.insertUsers("admin", "admin@domain.com", true, 1L, "developer");
	}

	@Test
	public void testInsertMultipleApplicationRoles() throws Throwable {
		List<String> userIdList = new ArrayList<>();
		userIdList.add("admin1");
		userIdList.add("admin2");
		int result = testedObject.insertMultipleApplicationRoles(userIdList, "developer", "DemoAppT");
	}

	@Test
	public void testInsertAdditionalJobParamDetails() throws Throwable {
		JobParam jobParam = new JobParam();
		jobParam.setJobParamName("Path1");
		jobParam.setJobParamSatic(false);
		jobParam.setJobParamValue("repository/path");
		jobParam.setJobType("string");
		int result = testedObject.insertAdditionalJobParamDetails(jobParam, "DemoAppT", "TC1_Maven");
	}

	@Test
	public void testDeleteAdditionalJobParamDetails() throws Throwable {
		testedObject.deleteAdditionalJobParamDetails(1L);
	}

	@Test
	public void testAddNotification() throws Throwable {
		testedObject.addNotification("TC1_Maven", "SUCESS", "userName");

	}

	@Test
	public void testUpdateSlaveDetails() throws Throwable {
		GetJob getJob = new GetJob();
		SlavesDetail slv = new SlavesDetail();
		slv.setBuildServerOS("linux");
		slv.setCreateNewSlave("off");
		slv.setBuild("on");
		slv.setDeploy("on");
		slv.setTest("on");
		slv.setSlaveName("dslave");
		slv.setLabels("dslave");

		Integer result = testedObject.updateSlaveDetails(slv, "DemoAppT");

	}

	
	/**
	 * Used to set up the test. This method is called by JUnit before each of the
	 * tests are executed.
	 */
	@Before
	public void setUp() throws Exception {
		/*
		 * Add any necessary initialization code here (e.g., open a socket). Call
		 * Repository.putTemporary() to provide initialized instances of objects to be
		 * used when testing.
		 */

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java JobDetailsInsertionServiceTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.dataapi.services.JobDetailsInsertionServiceTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class getTestedClass() {
		return JobDetailsInsertionService.class;
	}
}
