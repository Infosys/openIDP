/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import java.util.List;

import org.infy.idp.entities.jobs.common.Migration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store container deployment information
 * 
 * @author Infosys
 *
 */

public class DeployToContainer {

	@SerializedName("containerName")
	@Expose
	private String containerName;
	@SerializedName("appName")
	@Expose
	private String appName;
	@SerializedName("serverManagerURL")
	@Expose
	private String serverManagerURL;
	
	@SerializedName("resourceToBeDeployed")
	@Expose
	private String resourceToBeDeployed;
	@SerializedName("warPath")
	@Expose
	private String warPath;
	@SerializedName("contextPath")
	@Expose
	private String contextPath;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("ipOrDNS")
	@Expose
	private String ipOrDNS;

	@SerializedName("targetCellName")
	@Expose
	private String targetCellName;
	@SerializedName("targetNodeName")
	@Expose
	private String targetNodeName;
	@SerializedName("targetServerName")
	@Expose
	private String targetServerName;
	@SerializedName("hostName")
	@Expose
	private String hostName;

	@SerializedName("port")
	@Expose
	private String port;
	@SerializedName("derivedDataPath")
	@Expose
	private String derivedDataPath;
	@SerializedName("avdName")
	@Expose
	private String avdName;
	@SerializedName("sourcePath")
	@Expose
	private String sourcePath;
	@SerializedName("targetPath")
	@Expose
	private String targetPath;
	@SerializedName("scriptPath")
	@Expose
	private String scriptPath;
	@SerializedName("domain")
	@Expose
	private String domain;
	@SerializedName("mqManager")
	@Expose
	private String mqManager;
	@SerializedName("platform")
	@Expose
	private String platform;
	@SerializedName("automationScript")
	@Expose
	private String automationScript;
	@SerializedName("admin")
	@Expose
	private String admin;
	@SerializedName("adminPassword")
	@Expose
	private String adminPassword;
	@SerializedName("dbOwner")
	@Expose
	private String dbOwner;
	@SerializedName("dbOwnerPassword")
	@Expose
	private String dbOwnerPassword;
	@SerializedName("staticFiles")
	@Expose
	private List<Server> staticFiles = null;
	@SerializedName("srfServer")
	@Expose
	private List<Server> srfServer = null;
	@SerializedName("admWorkflowServer")
	@Expose
	private List<Server> admWorkflowServer = null;
	@SerializedName("sshExecution")
	@Expose
	private String sshExecution;
	@SerializedName("sshId")
	@Expose
	private String sshId;
	@SerializedName("sshPassword")
	@Expose
	private String sshPassword;
	@SerializedName("script")
	@Expose
	private String script;
	@SerializedName("updateDB")
	@Expose
	private String updateDB;
	@SerializedName("rollBackDB")
	@Expose
	private String rollBackDB;
	@SerializedName("logFilePath")
	@Expose
	private String logFilePath;
	@SerializedName("tagDB")
	@Expose
	private String tagDB;
	@SerializedName("testRollback")
	@Expose
	private String testRollback;
	@SerializedName("approver")
	@Expose
	private String approver;
	@SerializedName("rollbackStrategy")
	@Expose
	private String rollbackStrategy;
	@SerializedName("tagName")
	@Expose
	private String tagName;
	@SerializedName("hrs")
	@Expose
	private Integer hrs;
	@SerializedName("propertyFile")
	@Expose
	private String propertyFile;
	@SerializedName("buildType")
	@Expose
	private String buildType;
	@SerializedName("barFile")
	@Expose
	private String barFile;
	@SerializedName("narOS")
	@Expose
	private String narOS;
	@SerializedName("deployedFolder")
	@Expose
	private String deployedFolder;
	@SerializedName("sqlDeploy")
	@Expose
	private List<Server> sqlDeploy = null;
	@SerializedName("datExport")
	@Expose
	private List<Server> datExport = null;
	@SerializedName("datImport")
	@Expose
	private List<Server> datImport = null;
	@SerializedName("ddlSync")
	@Expose
	private List<Server> ddlSync = null;

	@SerializedName("sqlFilesAndPackages")
	@Expose
	private Migration sqlFilesAndPackages = null;
	@SerializedName("reportMigration")
	@Expose
	private Migration reportMigration = null;
	@SerializedName("publishForm")
	@Expose
	private Migration publishForm = null;
	@SerializedName("ctlMigration")
	@Expose
	private Migration ctlMigration = null;
	@SerializedName("oaMedia")
	@Expose
	private Migration oaMedia = null;
	@SerializedName("workflowMigration")
	@Expose
	private Migration workflowMigration = null;
	@SerializedName("aolScript")
	@Expose
	private Migration aolScript = null;

