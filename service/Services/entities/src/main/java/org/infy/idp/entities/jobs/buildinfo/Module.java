/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import java.util.List;

import org.infy.idp.entities.jobs.common.AbortScript;
import org.infy.idp.entities.jobs.common.Migration;
import org.infy.idp.entities.jobs.common.Proxy;
import org.infy.idp.entities.jobs.common.RunScript;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store build module info
 * 
 * @author Infosys
 *
 */
@SuppressWarnings("PMD")
public class Module {
	@SerializedName("codeFormatting")
	@Expose
	private String codeFormatting;
	@SerializedName("customScript")
	@Expose
	private String customScript;
	@SerializedName("buildType")
	@Expose
	private String buildType;
	@SerializedName("buildTool")
	@Expose
	private String buildTool;
	@SerializedName("transfer")
	@Expose
	private String transfer;
	@SerializedName("hostName")
	@Expose
	private String hostName;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("execute")
	@Expose
	private String execute;
	@SerializedName("localmachineExecution")
	@Expose
	private String localmachineExecution;
	@SerializedName("dependencyFile")
	@Expose
	private String dependencyFile;
	@SerializedName("masterMachineName")
	@Expose
	private String masterMachineName;
	@SerializedName("classFileName")
	@Expose
	private String classFileName;
	@SerializedName("outputFolder")
	@Expose
	private String outputFolder;
	@SerializedName("version")
	@Expose
	private String version;
	@SerializedName("codeAnalysisTragets")
	@Expose
	private String codeAnalysisTargets;
	@SerializedName("unitTestTargets")
	@Expose
	private String unitTestTargets;
	@SerializedName("securityProjName")
	@Expose
	private String securityProjName;
	@SerializedName("avdName")
	@Expose
	private String avdName;
	@SerializedName("envName")
	@Expose
	private String envName;
	@SerializedName("dbName")
	@Expose
	private String dbName;
	@SerializedName("port")
	@Expose
	private String port;
	@SerializedName("productKey")
	@Expose
	private String productKey;

	@SerializedName("messageFlows")
	@Expose
	private String messageFlows;
	@SerializedName("nexusType")
	@Expose
	private String nexusType;
	@SerializedName("nexusUrl")
	@Expose
	private String nexusUrl;
	@SerializedName("home")
	@Expose
	private String home;
	@SerializedName("thirdPartyJar")
	@Expose
	private String thirdPartyJar;
	@SerializedName("cdtIn")
	@Expose
	private String cdtIn;
	@SerializedName("cdtOut")
	@Expose
	private String cdtOut;
	@SerializedName("destDB")
	@Expose
	private String destDB;
	@SerializedName("destPassword")
	@Expose
	private String destPassword;
	@SerializedName("repository")
	@Expose
	private String repository;
	@SerializedName("runScript")
	@Expose
	private RunScript runScript;
	@SerializedName("abortScript")
	@Expose
	private AbortScript abortScript;

	@SerializedName("moduleName")
	@Expose
	private String moduleName;
	@SerializedName("relativePath")
	@Expose
	private String relativePath;
	@SerializedName("timeout")
	@Expose
	private String timeout;

	@SerializedName("securityAnalysis")
	@Expose
	private String securityAnalysis;
	@SerializedName("pafFilePath")
	@Expose
	private String pafFilePath;

	@SerializedName("codeAnalysis")
	@Expose
	private List<String> codeAnalysis = null;

	@SerializedName("unitTesting")
	@Expose
	private String unitTesting;
	@SerializedName("unitTestProjectName")
	@Expose
	private String unitTestProjectName;
	@SerializedName("unitTestCategory")
	@Expose
	private String unitTestCategory;
	@SerializedName("codeCoverage")
	@Expose
	private String codeCoverage;
	@SerializedName("testSettingFilePath")
	@Expose
	private String testSettingFilePath;
	@SerializedName("serverMachine")
	@Expose
	private String serverMachine;

	@SerializedName("customBuildXml")
	@Expose
	private String customBuildXml;
	@SerializedName("args")
	@Expose
	private String args;
	@SerializedName("customTasks")
	@Expose
	private String customTasks;
	@SerializedName("compile")
	@Expose
	private String compile;
	@SerializedName("jarPackaging")
	@Expose
	private String jarPackaging;

	@SerializedName("ejbDescriptor")
	@Expose
	private String ejbDescriptor;
	@SerializedName("javaMainClass")
	@Expose
	private String javaMainClass;
	@SerializedName("warPackaging")
	@Expose
	private String warPackaging;
	@SerializedName("globalGoals")
	@Expose
	private String globalGoals;
	@SerializedName("clean")
	@Expose
	private String clean;
	@SerializedName("install")
	@Expose
	private String install;
	@SerializedName("MVNOPTS")
	@Expose
	private String mVNOPTS;

	@SerializedName("jsonPath")
	@Expose
	private String jsonPath;
	@SerializedName("groupId")
	@Expose
	private String groupId;

