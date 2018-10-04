/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.testinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store test step details
 * 
 * @author Infosys
 *
 */
public class Test {

	@SerializedName("testCategory")
	@Expose
	private String testCategory;
	@SerializedName("testTypeName")
	@Expose
	private String testTypeName;
	@SerializedName("projectName")
	@Expose
	private String projectName;
	@SerializedName("frameWork")
	@Expose
	private String frameWork;
	@SerializedName("testCase")
	@Expose
	private String testCase;
	@SerializedName("testPlan")
	@Expose
	private String testPlan;
	@SerializedName("folderUrl")
	@Expose
	private String folderUrl;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("testSuiteName")
	@Expose
	private String testSuiteName;
	@SerializedName("projectLocation")
	@Expose
	private String projectLocation;
	@SerializedName("customParameters")
	@Expose
	private String customParameters;
	@SerializedName("ritVirtulizationVersion")
	@Expose
	private String ritVirtulizationVersion;
	@SerializedName("serverUrl")
	@Expose
	private String serverUrl;

	@SerializedName("testStagingDoc")
	@Expose
	private String testStagingDoc;
	@SerializedName("actionString")
	@Expose
	private String actionString;
	@SerializedName("servicesList")
	@Expose
	private String servicesList;

	@SerializedName("domain")
	@Expose
	private String domain;
	@SerializedName("environment")
	@Expose
	private String environment;
	@SerializedName("stubName")
	@Expose
	private String stubName;
	@SerializedName("stubVersion")
	@Expose
	private String stubVersion;
	@SerializedName("serviceName")
	@Expose
	private String serviceName;
	@SerializedName("path")
	@Expose
	private String path;
	@SerializedName("authenticationCode")
	@Expose
	private String authenticationCode;
	@SerializedName("timeout")
	@Expose
	private Integer timeout;
	@SerializedName("serverName")
	@Expose
	private String serverName;
	@SerializedName("dataPool")
	@Expose
	private String dataPool;
	@SerializedName("iteration")
	@Expose
	private Integer iteration;
	@SerializedName("fullIteration")
	@Expose
	private String fullIteration;
	@SerializedName("browserName")
	@Expose
	private String browserName;

	@SerializedName("rootDir")
	@Expose
	private String rootDir;
	@SerializedName("version")
	@Expose
	private String version;
	@SerializedName("externalFilePath")
	@Expose
	private String externalFilePath;
	@SerializedName("parameters")
	@Expose
	private String parameters;

	@SerializedName("scriptPath")
	@Expose
	private String scriptPath;

	@SerializedName("targets")
	@Expose
	private String targets;

	@SerializedName("arg")
	@Expose
	private String arg;
	@SerializedName("slaveName")
	@Expose
	private String slaveName;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("RPTworkspace")
	@Expose
	private String RPTworkspace;
	@SerializedName("testScript")
	@Expose
	private String testScript;

	@SerializedName("iosProjectName")
	@Expose
	private String iosProjectName;
	@SerializedName("iosTarget")
	@Expose
	private String iosTarget;
	@SerializedName("iosMonkeyTalkScript")
	@Expose
	private String iosMonkeyTalkScript;

	@SerializedName("buildDefId")
	@Expose
	private String buildDefId;

	@SerializedName("testConfig")
	@Expose
	private String testConfig;
	
	
	public String getIosProjectName() {
		return iosProjectName;
	}

	public void setIosProjectName(String iosProjectName) {
		this.iosProjectName = iosProjectName;
	}

	public String getIosTarget() {
		return iosTarget;
	}

	public void setIosTarget(String iosTarget) {
		this.iosTarget = iosTarget;
	}

	public String getIosMonkeyTalkScript() {
		return iosMonkeyTalkScript;
	}

	public void setIosMonkeyTalkScript(String iosMonkeyTalkScript) {
		this.iosMonkeyTalkScript = iosMonkeyTalkScript;
	}

	

	public String getBuildDefId() {
		return buildDefId;
	}

	public void setBuildDefId(String buildDefId) {
		this.buildDefId = buildDefId;
	}

	public String getTestScript() {
		return testScript;
	}

	public void setTestScript(String testScript) {
		this.testScript = testScript;
	}

	public String getFolderUrl() {
		return folderUrl;
	}

	public void setFolderUrl(String folderUrl) {
		this.folderUrl = folderUrl;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getExternalFilePath() {
		return externalFilePath;
	}

	public void setExternalFilePath(String externalFilePath) {
		this.externalFilePath = externalFilePath;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getRitVirtulizationVersion() {
		return ritVirtulizationVersion;
	}

	public void setRitVirtulizationVersion(String ritVirtulizationVersion) {
		this.ritVirtulizationVersion = ritVirtulizationVersion;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getStubName() {
		return stubName;
	}

	public void setStubName(String stubName) {
		this.stubName = stubName;
	}

	public String getStubVersion() {
		return stubVersion;
	}

	public void setStubVersion(String stubVersion) {
		this.stubVersion = stubVersion;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAuthenticationCode() {
		return authenticationCode;
	}

	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDataPool() {
		return dataPool;
	}

	public void setDataPool(String dataPool) {
		this.dataPool = dataPool;
	}

	public Integer getIteration() {
		return iteration;
	}

	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}

	public String getFullIteration() {
		return fullIteration;
	}

	public void setFullIteration(String fullIteration) {
		this.fullIteration = fullIteration;
	}

	public String getTestCategory() {
		return testCategory;
	}

	public void setTestCategory(String testCategory) {
		this.testCategory = testCategory;
	}

	public String getTestTypeName() {
		return testTypeName;
	}

	public void setTestTypeName(String testTypeName) {
		this.testTypeName = testTypeName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFrameWork() {
		return frameWork;
	}

	public void setFrameWork(String frameWork) {
		this.frameWork = frameWork;
	}

	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	public String getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
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

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public String getProjectLocation() {
		return projectLocation;
	}

	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public String getCustomParameters() {
		return customParameters;
	}

	public void setCustomParameters(String customParameters) {
		this.customParameters = customParameters;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getSlaveName() {
		return slaveName;
	}

	public void setSlaveName(String slaveName) {
		this.slaveName = slaveName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRPTworkspace() {
		return RPTworkspace;
	}

	public void setRPTworkspace(String rPTworkspace) {
		RPTworkspace = rPTworkspace;
	}

	public String getTestStagingDoc() {
		return testStagingDoc;
	}

	public void setTestStagingDoc(String testStagingDoc) {
		this.testStagingDoc = testStagingDoc;
	}

	public String getActionString() {
		return actionString;
	}

	public void setActionString(String actionString) {
		this.actionString = actionString;
	}

	public String getServicesList() {
		return servicesList;
	}

	public void setServicesList(String servicesList) {
		this.servicesList = servicesList;
	}

	public String getTestConfig() {
		return testConfig;
	}

	public void setTestConfig(String testConfig) {
		this.testConfig = testConfig;
	}

}
