/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.basicinfo.AdditionalMailRecipients;
import org.infy.idp.entities.jobs.basicinfo.BasicInfo;
import org.infy.idp.entities.jobs.basicinfo.BuildInterval;
import org.infy.idp.entities.jobs.buildinfo.ArtifactToStage;
import org.infy.idp.entities.jobs.buildinfo.BuildInfo;
import org.infy.idp.entities.jobs.buildinfo.Module;
import org.infy.idp.entities.jobs.code.Code;
import org.infy.idp.entities.jobs.code.Scm;
import org.infy.idp.entities.jobs.common.RunScript;
import org.infy.idp.entities.jobs.deployinfo.DeployInfo;
import org.infy.idp.entities.jobs.testinfo.TestInfo;
import org.infy.idp.entities.triggerparameter.Build;
import org.infy.idp.entities.triggerparameter.Deploy;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * TriggerBuildsTest is a test class for TriggerBuilds
 *
 * @see org.infy.idp.utils.TriggerBuilds
 * 
 */

@RunWith(MockitoJUnitRunner.class)
public class TriggerBuildsTest  {

	@InjectMocks
	private TriggerBuilds testedObject;

	@Spy
	private ConfigurationManager configmanager;

	private IDPJob idpjson;

	private TriggerParameters triggerParameters;

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TriggerBuildsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Used to set up the test. This method is called by JUnit before each of the
	 * tests are executed.
	 * 
	 * 
	 */
	
	
	@Test
	public void testBuildByJobName() {
		testedObject.buildByJobName(idpjson);
	}
	
	@Test
	public void testBuildByJobNameTriggerParam() {
		testedObject.buildByJobName(triggerParameters);
	}
	
	@Before
	public void setUp() throws Exception {
		/*
		 * Add any necessary initialization code here (e.g., open a socket). Call
		 * Repository.putTemporary() to provide initialized instances of objects to be
		 * used when testing.
		 */

		configmanager.setJenkinsurl("http://localhost:8085");
		configmanager.setSharePath("D:\\idpdata\\dsldata");
		configmanager.setPipelineScriptPath("D:\\idpdata\\dsldata\\src\\main\\groovy\\pipeline_sequence");
		configmanager.setJenkinsuserid("admin");
		configmanager.setJenkinspassword("dummy");

		idpjson = new IDPJob();
		idpjson.setSsoName("idpjson");
		idpjson.setSsoId("idpjson_1");
		idpjson.setErrorCode("idpjson_2");
		BasicInfo basicInfo = new BasicInfo();
		basicInfo.setGroupName("idpjson_3");
		basicInfo.setGroupId("idpjson_4");
		basicInfo.setApplicationName("RajaprabuUnitTest");
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
		/*
		 * Remove Jira ALM module1.setRaiseJiraBug("raiseJiraBug22");
		 */
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
		ArrayList deployEnv = new ArrayList(); // ??
		deployInfo.setDeployEnv(deployEnv);
		idpjson.setDeployInfo(deployInfo);

		TestInfo testInfo = new TestInfo();
		ArrayList testEnv = new ArrayList();
		testInfo.setTestEnv(testEnv);
		idpjson.setTestInfo(testInfo);

		// Trigger Parameters
		List<String> teststeps = new ArrayList<>();
		teststeps.add("testStep22");
		triggerParameters = new TriggerParameters();

		triggerParameters.setDashBoardLink("dashBoardLink22");
		triggerParameters.setErrorCode("errorCode22");
		triggerParameters.setJobBuildId("jobBuildId22");
		triggerParameters.setTestStep((ArrayList<String>) teststeps);
		triggerParameters.setEmailed("emailed22");
		triggerParameters.setApplicationName("RajaprabuUnitTest");
		triggerParameters.setPipelineName("pipelineName22");
		triggerParameters.setReleaseNumber("releaseNumber22");
		triggerParameters.setUserName("userName22");
		triggerParameters.setSlaveName("slaveName22");
		triggerParameters.setEnvSelected("envSelected22");
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

	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TriggerBuildsTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.utils.TriggerBuildsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return TriggerBuilds.class;
	}
}
