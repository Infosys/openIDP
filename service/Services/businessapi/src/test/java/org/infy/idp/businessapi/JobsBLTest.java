/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.infy.entities.triggerinputs.Steps;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.dataapi.services.DeleteInfo;
import org.infy.idp.dataapi.services.JobDetailsDL;
import org.infy.idp.dataapi.services.JobDetailsInsertionService;
import org.infy.idp.dataapi.services.UpdateJobDetails;
import org.infy.idp.entities.jobs.AppNames;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.Pipelines;
import org.infy.idp.entities.jobs.SubApplication;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.jobs.basicinfo.AdditionalMailRecipients;
import org.infy.idp.entities.jobs.basicinfo.BasicInfo;
import org.infy.idp.entities.jobs.basicinfo.BuildInterval;
import org.infy.idp.entities.jobs.buildinfo.ArtifactToStage;
import org.infy.idp.entities.jobs.buildinfo.BuildInfo;
import org.infy.idp.entities.jobs.buildinfo.Module;
import org.infy.idp.entities.jobs.code.Code;
import org.infy.idp.entities.jobs.code.Scm;
import org.infy.idp.entities.jobs.common.Notification;
import org.infy.idp.entities.jobs.common.RunScript;
import org.infy.idp.entities.jobs.deployinfo.DeployEnv;
import org.infy.idp.entities.jobs.deployinfo.DeployInfo;
import org.infy.idp.entities.jobs.deployinfo.DeployStep;
import org.infy.idp.entities.jobs.testinfo.TestEnv;
import org.infy.idp.entities.jobs.testinfo.TestInfo;
import org.infy.idp.entities.jobs.testinfo.TestStep;
import org.infy.idp.entities.triggerparameter.Build;
import org.infy.idp.entities.triggerparameter.Deploy;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.infy.idp.utils.BuildService;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.JenkinsCLI;
import org.infy.idp.utils.TriggerBuilds;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

import jtest.AppContext;

