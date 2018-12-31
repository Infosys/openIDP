/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.common.AbortScript;
import org.infy.idp.entities.jobs.common.Proxy;
import org.infy.idp.entities.jobs.common.RunScript;
import org.junit.Test;

/**
 * ModuleTest is a test class for Module
 *
 * @see org.infy.idp.entities.jobs.buildinfo.Module
 * 
 */
public class ModuleTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ModuleTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Module().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Module#Module()
	 * 
	 * 
	 */
	@Test
	public void testModule0() throws Throwable {
		Module testedObject = new Module();
		testedObject.setExcludeFolders("excludeFolders22");
		testedObject.setInstallGrunt("installGrunt22");
		testedObject.setMultiModule("multiModule22");
		testedObject.setExclude("exclude22");
		testedObject.setIncrementalScan("incrementalScan22");
		testedObject.setInterval("interval22");
		testedObject.setTeam("team22");
		testedObject.setPreset("preset22");
		testedObject.setServerUrl("serverUrl22");
		testedObject.setCheckmarxProjName("checkmarxProjName22");
		testedObject.setUnitTestDir("unitTestDir22");
		testedObject.setPafFilePath("pafFilePath22");
		testedObject.setOssMailRecipients("mailRecipients22");
		testedObject.setOssDistributionType("distributionType22");
		testedObject.setNexusType("analysisType22");
		testedObject.setRenaming("renaming22");
		testedObject.setControlFlow("controlFlow22");
		testedObject.setEncryption("encryption22");

		testedObject.setAppPort("app_port22");
		testedObject.setAppServ("app_server_domain22");
		testedObject.setArchName("arch_name22");
		testedObject.setBuildproperties("buildproperties_path22");
		testedObject.setCodeAna("code_analysis22");
		testedObject.setCodeAnalysisTarget("codeAnalysisTarget22");
		testedObject.setDllName("dllName");
		testedObject.setFixPath("fixPath");
		testedObject.setSonarUrl("sonar_url");
		testedObject.setSonarPassword("sonar_password");
		testedObject.setSonarUserName("sonar_user");
		testedObject.setSrcSchName("srcSchName");
		testedObject.setTarSchName("tarSchName");
		testedObject.setUnitTestingTarget("unitTestingTarget");

		testedObject.setUiEarFileName("uiEarFileName22");
		testedObject.setIntegrationFileName("integrationFileName22");
		List reportMigration = new ArrayList();
		testedObject.setReportMigration(reportMigration);
		List publishForms = new ArrayList();
		testedObject.setPublishForms(publishForms);
		List hostCTLMigration = new ArrayList();
		testedObject.setHostCTLMigration(hostCTLMigration);
		List oaFileMigration = new ArrayList();
		testedObject.setOaFileMigration(oaFileMigration);
		List workFlowPublish = new ArrayList();
		testedObject.setWorkFlowPublish(workFlowPublish);
		testedObject.setDbType("dbType22");
		testedObject.setProjectName("projectName22");
		testedObject.setCodeFormatting("codeFormatting22");
		testedObject.setCustomScript("customScript22");
		testedObject.setBuildType("buildType22");
		testedObject.setTransfer("transfer22");
		testedObject.setHostName("hostName22");
		testedObject.setUserName("userName22");
		testedObject.setPassword("password22");
		testedObject.setExecute("execute22");
		testedObject.setLocalmachineExecution("localmachineExecution22");
		testedObject.setDependencyFile("dependencyFile22");
		testedObject.setMasterMachineName("masterMachineName22");
		testedObject.setClassFileName("classFileName22");
		testedObject.setOutputFolder("outputFolder22");
		testedObject.setVersion("version22");
		testedObject.setCodeAnalysisTragets("codeAnalysisTragets22");
		testedObject.setUnitTestTargets("unitTestTargets22");
		testedObject.setSecurityProjName("securityProjName22");
		testedObject.setAvdName("avdName22");
		testedObject.setEnvName("envName22");
		testedObject.setDbName("dbName22");
		String port = "-2147483648";
		testedObject.setPort(port.toString());
		testedObject.setProductKey("productKey22");
		testedObject.setMessageFlows("messageFlows22");
		testedObject.setNexusType("nexusType22");
		testedObject.setNexusUrl("nexusUrl22");
		testedObject.setHome("home22");
		testedObject.setThirdPartyJar("thirdPartyJar22");
		testedObject.setCdtIn("cdtIn22");
		testedObject.setCdtOut("cdtOut22");
		testedObject.setDestDB("destDB22");
		testedObject.setDestPassword("destPassword22");
		testedObject.setRepository("repository22");
		RunScript runScript = new RunScript();
		testedObject.setRunScript(runScript);
		testedObject.setmVNOPTS("mVNOPTS43");
		testedObject.setJsonPath("jsonPath22");
		testedObject.setGroupId("groupId22");
		testedObject.setModuleName("moduleName22");
		testedObject.setRelativePath("relativePath22");
		testedObject.setSecurityAnalysis("securityAnalysis22");
		List codeAnalysis = new ArrayList();
		testedObject.setCodeAnalysis(codeAnalysis);
		testedObject.setUnitTesting("unitTesting22");
		testedObject.setUnitTestProjectName("unitTestProjectName22");
		testedObject.setUnitTestCategory("unitTestCategory22");
		testedObject.setCodeCoverage("codeCoverage22");
		testedObject.setTestSettingFilePath("testSettingFilePath22");
		testedObject.setServerMachine("serverMachine22");
		testedObject.setCustomBuildXml("customBuildXml22");
		testedObject.setArgs("args22");
		testedObject.setCompile("compile22");
		testedObject.setJarPackaging("jarPackaging22");
		testedObject.setEjbDescriptor("ejbDescriptor22");
		testedObject.setJavaMainClass("javaMainClass22");
		testedObject.setWarPackaging("warPackaging22");
		testedObject.setGlobalGoals("globalGoals22");
		testedObject.setClean("clean22");
		testedObject.setInstall("install22");
		testedObject.setmVNOPTS("mVNOPTS44");
		testedObject.setAuthenticationMode("authenticationMode");
		testedObject.setRollbackScript("rollbackScript");
		testedObject.setAllUnitTestPass("allUnitTestPass");
		testedObject.setNoViolations("noViolations");
		testedObject.setNpmProxy("npmProxy");
		List<String> unitTestTool = new ArrayList<String>();
		testedObject.setUnitTestTool(unitTestTool);
		List<String> codeCoverageTool = new ArrayList<String>();
		testedObject.setCodeCoverageTool(codeCoverageTool);
		testedObject.setBuildValue("buildValue");
		testedObject.setRemoteDir("remoteDir");
		testedObject.setPrivateKey("privateKey");
		testedObject.setReport("report");
		Proxy proxy = new Proxy();
		proxy.setEnabled("enabled");
		proxy.setHost("host");
		proxy.setPassword("pwd");
		proxy.setPort("8085");
		proxy.setUsername("userName");

		testedObject.setProxy(proxy);
		testedObject.setServMachine("machine");
		testedObject.setPigProjectName("pig");
		testedObject.setPigPomPath("pom.xml");
		testedObject.setPigUT("ut");
		testedObject.setHiveProjectPath("hive");
		AbortScript abortScript = new AbortScript();
		abortScript.setArchiveLogs("**/*.*");
		abortScript.setFlattenFilePath("**/*.*");
		testedObject.setAbortScript(abortScript);
		testedObject.setHivePomPath("pom.xml");
		testedObject.setHiveUT("ut");
		testedObject.setScalaProjectName("scala");
		testedObject.setScalaPomPath("pom.xml");
		testedObject.setScalaCC("cc");
		testedObject.setFolder("folder");
		testedObject.setFilePath("path");
		testedObject.setToolsRoot("root");
		testedObject.setToolsCfg("cfg");
		testedObject.setDataSource("source");
		testedObject.setSiebelUserName("userName");
		testedObject.setSiebelPassword("dummy4+");
		testedObject.setSiebelRepo("repo");
		testedObject.setSifImport("import");
		testedObject.setSibcompile("compile");
		testedObject.setDefaultModule("module");
		testedObject.setSqlFilesPackage("sql");
		testedObject.setConnectionName("conn");
		testedObject.setTestSuite("suite");
		testedObject.setNpmProxyUserName("admin");
		testedObject.setNpmProxyPassword("dummy");
		testedObject.setSourceRepo("repo");
		testedObject.setAngularBuildCommand("ng");
		testedObject.setSourceFolder("folder");
		testedObject.setTargetFolder("target");
		testedObject.setTargetFolderShared("folder");
		testedObject.setExportObjSharedXml("xml");
		testedObject.setExportObjXml("xml");
		testedObject.setDatabase("database");
		testedObject.setVarient("var");
		testedObject.setInspectionName("inspection");
		testedObject.setBuildTool("angular");
		testedObject.setOssAnalysisType("analysisType22");
		testedObject.setSourceFolderShared("shared");
		testedObject.setTargetRepo("repo");
		testedObject.setIpcHostName("host");
		testedObject.setIpcPassword("pwd");
		testedObject.setIpcUserName("admin");
		testedObject.setLogFilePath("log");
		testedObject.setSourceUsername("userName");
		testedObject.setSourcePassword("dummy4+");
		testedObject.setSourceUrl("url");
		testedObject.setTimeout("timeout");
//		testedObject.setRaiseJiraBug("raiseJiraBug");
		
//		assertEquals("raiseJiraBug", testedObject.getRaiseJiraBug());
		assertEquals(proxy, testedObject.getProxy());
		assertEquals("serverMachine22", testedObject.getServerMachine());
		assertEquals("pig", testedObject.getPigProjectName());
		assertEquals("pom.xml", testedObject.getPigPomPath());

		assertEquals("app_port22", testedObject.getAppPort());
		assertEquals("app_server_domain22", testedObject.getAppServ());
		assertEquals("arch_name22", testedObject.getArchName());
		assertEquals("buildproperties_path22", testedObject.getBuildproperties());
		assertEquals("code_analysis22", testedObject.getCodeAna());
		assertEquals("codeAnalysisTarget22", testedObject.getCodeAnalysisTarget());
		assertEquals("dllName", testedObject.getDllName());
		assertEquals("fixPath", testedObject.getFixPath());
		assertEquals("sonar_url", testedObject.getSonarUrl());
		assertEquals("sonar_password", testedObject.getSonarPassword());
		assertEquals("sonar_user", testedObject.getSonarUserName());
		assertEquals("srcSchName", testedObject.getSrcSchName());
		assertEquals("tarSchName", testedObject.getTarSchName());
		assertEquals("unitTestingTarget", testedObject.getUnitTestingTarget());
		/////////////////////////////////
		assertEquals("ut", testedObject.getPigUT());
		assertEquals("hive", testedObject.getHiveProjectPath());
		assertEquals("pom.xml", testedObject.getHivePomPath());
		assertEquals(abortScript, testedObject.getAbortScript());
		assertEquals("scala", testedObject.getScalaProjectName());
		assertEquals("pom.xml", testedObject.getScalaPomPath());
		assertEquals("cc", testedObject.getScalaCC());
		assertEquals("folder", testedObject.getFolder());
		assertEquals("path", testedObject.getFilePath());
		assertEquals("root", testedObject.getToolsRoot());
		assertEquals("cfg", testedObject.getToolsCfg());
		assertEquals("source", testedObject.getDataSource());
		assertEquals("ut", testedObject.getHiveUT());
		assertEquals("machine", testedObject.getServMachine());

		assertEquals("userName", testedObject.getSiebelUserName());
		assertEquals("dummy4+", testedObject.getSiebelPassword());
		assertEquals("repo", testedObject.getSiebelRepo());
		assertEquals("import", testedObject.getSifImport());
		assertEquals("compile", testedObject.getSibcompile());
		assertEquals("module", testedObject.getDefaultModule());
		assertEquals("sql", testedObject.getSqlFilesPackage());
		assertEquals("conn", testedObject.getConnectionName());
		assertEquals("suite", testedObject.getTestSuite());
		assertEquals("admin", testedObject.getNpmProxyUserName());
		assertEquals("dummy", testedObject.getNpmProxyPassword());
		assertEquals("repo", testedObject.getSourceRepo());

		assertEquals("ng", testedObject.getAngularBuildCommand());
		assertEquals("folder", testedObject.getSourceFolder());
		assertEquals("folder", testedObject.getTargetFolderShared());
		assertEquals("target", testedObject.getTargetFolder());
		assertEquals("xml", testedObject.getExportObjSharedXml());
		assertEquals("xml", testedObject.getExportObjXml());
		assertEquals("database", testedObject.getDatabase());
		assertEquals("var", testedObject.getVarient());
		assertEquals("inspection", testedObject.getInspectionName());
		assertEquals("angular", testedObject.getBuildTool());
		assertEquals("shared", testedObject.getSourceFolderShared());
		assertEquals("repo", testedObject.getTargetRepo());

		assertEquals("host", testedObject.getIpcHostName());
		assertEquals("pwd", testedObject.getIpcPassword());
		assertEquals("admin", testedObject.getIpcUserName());
		assertEquals("log", testedObject.getLogFilePath());
		assertEquals("userName", testedObject.getSourceUsername());
		assertEquals("dummy4+", testedObject.getSourcePassword());
		assertEquals("url", testedObject.getSourceUrl());
		assertEquals("timeout", testedObject.getTimeout());

		assertEquals("authenticationMode", testedObject.getAuthenticationMode());
		assertEquals("rollbackScript", testedObject.getRollbackScript());
		assertEquals("allUnitTestPass", testedObject.getAllUnitTestPass());
		assertEquals("noViolations", testedObject.getNoViolations());
		assertEquals("npmProxy", testedObject.getNpmProxy());
		assertEquals(unitTestTool, testedObject.getUnitTestTool());
		assertEquals(codeCoverageTool, testedObject.getCodeCoverageTool());
		assertEquals("buildValue", testedObject.getBuildValue());
		assertEquals("remoteDir", testedObject.getRemoteDir());
		assertEquals("privateKey", testedObject.getPrivateKey());
		assertEquals("report", testedObject.getReport());

		assertEquals("hostName22", testedObject.getHostName());
		assertEquals(port, testedObject.getPort());
		assertEquals("installGrunt22", testedObject.getInstallGrunt());
		assertEquals("multiModule22", testedObject.getMultiModule());
		assertEquals("exclude22", testedObject.getExclude());
		assertEquals("interval22", testedObject.getInterval());
		assertEquals("team22", testedObject.getTeam());
		assertEquals("preset22", testedObject.getPreset());
		assertEquals("serverUrl22", testedObject.getServerUrl());
		assertEquals("unitTestDir22", testedObject.getUnitTestDir());
		assertEquals("pafFilePath22", testedObject.getPafFilePath());
		assertEquals("analysisType22", testedObject.getOssAnalysisType());
		assertEquals("messageFlows22", testedObject.getMessageFlows());
		assertEquals("install22", testedObject.getInstall());
		assertEquals("relativePath22", testedObject.getRelativePath());
		assertEquals("avdName22", testedObject.getAvdName());
		assertEquals("destDB22", testedObject.getDestDB());
		assertEquals("mVNOPTS44", testedObject.getmVNOPTS());
		assertEquals("codeCoverage22", testedObject.getCodeCoverage());
		assertEquals("warPackaging22", testedObject.getWarPackaging());
		assertEquals("cdtOut22", testedObject.getCdtOut());

		assertEquals(reportMigration, testedObject.getPublishForms());
		assertEquals("execute22", testedObject.getExecute());
		assertEquals("repository22", testedObject.getRepository());
		assertEquals("ejbDescriptor22", testedObject.getEjbDescriptor());
		assertEquals("dbName22", testedObject.getDbName());
		assertEquals("transfer22", testedObject.getTransfer());
		assertEquals("moduleName22", testedObject.getModuleName());
		assertEquals("outputFolder22", testedObject.getOutputFolder());
		assertEquals("jarPackaging22", testedObject.getJarPackaging());
		assertEquals("mVNOPTS44", testedObject.getmVNOPTS());
		assertEquals("home22", testedObject.getHome());
		assertEquals("args22", testedObject.getArgs());
		assertEquals("globalGoals22", testedObject.getGlobalGoals());
		assertEquals(runScript, testedObject.getRunScript());
		assertEquals("groupId22", testedObject.getGroupId());
		assertEquals("customScript22", testedObject.getCustomScript());
		assertEquals("buildType22", testedObject.getBuildType());
		assertEquals("javaMainClass22", testedObject.getJavaMainClass());
		assertEquals("projectName22", testedObject.getProjectName());
		assertEquals("thirdPartyJar22", testedObject.getThirdPartyJar());
		assertEquals("jsonPath22", testedObject.getJsonPath());
		assertEquals("nexusUrl22", testedObject.getNexusUrl());
		assertEquals("password22", testedObject.getPassword());
		assertEquals("controlFlow22", testedObject.getControlFlow());
		assertEquals("dbType22", testedObject.getDbType());
		assertEquals("uiEarFileName22", testedObject.getUiEarFileName());
		assertEquals("envName22", testedObject.getEnvName());
		assertEquals("productKey22", testedObject.getProductKey());
		assertEquals("renaming22", testedObject.getRenaming());
		assertEquals("nexusType22", testedObject.getNexusType());
		assertEquals("userName22", testedObject.getUserName());
		assertEquals("cdtIn22", testedObject.getCdtIn());
		assertEquals("classFileName22", testedObject.getClassFileName());
		assertEquals("destPassword22", testedObject.getDestPassword());
		assertEquals(reportMigration, testedObject.getCodeAnalysis());
		assertEquals("unitTesting22", testedObject.getUnitTesting());
		assertEquals("serverMachine22", testedObject.getServerMachine());
		assertEquals("version22", testedObject.getVersion());
		assertEquals("compile22", testedObject.getCompile());
		assertEquals("clean22", testedObject.getClean());
		assertEquals("encryption22", testedObject.getEncryption());
		assertEquals(reportMigration, testedObject.getHostCTLMigration());
		assertEquals("codeFormatting22", testedObject.getCodeFormatting());
		assertEquals("codeAnalysisTragets22", testedObject.getCodeAnalysisTargets());
		assertEquals("unitTestTargets22", testedObject.getUnitTestTargets());
		assertEquals(reportMigration, testedObject.getWorkFlowPublish());
		assertEquals("customBuildXml22", testedObject.getCustomBuildXml());
		assertEquals(reportMigration, testedObject.getOaFileMigration());
		assertEquals("masterMachineName22", testedObject.getMasterMachineName());
		assertEquals("securityProjName22", testedObject.getSecurityProjName());
		assertEquals("unitTestCategory22", testedObject.getUnitTestCategory());
		assertEquals("testSettingFilePath22", testedObject.getTestSettingFilePath());
		assertEquals("dependencyFile22", testedObject.getDependencyFile());
		assertEquals("unitTestProjectName22", testedObject.getUnitTestProjectName());
		assertEquals("localmachineExecution22", testedObject.getLocalmachineExecution());
		assertEquals(reportMigration, testedObject.getReportMigration());
		assertEquals("securityAnalysis22", testedObject.getSecurityAnalysis());
		assertEquals("mailRecipients22", testedObject.getOssMailRecipients());
		assertEquals("distributionType22", testedObject.getOssDistributionType());
		assertEquals("checkmarxProjName22", testedObject.getCheckmarxProjName());
		assertEquals("integrationFileName22", testedObject.getIntegrationFileName());
		assertEquals("excludeFolders22", testedObject.getExcludeFolders());
		assertEquals("incrementalScan22", testedObject.getIncrementalScan());
		// No exception thrown

	}

	/**
	 * Test for method Module().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Module#Module()
	 * 
	 * 
	 */
	@Test
	public void testModule11() throws Throwable {
		Module testedObject = new Module();
		testedObject.setExcludeFolders("excludeFolders1");
		testedObject.setInstallGrunt("installGrunt1");
		testedObject.setMultiModule("multiModule1");
		testedObject.setExclude("exclude1");
		testedObject.setIncrementalScan("incrementalScan1");
		testedObject.setInterval("interval1");
		testedObject.setTeam("team1");
		testedObject.setPreset("preset1");
		testedObject.setServerUrl("serverUrl1");
		testedObject.setCheckmarxProjName("checkmarxProjName1");
		testedObject.setUnitTestDir("unitTestDir1");
		testedObject.setPafFilePath("pafFilePath1");
		testedObject.setOssMailRecipients("mailRecipients1");
		testedObject.setOssDistributionType("distributionType1");
		testedObject.setNexusType("analysisType1");
		testedObject.setRenaming("renaming1");
		testedObject.setControlFlow("controlFlow1");
		testedObject.setEncryption("encryption1");
		/*
		 * Remove Jira ALM testedObject.setRaiseJiraBug("raiseJiraBug1");
		 */
		testedObject.setUiEarFileName("uiEarFileName1");
		testedObject.setIntegrationFileName("integrationFileName1");
		testedObject.setReportMigration((List) null);
		testedObject.setPublishForms((List) null);
		testedObject.setHostCTLMigration((List) null);
		testedObject.setOaFileMigration((List) null);
		testedObject.setWorkFlowPublish((List) null);
		testedObject.setDbType("dbType1");
		testedObject.setProjectName("projectName1");
		testedObject.setCodeFormatting("codeFormatting1");
		testedObject.setCustomScript("customScript1");
		testedObject.setBuildType("buildType1");
		testedObject.setTransfer("transfer1");
		testedObject.setHostName("hostName1");
		testedObject.setUserName("userName1");
		testedObject.setPassword("password1");
		testedObject.setExecute("execute1");
		testedObject.setLocalmachineExecution("localmachineExecution1");
		testedObject.setDependencyFile("dependencyFile1");
		testedObject.setMasterMachineName("masterMachineName1");
		testedObject.setClassFileName("classFileName1");
		testedObject.setOutputFolder("outputFolder1");
		testedObject.setVersion("version1");
		testedObject.setCodeAnalysisTragets("codeAnalysisTragets1");
		testedObject.setUnitTestTargets("unitTestTargets1");
		testedObject.setSecurityProjName("securityProjName1");
		testedObject.setAvdName("avdName1");
		testedObject.setEnvName("envName1");
		testedObject.setDbName("dbName1");
		testedObject.setOssAnalysisType("analysisType1");
		testedObject.setPort(null);
		testedObject.setProductKey("productKey1");
		testedObject.setMessageFlows("messageFlows1");
		testedObject.setNexusType("nexusType1");
		testedObject.setNexusUrl("nexusUrl1");
		testedObject.setHome("home1");
		testedObject.setThirdPartyJar("thirdPartyJar1");
		testedObject.setCdtIn("cdtIn1");
		testedObject.setCdtOut("cdtOut1");
		testedObject.setDestDB("destDB1");
		testedObject.setDestPassword("destPassword1");
		testedObject.setRepository("repository1");
		testedObject.setRunScript((RunScript) null);
		testedObject.setmVNOPTS("mVNOPTS1");
		testedObject.setJsonPath("jsonPath1");
		testedObject.setGroupId("groupId1");
		testedObject.setModuleName("moduleName1");
		testedObject.setRelativePath("relativePath1");
		testedObject.setSecurityAnalysis("securityAnalysis1");
		testedObject.setCodeAnalysis((List) null);
		testedObject.setUnitTesting("unitTesting1");
		testedObject.setUnitTestProjectName("unitTestProjectName1");
		testedObject.setUnitTestCategory("unitTestCategory1");
		testedObject.setCodeCoverage("codeCoverage1");
		testedObject.setTestSettingFilePath("testSettingFilePath1");
		testedObject.setServerMachine("serverMachine1");
		testedObject.setCustomBuildXml("customBuildXml1");
		testedObject.setArgs("args1");
		testedObject.setCompile("compile1");
		testedObject.setJarPackaging("jarPackaging1");
		testedObject.setEjbDescriptor("ejbDescriptor1");
		testedObject.setJavaMainClass("javaMainClass1");
		testedObject.setWarPackaging("warPackaging1");
		testedObject.setGlobalGoals("globalGoals1");
		testedObject.setClean("clean1");
		testedObject.setInstall("install1");
		testedObject.setmVNOPTS("mVNOPTS2");
		assertEquals("hostName1", testedObject.getHostName());
		assertEquals(null, testedObject.getPort());
		assertEquals("installGrunt1", testedObject.getInstallGrunt());
		assertEquals("multiModule1", testedObject.getMultiModule());
		assertEquals("exclude1", testedObject.getExclude());
		assertEquals("interval1", testedObject.getInterval());
		assertEquals("team1", testedObject.getTeam());
		assertEquals("preset1", testedObject.getPreset());
		assertEquals("serverUrl1", testedObject.getServerUrl());
		assertEquals("unitTestDir1", testedObject.getUnitTestDir());
		assertEquals("pafFilePath1", testedObject.getPafFilePath());
		assertEquals("analysisType1", testedObject.getOssAnalysisType());
		assertEquals("messageFlows1", testedObject.getMessageFlows());
		assertEquals("install1", testedObject.getInstall());
		assertEquals("relativePath1", testedObject.getRelativePath());
		assertEquals("avdName1", testedObject.getAvdName());
		assertEquals("destDB1", testedObject.getDestDB());
		assertEquals("mVNOPTS2", testedObject.getmVNOPTS());
		assertEquals("codeCoverage1", testedObject.getCodeCoverage());
		assertEquals("warPackaging1", testedObject.getWarPackaging());
		assertEquals("cdtOut1", testedObject.getCdtOut());
		/*
		 * Remove Jira ALM assertEquals("raiseJiraBug1",
		 * testedObject.getRaiseJiraBug());
		 */
		assertEquals(null, testedObject.getPublishForms());
		assertEquals("execute1", testedObject.getExecute());
		assertEquals("repository1", testedObject.getRepository());
		assertEquals("ejbDescriptor1", testedObject.getEjbDescriptor());
		assertEquals("dbName1", testedObject.getDbName());
		assertEquals("transfer1", testedObject.getTransfer());
		assertEquals("moduleName1", testedObject.getModuleName());
		assertEquals("outputFolder1", testedObject.getOutputFolder());
		assertEquals("jarPackaging1", testedObject.getJarPackaging());
		assertEquals("mVNOPTS2", testedObject.getmVNOPTS());
		assertEquals("home1", testedObject.getHome());
		assertEquals("args1", testedObject.getArgs());
		assertEquals("globalGoals1", testedObject.getGlobalGoals());
		assertEquals(null, testedObject.getRunScript());
		assertEquals("groupId1", testedObject.getGroupId());
		assertEquals("customScript1", testedObject.getCustomScript());
		assertEquals("buildType1", testedObject.getBuildType());
		assertEquals("javaMainClass1", testedObject.getJavaMainClass());
		assertEquals("projectName1", testedObject.getProjectName());
		assertEquals("thirdPartyJar1", testedObject.getThirdPartyJar());
		assertEquals("jsonPath1", testedObject.getJsonPath());
		assertEquals("nexusUrl1", testedObject.getNexusUrl());
		assertEquals("password1", testedObject.getPassword());
		assertEquals("controlFlow1", testedObject.getControlFlow());
		assertEquals("dbType1", testedObject.getDbType());
		assertEquals("uiEarFileName1", testedObject.getUiEarFileName());
		assertEquals("envName1", testedObject.getEnvName());
		assertEquals("productKey1", testedObject.getProductKey());
		assertEquals("renaming1", testedObject.getRenaming());
		assertEquals("nexusType1", testedObject.getNexusType());
		assertEquals("userName1", testedObject.getUserName());
		assertEquals("cdtIn1", testedObject.getCdtIn());
		assertEquals("classFileName1", testedObject.getClassFileName());
		assertEquals("destPassword1", testedObject.getDestPassword());
		assertEquals(null, testedObject.getCodeAnalysis());
		assertEquals("unitTesting1", testedObject.getUnitTesting());
		assertEquals("serverMachine1", testedObject.getServerMachine());
		assertEquals("version1", testedObject.getVersion());
		assertEquals("compile1", testedObject.getCompile());
		assertEquals("clean1", testedObject.getClean());
		assertEquals("encryption1", testedObject.getEncryption());
		assertEquals(null, testedObject.getHostCTLMigration());
		assertEquals("codeFormatting1", testedObject.getCodeFormatting());
		assertEquals("codeAnalysisTragets1", testedObject.getCodeAnalysisTargets());
		assertEquals("unitTestTargets1", testedObject.getUnitTestTargets());
		assertEquals(null, testedObject.getWorkFlowPublish());
		assertEquals("customBuildXml1", testedObject.getCustomBuildXml());
		assertEquals(null, testedObject.getOaFileMigration());
		assertEquals("masterMachineName1", testedObject.getMasterMachineName());
		assertEquals("securityProjName1", testedObject.getSecurityProjName());
		assertEquals("unitTestCategory1", testedObject.getUnitTestCategory());
		assertEquals("testSettingFilePath1", testedObject.getTestSettingFilePath());
		assertEquals("dependencyFile1", testedObject.getDependencyFile());
		assertEquals("unitTestProjectName1", testedObject.getUnitTestProjectName());
		assertEquals("localmachineExecution1", testedObject.getLocalmachineExecution());
		assertEquals(null, testedObject.getReportMigration());
		assertEquals("securityAnalysis1", testedObject.getSecurityAnalysis());
		assertEquals("mailRecipients1", testedObject.getOssMailRecipients());
		assertEquals("distributionType1", testedObject.getOssDistributionType());
		assertEquals("checkmarxProjName1", testedObject.getCheckmarxProjName());
		assertEquals("integrationFileName1", testedObject.getIntegrationFileName());
		assertEquals("excludeFolders1", testedObject.getExcludeFolders());
		assertEquals("incrementalScan1", testedObject.getIncrementalScan());
		// No exception thrown

	}

	/**
	 * Test for method Module().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Module#Module()
	 * 
	 * 
	 */
	@Test
	public void testModule12() throws Throwable {
		Module testedObject = new Module();
		testedObject.setExcludeFolders("excludeFolders0");
		testedObject.setInstallGrunt("installGrunt0");
		testedObject.setMultiModule("multiModule0");
		testedObject.setExclude("exclude0");
		testedObject.setIncrementalScan("incrementalScan0");
		testedObject.setInterval("interval0");
		testedObject.setTeam("team0");
		testedObject.setPreset("preset0");
		testedObject.setServerUrl("serverUrl0");
		testedObject.setCheckmarxProjName("checkmarxProjName0");
		testedObject.setUnitTestDir("unitTestDir0");
		testedObject.setPafFilePath("pafFilePath0");
		testedObject.setOssMailRecipients("mailRecipients0");
		testedObject.setOssDistributionType("distributionType0");
		testedObject.setNexusType("analysisType0");
		testedObject.setRenaming("renaming0");
		testedObject.setControlFlow("controlFlow0");
		testedObject.setEncryption("encryption0");
		/*
		 * Remove Jira ALM testedObject.setRaiseJiraBug("raiseJiraBug0");
		 */
		testedObject.setUiEarFileName("uiEarFileName0");
		testedObject.setIntegrationFileName("integrationFileName0");
		testedObject.setReportMigration((List) null);
		testedObject.setPublishForms((List) null);
		testedObject.setHostCTLMigration((List) null);
		testedObject.setOaFileMigration((List) null);
		testedObject.setWorkFlowPublish((List) null);
		testedObject.setDbType("dbType0");
		testedObject.setProjectName("projectName0");
		testedObject.setCodeFormatting("codeFormatting0");
		testedObject.setCustomScript("customScript0");
		testedObject.setBuildType("buildType0");
		testedObject.setTransfer("transfer0");
		testedObject.setHostName("hostName0");
		testedObject.setUserName("userName0");
		testedObject.setPassword("password0");
		testedObject.setExecute("execute0");
		testedObject.setLocalmachineExecution("localmachineExecution0");
		testedObject.setDependencyFile("dependencyFile0");
		testedObject.setMasterMachineName("masterMachineName0");
		testedObject.setClassFileName("classFileName0");
		testedObject.setOutputFolder("outputFolder0");
		testedObject.setVersion("version0");
		testedObject.setCodeAnalysisTragets("codeAnalysisTragets0");
		testedObject.setUnitTestTargets("unitTestTargets0");
		testedObject.setSecurityProjName("securityProjName0");
		testedObject.setAvdName("avdName0");
		testedObject.setEnvName("envName0");
		testedObject.setDbName("dbName0");
		testedObject.setPort(null);
		testedObject.setProductKey("productKey0");
		testedObject.setMessageFlows("messageFlows0");
		testedObject.setNexusType("nexusType0");
		testedObject.setNexusUrl("nexusUrl0");
		testedObject.setHome("home0");
		testedObject.setThirdPartyJar("thirdPartyJar0");
		testedObject.setCdtIn("cdtIn0");
		testedObject.setCdtOut("cdtOut0");
		testedObject.setDestDB("destDB0");
		testedObject.setDestPassword("destPassword0");
		testedObject.setRepository("repository0");
		testedObject.setRunScript((RunScript) null);
		testedObject.setmVNOPTS("mVNOPTS0");
		testedObject.setJsonPath("jsonPath0");
		testedObject.setGroupId("groupId0");
		testedObject.setModuleName("moduleName0");
		testedObject.setRelativePath("relativePath0");
		testedObject.setSecurityAnalysis("securityAnalysis0");
		testedObject.setCodeAnalysis((List) null);
		testedObject.setUnitTesting("unitTesting0");
		testedObject.setUnitTestProjectName("unitTestProjectName0");
		testedObject.setUnitTestCategory("unitTestCategory0");
		testedObject.setCodeCoverage("codeCoverage0");
		testedObject.setTestSettingFilePath("testSettingFilePath0");
		testedObject.setServerMachine("serverMachine0");
		testedObject.setCustomBuildXml("customBuildXml0");
		testedObject.setArgs("args0");
		testedObject.setCompile("compile0");
		testedObject.setOssAnalysisType("analysisType0");
		testedObject.setJarPackaging("jarPackaging0");
		testedObject.setEjbDescriptor("ejbDescriptor0");
		testedObject.setJavaMainClass("javaMainClass0");
		testedObject.setWarPackaging("warPackaging0");
		testedObject.setGlobalGoals("globalGoals0");
		testedObject.setClean("clean0");
		testedObject.setInstall("install0");
		testedObject.setmVNOPTS((String) null);
		assertEquals("hostName0", testedObject.getHostName());
		assertEquals(null, testedObject.getPort());
		assertEquals("installGrunt0", testedObject.getInstallGrunt());
		assertEquals("multiModule0", testedObject.getMultiModule());
		assertEquals("exclude0", testedObject.getExclude());
		assertEquals("interval0", testedObject.getInterval());
		assertEquals("team0", testedObject.getTeam());
		assertEquals("preset0", testedObject.getPreset());
		assertEquals("serverUrl0", testedObject.getServerUrl());
		assertEquals("unitTestDir0", testedObject.getUnitTestDir());
		assertEquals("pafFilePath0", testedObject.getPafFilePath());
		assertEquals("analysisType0", testedObject.getOssAnalysisType());
		assertEquals("messageFlows0", testedObject.getMessageFlows());
		assertEquals("install0", testedObject.getInstall());
		assertEquals("relativePath0", testedObject.getRelativePath());
		assertEquals("avdName0", testedObject.getAvdName());
		assertEquals("destDB0", testedObject.getDestDB());
		assertEquals(null, testedObject.getmVNOPTS());
		assertEquals("codeCoverage0", testedObject.getCodeCoverage());
		assertEquals("warPackaging0", testedObject.getWarPackaging());
		assertEquals("cdtOut0", testedObject.getCdtOut());
		/*
		 * Remove Jira ALM assertEquals("raiseJiraBug0",
		 * testedObject.getRaiseJiraBug());
		 */
		assertEquals(null, testedObject.getPublishForms());
		assertEquals("execute0", testedObject.getExecute());
		assertEquals("repository0", testedObject.getRepository());
		assertEquals("ejbDescriptor0", testedObject.getEjbDescriptor());
		assertEquals("dbName0", testedObject.getDbName());
		assertEquals("transfer0", testedObject.getTransfer());
		assertEquals("moduleName0", testedObject.getModuleName());
		assertEquals("outputFolder0", testedObject.getOutputFolder());
		assertEquals("jarPackaging0", testedObject.getJarPackaging());
		assertEquals(null, testedObject.getmVNOPTS());
		assertEquals("home0", testedObject.getHome());
		assertEquals("args0", testedObject.getArgs());
		assertEquals("globalGoals0", testedObject.getGlobalGoals());
		assertEquals(null, testedObject.getRunScript());
		assertEquals("groupId0", testedObject.getGroupId());
		assertEquals("customScript0", testedObject.getCustomScript());
		assertEquals("buildType0", testedObject.getBuildType());
		assertEquals("javaMainClass0", testedObject.getJavaMainClass());
		assertEquals("projectName0", testedObject.getProjectName());
		assertEquals("thirdPartyJar0", testedObject.getThirdPartyJar());
		assertEquals("jsonPath0", testedObject.getJsonPath());
		assertEquals("nexusUrl0", testedObject.getNexusUrl());
		assertEquals("password0", testedObject.getPassword());
		assertEquals("controlFlow0", testedObject.getControlFlow());
		assertEquals("dbType0", testedObject.getDbType());
		assertEquals("uiEarFileName0", testedObject.getUiEarFileName());
		assertEquals("envName0", testedObject.getEnvName());
		assertEquals("productKey0", testedObject.getProductKey());
		assertEquals("renaming0", testedObject.getRenaming());
		assertEquals("nexusType0", testedObject.getNexusType());
		assertEquals("userName0", testedObject.getUserName());
		assertEquals("cdtIn0", testedObject.getCdtIn());
		assertEquals("classFileName0", testedObject.getClassFileName());
		assertEquals("destPassword0", testedObject.getDestPassword());
		assertEquals(null, testedObject.getCodeAnalysis());
		assertEquals("unitTesting0", testedObject.getUnitTesting());
		assertEquals("serverMachine0", testedObject.getServerMachine());
		assertEquals("version0", testedObject.getVersion());
		assertEquals("compile0", testedObject.getCompile());
		assertEquals("clean0", testedObject.getClean());
		assertEquals("encryption0", testedObject.getEncryption());
		assertEquals(null, testedObject.getHostCTLMigration());
		assertEquals("codeFormatting0", testedObject.getCodeFormatting());
		assertEquals("codeAnalysisTragets0", testedObject.getCodeAnalysisTargets());
		assertEquals("unitTestTargets0", testedObject.getUnitTestTargets());
		assertEquals(null, testedObject.getWorkFlowPublish());
		assertEquals("customBuildXml0", testedObject.getCustomBuildXml());
		assertEquals(null, testedObject.getOaFileMigration());
		assertEquals("masterMachineName0", testedObject.getMasterMachineName());
		assertEquals("securityProjName0", testedObject.getSecurityProjName());
		assertEquals("unitTestCategory0", testedObject.getUnitTestCategory());
		assertEquals("testSettingFilePath0", testedObject.getTestSettingFilePath());
		assertEquals("dependencyFile0", testedObject.getDependencyFile());
		assertEquals("unitTestProjectName0", testedObject.getUnitTestProjectName());
		assertEquals("localmachineExecution0", testedObject.getLocalmachineExecution());
		assertEquals(null, testedObject.getReportMigration());
		assertEquals("securityAnalysis0", testedObject.getSecurityAnalysis());
		assertEquals("mailRecipients0", testedObject.getOssMailRecipients());
		assertEquals("distributionType0", testedObject.getOssDistributionType());
		assertEquals("checkmarxProjName0", testedObject.getCheckmarxProjName());
		assertEquals("integrationFileName0", testedObject.getIntegrationFileName());
		assertEquals("excludeFolders0", testedObject.getExcludeFolders());
		assertEquals("incrementalScan0", testedObject.getIncrementalScan());
		// No exception thrown

	}

	/**
	 * Test for method Module().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Module#Module()
	 * 
	 * 
	 */
	@Test
	public void testModule13() throws Throwable {
		Module testedObject = new Module();
		assertEquals(null, testedObject.getHostName());
		assertEquals(null, testedObject.getPort());
		assertEquals(null, testedObject.getInstallGrunt());
		assertEquals(null, testedObject.getMultiModule());
		assertEquals(null, testedObject.getExclude());
		assertEquals(null, testedObject.getInterval());
		assertEquals(null, testedObject.getTeam());
		assertEquals(null, testedObject.getPreset());
		assertEquals(null, testedObject.getServerUrl());
		assertEquals(null, testedObject.getUnitTestDir());
		assertEquals(null, testedObject.getPafFilePath());
		assertEquals(null, testedObject.getOssAnalysisType());
		assertEquals(null, testedObject.getMessageFlows());
		assertEquals(null, testedObject.getInstall());
		assertEquals(null, testedObject.getRelativePath());
		assertEquals(null, testedObject.getAvdName());
		assertEquals(null, testedObject.getDestDB());
		assertEquals(null, testedObject.getmVNOPTS());
		assertEquals(null, testedObject.getCodeCoverage());
		assertEquals(null, testedObject.getWarPackaging());
		assertEquals(null, testedObject.getCdtOut());
		/*
		 * Remove Jira ALM assertEquals(null, testedObject.getRaiseJiraBug()); //
		 * jtest_unverified
		 */
		assertEquals(null, testedObject.getPublishForms());
		assertEquals(null, testedObject.getExecute());
		assertEquals(null, testedObject.getRepository());
		assertEquals(null, testedObject.getEjbDescriptor());
		assertEquals(null, testedObject.getDbName());
		assertEquals(null, testedObject.getTransfer());
		assertEquals(null, testedObject.getModuleName());
		assertEquals(null, testedObject.getOutputFolder());
		assertEquals(null, testedObject.getJarPackaging());
		assertEquals(null, testedObject.getmVNOPTS());
		assertEquals(null, testedObject.getHome());
		assertEquals(null, testedObject.getArgs());
		assertEquals(null, testedObject.getGlobalGoals());
		assertEquals(null, testedObject.getRunScript());
		assertEquals(null, testedObject.getGroupId());
		assertEquals(null, testedObject.getCustomScript());
		assertEquals(null, testedObject.getBuildType());
		assertEquals(null, testedObject.getJavaMainClass());
		assertEquals(null, testedObject.getProjectName());
		assertEquals(null, testedObject.getThirdPartyJar());
		assertEquals(null, testedObject.getJsonPath());
		assertEquals(null, testedObject.getNexusUrl());
		assertEquals(null, testedObject.getPassword());
		assertEquals(null, testedObject.getControlFlow());
		assertEquals(null, testedObject.getDbType());
		assertEquals(null, testedObject.getUiEarFileName());
		assertEquals(null, testedObject.getEnvName());
		assertEquals(null, testedObject.getProductKey());
		assertEquals(null, testedObject.getRenaming());
		assertEquals(null, testedObject.getNexusType());
		assertEquals(null, testedObject.getUserName());
		assertEquals(null, testedObject.getCdtIn());
		assertEquals(null, testedObject.getClassFileName());
		assertEquals(null, testedObject.getDestPassword());
		assertEquals(null, testedObject.getCodeAnalysis());
		assertEquals(null, testedObject.getUnitTesting());
		assertEquals(null, testedObject.getServerMachine());
		assertEquals(null, testedObject.getVersion());
		assertEquals(null, testedObject.getCompile());
		assertEquals(null, testedObject.getClean());
		assertEquals(null, testedObject.getEncryption());
		assertEquals(null, testedObject.getHostCTLMigration());
		assertEquals(null, testedObject.getCodeFormatting());
		assertEquals(null, testedObject.getCodeAnalysisTargets());
		assertEquals(null, testedObject.getUnitTestTargets());
		assertEquals(null, testedObject.getWorkFlowPublish());
		assertEquals(null, testedObject.getCustomBuildXml());
		assertEquals(null, testedObject.getOaFileMigration());
		assertEquals(null, testedObject.getMasterMachineName());
		assertEquals(null, testedObject.getSecurityProjName());
		assertEquals(null, testedObject.getUnitTestCategory());
		assertEquals(null, testedObject.getTestSettingFilePath());
		assertEquals(null, testedObject.getDependencyFile());
		assertEquals(null, testedObject.getUnitTestProjectName());
		assertEquals(null, testedObject.getLocalmachineExecution());
		assertEquals(null, testedObject.getReportMigration());
		assertEquals(null, testedObject.getSecurityAnalysis());
		assertEquals(null, testedObject.getOssMailRecipients());
		assertEquals(null, testedObject.getOssDistributionType());
		assertEquals(null, testedObject.getCheckmarxProjName());
		assertEquals(null, testedObject.getIntegrationFileName());
		assertEquals(null, testedObject.getExcludeFolders());
		assertEquals(null, testedObject.getIncrementalScan());
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ModuleTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.buildInfo.ModuleTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return Module.class;
	}
}
