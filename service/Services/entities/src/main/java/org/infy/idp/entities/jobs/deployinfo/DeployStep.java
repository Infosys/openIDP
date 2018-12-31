/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import java.util.List;

import org.infy.idp.entities.jobs.applicationinfo.EnvironmentProvDetails;
import org.infy.idp.entities.jobs.common.AbortScript;
import org.infy.idp.entities.jobs.common.EnvProv;
import org.infy.idp.entities.jobs.common.Proxy;
import org.infy.idp.entities.jobs.common.RunScript;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store deployment steps
 * 
 * @author Infosys
 *
 */
@SuppressWarnings("PMD")

public class DeployStep {

	@SerializedName("stepName")
	@Expose
	private String stepName;
	@SerializedName("applicationUserName")
	@Expose
	private String applicationUserName;
	@SerializedName("adminUserName")
	@Expose
	private String adminUserName;
	@SerializedName("adminPassword")
	@Expose
	private String adminPassword;
	@SerializedName("machine")
	@Expose
	private String machine;
	@SerializedName("deployOS")
	@Expose
	private String deployOS;
	@SerializedName("installationFolder")
	@Expose
	private String installationFolder;

	@SerializedName("applicationPassword")
	@Expose
	private String applicationPassword;
	@SerializedName("runScript")
	@Expose
	private RunScript runScript;
	@SerializedName("deployToContainer")
	@Expose
	private DeployToContainer deployToContainer;

	@SerializedName("deployDatabase")
	@Expose
	private DeployDatabase deployDatabase;

	@SerializedName("websiteName")
	@Expose
	private DeployToContainer websiteName;
	@SerializedName("appPoolName")
	@Expose
	private DeployToContainer appPoolName;
	@SerializedName("destServerPath")
	@Expose
	private DeployToContainer destServerPath;

	@SerializedName("parameters")
	@Expose
	private String parameters;

	@SerializedName("pathToFiles")
	@Expose
	private String pathToFiles;

	@SerializedName("port")
	@Expose
	private Integer port;

	@SerializedName("dockerFilePath")
	@Expose
	private String dockerFilePath;

	@SerializedName("repoUrl")
	@Expose
	private String repoUrl;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("pullFromRepo")
	@Expose
	private String pullFromRepo;

	@SerializedName("dockerPort")
	@Expose
	private Integer dockerPort;

	@SerializedName("applicationPort")
	@Expose
	private Integer applicationPort;

	@SerializedName("pushToRepo")
	@Expose
	private String pushToRepo;

	@SerializedName("tagName")
	@Expose
	private String tagName;

	@SerializedName("artifact")
	@Expose
	private String artifact;

	@SerializedName("s3location")
	@Expose
	private String s3location;

	@SerializedName("hostName")
	@Expose
	private String hostName;

	@SerializedName("privateKey")
	@Expose
	private String privateKey;

	@SerializedName("artifactsToBeDeployed")
	@Expose
	private List<String> artifactsToBeDeployed;

	@SerializedName("targetRepo")
	@Expose
	private String targetRepo;
	@SerializedName("impObjXml")
	@Expose
	private String impObjXml;
	@SerializedName("controlFileShared")
	@Expose
	private String controlFileShared;
	@SerializedName("controlFile")
	@Expose
	private String controlFile;