	@SerializedName("projectName")
	@Expose
	private String projectName;
	@SerializedName("dbType")
	@Expose
	private String dbType;

	@SerializedName("reportMigration")
	@Expose
	private List<Migration> reportMigration = null;

	@SerializedName("publishForms")
	@Expose
	private List<Migration> publishForms = null;
	@SerializedName("hostCTLMigration")
	@Expose
	private List<Migration> hostCTLMigration = null;
	@SerializedName("oaFileMigration")
	@Expose
	private List<Migration> oaFileMigration = null;
	@SerializedName("workFlowPublish")
	@Expose
	private List<Migration> workFlowPublish = null;

	@SerializedName("uiEarFileName")
	@Expose
	private String uiEarFileName;
	@SerializedName("integrationFileName")
	@Expose
	private String integrationFileName;
	@SerializedName("pegaUserName")
	@Expose
	private String pegaUserName;
	@SerializedName("pegaPassword")
	@Expose
	private String pegaPassword;
	@SerializedName("pegaProductKey")
	@Expose
	private String pegaProductKey;
	@SerializedName("pegaDBdataUname")
	@Expose
	private String pegaDBdataUname;
	@SerializedName("pegaDBName")
	@Expose
	private String pegaDBName;
	@SerializedName("pegaDBHost")
	@Expose
	private String pegaDBHost;
	@SerializedName("pegaDBDataPort")
	@Expose
	private String pegaDBDataPort;
	@SerializedName("pegaSqlFile")
	@Expose
	private String pegaSqlFile;
	@SerializedName("ruleSchema")
	@Expose
	private String ruleSchema;
	@SerializedName("pegaDBUname")
	@Expose
	private String pegaDBUname;
	@SerializedName("pegaDBPwd")
	@Expose
	private String pegaDBPwd;
	@SerializedName("pegaDBSchema")
	@Expose
	private String pegaDBSchema;
	@SerializedName("pegaDBPort")
	@Expose
	private String pegaDBPort;
	@SerializedName("pegHost")
	@Expose
	private String pegaHost;
	@SerializedName("pegaProjName")
	@Expose
	private String pegaProjName;
	@SerializedName("pegaTableName")
	@Expose
	private String pegaTableName;
	@SerializedName("ossMailRecipients")
	@Expose
	private String ossMailRecipients;
	@SerializedName("ossDistributionType")
	@Expose
	private String ossDistributionType;

	@SerializedName("ossAnalysisType")
	@Expose
	private String ossAnalysisType;

	@SerializedName("renaming")
	@Expose
	private String renaming;
	@SerializedName("controlFlow")
	@Expose
	private String controlFlow;
	@SerializedName("encryption")
	@Expose
	private String encryption;

	@SerializedName("unitTestDir")
	@Expose
	private String unitTestDir;

	@SerializedName("exclude")
	@Expose
	private String exclude;
	@SerializedName("installGrunt")
	@Expose
	private String installGrunt;
	@SerializedName("excludeFolders")
	@Expose
	private String excludeFolders;
	@SerializedName("incrementalScan")
	@Expose
	private String incrementalScan;
	@SerializedName("interval")
	@Expose
	private String interval;
	@SerializedName("team")
	@Expose
	private String team;
	@SerializedName("preset")
	@Expose
	private String preset;
	@SerializedName("serverUrl")
	@Expose
	private String serverUrl;
	@SerializedName("checkmarxProjName")
	@Expose
	private String checkmarxProjName;

	@SerializedName("multiModule")
	@Expose
	private String multiModule;
	@SerializedName("report")
	@Expose
	private String report;

	@SerializedName("privateKey")
	@Expose
	private String privateKey;

	@SerializedName("remoteDir")
	@Expose
	private String remoteDir;

	@SerializedName("buildValue")
	@Expose
	private String buildValue;

	@SerializedName("unitTestTool")
	@Expose
	private List<String> unitTestTool;

	@SerializedName("codeCoverageTool")
	@Expose
	private List<String> codeCoverageTool;
	@SerializedName("npmProxy")
	@Expose
	private String npmProxy;
	@SerializedName("angularBuildCommand")
	@Expose
	private String angularBuildCommand;

	@SerializedName("allUnitTestPass")
	@Expose
	private String allUnitTestPass;
	@SerializedName("noViolations")
	@Expose
	private String noViolations;
	@SerializedName("authenticationMode")
	@Expose
	private String authenticationMode;
	@SerializedName("rollbackScript")
	@Expose
	private String rollbackScript;

	@SerializedName("varient")
	@Expose
	private String varient;
	@SerializedName("inspectionName")
	@Expose
	private String inspectionName;

