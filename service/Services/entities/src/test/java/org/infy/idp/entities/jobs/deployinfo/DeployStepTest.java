/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.common.AbortScript;
import org.infy.idp.entities.jobs.common.Proxy;
import org.infy.idp.entities.jobs.common.RunScript;
import org.junit.Test;

/**
 * DeployStepTest is a test class for DeployStep
 *
 * @see org.infy.idp.entities.jobs.deployinfo.DeployStep
 * 
 */
public class DeployStepTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public DeployStepTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method DeployStep().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#DeployStep()
	 * 
	 * 
	 */
	@Test
	public void testDeployStep0() throws Throwable {
		DeployStep testedObject = new DeployStep();
		testedObject.setPathToFiles("pathToFiles22");
		Integer port = new Integer(-2147483648);
		testedObject.setPort(port);
		testedObject.setParameters("parameters22");
		RunScript deployScript = new RunScript();
		testedObject.setRunScript(deployScript);
		DeployToContainer websiteName = new DeployToContainer();
		testedObject.setWebsiteName(websiteName);
		DeployToContainer appPoolName = new DeployToContainer();
		testedObject.setAppPoolName(appPoolName);
		DeployToContainer destServerPath = new DeployToContainer();
		testedObject.setDestServerPath(destServerPath);
		testedObject.setStepName("stepName22");
		testedObject.setApplicationUserName("applicationUserName22");
		testedObject.setAdminPassword("adminPassword22");
		testedObject.setApplicationPassword("applicationPassword22");
		DeployToContainer deployToContainer = new DeployToContainer();
		testedObject.setDeployToContainer(deployToContainer);
		testedObject.setAdminUserName("adminUserName21");
		List<String> artifactsToBeDeployed = new ArrayList<String>();
		testedObject.setArtifactsToBeDeployed(artifactsToBeDeployed);
		testedObject.setS3location("s3location");
		testedObject.setHostName("hostName");
		testedObject.setPrivateKey("privateKey");
		testedObject.setArtifact("artifact");
		testedObject.setPushToRepo("pushToRepo");
		testedObject.setTagName("tagName");
		testedObject.setApplicationPort(100);
		testedObject.setDockerFilePath("dockerFilePath");
		testedObject.setRepoUrl("repoUrl");
		testedObject.setUserName("userName");
		testedObject.setPassword("password");
		testedObject.setPullFromRepo("pullFromRepo");
		testedObject.setDockerPort(9090);

		testedObject.setAppName("appName");
		testedObject.setAppPackName("appPackName");
		testedObject.setBuildproperties("buildproperties");
		testedObject.setDbName("dbName");
		testedObject.setDbPwd("dbPwd");
		testedObject.setDbServName("dbServName");
		testedObject.setDbUName("dbUName");
		testedObject.setDbuserNameOTM("dbuserNameOTM");
		testedObject.setTomHost("tomHost");
		testedObject.setTomPort("tomPort");
		testedObject.setTomPwd("tomPwd");
		testedObject.setTomUname("tomUname");

		AbortScript ab = new AbortScript();
		testedObject.setAbortScript(ab);
		testedObject.setTimeout("timeout");
		testedObject.setSqlFolder("folder");
		testedObject.setDbhostNameOTM("hostNameOTM");
		testedObject.setDbpasswordOTM("passwordOTM");
		testedObject.setDbPort("8085");
		testedObject.setDbSid("sid");
		testedObject.setOafFolderName("oaf");
		testedObject.setDbpassword("pwd");
		testedObject.setTargetUsername("admin");
		testedObject.setTargetPassword("pwd");
		Proxy p = new Proxy();
		testedObject.setProxy(p);
		testedObject.setSibelDatImport("import");
		testedObject.setDatFileImport("import");
		testedObject.setServiceName("service");
		DeployDatabase deployDatabase = new DeployDatabase();
		testedObject.setDeployDatabase(deployDatabase);
		testedObject.setDeployOperation("deploy");
		testedObject.setSourceFolderPath("folder");
		testedObject.setDestinationMachineName("machine");
		testedObject.setDestinationFolder("folder");
		testedObject.setDatabaseName("db");
		testedObject.setDatabaseUsername("admin");
		testedObject.setDatabasePassword("pwd");
		testedObject.setDestinationBranch("branch");
		testedObject.setDestinationEnv("dev");
		testedObject.setDatFileName("file");
		testedObject.setToolsRoot("root");
		testedObject.setServerLocalPath("local");
		testedObject.setDdltmpUsername("admin");
		testedObject.setDdltmpPassword("pwd");
		testedObject.setSiebelServer("server");
		testedObject.setSourceEnvironment("dev");
		testedObject.setDatLocalPath("local");
		testedObject.setPstoolspath("ps");
		testedObject.setRepIMPEXPExePath("exe");
		testedObject.setDatExportUsername("admin");
		testedObject.setDatExportPassword("pwd");
		testedObject.setDataSource("data");
		testedObject.setTableOwner("owner");
		testedObject.setDeployFuntion("function");
		testedObject.setSrfHostName("host");
		testedObject.setSrfPassword("pwd");
		testedObject.setSrfSrcPath("path");
		testedObject.setSrfUserName("admin");
		testedObject.setSourceFolder("folder");
		testedObject.setSourceFolderPath("path");
		testedObject.setSourceFolderShared("shared");
		testedObject.setAdmPassword("pwd");
		testedObject.setAdmUserName("admin");
		testedObject.setAdmHostName("host");
		testedObject.setStaticHostName("host");
		testedObject.setStaticSrcPath("src");
		testedObject.setStaticDestinationPath("path");
		testedObject.setStaticUserName("admin");
		testedObject.setStaticPassword("pwd");
		testedObject.setDbuserName("admin");
		testedObject.setDbPassword("pwd");
		testedObject.setDbhostName("host");
		testedObject.setBizScriptPath("script");
		testedObject.setBizPassword("pwd");
		testedObject.setBizserviceAccount("service");
		testedObject.setDeployUserName("admin");
		testedObject.setDeployPassword("pwd");
		testedObject.setDbOwner("owner");
		testedObject.setDbOwnerPassword("pwd");
		testedObject.setSqlFileName("sql");
		testedObject.setReportName("report");
		testedObject.setReportPath("path");
		testedObject.setFormName("form");
		testedObject.setFormFTPPath("path");
		testedObject.setFormsBasePath("path");
		testedObject.setFormsEnvFile("file");
		testedObject.setFormsDbPass("pwd");
		testedObject.setHostCtlfile("file");
		testedObject.setReportPathCtl("path");
		testedObject.setOaMediaFile("file");
		testedObject.setReportPathOa("path");
		testedObject.setWorkFlowName("workflow");
		testedObject.setWorkFlowBasePath("path");
		testedObject.setWorkFlowDbPass("pwd");
		testedObject.setWorkFlowEnvFile("file");
		testedObject.setWorkFlowFTPPath("path");
		testedObject.setCustomTopPath("path");
		testedObject.setAolBasePath("path");
		testedObject.setAolEnvFile("file");
		testedObject.setLoadInFile("file");
		testedObject.setUpDownFile("file");
		testedObject.setAppsPass("pwd");
		testedObject.setIpcUserName("admin");
		testedObject.setIpcPassword("pwd");
		testedObject.setIpcHostName("host");
		testedObject.setTargetRepo("repo");
		testedObject.setTargetFolder("folder");
		testedObject.setTargetFolderShared("shared");
		testedObject.setTargetPassword("pwd");
		testedObject.setTargetUsername("admin");
		testedObject.setImpObjshareXml("xml");
		testedObject.setImpObjXml("xml");
		testedObject.setMachine("machine");
		testedObject.setDeployOS("os");
		testedObject.setInstallationFolder("folder");
		testedObject.setMuleServerGroup("server");
		testedObject.setDeploymentOption("deploy");

		assertEquals(ab, testedObject.getAbortScript());
		assertEquals("timeout", testedObject.getTimeout());

		assertEquals("appName", testedObject.getAppName());
		assertEquals("appPackName", testedObject.getAppPackName());
		assertEquals("buildproperties", testedObject.getBuildproperties());
		assertEquals("dbName", testedObject.getDbName());
		assertEquals("dbPwd", testedObject.getDbPwd());
		assertEquals("dbServName", testedObject.getDbServName());
		assertEquals("dbUName", testedObject.getDbUName());
		assertEquals("dbuserNameOTM", testedObject.getDbuserNameOTM());
		assertEquals("tomHost", testedObject.getTomHost());
		assertEquals("tomPort", testedObject.getTomPort());
		assertEquals("tomPwd", testedObject.getTomPwd());
		assertEquals("tomUname", testedObject.getTomUname());

		assertEquals("folder", testedObject.getSqlFolder());
		assertEquals("passwordOTM", testedObject.getDbpasswordOTM());
		assertEquals("hostNameOTM", testedObject.getDbhostNameOTM());
		assertEquals("8085", testedObject.getDbPort());
		assertEquals("sid", testedObject.getDbSid());
		assertEquals("oaf", testedObject.getOafFolderName());
		assertEquals("pwd", testedObject.getDbPassword());
		assertEquals("admin", testedObject.getTargetUsername());
		assertEquals("pwd", testedObject.getTargetPassword());
		assertEquals(p, testedObject.getProxy());
		assertEquals("import", testedObject.getSibelDatImport());
		assertEquals("import", testedObject.getDatFileImport());
		assertEquals("service", testedObject.getServiceName());
		assertEquals(deployDatabase, testedObject.getDeployDatabase());
		assertEquals("deploy", testedObject.getDeployOperation());
		assertEquals("path", testedObject.getSourceFolderPath());
		assertEquals("machine", testedObject.getDestinationMachineName());
		assertEquals("folder", testedObject.getDestinationFolder());
		assertEquals("db", testedObject.getDatabaseName());
		assertEquals("admin", testedObject.getDatabaseUsername());
		assertEquals("pwd", testedObject.getDatabasePassword());
		assertEquals("branch", testedObject.getDestinationBranch());
		assertEquals("dev", testedObject.getDestinationEnv());
		assertEquals("file", testedObject.getDatFileName());
		assertEquals("root", testedObject.getToolsRoot());
		assertEquals("local", testedObject.getServerLocalPath());
		assertEquals("admin", testedObject.getDdltmpUsername());
		assertEquals("pwd", testedObject.getDdltmpPassword());
		assertEquals("server", testedObject.getSiebelServer());
		assertEquals("dev", testedObject.getSourceEnvironment());
		assertEquals("local", testedObject.getDatLocalPath());
		assertEquals("ps", testedObject.getPstoolspath());
		assertEquals("exe", testedObject.getRepIMPEXPExePath());
		assertEquals("admin", testedObject.getDatExportUsername());
		assertEquals("pwd", testedObject.getDatExportPassword());
		assertEquals("data", testedObject.getDataSource());
		assertEquals("owner", testedObject.getTableOwner());
		assertEquals("function", testedObject.getDeployFuntion());
		assertEquals("host", testedObject.getSrfHostName());
		assertEquals("path", testedObject.getSrfSrcPath());
		assertEquals("admin", testedObject.getSrfUserName());
		assertEquals("pwd", testedObject.getSrfPassword());
		assertEquals("host", testedObject.getAdmHostName());
		assertEquals("admin", testedObject.getAdmUserName());
		assertEquals("pwd", testedObject.getAdmPassword());
		assertEquals("host", testedObject.getStaticHostName());
		assertEquals("src", testedObject.getStaticSrcPath());
		assertEquals("path", testedObject.getStaticDestinationPath());
		assertEquals("admin", testedObject.getStaticUserName());
		assertEquals("pwd", testedObject.getStaticPassword());
		assertEquals("admin", testedObject.getDbuserName());
		assertEquals("pwd", testedObject.getDbpassword());
		assertEquals("host", testedObject.getDbhostName());
		assertEquals("script", testedObject.getBizScriptPath());
		assertEquals("service", testedObject.getBizserviceAccount());
		assertEquals("pwd", testedObject.getBizPassword());
		assertEquals("admin", testedObject.getDeployUserName());
		assertEquals("pwd", testedObject.getDeployPassword());
		assertEquals("owner", testedObject.getDbOwner());
		assertEquals("pwd", testedObject.getDbOwnerPassword());
		assertEquals("sql", testedObject.getSqlFileName());
		assertEquals("report", testedObject.getReportName());
		assertEquals("path", testedObject.getReportPath());
		assertEquals("form", testedObject.getFormName());
		assertEquals("path", testedObject.getFormFTPPath());
		assertEquals("path", testedObject.getFormsBasePath());
		assertEquals("file", testedObject.getFormsEnvFile());
		assertEquals("pwd", testedObject.getFormsDbPass());
		assertEquals("file", testedObject.getHostCtlfile());
		assertEquals("path", testedObject.getReportPathCtl());
		assertEquals("file", testedObject.getOaMediaFile());
		assertEquals("path", testedObject.getReportPathOa());
		assertEquals("workflow", testedObject.getWorkFlowName());
		assertEquals("path", testedObject.getWorkFlowFTPPath());
		assertEquals("path", testedObject.getWorkFlowBasePath());
		assertEquals("file", testedObject.getWorkFlowEnvFile());
		assertEquals("pwd", testedObject.getWorkFlowDbPass());
		assertEquals("path", testedObject.getCustomTopPath());
		assertEquals("path", testedObject.getAolBasePath());
		assertEquals("file", testedObject.getAolEnvFile());
		assertEquals("file", testedObject.getLoadInFile());
		assertEquals("file", testedObject.getUpDownFile());
		assertEquals("pwd", testedObject.getAppsPass());
		assertEquals("admin", testedObject.getIpcUserName());
		assertEquals("pwd", testedObject.getIpcPassword());
		assertEquals("host", testedObject.getIpcHostName());
		assertEquals("repo", testedObject.getTargetRepo());
		assertEquals("xml", testedObject.getImpObjXml());
		testedObject.setControlFile("file");
		testedObject.setControlFileShared("shared");
		assertEquals("shared", testedObject.getControlFileShared());
		assertEquals("file", testedObject.getControlFile());
		testedObject.setSourceRepo("repo");
		assertEquals("repo", testedObject.getSourceRepo());
		assertEquals("folder", testedObject.getSourceFolder());
		assertEquals("shared", testedObject.getSourceFolderShared());
		assertEquals("shared", testedObject.getTargetFolderShared());
		assertEquals("folder", testedObject.getTargetFolder());
		assertEquals("xml", testedObject.getImpObjshareXml());
		assertEquals("machine", testedObject.getMachine());
		assertEquals("os", testedObject.getDeployOS());
		assertEquals("folder", testedObject.getInstallationFolder());
		assertEquals("server", testedObject.getMuleServerGroup());
		assertEquals("deploy", testedObject.getDeploymentOption());

		assertEquals("hostName", testedObject.getHostName());
		assertEquals("privateKey", testedObject.getPrivateKey());
		assertEquals("artifact", testedObject.getArtifact());
		assertEquals("pushToRepo", testedObject.getPushToRepo());
		assertEquals("tagName", testedObject.getTagName());
		assertEquals((Integer) 100, testedObject.getApplicationPort());
		assertEquals("dockerFilePath", testedObject.getDockerFilePath());
		assertEquals("repoUrl", testedObject.getRepoUrl());
		assertEquals("userName", testedObject.getUserName());
		assertEquals("password", testedObject.getPassword());
		assertEquals((Integer) 9090, testedObject.getDockerPort());
		assertEquals("pullFromRepo", testedObject.getPullFromRepo());
		assertEquals("s3location", testedObject.getS3location());

		assertEquals(artifactsToBeDeployed, testedObject.getArtifactsToBeDeployed());
		assertEquals("parameters22", testedObject.getParameters());
		assertEquals(port, testedObject.getPort());
		assertEquals(appPoolName, testedObject.getAppPoolName());
		assertEquals(websiteName, testedObject.getWebsiteName());
		assertEquals(deployScript, testedObject.getRunScript());
		assertEquals(deployToContainer, testedObject.getDeployToContainer());
		assertEquals(destServerPath, testedObject.getDestServerPath());
		assertEquals("adminPassword22", testedObject.getAdminPassword());
		assertEquals("pathToFiles22", testedObject.getPathToFiles());
		assertEquals("stepName22", testedObject.getStepName());
		assertEquals("adminUserName21", testedObject.getAdminUserName());
		assertEquals("applicationPassword22", testedObject.getApplicationPassword());
		assertEquals("applicationUserName22", testedObject.getApplicationUserName());
		// No exception thrown

	}

	/**
	 * Test for method DeployStep().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#DeployStep()
	 * 
	 * 
	 */
	@Test
	public void testDeployStep11() throws Throwable {
		DeployStep testedObject = new DeployStep();
		testedObject.setPathToFiles("pathToFiles1");
		testedObject.setPort((Integer) null);
		testedObject.setParameters("parameters1");
		testedObject.setRunScript((RunScript) null);
		testedObject.setWebsiteName((DeployToContainer) null);
		testedObject.setAppPoolName((DeployToContainer) null);
		testedObject.setDestServerPath((DeployToContainer) null);
		testedObject.setStepName("stepName1");
		testedObject.setApplicationUserName("applicationUserName1");
		testedObject.setAdminPassword("adminPassword1");
		testedObject.setApplicationPassword("applicationPassword1");
		testedObject.setDeployToContainer((DeployToContainer) null);
		testedObject.setAdminUserName("adminUserName0");
		assertEquals("parameters1", testedObject.getParameters());
		assertEquals(null, testedObject.getPort());
		assertEquals(null, testedObject.getAppPoolName());
		assertEquals(null, testedObject.getWebsiteName());
		assertEquals(null, testedObject.getRunScript());
		assertEquals(null, testedObject.getDeployToContainer());
		assertEquals(null, testedObject.getDestServerPath());
		assertEquals("adminPassword1", testedObject.getAdminPassword());
		assertEquals("pathToFiles1", testedObject.getPathToFiles());
		assertEquals("stepName1", testedObject.getStepName());
		assertEquals("adminUserName0", testedObject.getAdminUserName());
		assertEquals("applicationPassword1", testedObject.getApplicationPassword());
		assertEquals("applicationUserName1", testedObject.getApplicationUserName());
		// No exception thrown

	}

	/**
	 * Test for method DeployStep().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#DeployStep()
	 * 
	 * 
	 */
	@Test
	public void testDeployStep12() throws Throwable {
		DeployStep testedObject = new DeployStep();
		testedObject.setPathToFiles("pathToFiles0");
		testedObject.setPort((Integer) null);
		testedObject.setParameters("parameters0");
		testedObject.setRunScript((RunScript) null);
		testedObject.setWebsiteName((DeployToContainer) null);
		testedObject.setAppPoolName((DeployToContainer) null);
		testedObject.setDestServerPath((DeployToContainer) null);
		testedObject.setStepName("stepName0");
		testedObject.setApplicationUserName("applicationUserName0");
		testedObject.setAdminPassword("adminPassword0");
		testedObject.setApplicationPassword("applicationPassword0");
		testedObject.setDeployToContainer((DeployToContainer) null);
		testedObject.setAdminUserName((String) null);
		assertEquals("parameters0", testedObject.getParameters());
		assertEquals(null, testedObject.getPort());
		assertEquals(null, testedObject.getAppPoolName());
		assertEquals(null, testedObject.getWebsiteName());
		assertEquals(null, testedObject.getRunScript());
		assertEquals(null, testedObject.getDeployToContainer());
		assertEquals(null, testedObject.getDestServerPath());
		assertEquals("adminPassword0", testedObject.getAdminPassword());
		assertEquals("pathToFiles0", testedObject.getPathToFiles());
		assertEquals("stepName0", testedObject.getStepName());
		assertEquals(null, testedObject.getAdminUserName());
		assertEquals("applicationPassword0", testedObject.getApplicationPassword());
		assertEquals("applicationUserName0", testedObject.getApplicationUserName());
		// No exception thrown

	}

	/**
	 * Test for method DeployStep().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#DeployStep()
	 * 
	 * 
	 */
	@Test
	public void testDeployStep13() throws Throwable {
		DeployStep testedObject = new DeployStep();
		assertEquals(null, testedObject.getParameters());
		assertEquals(null, testedObject.getPort());
		assertEquals(null, testedObject.getAppPoolName());
		assertEquals(null, testedObject.getWebsiteName());
		assertEquals(null, testedObject.getRunScript());
		assertEquals(null, testedObject.getDeployToContainer());
		assertEquals(null, testedObject.getDestServerPath());
		assertEquals(null, testedObject.getAdminPassword());
		assertEquals(null, testedObject.getPathToFiles());
		assertEquals(null, testedObject.getStepName());
		assertEquals(null, testedObject.getAdminUserName());
		assertEquals(null, testedObject.getApplicationPassword());
		assertEquals(null, testedObject.getApplicationUserName());
		// No exception thrown

	}

	/**
	 * Test for method getAppPoolName().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#getAppPoolName()
	 * 
	 * 
	 */
	@Test
	public void testGetAppPoolName0() throws Throwable {
		DeployStep testedObject = new DeployStep();
		DeployToContainer result = testedObject.getAppPoolName();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Test for method getDeployToContainer().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#getDeployToContainer()
	 * 
	 * 
	 */
	@Test
	public void testGetDeployToContainer0() throws Throwable {
		DeployStep testedObject = new DeployStep();
		DeployToContainer result = testedObject.getDeployToContainer();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Test for method getDestServerPath().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#getDestServerPath()
	 * 
	 * 
	 */
	@Test
	public void testGetDestServerPath0() throws Throwable {
		DeployStep testedObject = new DeployStep();
		DeployToContainer result = testedObject.getDestServerPath();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Test for method getPort().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#getPort()
	 * 
	 * 
	 */
	@Test
	public void testGetPort0() throws Throwable {
		DeployStep testedObject = new DeployStep();
		Integer result = testedObject.getPort();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Test for method getRunScript().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#getRunScript()
	 * 
	 * 
	 */
	@Test
	public void testGetRunScript0() throws Throwable {
		DeployStep testedObject = new DeployStep();
		RunScript result = testedObject.getRunScript();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Test for method getWebsiteName().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployStep#getWebsiteName()
	 * 
	 * 
	 */
	@Test
	public void testGetWebsiteName0() throws Throwable {
		DeployStep testedObject = new DeployStep();
		DeployToContainer result = testedObject.getWebsiteName();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java DeployStepTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.deployInfo.DeployStepTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return DeployStep.class;
	}
}