	@SerializedName("sourceRepo")
	@Expose
	private String sourceRepo;
	@SerializedName("sourceFolder")
	@Expose
	private String sourceFolder;
	@SerializedName("sourceFolderShared")
	@Expose
	private String sourceFolderShared;
	@SerializedName("targetFolderShared")
	@Expose
	private String targetFolderShared;
	@SerializedName("targetFolder")
	@Expose
	private String targetFolder;
	@SerializedName("impObjshareXml")
	@Expose
	private String impObjshareXml;
	@SerializedName("ipcUserName")
	@Expose
	private String ipcUserName;
	@SerializedName("ipcPassword")
	@Expose
	private String ipcPassword;
	@SerializedName("ipcHostName")
	@Expose
	private String ipcHostName;
	@SerializedName("sqlFileName")
	@Expose
	private String sqlFileName;
	@SerializedName("reportName")
	@Expose
	private String reportName;
	@SerializedName("reportPath")
	@Expose
	private String reportPath;
	@SerializedName("oafFolderName")
	@Expose
	private String oafFolderName;
	@SerializedName("dbPassword")
	@Expose
	private String dbPassword;
	@SerializedName("sqlFolder")
	@Expose
	private String sqlFolder;
	@SerializedName("dbuserNameOTM")
	@Expose
	private String dbuserNameOTM;
	@SerializedName("dbpasswordOTM")
	@Expose
	private String dbpasswordOTM;
	@SerializedName("dbhostNameOTM")
	@Expose
	private String dbhostNameOTM;
	@SerializedName("dbPort")
	@Expose
	private String dbPort;
	@SerializedName("dbSid")
	@Expose
	private String dbSid;
	@SerializedName("tomUname")
	@Expose
	private String tomUname;
	@SerializedName("tomPwd")
	@Expose
	private String tomPwd;
	@SerializedName("tomHost")
	@Expose
	private String tomHost;
	@SerializedName("tomPort")
	@Expose
	private String tomPort;
	@SerializedName("appName")
	@Expose
	private String appName;
	@SerializedName("clrfld")
	@Expose
	private String clrfld;

	@SerializedName("timeout")
	@Expose
	private String timeout;

	@SerializedName("abortScript")
	@Expose
	private AbortScript abortScript;

	@SerializedName("formName")
	@Expose
	private String formName;
	@SerializedName("formFTPPath")
	@Expose
	private String formFTPPath;
	@SerializedName("formsBasePath")
	@Expose
	private String formsBasePath;
	@SerializedName("formsEnvFile")
	@Expose
	private String formsEnvFile;
	@SerializedName("formsDbPass")
	@Expose
	private String formsDbPass;
	@SerializedName("hostCtlfile")
	@Expose
	private String hostCtlfile;
	@SerializedName("reportPathCtl")
	@Expose
	private String reportPathCtl;
	@SerializedName("oaMediaFile")
	@Expose
	private String oaMediaFile;
	@SerializedName("reportPathOa")
	@Expose
	private String reportPathOa;
	@SerializedName("workFlowName")
	@Expose
	private String workFlowName;
	@SerializedName("workFlowFTPPath")
	@Expose
	private String workFlowFTPPath;
	@SerializedName("workFlowBasePath")
	@Expose
	private String workFlowBasePath;
	@SerializedName("workFlowEnvFile")
	@Expose
	private String workFlowEnvFile;
	@SerializedName("workFlowDbPass")
	@Expose
	private String workFlowDbPass;
	@SerializedName("customTopPath")
	@Expose
	private String customTopPath;
	@SerializedName("aolBasePath")
	@Expose
	private String aolBasePath;
	@SerializedName("aolEnvFile")
	@Expose
	private String aolEnvFile;
	@SerializedName("loadInFile")
	@Expose
	private String loadInFile;
	@SerializedName("upDownFile")
	@Expose
	private String upDownFile;
	@SerializedName("appsPass")
	@Expose
	private String appsPass;
	@SerializedName("deployUserName")
	@Expose
	private String deployUserName;
	@SerializedName("deployPassword")
	@Expose
	private String deployPassword;
	@SerializedName("dbOwner")
	@Expose
	private String dbOwner;
	@SerializedName("dbOwnerPassword")
	@Expose
	private String dbOwnerPassword;
	@SerializedName("iosDataPath")
	@Expose
	private String iosDataPath;
	@SerializedName("bizScriptPath")
	@Expose
	private String bizScriptPath;
	@SerializedName("bizserviceAccount")
	@Expose
	private String bizserviceAccount;
	@SerializedName("bizPassword")
	@Expose
	private String bizPassword;
	@SerializedName("dbuserName")
	@Expose
	private String dbuserName;
	@SerializedName("dbpassword")
	@Expose
	private String dbpassword;
	@SerializedName("dbhostName")
	@Expose
	private String dbhostName;
	@SerializedName("staticHostName")
	@Expose
	private String staticHostName;
	@SerializedName("staticSrcPath")
	@Expose
	private String staticSrcPath;
	@SerializedName("staticDestinationPath")
	@Expose
	private String staticDestinationPath;
	@SerializedName("staticUserName")
	@Expose
	private String staticUserName;