	@SerializedName("database")
	@Expose
	private String database;

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
	@SerializedName("exportObjSharedXml")
	@Expose
	private String exportObjSharedXml;
	@SerializedName("exportObjXml")
	@Expose
	private String exportObjXml;
	@SerializedName("targetRepo")
	@Expose
	private String targetRepo;
	@SerializedName("ipcHostName")
	@Expose
	private String ipcHostName;
	@SerializedName("ipcPassword")
	@Expose
	private String ipcPassword;
	@SerializedName("ipcUserName")
	@Expose
	private String ipcUserName;
	@SerializedName("iosCodeAnalysis")
	@Expose
	private String iosCodeAnalysis;
	@SerializedName("iosBuildVersion")
	@Expose
	private String iosBuildVersion;
	@SerializedName("iosBuildTarget")
	@Expose
	private String iosBuildTarget;
	@Expose
	private String codeAnalysisTarget;
	@SerializedName("unitTestingTarget")
	@Expose
	private String unitTestingTarget;
	@SerializedName("archName")
	@Expose
	private String archName;
	@SerializedName("appServ")
	@Expose
	private String appServ;
	@SerializedName("appPort")
	@Expose
	private String appPort;
	@SerializedName("proPar")
	@Expose
	private String proPar;

	@SerializedName("serUname")
	@Expose
	private String serUname;
	@SerializedName("servPass")
	@Expose
	private String servPass;

	@SerializedName("sonarUrl")
	@Expose
	private String sonarUrl;

	@SerializedName("sonarUserName")
	@Expose
	private String sonarUserName;

	@SerializedName("sonarPassword")
	@Expose
	private String sonarPassword;

	@SerializedName("sqlFilesPackage")
	@Expose
	private String sqlFilesPackage;
	@SerializedName("connectionName")
	@Expose
	private String connectionName;
	@SerializedName("testSuite")
	@Expose
	private String testSuite;

	@SerializedName("logFilePath")
	@Expose
	private String logFilePath;

	@SerializedName("toolsRoot")
	@Expose
	private String toolsRoot;
	@SerializedName("toolsCfg")
	@Expose
	private String toolsCfg;
	@SerializedName("dataSource")
	@Expose
	private String dataSource;
	@SerializedName("siebelUserName")
	@Expose
	private String siebelUserName;
	@SerializedName("siebelPassword")
	@Expose
	private String siebelPassword;
	@SerializedName("siebelRepo")
	@Expose
	private String siebelRepo;
	@SerializedName("sifImport")
	@Expose
	private String sifImport;
	@SerializedName("sibcompile")
	@Expose
	private String sibcompile;

	@SerializedName("defaultModule")
	@Expose
	private String defaultModule;

	@SerializedName("folder")
	@Expose
	private String folder;
	@SerializedName("filePath")
	@Expose
	private String filePath;
	@SerializedName("pigProjectName")
	@Expose
	private String pigProjectName;
	@SerializedName("pigPomPath")
	@Expose
	private String pigPomPath;
	@SerializedName("pigUT")
	@Expose
	private String pigUT;
	@SerializedName("hiveProjectPath")
	@Expose
	private String hiveProjectPath;
	@SerializedName("hivePomPath")
	@Expose
	private String hivePomPath;
	@SerializedName("hiveUT")
	@Expose
	private String hiveUT;
	@SerializedName("scalaProjectName")
	@Expose
	private String scalaProjectName;
	@SerializedName("scalaPomPath")
	@Expose
	private String scalaPomPath;
	@SerializedName("scalaCC")
	@Expose
	private String scalaCC;

	@SerializedName("sourceUsername")
	@Expose
	private String sourceUsername;
	@SerializedName("sourcePassword")
	@Expose
	private String sourcePassword;
	@SerializedName("sourceUrl")
	@Expose
	private String sourceUrl;
	@SerializedName("proxy")
	@Expose
	private Proxy proxy;


	@SerializedName("servMachine")
	@Expose
	private String servMachine;

	@SerializedName("projPath")
	@Expose
	private String projPath;
	@SerializedName("srcSchName")
	@Expose
	private String srcSchName;
	@SerializedName("tarSchName")
	@Expose
	private String tarSchName;
	@SerializedName("mainProjectName")
	@Expose
	private String mainProjectName;
	@SerializedName("codeAna")
	@Expose
	private String codeAna;
	@SerializedName("dllName")
	@Expose
	private String dllName;
	@SerializedName("fixPath")
	@Expose
	private String fixPath;
	@SerializedName("nUnitprojPath")
	@Expose
	private String nUnitprojPath;

	@SerializedName("nexusurl")
	@Expose
	private String nexusurl;
	@SerializedName("buildproperties")
	@Expose
	private String buildproperties;
	

	@SerializedName("npmProxyUserName")
	@Expose
	private String npmProxyUserName;

	@SerializedName("npmProxyPassword")
	@Expose
	private String npmProxyPassword;
	
	@SerializedName("sonarProjectKey")
	@Expose
	private String sonarProjectKey;
	
	@SerializedName("sonarProperties")
	@Expose
	private String sonarProperties;

	public String getSonarProperties() {
		return sonarProperties;
	}

	public void setSonarProperties(String sonarProperties) {
		this.sonarProperties = sonarProperties;
	}


