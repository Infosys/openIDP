/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.code;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store SCM repository details for project
 * 
 * @author Infosys
 *
 */
public class Scm {
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("repositoryBrowser")
	@Expose
	private String repositoryBrowser;
	@SerializedName("browserUrl")
	@Expose
	private String browserUrl;
	@SerializedName("projectName")
	@Expose
	private String projectName;
	@SerializedName("branch")
	@Expose
	private String branch;
	@SerializedName("projPath")
	@Expose
	private String projPath;
	@SerializedName("moduleName")
	@Expose
	private String moduleName;
	@SerializedName("clearcaseType")
	@Expose
	private String clearcaseType;
	@SerializedName("vobName")
	@Expose
	private String vobName;
	@SerializedName("snapshotViewName")
	@Expose
	private String snapshotViewName;
	@SerializedName("configSpec")
	@Expose
	private String configSpec;
	@SerializedName("developmentStreamName")
	@Expose
	private String developmentStreamName;
	@SerializedName("buildConfiguration")
	@Expose
	private String buildConfiguration;
	@SerializedName("buildDefinition")
	@Expose
	private String buildDefinition;
	@SerializedName("repositoryWorkspace")
	@Expose
	private String repositoryWorkspace;
	@SerializedName("projArea")
	@Expose
	private String projArea;

	@SerializedName("hostName")
	@Expose
	private String hostName;
	@SerializedName("port")
	@Expose
	private String port;
	@SerializedName("server")
	@Expose
	private String server;
	@SerializedName("version")
	@Expose
	private String version;
	@SerializedName("exclude")
	@Expose
	private String exclude;

	@SerializedName("proxy")
	@Expose
	private String proxy;
	@SerializedName("proxyPort")
	@Expose
	private String proxyPort;

	@SerializedName("appRepo")
	@Expose
	private String appRepo;

	@SerializedName("deployRepo")
	@Expose
	private String deployRepo;

	@SerializedName("testRepo")
	@Expose
	private String testRepo;

	public String getAppRepo() {
		return appRepo;
	}

	public void setAppRepo(String appRepo) {
		this.appRepo = appRepo;
	}

	public String getDeployRepo() {
		return deployRepo;
	}

	public void setDeployRepo(String deployRepo) {
		this.deployRepo = deployRepo;
	}

	public String getTestRepo() {
		return testRepo;
	}

	public void setTestRepo(String testRepo) {
		this.testRepo = testRepo;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getRepositoryBrowser() {
		return repositoryBrowser;
	}

	public void setRepositoryBrowser(String repositoryBrowser) {
		this.repositoryBrowser = repositoryBrowser;
	}

	public String getBrowserUrl() {
		return browserUrl;
	}

	public void setBrowserUrl(String browserUrl) {
		this.browserUrl = browserUrl;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getProjPath() {
		return projPath;
	}

	public void setProjPath(String projPath) {
		this.projPath = projPath;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getClearcaseType() {
		return clearcaseType;
	}

	public void setClearcaseType(String clearcaseType) {
		this.clearcaseType = clearcaseType;
	}

	public String getVobName() {
		return vobName;
	}

	public void setVobName(String vobName) {
		this.vobName = vobName;
	}

	public String getSnapshotViewName() {
		return snapshotViewName;
	}

	public void setSnapshotViewName(String snapshotViewName) {
		this.snapshotViewName = snapshotViewName;
	}

	public String getConfigSpec() {
		return configSpec;
	}

	public void setConfigSpec(String configSpec) {
		this.configSpec = configSpec;
	}

	public String getDevelopmentStreamName() {
		return developmentStreamName;
	}

	public void setDevelopmentStreamName(String developmentStreamName) {
		this.developmentStreamName = developmentStreamName;
	}

	public String getBuildConfiguration() {
		return buildConfiguration;
	}

	public void setBuildConfiguration(String buildConfiguration) {
		this.buildConfiguration = buildConfiguration;
	}

	public String getBuildDefinition() {
		return buildDefinition;
	}

	public void setBuildDefinition(String buildDefinition) {
		this.buildDefinition = buildDefinition;
	}

	public String getRepositoryWorkspace() {
		return repositoryWorkspace;
	}

	public void setRepositoryWorkspace(String repositoryWorkspace) {
		this.repositoryWorkspace = repositoryWorkspace;
	}

	public String getProjArea() {
		return projArea;
	}

	public void setProjArea(String projArea) {
		this.projArea = projArea;
	}

}