	@SerializedName("deploymentOption")
	@Expose
	private String deploymentOption;

	@SerializedName("staticPassword")
	@Expose
	private String staticPassword;
	@SerializedName("srfHostName")
	@Expose
	private String srfHostName;
	@SerializedName("srfSrcPath")
	@Expose
	private String srfSrcPath;
	@SerializedName("srfUserName")
	@Expose
	private String srfUserName;
	@SerializedName("srfPassword")
	@Expose
	private String srfPassword;
	@SerializedName("admHostName")
	@Expose
	private String admHostName;
	@SerializedName("admUserName")
	@Expose
	private String admUserName;
	@SerializedName("admPassword")
	@Expose
	private String admPassword;

	@SerializedName("deployOperation")
	@Expose
	private String deployOperation;
	@SerializedName("sourceFolderPath")
	@Expose
	private String sourceFolderPath;
	@SerializedName("destinationMachineName")
	@Expose
	private String destinationMachineName;
	@SerializedName("destinationFolder")
	@Expose
	private String destinationFolder;
	@SerializedName("databaseName")
	@Expose
	private String databaseName;
	@SerializedName("databaseUsername")
	@Expose
	private String databaseUsername;
	@SerializedName("databasePassword")
	@Expose
	private String databasePassword;
	@SerializedName("destinationBranch")
	@Expose
	private String destinationBranch;
	@SerializedName("destinationEnv")
	@Expose
	private String destinationEnv;
	@SerializedName("datFileName")
	@Expose
	private String datFileName;
	@SerializedName("toolsRoot")
	@Expose
	private String toolsRoot;
	@SerializedName("serverLocalPath")
	@Expose
	private String serverLocalPath;
	@SerializedName("ddltmpUsername")
	@Expose
	private String ddltmpUsername;
	@SerializedName("ddltmpPassword")
	@Expose
	private String ddltmpPassword;
	@SerializedName("siebelServer")
	@Expose
	private String siebelServer;
	@SerializedName("sourceEnvironment")
	@Expose
	private String sourceEnvironment;
	@SerializedName("datLocalPath")
	@Expose
	private String datLocalPath;
	@SerializedName("pstoolspath")
	@Expose
	private String pstoolspath;
	@SerializedName("repIMPEXPExePath")
	@Expose
	private String repIMPEXPExePath;
	@SerializedName("datExportUsername")
	@Expose
	private String datExportUsername;
	@SerializedName("datExportPassword")
	@Expose
	private String datExportPassword;
	@SerializedName("dataSource")
	@Expose
	private String dataSource;
	@SerializedName("tableOwner")
	@Expose
	private String tableOwner;

	@SerializedName("deployFuntion")
	@Expose
	private String deployFuntion;
	@SerializedName("serviceName")
	@Expose
	private String serviceName;
	@SerializedName("sibelDatImport")
	@Expose
	private String sibelDatImport;
	@SerializedName("datFileImport ")
	@Expose
	private String datFileImport;

	@SerializedName("targetUsername")
	@Expose
	private String targetUsername;

	@SerializedName("targetPassword")
	@Expose
	private String targetPassword;
	@SerializedName("proxy")
	@Expose
	private Proxy proxy;
	@SerializedName("muleServerGroup")
	@Expose
	private String muleServerGroup;
	@SerializedName("environmentProvDetails")
	@Expose
	private List<EnvironmentProvDetails> environmentProvDetails = null;