	public String getSonarProjectKey() {
		return sonarProjectKey;
	}

	public void setSonarProjectKey(String sonarProjectKey) {
		this.sonarProjectKey = sonarProjectKey;
	}

	//Add Jira
//	@SerializedName("raiseJiraBug")
//	@Expose
//	private String raiseJiraBug;

	
	
	
//	public String getRaiseJiraBug() {
//		return raiseJiraBug;
//	}
//
//	public void setRaiseJiraBug(String raiseJiraBug) {
//		this.raiseJiraBug = raiseJiraBug;
//	}

	public void setCodeAnalysisTargets(String codeAnalysisTargets) {
		this.codeAnalysisTargets = codeAnalysisTargets;
	}

	public String getSonarUserName() {
		return sonarUserName;
	}

	public void setSonarUserName(String sonarUserName) {
		this.sonarUserName = sonarUserName;
	}

	public String getSonarPassword() {
		return sonarPassword;
	}

	public void setSonarPassword(String sonarPassword) {
		this.sonarPassword = sonarPassword;
	}

	public String getSonarUrl() {
		return sonarUrl;
	}

	public void setSonarUrl(String sonarUrl) {
		this.sonarUrl = sonarUrl;
	}

	public String getSerUname() {
		return serUname;
	}

	public void setSerUname(String serUname) {
		this.serUname = serUname;
	}

	public String getServPass() {
		return servPass;
	}

	public void setServPass(String servPass) {
		this.servPass = servPass;
	}


	public String getAppServ() {
		return appServ;
	}

	public void setAppServ(String appServ) {
		this.appServ = appServ;
	}

	public String getAppPort() {
		return appPort;
	}

	public void setAppPort(String appPort) {
		this.appPort = appPort;
	}

	public String getProPar() {
		return proPar;
	}

	public void setProPar(String proPar) {
		this.proPar = proPar;
	}
	public String getIosBuildVersion() {
		return iosBuildVersion;
	}

	public void setIosBuildVersion(String iosBuildVersion) {
		this.iosBuildVersion = iosBuildVersion;
	}

	public String getIosBuildTarget() {
		return iosBuildTarget;
	}

	public void setIosBuildTarget(String iosBuildTarget) {
		this.iosBuildTarget = iosBuildTarget;
	}
	
	public String getIosCodeAnalysis() {
		return iosCodeAnalysis;
	}

	public void setIosCodeAnalysis(String iosCodeAnalysis) {
		this.iosCodeAnalysis = iosCodeAnalysis;
	}


	public String getArchName() {
		return archName;
	}

	public void setArchName(String archName) {
		this.archName = archName;
	}

	public String getCodeAnalysisTarget() {
		return codeAnalysisTarget;
	}

	public void setCodeAnalysisTarget(String codeAnalysisTarget) {
		this.codeAnalysisTarget = codeAnalysisTarget;
	}

	public String getUnitTestingTarget() {
		return unitTestingTarget;
	}