/**
 * JobsBLTest is a test class for JobsBL
 *
 * @see org.infy.idp.businessapi.JobsBL
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class JobsBLTest {

	@Mock
	private EnvironmentBL environmentBL;
	@Mock
	private KafkaTemplate<String, String> kafkaTemplate;
	@InjectMocks
	private JobsBL jobsBL;
	@InjectMocks
	private JobsManagementBL jobsmgmtBL;
	@InjectMocks
	private JobsAdditionalInfo jobsaddInfo;

	@Spy
	private FetchJobDetails fetchJobDetails;
	@Spy
	private DeleteInfo delinfo;
	@Spy
	private TriggerDetailBL getTriggerDetails;
	@Spy
	@InjectMocks
	private JobDetailsInsertionService jobDetailsInsertion;
	@Spy
	private UpdateJobDetails updateJobDetails;

	@Spy
	private TriggerBuilds triggerBuilds;
	@Spy
	private JenkinsCLI cli;

	@Spy
	@InjectMocks
	private JobDetailsDL getJobDetails;

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;

	@Spy
	private BuildService buildService;

	private IDPJob idpjson;

	private TriggerParameters triggerParameters;

	private ApplicationInfo app;

	public JobsBLTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testTriggerJobs() throws Throwable {
		assertEquals("SUCESS", jobsBL.triggerJobs(triggerParameters, "idpadmin"));
	}

	@Test(expected = NullPointerException.class)
	public void testInsertApplicationDetails() throws Throwable {
		jobsmgmtBL.insertApplicationDetails(app, "idpadmin", "INFOSYS");
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateApp() throws Throwable {
		jobsmgmtBL.updateApp(app, "idpadmin", "INFOSYS");

	}

	@Test
	public void testgetReleaseInfo() {
		List<String> list = jobsBL.getReleaseInfo("DemoAppT");
		assertNotNull(list);
		assertNotEquals(0, list.size());

	}

	@Test
	public void testGetReleaseNumber() {
		List<String> pipelineList = new ArrayList<>();
		pipelineList.add("JFrogTest1");
		Set<String> set = jobsmgmtBL.getReleaseNumber("DemoAppT", "JFrogTest1", pipelineList);
		assertNotNull(set);

	}

	@Test
	public void testGetJobJSON() throws Throwable {
		jobsmgmtBL.getJobJSON("JFrogTest1", "job");
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadArtifacts() throws Throwable {
		jobsaddInfo.downloadArtifacts(null);
	}

	@Test
	public void testSubmitJob() throws Throwable {

		Gson gson = new Gson();

		String json = "{\"basicInfo\":{\"applicationName\":\"DemoAppT\",\"pipelineName\":\"TC2_Maven\",\"buildServerOS\":\"windows\",\"masterSequence\":\"pipeline\",\"engine\":\"Jenkins Workflow\",\"buildInterval\":{\"pollSCM\":\"off\",\"buildInterval\":\"\",\"buildIntervalValue\":\"0\",\"buildAtSpecificInterval\":\"off\",\"event\":[{\"type\":\"--Select--\",\"hour\":\"00\",\"minute\":\"00\"}]},\"additionalMailRecipients\":{\"applicationTeam\":\"\",\"emailIds\":\"\"},\"userName\":\"idpadmin\",\"pipelineStatus\":\"create\",\"customPipelineAdmins\":[]},\"code\":{\"category\":\"Standard\",\"technology\":\"J2EE/Maven\",\"scm\":[{\"type\":\"git\",\"url\":\"http://xyz.com/idpadmin/JPetStore_Maven.git\",\"userName\":\"idpadmin\",\"password\":\"dummyuser\",\"repositoryBrowser\":\"gitLab\",\"browserUrl\":\"http://xyz.com\",\"projectName\":\"\",\"branch\":\"master\",\"projPath\":\"\",\"moduleName\":\"\",\"clearcaseType\":\"\",\"vobName\":\"\",\"snapshotViewName\":\"\",\"configSpec\":\"\",\"developmentStreamName\":\"\",\"buildConfiguration\":\"\",\"buildDefinition\":\"\",\"repositoryWorkspace\":\"\",\"projArea\":\"\",\"port\":\"\",\"version\":\"default\",\"exclude\":\"\",\"proxy\":\"\",\"proxyPort\":\"\",\"appRepo\":\"on\",\"deployRepo\":\"\",\"testRepo\":\"\"}],\"jobParam\":[],\"buildScript\":[{\"tool\":\"\"},{\"tool\":\"\"},{}],\"subApplication\":\"\"},\"buildInfo\":{\"buildtool\":\"maven\",\"artifactToStage\":{\"artifactRepo\":{},\"artifactRepoName\":\"na\"},\"castAnalysis\":{},\"modules\":[{\"moduleName\":\"JPetStore\",\"relativePath\":\"JPetStore_Maven/JPetStore/pom.xml\",\"codeAnalysis\":[],\"unitTesting\":\"off\",\"codeCoverage\":\"off\",\"args\":\"\",\"compile\":\"on\",\"clean\":\"on\",\"install\":\"on\",\"MVNOPTS\":\"\",\"ossDistributionType\":\"\",\"interval\":\"\"}],\"postBuildScript\":{\"dependentPipelineList\":[]}},\"deployInfo\":{\"deployEnv\":[{\"envName\":\"dev\",\"envFlag\":\"off\",\"scriptType\":\"\"}]},\"testInfo\":{\"testEnv\":[{\"envName\":\"dev\",\"envFlag\":\"off\"}]}}";

		IDPJob idpjob1 = gson.fromJson(json, IDPJob.class);

		jobsBL.submitJob(idpjob1, "idpadmin");

	}

	@Test
	public void testSubmitJob2() throws Throwable {

		Gson gson = new Gson();

		String json = "{\"basicInfo\":{\"applicationName\":\"JFrog\",\"pipelineName\":\"JFrogTest2\",\"buildServerOS\":\"windows\",\"engine\":\"Jenkins Workflow\",\"buildInterval\":{\"pollSCM\":\"off\",\"buildInterval\":\"\",\"buildIntervalValue\":\"0\",\"buildAtSpecificInterval\":\"off\",\"event\":[{\"type\":\"--Select--\",\"hour\":\"00\",\"minute\":\"00\"}]},\"additionalMailRecipients\":{\"applicationTeam\":\"\",\"emailIds\":\"\"},\"userName\":\"idpadmin\",\"pipelineStatus\":\"edit\"},\"code\":{\"category\":\"Standard\",\"technology\":\"J2EE/Ant\",\"scm\":[{\"type\":\"git\",\"url\":\"http://xyz.com/dummyuser/JPetStoreRepo.git\",\"userName\":\"idpadmin\",\"password\":\"dummyuser\",\"repositoryBrowser\":\"gitLab\",\"browserUrl\":\"http://xyz.com\",\"projectName\":\"\",\"branch\":\"master\",\"projPath\":\"\",\"moduleName\":\"\",\"clearcaseType\":\"\",\"vobName\":\"\",\"snapshotViewName\":\"\",\"configSpec\":\"\",\"developmentStreamName\":\"\",\"buildConfiguration\":\"\",\"buildDefinition\":\"\",\"repositoryWorkspace\":\"\",\"projArea\":\"\",\"port\":\"\",\"version\":\"default\",\"exclude\":\"\",\"proxy\":\"\",\"proxyPort\":\"\",\"appRepo\":\"on\",\"deployRepo\":\"\",\"testRepo\":\"\"}],\"jobParam\":[],\"buildScript\":[{\"tool\":\"\"},{\"tool\":\"\"},{}],\"subApplication\":\"\"},\"buildInfo\":{\"buildtool\":\"ant\",\"artifactToStage\":{\"artifact\":\"**/*.*\",\"artifactRepo\":{\"repoURL\":\"http://dummyuser:8081/artifactory\",\"repoName\":\"idp\",\"repoUsername\":\"admin\",\"repoPassword\":\"password\"},\"artifactRepoName\":\"jfrog\"},\"castAnalysis\":{},\"modules\":[{\"moduleName\":\"JPetStore\",\"relativePath\":\"JPetStoreRepo/JPetStore/src\",\"codeAnalysis\":[],\"codeCoverage\":\"off\",\"customBuildXml\":\"\",\"args\":\"\",\"compile\":\"on\",\"ejbDescriptor\":\"\",\"javaMainClass\":\"\",\"warPackaging\":\"JPetStoreRepo/JPetStore/WebContent/WEB-INF/web.xml\",\"ossDistributionType\":\"\",\"unitTestDir\":\"\"}],\"postBuildScript\":{\"dependentPipelineList\":[]},\"javaModules\":\"\",\"ejbModules\":\"\",\"webModules\":\"\"},\"deployInfo\":{\"deployEnv\":[{\"envName\":\"dev\",\"envFlag\":\"on\",\"scriptType\":\"\",\"deploySteps\":[{\"stepName\":\"Tomcat\",\"deployOS\":\"\",\"runScript\":{\"scriptType\":\"\",\"scriptFilePath\":\"\",\"targets\":\"\"},\"deployToContainer\":{\"containerName\":\"tomcat\",\"serverManagerURL\":\"http://dummyuser:7979\",\"resourceToBeDeployed\":\"JPetStore.war\",\"warPath\":\"\",\"contextPath\":\"/JPetStoreLocal\",\"userName\":\"tomcatadmin\",\"password\":\"tomcatadmin\",\"targetCellName\":\"\",\"targetNodeName\":\"\",\"targetServerName\":\"\",\"hostName\":\"\",\"port\":\"\",\"updateDB\":\"\",\"rollbackStrategy\":\"\",\"narOS\":\"\",\"deployedFolder\":\"\",\"fileName\":\"\",\"pairName\":\"\",\"srcEnvName\":\"\"},\"deployDatabase\":{\"restorusername\":\"\",\"restorpassword\":\"\",\"dbusername\":\"\",\"dbpassword\":\"\",\"script\":\"\"},\"abortScript\":{\"scriptType\":\"\"},\"deploymentOption\":\"\",\"deployOperation\":\"\",\"proxy\":{\"username\":\"\",\"password\":\"\",\"host\":\"\",\"port\":\"\",\"enabled\":\"\"}}]},{\"envName\":\"QA\",\"envFlag\":\"off\"}]},\"testInfo\":{\"testEnv\":[{\"envName\":\"dev\",\"envFlag\":\"off\"},{\"envName\":\"QA\",\"envFlag\":\"off\"}]}}";

		IDPJob idpjob1 = gson.fromJson(json, IDPJob.class);

		jobsBL.submitJob(idpjob1, "idpadmin");

	}

	@Test
	public void testLastbuild() throws Throwable {

		jobsBL.lastbuild("TC1_Maven", "idpadmin", "dummyuser");

	}

	@Test(expected = NullPointerException.class)
	public void testGetBuildJobs() throws Throwable {

		jobsmgmtBL.getBuildJobs(null);
	}

	@Test
	public void testGetExistingApps() throws Throwable {

		Applications app = jobsBL.getExistingApps("idpadmin");
		assertNotNull(app);
	}

	@Test
	public void testGetApplicationDetails() {

		Application app = null;
		try {
			app = jobsmgmtBL.getApplicationDetails("DemoAppT", "idpadmin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(app);
	}

	@Test
	public void testGetFilteredApplications() {

		AppNames app = null;
		try {
			app = jobsmgmtBL.getFilteredApplications("JFrog", "idpadmin", "IDP");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(app);
	}

	@Test
	public void testGetNotifications() {

		List<Notification> list = jobsBL.getNotifications("idpadmin");
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}

	@Test
	public void testGetExistingAppsOrg() {
		jobsBL.getExistingApps("idpadmin", "INFOSYS");
		assertNotNull(app);

	}

	@Test
	public void testGetApplicationID() {
		String id = jobsaddInfo.getApplicationID("DemoAppT");
		assertNotNull(id);

	}

/*	@Test
	public void testdbDeployPipelineNamesForApplication() {
		Names names = jobsaddInfo.dbDeployPipelineNamesForApplication("DemoAppT", "idpadmin");
		assertNotNull(names);

	}*/

	@Test
	public void testGetStageViewUrl() {
		String url = null;
		try {
			url = jobsaddInfo.getStageViewUrl("DemoAppT", "TC1_Maven");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(url);

	}

	@Test
	public void testGetApplicationNameForReleaseManager() {
		Names names = jobsaddInfo.getApplicationNameForReleaseManager("idpadmin", "IDP");
		assertNotNull(names);

	}

	@Test
	public void testGetSubApplications() {
		SubApplication app = jobsaddInfo.getSubApplications("DemoAppT");
		assertNull(app);

	}

	@Test
	public void testGetDependencyPipelines() {
		Pipelines app = jobsaddInfo.getDependencyPipelines("DemoAppT", "idpadmin");
		assertNotNull(app);
	}

	@Test
	public void testGetExistingAppNames() {
		Applications app = jobsBL.getExistingAppNames("idpadmin", "IDP");
		assertNotNull(app);
	}

	@Test
	public void testGetExistingAppNamesOrg() {
		Applications app = jobsBL.getExistingAppNames("idpadmin", "INFOSYS", "IDP");
		assertNotNull(app);
	}

	@Test
	public void testFecthTriggerSteps() {
		Steps steps = null;
		try {
			steps = jobsaddInfo.fecthTriggerSteps("DemoAppT", "idpadmin", "QA");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(steps);
	}

	@Test
	public void testGetPipelines() throws Throwable {

		jobsBL.getpipelinesByAppNameAndUser("DemoAppT", "idpadmin");
	}

	@Test(expected = NullPointerException.class)
	public void testGetPipelineDetails() throws Throwable {

		jobsmgmtBL.getPipelineDetails(null);
	}

	@Test
	public void testCreateSshHostServer() throws Throwable {

	}

	@Test
	public void testCreateSlaves() throws Throwable {

		jobsmgmtBL.createSlaves(app);
	}

	@Test
	public void testValidateTestStep() throws Throwable {

	}

	@Test(expected = NullPointerException.class)
	public void testDeletePipeline() throws Throwable {

		jobsaddInfo.deletePipeline(null);

	}

	@Test
	public void testGetExistingPipelines() throws Throwable {

		jobsBL.getExistingPipelines("DemoAppT");
	}

	@Test
	public void testCheckAvailableJobsToTrigger() throws Throwable {

		jobsmgmtBL.checkAvailableJobsToTrigger("idpadmin", "IDP");
	}

	@Test
	public void testGetApplications() throws Throwable {
		jobsmgmtBL.getApplications("idpadmin", "IDP");
	}

	@Test
	public void testGetRolesForApp() throws Throwable {
		jobsaddInfo.getRolesForApp("idpadmin", "DemoAppT");
	}

	@Test
	public void testGetPermissionForApplications() throws Throwable {
		jobsaddInfo.getPermissionForApplications("DemoAppT", "idpadmin");
	}

	@Test
	public void testGetRoles() throws Throwable {
		jobsmgmtBL.getRoles("idpadmin");
	}

	@Test
	public void testGetPremission() throws Throwable {
		jobsmgmtBL.getPermission("ciplatfrom");
	}

	@Test
	public void testGetBaseRoles() throws Throwable {
		jobsmgmtBL.getBaseRoles("idpadmin");
	}

	@Test
	public void testBeanToString() throws Throwable {
		jobsmgmtBL.beanToString(null);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateInfo() throws Throwable {
		jobsmgmtBL.updateInfo(app, "idpadmin", "INFOSYS");
	}

	@Test(expected = NullPointerException.class)
	public void testDeleteRole() throws Throwable {
		jobsmgmtBL.deleteRole(app);
	}

	@Test(expected = NullPointerException.class)
	public void testFetchTriggerOtions() throws Throwable {
		jobsmgmtBL.fecthTriggerOptions(null);
	}

	@Test
	public void testGetBasePermission() throws Throwable {
		jobsmgmtBL.getBasePermission("userId");
	}

	@Test
	public void testGetRolesApp() throws Throwable {
		jobsmgmtBL.getRolesApp("idpadmin", "DemoAppT");

	}

	@Test
	public void testGetReleaseNo() throws Throwable {
		jobsmgmtBL.getReleaseNo("pipelinename", "DemoAppT");
	}

	@Test
	public void testGetExistingApps_Success() throws Throwable {

		jobsBL.getExistingApps("idpadmin");

	}

	@Test
	public void testGetApplicationInfo_Success() throws Throwable {

		// IDPJob idpjson_temp=idpjson;
		// idpjson_temp.getBasicInfo().setApplicationName("getApplicationInfo");

		jobsBL.getApplicationInfo("JFrogTest1", "idpadmin");
	}

	@Test
	public void testGetExistingPipelines_Success() throws Throwable {

		jobsBL.getExistingPipelines("JFrogTest1");

	}

	@Test
	public void testgetpipelines_Success() throws Throwable {

		jobsBL.getpipelinesByAppNameAndUser("JFrogTest1", "idpadmin");

	}

	@Test
	public void testgetEnvironmentNames_Success() throws Throwable {

		jobsBL.getEnvironmentNames("JFrogTest1", "idpadmin");

	}

	@Test
	public void testcheckAvailableJobsToTrigger_Success() throws Throwable {

		jobsmgmtBL.checkAvailableJobsToTrigger("idpadmin", "IDP");
	}

	@Test
	public void testCreateApplication() throws Throwable {
		app.setApplicationName("DemoAppT");
		app.setDevelopers("idpadmin");
		app.setPipelineAdmins("idpadmin");
		app.setReleaseManager("idpadmin");

		List<EnvironmentOwnerDetail> environmentOwnerDetails = new ArrayList<EnvironmentOwnerDetail>();
		EnvironmentOwnerDetail environmentOwnerDetail_value1 = new EnvironmentOwnerDetail();
		environmentOwnerDetail_value1.setEnvironmentName("DEV");
		environmentOwnerDetail_value1.setdBOwners("idpadmin");
		environmentOwnerDetail_value1.setEnvironmentOwners("idpadmin");
		environmentOwnerDetails.add(environmentOwnerDetail_value1);
		app.setEnvironmentOwnerDetails(environmentOwnerDetails);
		ArrayList slavesDetails = new ArrayList();
		app.setSlavesDetails(slavesDetails);
		String result = jobsmgmtBL.createApplication(app, "idpadmin", "Infosys");
		assertNotNull(result);
	}

	/**
	 * Used to set up the test. This method is called by JUnit before each of the
	 * tests are executed.
	 * 
	 */
	@Before
	public void setUp() throws Exception {

		idpjson = new IDPJob();
		idpjson.setSsoName("idpjson");
		idpjson.setSsoId("idpjson_1");
		idpjson.setErrorCode("idpjson_2");
		BasicInfo basicInfo = new BasicInfo();
		basicInfo.setGroupName("idpjson_3");
		basicInfo.setGroupId("idpjson_4");
		basicInfo.setApplicationName("DemoAppT");
		basicInfo.setPipelineName("Testing");
		basicInfo.setBuildServerOS("idpjson_7");
		basicInfo.setEngine("idpjson_8");
		BuildInterval buildInterval = new BuildInterval();
		buildInterval.setPollSCM("idpjson_9");
		buildInterval.setBuildInterval("idpjson_10");
		buildInterval.setBuildIntervalValue("idpjson_11");
		basicInfo.setBuildInterval(buildInterval);
		AdditionalMailRecipients additionalMailRecipients = new AdditionalMailRecipients();
		additionalMailRecipients.setApplicationTeam("idpjson_12");
		additionalMailRecipients.setEmailIds("idpjson_13");
		basicInfo.setAdditionalMailRecipients(additionalMailRecipients);
		idpjson.setBasicInfo(basicInfo);

		Code code = new Code();
		ArrayList buildScript1 = new ArrayList();

		RunScript buildScript = new RunScript();

		buildScript.setScriptType("scriptType12");
		buildScript.setFlattenFilePath("flattenFilePath12");
		Integer port = new Integer(5);
		buildScript.setPort(port);
		buildScript.setHost("host12");
		buildScript.setUserName("userName12");
		buildScript.setPassword("password12");
		buildScript.setScript("script12");
		buildScript.setPathToFiles("pathToFiles12");
		buildScript.setDestinationDir("destinationDir12");
		buildScript.setArchiveLogs("archiveLogs12");
		buildScript.setTool("tool12");
		buildScript.setScriptFilePath("scriptFilePath12");
		buildScript.setTargets("targets11");

		buildScript1.add(buildScript);

		code.setBuildScript(buildScript1);
		code.setCategory("idpjson_14");
		code.setTechnology("idpjson_15");
		ArrayList scm1 = new ArrayList();

		Scm scm = new Scm();

		scm.setExclude("exclude12");
		scm.setVersion("version12");
		scm.setHostName("hostName12");
		scm.setPort("port12");
		scm.setServer("server12");
		scm.setType("type12");
		scm.setUrl("url12");
		scm.setUserName("userName12");
		scm.setPassword("password12");
		scm.setRepositoryBrowser("repositoryBrowser12");
		scm.setBrowserUrl("browserUrl12");
		scm.setProjectName("projectName12");
		scm.setBranch("branch12");
		scm.setProjPath("projPath12");
		scm.setModuleName("moduleName12");
		scm.setClearcaseType("clearcaseType12");
		scm.setVobName("vobName12");
		scm.setSnapshotViewName("snapshotViewName12");
		scm.setConfigSpec("configSpec12");
		scm.setDevelopmentStreamName("developmentStreamName12");
		scm.setBuildConfiguration("buildConfiguration12");
		scm.setBuildDefinition("buildDefinition12");
		scm.setRepositoryWorkspace("repositoryWorkspace12");
		scm.setProjArea("projArea11");

		scm1.add(scm);

		code.setScm(scm1);

		idpjson.setCode(code);

		BuildInfo buildInfo = new BuildInfo();
		buildInfo.setJavaModules("idpjson_18");
		buildInfo.setEjbModules("idpjson_19");
		buildInfo.setWebModules("idpjson_20");
		buildInfo.setNexusType("idpjson_21");
		buildInfo.setNexusUrl("idpjson_22");
		buildInfo.setUserName("idpjson_23");
		buildInfo.setPassword("idpjson_24");
		RunScript postBuildScript = new RunScript();
		postBuildScript.setScriptType("idpjson_25");
		postBuildScript.setFlattenFilePath("idpjson_26");
		Integer port1 = 9091; // (Integer) Repository.getObject(Integer.class, "integer");
		postBuildScript.setPort(port1);
		postBuildScript.setHost("idpjson_27");
		postBuildScript.setUserName("idpjson_28");
		postBuildScript.setPassword("idpjson_29");
		postBuildScript.setScript("idpjson_30");
		postBuildScript.setPathToFiles("idpjson_31");
		postBuildScript.setDestinationDir("idpjson_32");
		postBuildScript.setArchiveLogs("idpjson_33");
		postBuildScript.setTool("idpjson_34");
		postBuildScript.setScriptFilePath("idpjson_35");
		postBuildScript.setTargets("idpjson_36");
		buildInfo.setPostBuildScript(postBuildScript);
		buildInfo.setBuildtool("idpjson_37");
		ArtifactToStage artifactToStage = new ArtifactToStage();
		artifactToStage.setFlattenFileStructure("idpjson_38");
		artifactToStage.setArtifact("idpjson_39");
		buildInfo.setArtifactToStage(artifactToStage);

		Module module1 = new Module();

		module1.setExcludeFolders("excludeFolders22");
		module1.setInstallGrunt("installGrunt22");
		module1.setMultiModule("multiModule22");
		module1.setExclude("exclude22");
		module1.setIncrementalScan("incrementalScan22");
		module1.setInterval("interval22");
		module1.setTeam("team22");
		module1.setPreset("preset22");
		module1.setServerUrl("serverUrl22");
		module1.setCheckmarxProjName("checkmarxProjName22");
		module1.setUnitTestDir("unitTestDir22");
		module1.setPafFilePath("pafFilePath22");
		module1.setOssMailRecipients("mailRecipients22");
		module1.setOssDistributionType("distributionType22");
		module1.setOssAnalysisType("analysisType22");
		module1.setRenaming("renaming22");
		module1.setControlFlow("controlFlow22");
		module1.setEncryption("encryption22");
		module1.setUiEarFileName("uiEarFileName22");
		module1.setIntegrationFileName("integrationFileName22");
		List reportMigration = new ArrayList(); // ??
		module1.setReportMigration(reportMigration);
		List publishForms = new ArrayList(); // ??
		module1.setPublishForms(publishForms);
		List hostCTLMigration = new ArrayList(); // ??
		module1.setHostCTLMigration(hostCTLMigration);
		List oaFileMigration = new ArrayList(); // ??
		module1.setOaFileMigration(oaFileMigration);
		List workFlowPublish = new ArrayList(); // ??
		module1.setWorkFlowPublish(workFlowPublish);
		module1.setDbType("dbType22");
		module1.setProjectName("projectName22");
		module1.setCodeFormatting("codeFormatting22");
		module1.setCustomScript("customScript22");
		module1.setBuildType("buildType22");
		module1.setTransfer("transfer22");
		module1.setHostName("hostName22");
		module1.setUserName("userName22");
		module1.setPassword("password22");
		module1.setExecute("execute22");
		module1.setLocalmachineExecution("localmachineExecution22");
		module1.setDependencyFile("dependencyFile22");
		module1.setMasterMachineName("masterMachineName22");
		module1.setClassFileName("classFileName22");
		module1.setOutputFolder("outputFolder22");
		module1.setVersion("version22");
		module1.setCodeAnalysisTragets("codeAnalysisTragets22");
		module1.setUnitTestTargets("unitTestTargets22");
		module1.setSecurityProjName("securityProjName22");
		module1.setAvdName("avdName22");
		module1.setEnvName("envName22");
		module1.setDbName("dbName22");
		Integer port2 = 9092;// new Integer(-2147483648);
		module1.setPort(port2.toString());
		module1.setProductKey("productKey22");
		module1.setMessageFlows("messageFlows22");
		module1.setNexusType("nexusType22");
		module1.setNexusUrl("nexusUrl22");
		module1.setHome("home22");
		module1.setThirdPartyJar("thirdPartyJar22");
		module1.setCdtIn("cdtIn22");
		module1.setCdtOut("cdtOut22");
		module1.setDestDB("destDB22");
		module1.setDestPassword("destPassword22");
		module1.setRepository("repository22");
		RunScript runScript = new RunScript(); // ??
		module1.setRunScript(runScript);
		module1.setmVNOPTS("mVNOPTS43");
		module1.setJsonPath("jsonPath22");
		module1.setGroupId("groupId22");
		module1.setModuleName("moduleName22");
		module1.setRelativePath("relativePath22");
		module1.setSecurityAnalysis("securityAnalysis22");
		List codeAnalysis = new ArrayList(); // ??
		module1.setCodeAnalysis(codeAnalysis);
		module1.setUnitTesting("unitTesting22");
		module1.setUnitTestProjectName("unitTestProjectName22");
		module1.setUnitTestCategory("unitTestCategory22");
		module1.setCodeCoverage("codeCoverage22");
		module1.setTestSettingFilePath("testSettingFilePath22");
		module1.setServerMachine("serverMachine22");
		module1.setCustomBuildXml("customBuildXml22");
		module1.setArgs("args22");
		module1.setCompile("compile22");
		module1.setJarPackaging("jarPackaging22");
		module1.setEjbDescriptor("ejbDescriptor22");
		module1.setJavaMainClass("javaMainClass22");
		module1.setWarPackaging("warPackaging22");
		module1.setGlobalGoals("globalGoals22");
		module1.setClean("clean22");
		module1.setInstall("install22");
		module1.setmVNOPTS("mVNOPTS44");
		module1.setAuthenticationMode("authenticationMode");
		module1.setRollbackScript("rollbackScript");
		module1.setAllUnitTestPass("allUnitTestPass");
		module1.setNoViolations("noViolations");
		module1.setNpmProxy("npmProxy");
		List<String> unitTestTool = new ArrayList<String>(); // ??
		module1.setUnitTestTool(unitTestTool);
		List<String> codeCoverageTool = new ArrayList<String>(); // ??
		module1.setCodeCoverageTool(codeCoverageTool);
		module1.setBuildValue("buildValue");
		module1.setRemoteDir("remoteDir");
		module1.setPrivateKey("privateKey");
		module1.setReport("report");

		ArrayList modules = new ArrayList();

		modules.add(module1); // ??

		buildInfo.setModules(modules);
		idpjson.setBuildInfo(buildInfo);

		DeployInfo deployInfo = new DeployInfo();
		List<DeployEnv> deployEnvList = new ArrayList<DeployEnv>();
		DeployEnv deployEnv = new DeployEnv();
		deployEnv.setApprover("approver");
		deployEnv.setEnvName("envName");

		DeployStep deployStep = new DeployStep();
		List<DeployStep> deploySteps = new ArrayList<>();
		deploySteps.add(deployStep);
		deployEnv.setDeploySteps(deploySteps);
		deployEnvList.add(deployEnv);

		deployInfo.setDeployEnv(deployEnvList);
		idpjson.setDeployInfo(deployInfo);

		TestInfo testInfo = new TestInfo();
		List<TestEnv> testEnvList = new ArrayList<>();

		TestEnv testEnv = new TestEnv();
		testEnv.setEnvName("envName");

		List<TestStep> testSteps = new ArrayList<>();
		TestStep testStep = new TestStep();
		testStep.setRunScript(null);
		testStep.setStepName("stepName");
		testStep.setTest(null);
		testSteps.add(testStep);
		testEnv.setTestSteps(testSteps);
		testEnvList.add(testEnv);
		testInfo.setTestEnv(testEnvList);
		idpjson.setTestInfo(testInfo);

		triggerParameters = new TriggerParameters();

		triggerParameters.setDashBoardLink("dashBoardLink22");
		triggerParameters.setErrorCode("errorCode22");
		triggerParameters.setJobBuildId("jobBuildId22");
		// triggerParameters.setTestStep("testStep22");
		triggerParameters.setEmailed("emailed22");
		triggerParameters.setApplicationName("Test_App");
		triggerParameters.setPipelineName("BitBucketTest");
		triggerParameters.setReleaseNumber("releaseNumber22");
		triggerParameters.setUserName("idpadmin");
		triggerParameters.setSlaveName("slaveName22");
		triggerParameters.setEnvSelected("qa");
		Build build = new Build();

		build.setBranchSelected("branchSelected11");
		List module = new ArrayList();
		build.setModule(module);

		triggerParameters.setBuild(build);
		Deploy deploy = new Deploy();

		deploy.setVersion("version12");
		deploy.setArtifactID("artifactID12");
		deploy.setNexusId("nexusId11");

		triggerParameters.setDeploy(deploy);
		triggerParameters.setTestSelected("testSelected21");

		// ApplicationInfo

		app = new ApplicationInfo();
		app.setApplicationName("DemoAppT");
		app.setDevelopers("app_1");
		app.setPipelineAdmins("app_2");
		app.setReleaseManager("app_3");
		List<EnvironmentOwnerDetail> environmentOwnerDetails = new ArrayList<EnvironmentOwnerDetail>();
		EnvironmentOwnerDetail environmentOwnerDetail_value1 = new EnvironmentOwnerDetail();
		environmentOwnerDetail_value1.setEnvironmentName("environmentName");
		environmentOwnerDetail_value1.setdBOwners("dBOwners");
		environmentOwnerDetail_value1.setEnvironmentOwners("environmentOwners");
		environmentOwnerDetails.add(environmentOwnerDetail_value1);
		app.setEnvironmentOwnerDetails(environmentOwnerDetails);
		ArrayList<SlavesDetail> slavesDetails = new ArrayList<>();
		SlavesDetail sd = new SlavesDetail();
		sd.setBuild("on");
		sd.setTest("on");
		sd.setDeploy("on");
		sd.setLabels("DSlave");
		sd.setSlaveName("DSlave");
		sd.setSlaveUsage("both");
		slavesDetails.add(sd);

		app.setSlavesDetails(slavesDetails);

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
	 * Usage: java JobsBLTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.businessapi.JobsBLTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class getTestedClass() {
		return JobsBL.class;
	}
}