	@SerializedName("profilePath")
	@Expose
	private String profilePath;
	@SerializedName("dbcScriptName")
	@Expose
	private String dbcScriptName;
	@SerializedName("xmlScripts")
	@Expose
	private String xmlScripts;
	@SerializedName("importReports")
	@Expose
	private String importReports;

	@SerializedName("applicationName")
	@Expose
	private String applicationName;

	@SerializedName("destServerPath")
	@Expose
	private String destServerPath;

	@SerializedName("websiteName")
	@Expose
	private String websiteName;

	@SerializedName("appPoolName")
	@Expose
	private String appPoolName;

	@SerializedName("fileName")
	@Expose
	private String fileName;
	@SerializedName("launcherActivity")
	@Expose
	private String launcherActivity;
	@SerializedName("token")
	@Expose
	private String token;
	@SerializedName("dbDeployDBOwners")
	@Expose
	private String dbDeployDBOwners;
	@SerializedName("dbDeployPipelineList")
	@Expose
	private List<String> dbDeployPipelineList;

	@SerializedName("pairName")
	@Expose
	private String pairName;

	@SerializedName("srcEnvName")
	@Expose
	private String srcEnvName;
    
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getSrcEnvName() {
		return srcEnvName;
	}

	public void setSrcEnvName(String srcEnvName) {
		this.srcEnvName = srcEnvName;
	}

	public String getPairName() {
		return pairName;
	}