	public void setUnitTestingTarget(String unitTestingTarget) {
		this.unitTestingTarget = unitTestingTarget;
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

	public String getnUnitprojPath() {
		return nUnitprojPath;
	}

	public void setnUnitprojPath(String nUnitprojPath) {
		this.nUnitprojPath = nUnitprojPath;
	}

	public String getMainProjectName() {
		return mainProjectName;
	}

	public void setMainProjectName(String mainProjectName) {
		this.mainProjectName = mainProjectName;
	}

	public String getCodeAna() {
		return codeAna;
	}

	public void setCodeAna(String codeAna) {
		this.codeAna = codeAna;
	}

	public String getDllName() {
		return dllName;
	}

	public void setDllName(String dllName) {
		this.dllName = dllName;
	}

	public String getFixPath() {
		return fixPath;
	}

	public void setFixPath(String fixPath) {
		this.fixPath = fixPath;
	}

	public String getProjPath() {
		return projPath;
	}

	public void setProjPath(String projPath) {
		this.projPath = projPath;
	}

	public String getSrcSchName() {
		return srcSchName;
	}

	public void setSrcSchName(String srcSchName) {
		this.srcSchName = srcSchName;
	}

	public String getTarSchName() {
		return tarSchName;
	}

	public void setTarSchName(String tarSchName) {
		this.tarSchName = tarSchName;
	}


	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public String getServMachine() {
		return servMachine;
	}

	public void setServMachine(String servMachine) {
		this.servMachine = servMachine;
	}

	public String getPigProjectName() {

		return pigProjectName;
	}

	public void setPigProjectName(String pigProjectName) {
		this.pigProjectName = pigProjectName;
	}

	public String getPigPomPath() {
		return pigPomPath;
	}

	public void setPigPomPath(String pigPomPath) {
		this.pigPomPath = pigPomPath;
	}

	public String getPigUT() {
		return pigUT;
	}

	public void setPigUT(String pigUT) {
		this.pigUT = pigUT;
	}

	public String getHiveProjectPath() {
		return hiveProjectPath;
	}

	public void setHiveProjectPath(String hiveProjectPath) {
		this.hiveProjectPath = hiveProjectPath;
	}

	public AbortScript getAbortScript() {
		return abortScript;
	}

	public void setAbortScript(AbortScript abortScript) {
		this.abortScript = abortScript;
	}

	public String getHivePomPath() {
		return hivePomPath;
	}

	public void setHivePomPath(String hivePomPath) {
		this.hivePomPath = hivePomPath;
	}

	public String getHiveUT() {
		return hiveUT;
	}

	public void setHiveUT(String hiveUT) {
		this.hiveUT = hiveUT;
	}

	public String getScalaProjectName() {
		return scalaProjectName;
	}

	public void setScalaProjectName(String scalaProjectName) {
		this.scalaProjectName = scalaProjectName;
	}

	public String getScalaPomPath() {
		return scalaPomPath;
	}

	public void setScalaPomPath(String scalaPomPath) {
		this.scalaPomPath = scalaPomPath;
	}

	public String getScalaCC() {
		return scalaCC;
	}

	public void setScalaCC(String scalaCC) {
		this.scalaCC = scalaCC;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getToolsRoot() {
		return toolsRoot;
	}

	public void setToolsRoot(String toolsRoot) {
		this.toolsRoot = toolsRoot;
	}

	public String getToolsCfg() {
		return toolsCfg;
	}

	public void setToolsCfg(String toolsCfg) {
		this.toolsCfg = toolsCfg;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getSiebelUserName() {
		return siebelUserName;
	}

	public void setSiebelUserName(String siebelUserName) {
		this.siebelUserName = siebelUserName;
	}

	public String getSiebelPassword() {
		return siebelPassword;
	}

	public void setSiebelPassword(String siebelPassword) {
		this.siebelPassword = siebelPassword;
	}

	public String getSiebelRepo() {
		return siebelRepo;
	}

	public void setSiebelRepo(String siebelRepo) {
		this.siebelRepo = siebelRepo;
	}

	public String getSifImport() {
		return sifImport;
	}

	public void setSifImport(String sifImport) {
		this.sifImport = sifImport;
	}

	public String getSibcompile() {
		return sibcompile;
	}

	public void setSibcompile(String sibcompile) {
		this.sibcompile = sibcompile;
	}

	public String getDefaultModule() {
		return defaultModule;
	}

	public void setDefaultModule(String defaultModule) {
		this.defaultModule = defaultModule;

	}

	public String getSqlFilesPackage() {
		return sqlFilesPackage;
	}

	public void setSqlFilesPackage(String sqlFilesPackage) {
		this.sqlFilesPackage = sqlFilesPackage;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(String testSuite) {
		this.testSuite = testSuite;
	}

	public String getNpmProxyUserName() {
		return npmProxyUserName;
	}

	public void setNpmProxyUserName(String npmProxyUserName) {
		this.npmProxyUserName = npmProxyUserName;
	}

	public String getNpmProxyPassword() {
		return npmProxyPassword;
	}

	public void setNpmProxyPassword(String npmProxyPassword) {
		this.npmProxyPassword = npmProxyPassword;
	}

	public String getmVNOPTS() {
		return mVNOPTS;
	}

	public void setmVNOPTS(String mVNOPTS) {
		this.mVNOPTS = mVNOPTS;
	}

	public String getSourceRepo() {
		return sourceRepo;
	}

	public void setSourceRepo(String sourceRepo) {
		this.sourceRepo = sourceRepo;
	}

	public String getAngularBuildCommand() {
		return angularBuildCommand;
	}

	public void setAngularBuildCommand(String angularBuildCommand) {
		this.angularBuildCommand = angularBuildCommand;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
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

	public String getExportObjSharedXml() {
		return exportObjSharedXml;
	}

	public void setExportObjSharedXml(String exportObjSharedXml) {
		this.exportObjSharedXml = exportObjSharedXml;
	}

	public String getExportObjXml() {
		return exportObjXml;
	}

	public void setExportObjXml(String exportObjXml) {
		this.exportObjXml = exportObjXml;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getVarient() {
		return varient;
	}

	public void setVarient(String varient) {
		this.varient = varient;
	}

	public String getInspectionName() {
		return inspectionName;
	}

	public void setInspectionName(String inspectionName) {
		this.inspectionName = inspectionName;
	}

	public String getAuthenticationMode() {
		return authenticationMode;
	}

	public void setAuthenticationMode(String authenticationMode) {
		this.authenticationMode = authenticationMode;
	}

	public String getRollbackScript() {
		return rollbackScript;
	}

	public void setRollbackScript(String rollbackScript) {
		this.rollbackScript = rollbackScript;
	}

	public String getInstallGrunt() {
		return installGrunt;
	}

	public String getExcludeFolders() {
		return excludeFolders;
	}

	public void setExcludeFolders(String excludeFolders) {
		this.excludeFolders = excludeFolders;
	}

	public void setInstallGrunt(String installGrunt) {
		this.installGrunt = installGrunt;
	}

	public String getAllUnitTestPass() {
		return allUnitTestPass;
	}

	public void setAllUnitTestPass(String allUnitTestPass) {
		this.allUnitTestPass = allUnitTestPass;
	}

	public String getNoViolations() {
		return noViolations;
	}

	public void setNoViolations(String noViolations) {
		this.noViolations = noViolations;
	}

	public String getNpmProxy() {
		return npmProxy;
	}

	public void setNpmProxy(String npmProxy) {
		this.npmProxy = npmProxy;
	}

	public List<String> getUnitTestTool() {
		return unitTestTool;
	}

	public void setUnitTestTool(List<String> unitTestTool) {
		this.unitTestTool = unitTestTool;
	}

	public List<String> getCodeCoverageTool() {
		return codeCoverageTool;
	}

	public void setCodeCoverageTool(List<String> codeCoverageTool) {
		this.codeCoverageTool = codeCoverageTool;
	}

	public String getBuildValue() {
		return buildValue;
	}

	public void setBuildValue(String buildValue) {
		this.buildValue = buildValue;
	}

	public String getRemoteDir() {
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getMultiModule() {
		return multiModule;
	}

	public void setMultiModule(String multiModule) {
		this.multiModule = multiModule;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}
	public String getCustomTasks() {
		return customTasks;
	}

	public void setCustomTasks(String customTasks) {
		this.customTasks = customTasks;
	}


	public String getIncrementalScan() {
		return incrementalScan;
	}

	public void setIncrementalScan(String incrementalScan) {
		this.incrementalScan = incrementalScan;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPreset() {
		return preset;
	}

	public void setPreset(String preset) {
		this.preset = preset;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getCheckmarxProjName() {
		return checkmarxProjName;
	}

	public void setCheckmarxProjName(String checkmarxProjName) {
		this.checkmarxProjName = checkmarxProjName;
	}

	public String getUnitTestDir() {
		return unitTestDir;
	}

	public void setUnitTestDir(String unitTestDir) {
		this.unitTestDir = unitTestDir;
	}

	public String getPafFilePath() {
		return pafFilePath;
	}

	public void setPafFilePath(String pafFilePath) {
		this.pafFilePath = pafFilePath;
	}

	public String getRenaming() {
		return renaming;
	}

	public void setRenaming(String renaming) {
		this.renaming = renaming;
	}

	public String getOssMailRecipients() {
		return ossMailRecipients;
	}

	public void setOssMailRecipients(String ossMailRecipients) {
		this.ossMailRecipients = ossMailRecipients;
	}

	public String getOssDistributionType() {
		return ossDistributionType;
	}

	public void setOssDistributionType(String ossDistributionType) {
		this.ossDistributionType = ossDistributionType;
	}

	public String getOssAnalysisType() {
		return ossAnalysisType;
	}

	public void setOssAnalysisType(String ossAnalysisType) {
		this.ossAnalysisType = ossAnalysisType;
	}

	public String getControlFlow() {
		return controlFlow;
	}

	public void setControlFlow(String controlFlow) {
		this.controlFlow = controlFlow;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public String getUiEarFileName() {
		return uiEarFileName;
	}

	public void setUiEarFileName(String uiEarFileName) {
		this.uiEarFileName = uiEarFileName;
	}

	public String getIntegrationFileName() {
		return integrationFileName;
	}

	public void setIntegrationFileName(String integrationFileName) {
		this.integrationFileName = integrationFileName;
	}

	public List<Migration> getReportMigration() {
		return reportMigration;
	}

	public void setReportMigration(List<Migration> reportMigration) {
		this.reportMigration = reportMigration;
	}

	public List<Migration> getPublishForms() {
		return publishForms;
	}

	public void setPublishForms(List<Migration> publishForms) {
		this.publishForms = publishForms;
	}

	public List<Migration> getHostCTLMigration() {
		return hostCTLMigration;
	}

	public void setHostCTLMigration(List<Migration> hostCTLMigration) {
		this.hostCTLMigration = hostCTLMigration;
	}

	public List<Migration> getOaFileMigration() {
		return oaFileMigration;
	}

	public void setOaFileMigration(List<Migration> oaFileMigration) {
		this.oaFileMigration = oaFileMigration;
	}

	public List<Migration> getWorkFlowPublish() {
		return workFlowPublish;
	}

	public void setWorkFlowPublish(List<Migration> workFlowPublish) {
		this.workFlowPublish = workFlowPublish;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCodeFormatting() {
		return codeFormatting;
	}

	public void setCodeFormatting(String codeFormatting) {
		this.codeFormatting = codeFormatting;
	}

	public String getCustomScript() {
		return customScript;
	}

	public void setCustomScript(String customScript) {
		this.customScript = customScript;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
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

	public String getExecute() {
		return execute;
	}

	public void setExecute(String execute) {
		this.execute = execute;
	}

	public String getLocalmachineExecution() {
		return localmachineExecution;
	}

	public void setLocalmachineExecution(String localmachineExecution) {
		this.localmachineExecution = localmachineExecution;
	}

	public String getDependencyFile() {
		return dependencyFile;
	}

	public void setDependencyFile(String dependencyFile) {
		this.dependencyFile = dependencyFile;
	}

	public String getMasterMachineName() {
		return masterMachineName;
	}

	public void setMasterMachineName(String masterMachineName) {
		this.masterMachineName = masterMachineName;
	}

	public String getClassFileName() {
		return classFileName;
	}

	public void setClassFileName(String classFileName) {
		this.classFileName = classFileName;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCodeAnalysisTargets() {
		return codeAnalysisTargets;
	}

	public void setCodeAnalysisTragets(String codeAnalysisTargets) {
		this.codeAnalysisTargets = codeAnalysisTargets;
	}

	public String getUnitTestTargets() {
		return unitTestTargets;
	}

	public void setUnitTestTargets(String unitTestTargets) {
		this.unitTestTargets = unitTestTargets;
	}

	public String getSecurityProjName() {
		return securityProjName;
	}

	public void setSecurityProjName(String securityProjName) {
		this.securityProjName = securityProjName;
	}

	public String getAvdName() {
		return avdName;
	}

	public void setAvdName(String avdName) {
		this.avdName = avdName;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public String getMessageFlows() {
		return messageFlows;
	}

	public void setMessageFlows(String messageFlows) {
		this.messageFlows = messageFlows;
	}

	public String getNexusType() {
		return nexusType;
	}

	public void setNexusType(String nexusType) {
		this.nexusType = nexusType;
	}

	public String getNexusUrl() {
		return nexusUrl;
	}

	public void setNexusUrl(String nexusUrl) {
		this.nexusUrl = nexusUrl;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getThirdPartyJar() {
		return thirdPartyJar;
	}

	public void setThirdPartyJar(String thirdPartyJar) {
		this.thirdPartyJar = thirdPartyJar;
	}

	public String getCdtIn() {
		return cdtIn;
	}

	public void setCdtIn(String cdtIn) {
		this.cdtIn = cdtIn;
	}

	public String getCdtOut() {
		return cdtOut;
	}

	public void setCdtOut(String cdtOut) {
		this.cdtOut = cdtOut;
	}

	public String getDestDB() {
		return destDB;
	}

	public void setDestDB(String destDB) {
		this.destDB = destDB;
	}

	public String getDestPassword() {
		return destPassword;
	}

	public void setDestPassword(String destPassword) {
		this.destPassword = destPassword;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public RunScript getRunScript() {
		return runScript;
	}

	public void setRunScript(RunScript runScript) {
		this.runScript = runScript;
	}

	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getSecurityAnalysis() {
		return securityAnalysis;
	}

	public void setSecurityAnalysis(String securityAnalysis) {
		this.securityAnalysis = securityAnalysis;
	}

	public List<String> getCodeAnalysis() {
		return codeAnalysis;
	}

	public void setCodeAnalysis(List<String> codeAnalysis) {
		this.codeAnalysis = codeAnalysis;
	}

	public String getUnitTesting() {
		return unitTesting;
	}

	public void setUnitTesting(String unitTesting) {
		this.unitTesting = unitTesting;
	}

	public String getUnitTestProjectName() {
		return unitTestProjectName;
	}

	public void setUnitTestProjectName(String unitTestProjectName) {
		this.unitTestProjectName = unitTestProjectName;
	}

	public String getUnitTestCategory() {
		return unitTestCategory;
	}

	public void setUnitTestCategory(String unitTestCategory) {
		this.unitTestCategory = unitTestCategory;
	}

	public String getCodeCoverage() {
		return codeCoverage;
	}

	public void setCodeCoverage(String codeCoverage) {
		this.codeCoverage = codeCoverage;
	}

	public String getTestSettingFilePath() {
		return testSettingFilePath;
	}

	public void setTestSettingFilePath(String testSettingFilePath) {
		this.testSettingFilePath = testSettingFilePath;
	}

	public String getServerMachine() {
		return serverMachine;
	}

	public void setServerMachine(String serverMachine) {
		this.serverMachine = serverMachine;
	}

	public String getCustomBuildXml() {
		return customBuildXml;
	}

	public void setCustomBuildXml(String customBuildXml) {
		this.customBuildXml = customBuildXml;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getCompile() {
		return compile;
	}

	public void setCompile(String compile) {
		this.compile = compile;
	}

	public String getJarPackaging() {
		return jarPackaging;
	}

	public void setJarPackaging(String jarPackaging) {
		this.jarPackaging = jarPackaging;
	}

	public String getEjbDescriptor() {
		return ejbDescriptor;
	}

	public void setEjbDescriptor(String ejbDescriptor) {
		this.ejbDescriptor = ejbDescriptor;
	}

	public String getJavaMainClass() {
		return javaMainClass;
	}

	public void setJavaMainClass(String javaMainClass) {
		this.javaMainClass = javaMainClass;
	}

	public String getWarPackaging() {
		return warPackaging;
	}

	public void setWarPackaging(String warPackaging) {
		this.warPackaging = warPackaging;
	}

	public String getGlobalGoals() {
		return globalGoals;
	}

	public void setGlobalGoals(String globalGoals) {
		this.globalGoals = globalGoals;
	}

	public String getClean() {
		return clean;
	}

	public void setClean(String clean) {
		this.clean = clean;
	}

	public String getInstall() {
		return install;
	}

	public void setInstall(String install) {
		this.install = install;
	}

	public String getBuildTool() {
		return buildTool;
	}

	public void setBuildTool(String buildTool) {
		this.buildTool = buildTool;
	}

	public String getSourceFolderShared() {
		return sourceFolderShared;
	}

	public void setSourceFolderShared(String sourceFolderShared) {
		this.sourceFolderShared = sourceFolderShared;
	}

	public String getTargetRepo() {
		return targetRepo;
	}

	public void setTargetRepo(String targetRepo) {
		this.targetRepo = targetRepo;
	}

	public String getIpcHostName() {
		return ipcHostName;
	}

	public void setIpcHostName(String ipcHostName) {
		this.ipcHostName = ipcHostName;
	}

	public String getIpcPassword() {
		return ipcPassword;
	}

	public void setIpcPassword(String ipcPassword) {
		this.ipcPassword = ipcPassword;
	}

	public String getIpcUserName() {
		return ipcUserName;
	}

	public void setIpcUserName(String ipcUserName) {
		this.ipcUserName = ipcUserName;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public String getSourceUsername() {
		return sourceUsername;
	}

	public void setSourceUsername(String sourceUsername) {
		this.sourceUsername = sourceUsername;
	}

	public String getSourcePassword() {
		return sourcePassword;
	}

	public void setSourcePassword(String sourcePassword) {
		this.sourcePassword = sourcePassword;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getPegaUserName() {
		return pegaUserName;
	}

	public void setPegaUserName(String pegaUserName) {
		this.pegaUserName = pegaUserName;
	}

	public String getPegaPassword() {
		return pegaPassword;
	}

	public void setPegaPassword(String pegaPassword) {
		this.pegaPassword = pegaPassword;
	}

	public String getPegaProductKey() {
		return pegaProductKey;
	}

	public void setPegaProductKey(String pegaProductKey) {
		this.pegaProductKey = pegaProductKey;
	}

	public String getPegaDBdataUname() {
		return pegaDBdataUname;
	}

	public void setPegaDBdataUname(String pegaDBdataUname) {
		this.pegaDBdataUname = pegaDBdataUname;
	}

	public String getPegaDBName() {
		return pegaDBName;
	}

	public void setPegaDBName(String pegaDBName) {
		this.pegaDBName = pegaDBName;
	}

	public String getPegaDBHost() {
		return pegaDBHost;
	}

	public void setPegaDBHost(String pegaDBHost) {
		this.pegaDBHost = pegaDBHost;
	}

	public String getPegaDBDataPort() {
		return pegaDBDataPort;
	}

	public void setPegaDBDataPort(String pegaDBDataPort) {
		this.pegaDBDataPort = pegaDBDataPort;
	}

	public String getPegaSqlFile() {
		return pegaSqlFile;
	}

	public void setPegaSqlFile(String pegaSqlFile) {
		this.pegaSqlFile = pegaSqlFile;
	}

	public String getRuleSchema() {
		return ruleSchema;
	}

	public void setRuleSchema(String ruleSchema) {
		this.ruleSchema = ruleSchema;
	}

	public String getPegaDBUname() {
		return pegaDBUname;
	}

	public void setPegaDBUname(String pegaDBUname) {
		this.pegaDBUname = pegaDBUname;
	}

	public String getPegaDBPwd() {
		return pegaDBPwd;
	}

	public void setPegaDBPwd(String pegaDBPwd) {
		this.pegaDBPwd = pegaDBPwd;
	}

	public String getPegaDBSchema() {
		return pegaDBSchema;
	}

	public void setPegaDBSchema(String pegaDBSchema) {
		this.pegaDBSchema = pegaDBSchema;
	}

	public String getPegaDBPort() {
		return pegaDBPort;
	}

	public void setPegaDBPort(String pegaDBPort) {
		this.pegaDBPort = pegaDBPort;
	}

	public String getPegaHost() {
		return pegaHost;
	}

	public void setPegaHost(String pegaHost) {
		this.pegaHost = pegaHost;
	}

	public String getPegaProjName() {
		return pegaProjName;
	}

	public void setPegaProjName(String pegaProjName) {
		this.pegaProjName = pegaProjName;
	}

	public String getPegaTableName() {
		return pegaTableName;
	}

	public void setPegaTableName(String pegaTableName) {
		this.pegaTableName = pegaTableName;
	}



}
