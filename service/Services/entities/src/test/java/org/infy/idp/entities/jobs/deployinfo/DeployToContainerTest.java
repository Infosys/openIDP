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

import org.infy.idp.entities.jobs.common.Migration;
import org.junit.Test;

/**
 * DeployToContainerTest is a test class for DeployToContainer
 *
 * @see org.infy.idp.entities.jobs.deployinfo.DeployToContainer
 * 
 */
public class DeployToContainerTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public DeployToContainerTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method DeployToContainer().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployToContainer#DeployToContainer()
	 * 
	 * 
	 */
	@Test
	public void testDeployToContainer0() throws Throwable {
		DeployToContainer testedObject = new DeployToContainer();
		testedObject.setApplicationName("applicationName22");
		testedObject.setNarOS("narOS22");
		testedObject.setDeployedFolder("deployedFolder22");
		testedObject.setProfilePath("profilePath22");
		testedObject.setDbcScriptName("dbcScriptName22");
		testedObject.setXmlScripts("xmlScripts22");
		testedObject.setImportReports("importReports22");
		testedObject.setSrcEnvName("dev");
		testedObject.setPairName("dev-sit");
		testedObject.setFileName("exportdata.xml");
		testedObject.setLauncherActivity("main");
		testedObject.setToken("token");
		testedObject.setDbDeployDBOwners("owner");
		ArrayList<String> dbDeployPipelineList = new ArrayList<>();
		dbDeployPipelineList.add("pipeline1");
		testedObject.setDbDeployPipelineList(dbDeployPipelineList);
		Migration sqlFilesAndPackages = new Migration();
		testedObject.setSqlFilesAndPackages(sqlFilesAndPackages);
		Migration reportMigration = new Migration();
		testedObject.setReportMigration(reportMigration);
		Migration publishForm = new Migration();
		testedObject.setPublishForm(publishForm);
		Migration ctlMigration = new Migration();
		testedObject.setCtlMigration(ctlMigration);
		Migration oaMedia = new Migration();
		testedObject.setOaMedia(oaMedia);
		Migration workflowMigration = new Migration();
		testedObject.setWorkflowMigration(workflowMigration);
		Migration aolScript = new Migration();
		testedObject.setAolScript(aolScript);
		List sqlDeploy = new ArrayList();
		testedObject.setSqlDeploy(sqlDeploy);
		List datExport = new ArrayList();
		testedObject.setDatExport(datExport);
		List datImport = new ArrayList();
		testedObject.setDatImport(datImport);
		List ddlSync = new ArrayList();
		testedObject.setDdlSync(ddlSync);
		testedObject.setPropertyFile("propertyFile22");
		testedObject.setBuildType("buildType22");
		testedObject.setBarFile("barFile22");
		// Integer port = new Integer(5);
		String port = "5";
		testedObject.setPort(port);
		testedObject.setDerivedDataPath("derivedDataPath22");
		testedObject.setAvdName("avdName22");
		testedObject.setSourcePath("sourcePath22");
		testedObject.setTargetPath("targetPath22");
		testedObject.setScriptPath("scriptPath22");
		testedObject.setDomain("domain22");
		testedObject.setMqManager("mqManager22");
		testedObject.setPlatform("platform22");
		testedObject.setAutomationScript("automationScript22");
		testedObject.setAdmin("admin22");
		testedObject.setAdminPassword("adminPassword22");
		testedObject.setDbOwner("dbOwner22");
		testedObject.setDbOwnerPassword("dbOwnerPassword22");
		List staticFiles = new ArrayList();
		testedObject.setStaticFiles(staticFiles);
		List srfServer = new ArrayList();
		testedObject.setSrfServer(srfServer);
		List admWorkflowServer = new ArrayList();
		testedObject.setAdmWorkflowServer(admWorkflowServer);
		testedObject.setSshExecution("sshExecution22");
		testedObject.setSshId("sshId22");
		testedObject.setSshPassword("sshPassword22");
		testedObject.setScript("script22");
		testedObject.setUpdateDB("updateDB22");
		testedObject.setRollBackDB("rollBackDB22");
		testedObject.setLogFilePath("logFilePath22");
		testedObject.setTagDB("tagDB22");
		testedObject.setTestRollback("testRollback22");
		testedObject.setApprover("approver22");
		testedObject.setRollbackStrategy("rollbackStrategy22");
		testedObject.setTagName("tagName22");
		Integer hrs = new Integer("0");
		testedObject.setHrs(hrs);
		testedObject.setIpOrDNS("ipOrDNS44");
		testedObject.setHostName("hostName22");
		testedObject.setContainerName("containerName22");
		testedObject.setServerManagerURL("serverManagerURL22");
		testedObject.setResourceToBeDeployed("resourceToBeDeployed22");
		testedObject.setWarPath("warPath22");
		testedObject.setContextPath("contextPath22");
		testedObject.setUserName("userName22");
		testedObject.setPassword("password22");
		testedObject.setIpDns("ipOrDNS45");
		testedObject.setTargetCellName("targetCellName22");
		testedObject.setTargetNodeName("targetNodeName22");
		testedObject.setTargetServerName("targetServerName21");
		testedObject.setDestServerPath("destServerPath");
		testedObject.setWebsiteName("websiteName");
		testedObject.setAppPoolName("appPoolName");

		assertEquals("dev", testedObject.getSrcEnvName());
		assertEquals("dev-sit", testedObject.getPairName());
		assertEquals("main", testedObject.getLauncherActivity());
		assertEquals("token", testedObject.getToken());
		assertEquals("owner", testedObject.getDbDeployDBOwners());
		assertEquals(dbDeployPipelineList, testedObject.getDbDeployPipelineList());
		assertEquals("destServerPath", testedObject.getDestServerPath());
		assertEquals("websiteName", testedObject.getWebsiteName());
		assertEquals("appPoolName", testedObject.getAppPoolName());
		assertEquals("hostName22", testedObject.getHostName());
		assertEquals(port, testedObject.getPort());
		assertEquals("script22", testedObject.getScript());
		assertEquals(sqlFilesAndPackages, testedObject.getSqlFilesAndPackages());
		assertEquals("targetNodeName22", testedObject.getTargetNodeName());
		assertEquals(workflowMigration, testedObject.getWorkflowMigration());
		assertEquals("deployedFolder22", testedObject.getDeployedFolder());
		assertEquals("serverManagerURL22", testedObject.getServerManagerURL());
		assertEquals(reportMigration, testedObject.getReportMigration());
		assertEquals("resourceToBeDeployed22", testedObject.getResourceToBeDeployed());
		assertEquals("targetServerName21", testedObject.getTargetServerName());
		assertEquals("dbOwnerPassword22", testedObject.getDbOwnerPassword());
		assertEquals("derivedDataPath22", testedObject.getDerivedDataPath());
		assertEquals("automationScript22", testedObject.getAutomationScript());
		assertEquals(sqlDeploy, testedObject.getAdmWorkflowServer());
		assertEquals("rollbackStrategy22", testedObject.getRollbackStrategy());
		assertEquals("targetCellName22", testedObject.getTargetCellName());
		assertEquals("applicationName22", testedObject.getApplicationName());
		assertEquals("admin22", testedObject.getAdmin());
		assertEquals("warPath22", testedObject.getWarPath());
		assertEquals(sqlDeploy, testedObject.getSrfServer());
		assertEquals(sqlDeploy, testedObject.getStaticFiles());
		assertEquals("testRollback22", testedObject.getTestRollback());
		assertEquals("tagName22", testedObject.getTagName());
		assertEquals("scriptPath22", testedObject.getScriptPath());
		assertEquals("containerName22", testedObject.getContainerName());
		assertEquals("logFilePath22", testedObject.getLogFilePath());
		assertEquals("sshPassword22", testedObject.getSshPassword());
		assertEquals("dbOwner22", testedObject.getDbOwner());
		assertEquals("sshExecution22", testedObject.getSshExecution());
		assertEquals("sshId22", testedObject.getSshId());
		assertEquals("updateDB22", testedObject.getUpdateDB());
		assertEquals("platform22", testedObject.getPlatform());
		assertEquals(hrs, testedObject.getHrs());
		assertEquals("ipOrDNS45", testedObject.getIpOrDNS());
		assertEquals("approver22", testedObject.getApprover());
		assertEquals("rollBackDB22", testedObject.getRollBackDB());
		assertEquals("adminPassword22", testedObject.getAdminPassword());
		assertEquals("mqManager22", testedObject.getMqManager());
		assertEquals("domain22", testedObject.getDomain());
		assertEquals("userName22", testedObject.getUserName());
		assertEquals("password22", testedObject.getPassword());
		assertEquals("contextPath22", testedObject.getContextPath());
		assertEquals("targetPath22", testedObject.getTargetPath());
		assertEquals("tagDB22", testedObject.getTagDB());
		assertEquals("dbcScriptName22", testedObject.getDbcScriptName());
		assertEquals("xmlScripts22", testedObject.getXmlScripts());
		assertEquals("importReports22", testedObject.getImportReports());
		assertEquals(publishForm, testedObject.getPublishForm());
		assertEquals("profilePath22", testedObject.getProfilePath());
		assertEquals(ctlMigration, testedObject.getCtlMigration());
		assertEquals(oaMedia, testedObject.getOaMedia());
		assertEquals(sqlDeploy, testedObject.getSqlDeploy());
		assertEquals(sqlDeploy, testedObject.getDatExport());
		assertEquals(sqlDeploy, testedObject.getDatImport());
		assertEquals(sqlDeploy, testedObject.getDdlSync());
		assertEquals("propertyFile22", testedObject.getPropertyFile());
		assertEquals(aolScript, testedObject.getAolScript());
		assertEquals("barFile22", testedObject.getBarFile());
		assertEquals("buildType22", testedObject.getBuildType());
		assertEquals("avdName22", testedObject.getAvdName());
		assertEquals("sourcePath22", testedObject.getSourcePath());
		assertEquals("narOS22", testedObject.getNarOS());

		// No exception thrown

	}

	/**
	 * Test for method DeployToContainer().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployToContainer#DeployToContainer()
	 * 
	 * 
	 */
	@Test
	public void testDeployToContainer11() throws Throwable {
		DeployToContainer testedObject = new DeployToContainer();
		testedObject.setApplicationName("applicationName1");
		testedObject.setNarOS("narOS1");
		testedObject.setDeployedFolder("deployedFolder1");
		testedObject.setProfilePath("profilePath1");
		testedObject.setDbcScriptName("dbcScriptName1");
		testedObject.setXmlScripts("xmlScripts1");
		testedObject.setImportReports("importReports1");
		testedObject.setSqlFilesAndPackages((Migration) null);
		testedObject.setReportMigration((Migration) null);
		testedObject.setPublishForm((Migration) null);
		testedObject.setCtlMigration((Migration) null);
		testedObject.setOaMedia((Migration) null);
		testedObject.setWorkflowMigration((Migration) null);
		testedObject.setAolScript((Migration) null);
		testedObject.setSqlDeploy((List) null);
		testedObject.setDatExport((List) null);
		testedObject.setDatImport((List) null);
		testedObject.setDdlSync((List) null);
		testedObject.setPropertyFile("propertyFile1");
		testedObject.setBuildType("buildType1");
		testedObject.setBarFile("barFile1");
		testedObject.setPort(null);
		testedObject.setDerivedDataPath("derivedDataPath1");
		testedObject.setAvdName("avdName1");
		testedObject.setSourcePath("sourcePath1");
		testedObject.setTargetPath("targetPath1");
		testedObject.setScriptPath("scriptPath1");
		testedObject.setDomain("domain1");
		testedObject.setMqManager("mqManager1");
		testedObject.setPlatform("platform1");
		testedObject.setAutomationScript("automationScript1");
		testedObject.setAdmin("admin1");
		testedObject.setAdminPassword("adminPassword1");
		testedObject.setDbOwner("dbOwner1");
		testedObject.setDbOwnerPassword("dbOwnerPassword1");
		testedObject.setStaticFiles((List) null);
		testedObject.setSrfServer((List) null);
		testedObject.setAdmWorkflowServer((List) null);
		testedObject.setSshExecution("sshExecution1");
		testedObject.setSshId("sshId1");
		testedObject.setSshPassword("sshPassword1");
		testedObject.setScript("script1");
		testedObject.setUpdateDB("updateDB1");
		testedObject.setRollBackDB("rollBackDB1");
		testedObject.setLogFilePath("logFilePath1");
		testedObject.setTagDB("tagDB1");
		testedObject.setTestRollback("testRollback1");
		testedObject.setApprover("approver1");
		testedObject.setRollbackStrategy("rollbackStrategy1");
		testedObject.setTagName("tagName1");
		testedObject.setHrs((Integer) null);
		testedObject.setIpOrDNS("ipOrDNS2");
		testedObject.setHostName("hostName1");
		testedObject.setContainerName("containerName1");
		testedObject.setServerManagerURL("serverManagerURL1");
		testedObject.setResourceToBeDeployed("resourceToBeDeployed1");
		testedObject.setWarPath("warPath1");
		testedObject.setContextPath("contextPath1");
		testedObject.setUserName("userName1");
		testedObject.setPassword("password1");
		testedObject.setIpDns("ipOrDNS3");
		testedObject.setTargetCellName("targetCellName1");
		testedObject.setTargetNodeName("targetNodeName1");
		testedObject.setTargetServerName("targetServerName0");
		assertEquals("hostName1", testedObject.getHostName());
		assertEquals(null, testedObject.getPort());
		assertEquals("script1", testedObject.getScript());
		assertEquals(null, testedObject.getSqlFilesAndPackages());
		assertEquals("targetNodeName1", testedObject.getTargetNodeName());
		assertEquals(null, testedObject.getWorkflowMigration());
		assertEquals("deployedFolder1", testedObject.getDeployedFolder());
		assertEquals("serverManagerURL1", testedObject.getServerManagerURL());
		assertEquals(null, testedObject.getReportMigration());
		assertEquals("resourceToBeDeployed1", testedObject.getResourceToBeDeployed());
		assertEquals("targetServerName0", testedObject.getTargetServerName());
		assertEquals("dbOwnerPassword1", testedObject.getDbOwnerPassword());
		assertEquals("derivedDataPath1", testedObject.getDerivedDataPath());
		assertEquals("automationScript1", testedObject.getAutomationScript());
		assertEquals(null, testedObject.getAdmWorkflowServer());
		assertEquals("rollbackStrategy1", testedObject.getRollbackStrategy());
		assertEquals("targetCellName1", testedObject.getTargetCellName());
		assertEquals("applicationName1", testedObject.getApplicationName());
		assertEquals("admin1", testedObject.getAdmin());
		assertEquals("warPath1", testedObject.getWarPath());
		assertEquals(null, testedObject.getSrfServer());
		assertEquals(null, testedObject.getStaticFiles());
		assertEquals("testRollback1", testedObject.getTestRollback());
		assertEquals("tagName1", testedObject.getTagName());
		assertEquals("scriptPath1", testedObject.getScriptPath());
		assertEquals("containerName1", testedObject.getContainerName());
		assertEquals("logFilePath1", testedObject.getLogFilePath());
		assertEquals("sshPassword1", testedObject.getSshPassword());
		assertEquals("dbOwner1", testedObject.getDbOwner());
		assertEquals("sshExecution1", testedObject.getSshExecution());
		assertEquals("sshId1", testedObject.getSshId());
		assertEquals("updateDB1", testedObject.getUpdateDB());
		assertEquals("platform1", testedObject.getPlatform());
		assertEquals(null, testedObject.getHrs());
		assertEquals("ipOrDNS3", testedObject.getIpOrDNS());
		assertEquals("approver1", testedObject.getApprover());
		assertEquals("rollBackDB1", testedObject.getRollBackDB());
		assertEquals("adminPassword1", testedObject.getAdminPassword());
		assertEquals("mqManager1", testedObject.getMqManager());
		assertEquals("domain1", testedObject.getDomain());
		assertEquals("userName1", testedObject.getUserName());
		assertEquals("password1", testedObject.getPassword());
		assertEquals("contextPath1", testedObject.getContextPath());
		assertEquals("targetPath1", testedObject.getTargetPath());
		assertEquals("tagDB1", testedObject.getTagDB());
		assertEquals("dbcScriptName1", testedObject.getDbcScriptName());
		assertEquals("xmlScripts1", testedObject.getXmlScripts());
		assertEquals("importReports1", testedObject.getImportReports());
		assertEquals(null, testedObject.getPublishForm());
		assertEquals("profilePath1", testedObject.getProfilePath());
		assertEquals(null, testedObject.getCtlMigration());
		assertEquals(null, testedObject.getOaMedia());
		assertEquals(null, testedObject.getSqlDeploy());
		assertEquals(null, testedObject.getDatExport());
		assertEquals(null, testedObject.getDatImport());
		assertEquals(null, testedObject.getDdlSync());
		assertEquals("propertyFile1", testedObject.getPropertyFile());
		assertEquals(null, testedObject.getAolScript());
		assertEquals("barFile1", testedObject.getBarFile());
		assertEquals("buildType1", testedObject.getBuildType());
		assertEquals("avdName1", testedObject.getAvdName());
		assertEquals("sourcePath1", testedObject.getSourcePath());
		assertEquals("narOS1", testedObject.getNarOS());
		// No exception thrown

	}

	/**
	 * Test for method DeployToContainer().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployToContainer#DeployToContainer()
	 * 
	 * 
	 */
	@Test
	public void testDeployToContainer12() throws Throwable {
		DeployToContainer testedObject = new DeployToContainer();
		testedObject.setApplicationName("applicationName0");
		testedObject.setNarOS("narOS0");
		testedObject.setDeployedFolder("deployedFolder0");
		testedObject.setProfilePath("profilePath0");
		testedObject.setDbcScriptName("dbcScriptName0");
		testedObject.setXmlScripts("xmlScripts0");
		testedObject.setImportReports("importReports0");
		testedObject.setSqlFilesAndPackages((Migration) null);
		testedObject.setReportMigration((Migration) null);
		testedObject.setPublishForm((Migration) null);
		testedObject.setCtlMigration((Migration) null);
		testedObject.setOaMedia((Migration) null);
		testedObject.setWorkflowMigration((Migration) null);
		testedObject.setAolScript((Migration) null);
		testedObject.setSqlDeploy((List) null);
		testedObject.setDatExport((List) null);
		testedObject.setDatImport((List) null);
		testedObject.setDdlSync((List) null);
		testedObject.setPropertyFile("propertyFile0");
		testedObject.setBuildType("buildType0");
		testedObject.setBarFile("barFile0");
		testedObject.setPort(null);
		testedObject.setDerivedDataPath("derivedDataPath0");
		testedObject.setAvdName("avdName0");
		testedObject.setSourcePath("sourcePath0");
		testedObject.setTargetPath("targetPath0");
		testedObject.setScriptPath("scriptPath0");
		testedObject.setDomain("domain0");
		testedObject.setMqManager("mqManager0");
		testedObject.setPlatform("platform0");
		testedObject.setAutomationScript("automationScript0");
		testedObject.setAdmin("admin0");
		testedObject.setAdminPassword("adminPassword0");
		testedObject.setDbOwner("dbOwner0");
		testedObject.setDbOwnerPassword("dbOwnerPassword0");
		testedObject.setStaticFiles((List) null);
		testedObject.setSrfServer((List) null);
		testedObject.setAdmWorkflowServer((List) null);
		testedObject.setSshExecution("sshExecution0");
		testedObject.setSshId("sshId0");
		testedObject.setSshPassword("sshPassword0");
		testedObject.setScript("script0");
		testedObject.setUpdateDB("updateDB0");
		testedObject.setRollBackDB("rollBackDB0");
		testedObject.setLogFilePath("logFilePath0");
		testedObject.setTagDB("tagDB0");
		testedObject.setTestRollback("testRollback0");
		testedObject.setApprover("approver0");
		testedObject.setRollbackStrategy("rollbackStrategy0");
		testedObject.setTagName("tagName0");
		testedObject.setHrs((Integer) null);
		testedObject.setIpOrDNS("ipOrDNS0");
		testedObject.setHostName("hostName0");
		testedObject.setContainerName("containerName0");
		testedObject.setServerManagerURL("serverManagerURL0");
		testedObject.setResourceToBeDeployed("resourceToBeDeployed0");
		testedObject.setWarPath("warPath0");
		testedObject.setContextPath("contextPath0");
		testedObject.setUserName("userName0");
		testedObject.setPassword("password0");
		testedObject.setIpDns("ipOrDNS1");
		testedObject.setTargetCellName("targetCellName0");
		testedObject.setTargetNodeName("targetNodeName0");
		testedObject.setTargetServerName((String) null);
		assertEquals("hostName0", testedObject.getHostName());
		assertEquals(null, testedObject.getPort());
		assertEquals("script0", testedObject.getScript());
		assertEquals(null, testedObject.getSqlFilesAndPackages());
		assertEquals("targetNodeName0", testedObject.getTargetNodeName());
		assertEquals(null, testedObject.getWorkflowMigration());
		assertEquals("deployedFolder0", testedObject.getDeployedFolder());
		assertEquals("serverManagerURL0", testedObject.getServerManagerURL());
		assertEquals(null, testedObject.getReportMigration());
		assertEquals("resourceToBeDeployed0", testedObject.getResourceToBeDeployed());
		assertEquals(null, testedObject.getTargetServerName());
		assertEquals("dbOwnerPassword0", testedObject.getDbOwnerPassword());
		assertEquals("derivedDataPath0", testedObject.getDerivedDataPath());
		assertEquals("automationScript0", testedObject.getAutomationScript());
		assertEquals(null, testedObject.getAdmWorkflowServer());
		assertEquals("rollbackStrategy0", testedObject.getRollbackStrategy());
		assertEquals("targetCellName0", testedObject.getTargetCellName());
		assertEquals("applicationName0", testedObject.getApplicationName());
		assertEquals("admin0", testedObject.getAdmin());
		assertEquals("warPath0", testedObject.getWarPath());
		assertEquals(null, testedObject.getSrfServer());
		assertEquals(null, testedObject.getStaticFiles());
		assertEquals("testRollback0", testedObject.getTestRollback());
		assertEquals("tagName0", testedObject.getTagName());
		assertEquals("scriptPath0", testedObject.getScriptPath());
		assertEquals("containerName0", testedObject.getContainerName());
		assertEquals("logFilePath0", testedObject.getLogFilePath());
		assertEquals("sshPassword0", testedObject.getSshPassword());
		assertEquals("dbOwner0", testedObject.getDbOwner());
		assertEquals("sshExecution0", testedObject.getSshExecution());
		assertEquals("sshId0", testedObject.getSshId());
		assertEquals("updateDB0", testedObject.getUpdateDB());
		assertEquals("platform0", testedObject.getPlatform());
		assertEquals(null, testedObject.getHrs());
		assertEquals("ipOrDNS1", testedObject.getIpOrDNS());
		assertEquals("approver0", testedObject.getApprover());
		assertEquals("rollBackDB0", testedObject.getRollBackDB());
		assertEquals("adminPassword0", testedObject.getAdminPassword());
		assertEquals("mqManager0", testedObject.getMqManager());
		assertEquals("domain0", testedObject.getDomain());
		assertEquals("userName0", testedObject.getUserName());
		assertEquals("password0", testedObject.getPassword());
		assertEquals("contextPath0", testedObject.getContextPath());
		assertEquals("targetPath0", testedObject.getTargetPath());
		assertEquals("tagDB0", testedObject.getTagDB());
		assertEquals("dbcScriptName0", testedObject.getDbcScriptName());
		assertEquals("xmlScripts0", testedObject.getXmlScripts());
		assertEquals("importReports0", testedObject.getImportReports());
		assertEquals(null, testedObject.getPublishForm());
		assertEquals("profilePath0", testedObject.getProfilePath());
		assertEquals(null, testedObject.getCtlMigration());
		assertEquals(null, testedObject.getOaMedia());
		assertEquals(null, testedObject.getSqlDeploy());
		assertEquals(null, testedObject.getDatExport());
		assertEquals(null, testedObject.getDatImport());
		assertEquals(null, testedObject.getDdlSync());
		assertEquals("propertyFile0", testedObject.getPropertyFile());
		assertEquals(null, testedObject.getAolScript());
		assertEquals("barFile0", testedObject.getBarFile());
		assertEquals("buildType0", testedObject.getBuildType());
		assertEquals("avdName0", testedObject.getAvdName());
		assertEquals("sourcePath0", testedObject.getSourcePath());
		assertEquals("narOS0", testedObject.getNarOS());
		// No exception thrown

	}

	/**
	 * Test for method DeployToContainer().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployToContainer#DeployToContainer()
	 * 
	 * 
	 */
	@Test
	public void testDeployToContainer13() throws Throwable {
		DeployToContainer testedObject = new DeployToContainer();
		assertEquals(null, testedObject.getHostName());
		assertEquals(null, testedObject.getPort());
		assertEquals(null, testedObject.getScript());
		assertEquals(null, testedObject.getSqlFilesAndPackages());
		assertEquals(null, testedObject.getTargetNodeName());
		assertEquals(null, testedObject.getWorkflowMigration());
		assertEquals(null, testedObject.getDeployedFolder());
		assertEquals(null, testedObject.getServerManagerURL());
		assertEquals(null, testedObject.getReportMigration());
		assertEquals(null, testedObject.getResourceToBeDeployed());
		assertEquals(null, testedObject.getTargetServerName());
		assertEquals(null, testedObject.getDbOwnerPassword());
		assertEquals(null, testedObject.getDerivedDataPath());
		assertEquals(null, testedObject.getAutomationScript());
		assertEquals(null, testedObject.getAdmWorkflowServer());
		assertEquals(null, testedObject.getRollbackStrategy());
		assertEquals(null, testedObject.getTargetCellName());
		assertEquals(null, testedObject.getApplicationName());
		assertEquals(null, testedObject.getAdmin());
		assertEquals(null, testedObject.getWarPath());
		assertEquals(null, testedObject.getSrfServer());
		assertEquals(null, testedObject.getStaticFiles());
		assertEquals(null, testedObject.getTestRollback());
		assertEquals(null, testedObject.getTagName());
		assertEquals(null, testedObject.getScriptPath());
		assertEquals(null, testedObject.getContainerName());
		assertEquals(null, testedObject.getLogFilePath());
		assertEquals(null, testedObject.getSshPassword());
		assertEquals(null, testedObject.getDbOwner());
		assertEquals(null, testedObject.getSshExecution());
		assertEquals(null, testedObject.getSshId());
		assertEquals(null, testedObject.getUpdateDB());
		assertEquals(null, testedObject.getPlatform());
		assertEquals(null, testedObject.getHrs());
		assertEquals(null, testedObject.getIpOrDNS());
		assertEquals(null, testedObject.getApprover());
		assertEquals(null, testedObject.getRollBackDB());
		assertEquals(null, testedObject.getAdminPassword());
		assertEquals(null, testedObject.getMqManager());
		assertEquals(null, testedObject.getDomain());
		assertEquals(null, testedObject.getUserName());
		assertEquals(null, testedObject.getPassword());
		assertEquals(null, testedObject.getContextPath());
		assertEquals(null, testedObject.getTargetPath());
		assertEquals(null, testedObject.getTagDB());
		assertEquals(null, testedObject.getDbcScriptName());
		assertEquals(null, testedObject.getXmlScripts());
		assertEquals(null, testedObject.getImportReports());
		assertEquals(null, testedObject.getPublishForm());
		assertEquals(null, testedObject.getProfilePath());
		assertEquals(null, testedObject.getCtlMigration());
		assertEquals(null, testedObject.getOaMedia());
		assertEquals(null, testedObject.getSqlDeploy());
		assertEquals(null, testedObject.getDatExport());
		assertEquals(null, testedObject.getDatImport());
		assertEquals(null, testedObject.getDdlSync());
		assertEquals(null, testedObject.getPropertyFile());
		assertEquals(null, testedObject.getAolScript());
		assertEquals(null, testedObject.getBarFile());
		assertEquals(null, testedObject.getBuildType());
		assertEquals(null, testedObject.getAvdName());
		assertEquals(null, testedObject.getSourcePath());
		assertEquals(null, testedObject.getNarOS());
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java DeployToContainerTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.deployInfo.DeployToContainerTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return DeployToContainer.class;
	}
}