	@SerializedName("pubProfName")
	@Expose
	private String pubProfName;
	@SerializedName("appPackName")
	@Expose
	private String appPackName;
	@SerializedName("dbServName")
	@Expose
	private String dbServName;
	@SerializedName("dbName")
	@Expose
	private String dbName;
	@SerializedName("dbUName")
	@Expose
	private String dbUName;
	@SerializedName("dbPwd")
	@Expose
	private String dbPwd;

	@SerializedName("nexusurl")
	@Expose
	private String nexusurl;

	@SerializedName("envProv")
	@Expose
	private EnvProv envProv;

	@SerializedName("buildproperties")
	@Expose
	private String buildproperties;

	public EnvProv getEnvProv() {
		return envProv;
	}

	public void setEnvProv(EnvProv envProv) {
		this.envProv = envProv;
	}

	public String getNexusurl() {
		return nexusurl;
	}

	public void setNexusurl(String nexusurl) {
		this.nexusurl = nexusurl;
	}

	public String getBuildproperties() {
		return buildproperties;
	}

	public void setBuildproperties(String buildproperties) {
		this.buildproperties = buildproperties;
	}

	public String getIosDataPath() {
		return iosDataPath;
	}

	public void setIosDataPath(String iosDataPath) {
		this.iosDataPath = iosDataPath;
	}

	public String getPubProfName() {
		return pubProfName;
	}

	public void setPubProfName(String pubProfName) {
		this.pubProfName = pubProfName;
	}

	public String getAppPackName() {
		return appPackName;
	}

	public void setAppPackName(String appPackName) {
		this.appPackName = appPackName;
	}

	public String getDbServName() {
		return dbServName;
	}