	public void setPairName(String pairName) {
		this.pairName = pairName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLauncherActivity() {
		return launcherActivity;
	}

	public void setLauncherActivity(String launcherActivity) {
		this.launcherActivity = launcherActivity;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDestServerPath() {
		return destServerPath;
	}

	public void setDestServerPath(String destServerPath) {
		this.destServerPath = destServerPath;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getAppPoolName() {
		return appPoolName;
	}

	public void setAppPoolName(String appPoolName) {
		this.appPoolName = appPoolName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getNarOS() {
		return narOS;
	}

	public void setNarOS(String narOS) {
		this.narOS = narOS;
	}

	public String getDeployedFolder() {
		return deployedFolder;
	}

	public void setDeployedFolder(String deployedFolder) {
		this.deployedFolder = deployedFolder;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public String getDbcScriptName() {
		return dbcScriptName;
	}

	public void setDbcScriptName(String dbcScriptName) {
		this.dbcScriptName = dbcScriptName;
	}

	public String getXmlScripts() {
		return xmlScripts;
	}

	public void setXmlScripts(String xmlScripts) {
		this.xmlScripts = xmlScripts;
	}

	public String getImportReports() {
		return importReports;
	}

	public void setImportReports(String importReports) {
		this.importReports = importReports;
	}

	public Migration getSqlFilesAndPackages() {
		return sqlFilesAndPackages;
	}

	public void setSqlFilesAndPackages(Migration sqlFilesAndPackages) {
		this.sqlFilesAndPackages = sqlFilesAndPackages;
	}

	public Migration getReportMigration() {
		return reportMigration;
	}

	public void setReportMigration(Migration reportMigration) {
		this.reportMigration = reportMigration;
	}

	public Migration getPublishForm() {
		return publishForm;
	}

	public void setPublishForm(Migration publishForm) {
		this.publishForm = publishForm;
	}

	public Migration getCtlMigration() {
		return ctlMigration;
	}

	public void setCtlMigration(Migration ctlMigration) {
		this.ctlMigration = ctlMigration;
	}

	public Migration getOaMedia() {
		return oaMedia;
	}

	public void setOaMedia(Migration oaMedia) {
		this.oaMedia = oaMedia;
	}

	public Migration getWorkflowMigration() {
		return workflowMigration;
	}

	public void setWorkflowMigration(Migration workflowMigration) {
		this.workflowMigration = workflowMigration;
	}

	public Migration getAolScript() {
		return aolScript;
	}

	public void setAolScript(Migration aolScript) {
		this.aolScript = aolScript;
	}

	public List<Server> getSqlDeploy() {
		return sqlDeploy;
	}

	public void setSqlDeploy(List<Server> sqlDeploy) {
		this.sqlDeploy = sqlDeploy;
	}

	public List<Server> getDatExport() {
		return datExport;
	}

	public void setDatExport(List<Server> datExport) {
		this.datExport = datExport;
	}

	public List<Server> getDatImport() {
		return datImport;
	}

	public void setDatImport(List<Server> datImport) {
		this.datImport = datImport;
	}

	public List<Server> getDdlSync() {
		return ddlSync;
	}

	public void setDdlSync(List<Server> ddlSync) {
		this.ddlSync = ddlSync;
	}

	public String getPropertyFile() {
		return propertyFile;
	}

	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public String getBarFile() {
		return barFile;
	}

	public void setBarFile(String barFile) {
		this.barFile = barFile;
	}

	/**
	 * method getmVNOPTS
	 * 
	 * @return mVNOPTS the String
	 */

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDerivedDataPath() {
		return derivedDataPath;
	}

	public void setDerivedDataPath(String derivedDataPath) {
		this.derivedDataPath = derivedDataPath;
	}

	public String getAvdName() {
		return avdName;
	}

	public void setAvdName(String avdName) {
		this.avdName = avdName;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getMqManager() {
		return mqManager;
	}

	public void setMqManager(String mqManager) {
		this.mqManager = mqManager;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAutomationScript() {
		return automationScript;
	}

	public void setAutomationScript(String automationScript) {
		this.automationScript = automationScript;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getDbOwner() {
		return dbOwner;
	}

	public void setDbOwner(String dbOwner) {
		this.dbOwner = dbOwner;
	}

	public String getDbOwnerPassword() {
		return dbOwnerPassword;
	}

	public void setDbOwnerPassword(String dbOwnerPassword) {
		this.dbOwnerPassword = dbOwnerPassword;
	}

	public List<Server> getStaticFiles() {
		return staticFiles;
	}

	public void setStaticFiles(List<Server> staticFiles) {
		this.staticFiles = staticFiles;
	}

	public List<Server> getSrfServer() {
		return srfServer;
	}

	public void setSrfServer(List<Server> srfServer) {
		this.srfServer = srfServer;
	}

	public List<Server> getAdmWorkflowServer() {
		return admWorkflowServer;
	}

	public void setAdmWorkflowServer(List<Server> admWorkflowServer) {
		this.admWorkflowServer = admWorkflowServer;
	}

	public String getSshExecution() {
		return sshExecution;
	}

	public void setSshExecution(String sshExecution) {
		this.sshExecution = sshExecution;
	}

	public String getSshId() {
		return sshId;
	}

	public void setSshId(String sshId) {
		this.sshId = sshId;
	}

	public String getSshPassword() {
		return sshPassword;
	}

	public void setSshPassword(String sshPassword) {
		this.sshPassword = sshPassword;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getUpdateDB() {
		return updateDB;
	}

	public void setUpdateDB(String updateDB) {
		this.updateDB = updateDB;
	}

	public String getRollBackDB() {
		return rollBackDB;
	}

	public void setRollBackDB(String rollBackDB) {
		this.rollBackDB = rollBackDB;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public String getTagDB() {
		return tagDB;
	}

	public void setTagDB(String tagDB) {
		this.tagDB = tagDB;
	}

	public String getTestRollback() {
		return testRollback;
	}

	public void setTestRollback(String testRollback) {
		this.testRollback = testRollback;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getRollbackStrategy() {
		return rollbackStrategy;
	}

	public void setRollbackStrategy(String rollbackStrategy) {
		this.rollbackStrategy = rollbackStrategy;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getHrs() {
		return hrs;
	}

	public void setHrs(Integer hrs) {
		this.hrs = hrs;
	}

	public void setIpOrDNS(String ipOrDNS) {
		this.ipOrDNS = ipOrDNS;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getServerManagerURL() {
		return serverManagerURL;
	}

	public void setServerManagerURL(String serverManagerURL) {
		this.serverManagerURL = serverManagerURL;
	}

	public String getResourceToBeDeployed() {
		return resourceToBeDeployed;
	}

	public void setResourceToBeDeployed(String resourceToBeDeployed) {
		this.resourceToBeDeployed = resourceToBeDeployed;
	}

	public String getWarPath() {
		return warPath;
	}

	public void setWarPath(String warPath) {
		this.warPath = warPath;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpOrDNS() {
		return ipOrDNS;
	}

	public void setIpDns(String ipOrDNS) {
		this.ipOrDNS = ipOrDNS;
	}

	public String getTargetCellName() {
		return targetCellName;
	}

	public void setTargetCellName(String targetCellName) {
		this.targetCellName = targetCellName;
	}

	public String getTargetNodeName() {
		return targetNodeName;
	}

	public void setTargetNodeName(String targetNodeName) {
		this.targetNodeName = targetNodeName;
	}

	public String getTargetServerName() {
		return targetServerName;
	}

	public void setTargetServerName(String targetServerName) {
		this.targetServerName = targetServerName;
	}

	public String getDbDeployDBOwners() {
		return dbDeployDBOwners;
	}

	public void setDbDeployDBOwners(String dbDeployDBOwners) {
		this.dbDeployDBOwners = dbDeployDBOwners;
	}

	public List<String> getDbDeployPipelineList() {
		return dbDeployPipelineList;
	}

	public void setDbDeployPipelineList(List<String> dbDeployPipelineList) {
		this.dbDeployPipelineList = dbDeployPipelineList;
	}

}