	public void setDbServName(String dbServName) {
		this.dbServName = dbServName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUName() {
		return dbUName;
	}

	public void setDbUName(String dbUName) {
		this.dbUName = dbUName;
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

	public List<EnvironmentProvDetails> getEnvironmentProvDetails() {
		return environmentProvDetails;
	}

	public void setEnvironmentProvDetails(List<EnvironmentProvDetails> environmentProvDetails) {
		this.environmentProvDetails = environmentProvDetails;
	}

	public String getTomUname() {
		return tomUname;
	}

	public void setTomUname(String tomUname) {
		this.tomUname = tomUname;
	}

	public String getTomPwd() {
		return tomPwd;
	}

	public void setTomPwd(String tomPwd) {
		this.tomPwd = tomPwd;
	}

	public String getTomHost() {
		return tomHost;
	}

	public void setTomHost(String tomHost) {
		this.tomHost = tomHost;
	}

	public String getTomPort() {
		return tomPort;
	}

	public void setTomPort(String tomPort) {
		this.tomPort = tomPort;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getClrfld() {
		return clrfld;
	}

	public void setClrfld(String clrfld) {
		this.clrfld = clrfld;
	}

	public AbortScript getAbortScript() {
		return abortScript;
	}

	public void setAbortScript(AbortScript abortScript) {
		this.abortScript = abortScript;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getSqlFolder() {
		return sqlFolder;
	}

	public void setSqlFolder(String sqlFolder) {
		this.sqlFolder = sqlFolder;
	}

	public String getDbuserNameOTM() {
		return dbuserNameOTM;
	}

	public void setDbuserNameOTM(String dbuserNameOTM) {
		this.dbuserNameOTM = dbuserNameOTM;
	}

	public String getDbpasswordOTM() {
		return dbpasswordOTM;
	}

	public void setDbpasswordOTM(String dbpasswordOTM) {
		this.dbpasswordOTM = dbpasswordOTM;
	}

	public String getDbhostNameOTM() {
		return dbhostNameOTM;
	}

	public void setDbhostNameOTM(String dbhostNameOTM) {
		this.dbhostNameOTM = dbhostNameOTM;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbSid() {
		return dbSid;
	}

	public void setDbSid(String dbSid) {
		this.dbSid = dbSid;
	}

	public String getOafFolderName() {
		return oafFolderName;
	}

	public void setOafFolderName(String oafFolderName) {
		this.oafFolderName = oafFolderName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getTargetUsername() {
		return targetUsername;
	}

	public void setTargetUsername(String targetUsername) {
		this.targetUsername = targetUsername;
	}

	public String getTargetPassword() {
		return targetPassword;
	}

	public void setTargetPassword(String targetPassword) {
		this.targetPassword = targetPassword;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public String getSibelDatImport() {
		return sibelDatImport;
	}

	public void setSibelDatImport(String sibelDatImport) {
		this.sibelDatImport = sibelDatImport;
	}

	public String getDatFileImport() {
		return datFileImport;
	}

	public void setDatFileImport(String datFileImport) {
		this.datFileImport = datFileImport;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public DeployDatabase getDeployDatabase() {
		return deployDatabase;
	}

	public void setDeployDatabase(DeployDatabase deployDatabase) {
		this.deployDatabase = deployDatabase;
	}

	public String getDeployOperation() {
		return deployOperation;
	}

	public void setDeployOperation(String deployOperation) {
		this.deployOperation = deployOperation;
	}

	public String getSourceFolderPath() {
		return sourceFolderPath;
	}

	public void setSourceFolderPath(String sourceFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
	}

	public String getDestinationMachineName() {
		return destinationMachineName;
	}

	public void setDestinationMachineName(String destinationMachineName) {
		this.destinationMachineName = destinationMachineName;
	}

	public String getDestinationFolder() {
		return destinationFolder;
	}

	public void setDestinationFolder(String destinationFolder) {
		this.destinationFolder = destinationFolder;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public String getDestinationBranch() {
		return destinationBranch;
	}

	public void setDestinationBranch(String destinationBranch) {
		this.destinationBranch = destinationBranch;
	}

	public String getDestinationEnv() {
		return destinationEnv;
	}

	public void setDestinationEnv(String destinationEnv) {
		this.destinationEnv = destinationEnv;
	}

	public String getDatFileName() {
		return datFileName;
	}

	public void setDatFileName(String datFileName) {
		this.datFileName = datFileName;
	}

	public String getToolsRoot() {
		return toolsRoot;
	}

	public void setToolsRoot(String toolsRoot) {
		this.toolsRoot = toolsRoot;
	}

	public String getServerLocalPath() {
		return serverLocalPath;
	}

	public void setServerLocalPath(String serverLocalPath) {
		this.serverLocalPath = serverLocalPath;
	}

	public String getDdltmpUsername() {
		return ddltmpUsername;
	}

	public void setDdltmpUsername(String ddltmpUsername) {
		this.ddltmpUsername = ddltmpUsername;
	}

	public String getDdltmpPassword() {
		return ddltmpPassword;
	}

	public void setDdltmpPassword(String ddltmpPassword) {
		this.ddltmpPassword = ddltmpPassword;
	}

	public String getSiebelServer() {
		return siebelServer;
	}

	public void setSiebelServer(String siebelServer) {
		this.siebelServer = siebelServer;
	}

	public String getSourceEnvironment() {
		return sourceEnvironment;
	}

	public void setSourceEnvironment(String sourceEnvironment) {
		this.sourceEnvironment = sourceEnvironment;
	}

	public String getDatLocalPath() {
		return datLocalPath;
	}

	public void setDatLocalPath(String datLocalPath) {
		this.datLocalPath = datLocalPath;
	}

	public String getPstoolspath() {
		return pstoolspath;
	}

	public void setPstoolspath(String pstoolspath) {
		this.pstoolspath = pstoolspath;
	}

	public String getRepIMPEXPExePath() {
		return repIMPEXPExePath;
	}

	public void setRepIMPEXPExePath(String repIMPEXPExePath) {
		this.repIMPEXPExePath = repIMPEXPExePath;
	}

	public String getDatExportUsername() {
		return datExportUsername;
	}

	public void setDatExportUsername(String datExportUsername) {
		this.datExportUsername = datExportUsername;
	}

	public String getDatExportPassword() {
		return datExportPassword;
	}

	public void setDatExportPassword(String datExportPassword) {
		this.datExportPassword = datExportPassword;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getTableOwner() {
		return tableOwner;
	}

	public void setTableOwner(String tableOwner) {
		this.tableOwner = tableOwner;
	}

	public String getDeployFuntion() {
		return deployFuntion;
	}

	public void setDeployFuntion(String deployFuntion) {
		this.deployFuntion = deployFuntion;
	}

	public String getSrfHostName() {
		return srfHostName;
	}

	public void setSrfHostName(String srfHostName) {
		this.srfHostName = srfHostName;
	}

	public String getSrfSrcPath() {
		return srfSrcPath;
	}

	public void setSrfSrcPath(String srfSrcPath) {
		this.srfSrcPath = srfSrcPath;
	}

	public String getSrfUserName() {
		return srfUserName;
	}

	public void setSrfUserName(String srfUserName) {
		this.srfUserName = srfUserName;
	}

	public String getSrfPassword() {
		return srfPassword;
	}

	public void setSrfPassword(String srfPassword) {
		this.srfPassword = srfPassword;
	}

	public String getAdmHostName() {
		return admHostName;
	}

	public void setAdmHostName(String admHostName) {
		this.admHostName = admHostName;
	}

	public String getAdmUserName() {
		return admUserName;
	}

	public void setAdmUserName(String admUserName) {
		this.admUserName = admUserName;
	}

	public String getAdmPassword() {
		return admPassword;
	}

	public void setAdmPassword(String admPassword) {
		this.admPassword = admPassword;
	}

	public String getStaticHostName() {
		return staticHostName;
	}

	public void setStaticHostName(String staticHostName) {
		this.staticHostName = staticHostName;
	}

	public String getStaticSrcPath() {
		return staticSrcPath;
	}

	public void setStaticSrcPath(String staticSrcPath) {
		this.staticSrcPath = staticSrcPath;
	}

	public String getStaticDestinationPath() {
		return staticDestinationPath;
	}

	public void setStaticDestinationPath(String staticDestinationPath) {
		this.staticDestinationPath = staticDestinationPath;
	}

	public String getStaticUserName() {
		return staticUserName;
	}

	public void setStaticUserName(String staticUserName) {
		this.staticUserName = staticUserName;
	}

	public String getStaticPassword() {
		return staticPassword;
	}

	public void setStaticPassword(String staticPassword) {
		this.staticPassword = staticPassword;
	}

	public String getDbuserName() {
		return dbuserName;
	}

	public void setDbuserName(String dbuserName) {
		this.dbuserName = dbuserName;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public String getDbhostName() {
		return dbhostName;
	}

	public void setDbhostName(String dbhostName) {
		this.dbhostName = dbhostName;
	}

	public String getBizScriptPath() {
		return bizScriptPath;
	}

	public void setBizScriptPath(String bizScriptPath) {
		this.bizScriptPath = bizScriptPath;
	}

	public String getBizserviceAccount() {
		return bizserviceAccount;
	}

	public void setBizserviceAccount(String bizserviceAccount) {
		this.bizserviceAccount = bizserviceAccount;
	}

	public String getBizPassword() {
		return bizPassword;
	}

	public void setBizPassword(String bizPassword) {
		this.bizPassword = bizPassword;
	}

	public String getDeployUserName() {
		return deployUserName;
	}

	public void setDeployUserName(String deployUserName) {
		this.deployUserName = deployUserName;
	}

	public String getDeployPassword() {
		return deployPassword;
	}

	public void setDeployPassword(String deployPassword) {
		this.deployPassword = deployPassword;
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

	public String getSqlFileName() {
		return sqlFileName;
	}

	public void setSqlFileName(String sqlFileName) {
		this.sqlFileName = sqlFileName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormFTPPath() {
		return formFTPPath;
	}

	public void setFormFTPPath(String formFTPPath) {
		this.formFTPPath = formFTPPath;
	}

	public String getFormsBasePath() {
		return formsBasePath;
	}

	public void setFormsBasePath(String formsBasePath) {
		this.formsBasePath = formsBasePath;
	}

	public String getFormsEnvFile() {
		return formsEnvFile;
	}

	public void setFormsEnvFile(String formsEnvFile) {
		this.formsEnvFile = formsEnvFile;
	}

	public String getFormsDbPass() {
		return formsDbPass;
	}

	public void setFormsDbPass(String formsDbPass) {
		this.formsDbPass = formsDbPass;
	}

	public String getHostCtlfile() {
		return hostCtlfile;
	}

	public void setHostCtlfile(String hostCtlfile) {
		this.hostCtlfile = hostCtlfile;
	}

	public String getReportPathCtl() {
		return reportPathCtl;
	}

	public void setReportPathCtl(String reportPathCtl) {
		this.reportPathCtl = reportPathCtl;
	}

	public String getOaMediaFile() {
		return oaMediaFile;
	}

	public void setOaMediaFile(String oaMediaFile) {
		this.oaMediaFile = oaMediaFile;
	}

	public String getReportPathOa() {
		return reportPathOa;
	}

	public void setReportPathOa(String reportPathOa) {
		this.reportPathOa = reportPathOa;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getWorkFlowFTPPath() {
		return workFlowFTPPath;
	}

	public void setWorkFlowFTPPath(String workFlowFTPPath) {
		this.workFlowFTPPath = workFlowFTPPath;
	}

	public String getWorkFlowBasePath() {
		return workFlowBasePath;
	}

	public void setWorkFlowBasePath(String workFlowBasePath) {
		this.workFlowBasePath = workFlowBasePath;
	}

	public String getWorkFlowEnvFile() {
		return workFlowEnvFile;
	}

	public void setWorkFlowEnvFile(String workFlowEnvFile) {
		this.workFlowEnvFile = workFlowEnvFile;
	}

	public String getWorkFlowDbPass() {
		return workFlowDbPass;
	}

	public void setWorkFlowDbPass(String workFlowDbPass) {
		this.workFlowDbPass = workFlowDbPass;
	}

	public String getCustomTopPath() {
		return customTopPath;
	}

	public void setCustomTopPath(String customTopPath) {
		this.customTopPath = customTopPath;
	}

	public String getAolBasePath() {
		return aolBasePath;
	}

	public void setAolBasePath(String aolBasePath) {
		this.aolBasePath = aolBasePath;
	}

	public String getAolEnvFile() {
		return aolEnvFile;
	}

	public void setAolEnvFile(String aolEnvFile) {
		this.aolEnvFile = aolEnvFile;
	}

	public String getLoadInFile() {
		return loadInFile;
	}

	public void setLoadInFile(String loadInFile) {
		this.loadInFile = loadInFile;
	}

	public String getUpDownFile() {
		return upDownFile;
	}

	public void setUpDownFile(String upDownFile) {
		this.upDownFile = upDownFile;
	}

	public String getAppsPass() {
		return appsPass;
	}

	public void setAppsPass(String appsPass) {
		this.appsPass = appsPass;
	}

	public String getIpcUserName() {
		return ipcUserName;
	}

	public void setIpcUserName(String ipcUserName) {
		this.ipcUserName = ipcUserName;
	}

	public String getIpcPassword() {
		return ipcPassword;
	}

	public void setIpcPassword(String ipcPassword) {
		this.ipcPassword = ipcPassword;
	}

	public String getIpcHostName() {
		return ipcHostName;
	}

	public void setIpcHostName(String ipcHostName) {
		this.ipcHostName = ipcHostName;
	}

	public String getTargetRepo() {
		return targetRepo;
	}

	public void setTargetRepo(String targetRepo) {
		this.targetRepo = targetRepo;
	}

	public String getImpObjXml() {
		return impObjXml;
	}

	public void setImpObjXml(String impObjXml) {
		this.impObjXml = impObjXml;
	}

	public String getControlFileShared() {
		return controlFileShared;
	}

	public void setControlFileShared(String controlFileShared) {
		this.controlFileShared = controlFileShared;
	}

	public String getControlFile() {
		return controlFile;
	}

	public void setControlFile(String controlFile) {
		this.controlFile = controlFile;
	}

	public String getSourceRepo() {
		return sourceRepo;
	}

	public void setSourceRepo(String sourceRepo) {
		this.sourceRepo = sourceRepo;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getSourceFolderShared() {
		return sourceFolderShared;
	}

	public void setSourceFolderShared(String sourceFolderShared) {
		this.sourceFolderShared = sourceFolderShared;
	}

	public String getTargetFolderShared() {
		return targetFolderShared;
	}

	public void setTargetFolderShared(String targetFolderShared) {
		this.targetFolderShared = targetFolderShared;
	}

	public String getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	public String getImpObjshareXml() {
		return impObjshareXml;
	}

	public void setImpObjshareXml(String impObjshareXml) {
		this.impObjshareXml = impObjshareXml;
	}

	public List<String> getArtifactsToBeDeployed() {
		return artifactsToBeDeployed;
	}

	public void setArtifactsToBeDeployed(List<String> artifactsToBeDeployed) {
		this.artifactsToBeDeployed = artifactsToBeDeployed;
	}

	public String getS3location() {
		return s3location;
	}

	public void setS3location(String s3location) {
		this.s3location = s3location;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getPushToRepo() {
		return pushToRepo;
	}

	public void setPushToRepo(String pushToRepo) {
		this.pushToRepo = pushToRepo;
	}

	public Integer getApplicationPort() {
		return applicationPort;
	}

	public void setApplicationPort(Integer applicationPort) {
		this.applicationPort = applicationPort;
	}

	public String getDockerFilePath() {
		return dockerFilePath;
	}

	public void setDockerFilePath(String dockerFilePath) {
		this.dockerFilePath = dockerFilePath;
	}

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
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

	public String getPullFromRepo() {
		return pullFromRepo;
	}

	public void setPullFromRepo(String pullFromRepo) {
		this.pullFromRepo = pullFromRepo;
	}

	public Integer getDockerPort() {
		return dockerPort;
	}

	public void setDockerPort(Integer dockerPort) {
		this.dockerPort = dockerPort;
	}

	public String getPathToFiles() {
		return pathToFiles;
	}

	public void setPathToFiles(String pathToFiles) {
		this.pathToFiles = pathToFiles;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public String getDeployOS() {
		return deployOS;
	}

	public void setDeployOS(String deployOS) {
		this.deployOS = deployOS;
	}

	public String getInstallationFolder() {
		return installationFolder;
	}

	public void setInstallationFolder(String installationFolder) {
		this.installationFolder = installationFolder;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public RunScript getRunScript() {
		return runScript;
	}

	public void setRunScript(RunScript deployScript) {
		this.runScript = deployScript;
	}

	public DeployToContainer getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(DeployToContainer websiteName) {
		this.websiteName = websiteName;
	}

	public DeployToContainer getAppPoolName() {
		return appPoolName;
	}

	public void setAppPoolName(DeployToContainer appPoolName) {
		this.appPoolName = appPoolName;
	}

	public DeployToContainer getDestServerPath() {
		return destServerPath;
	}

	public void setDestServerPath(DeployToContainer destServerPath) {
		this.destServerPath = destServerPath;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getApplicationUserName() {
		return applicationUserName;
	}

	public void setApplicationUserName(String applicationUserName) {
		this.applicationUserName = applicationUserName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getApplicationPassword() {
		return applicationPassword;
	}

	public void setApplicationPassword(String applicationPassword) {
		this.applicationPassword = applicationPassword;
	}

	public DeployToContainer getDeployToContainer() {
		return deployToContainer;
	}

	public void setDeployToContainer(DeployToContainer deployToContainer) {
		this.deployToContainer = deployToContainer;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getMuleServerGroup() {
		return muleServerGroup;
	}

	public void setMuleServerGroup(String muleServerGroup) {
		this.muleServerGroup = muleServerGroup;
	}

	public String getDeploymentOption() {
		return deploymentOption;
	}

	public void setDeploymentOption(String deploymentOption) {
		this.deploymentOption = deploymentOption;
	}

}
