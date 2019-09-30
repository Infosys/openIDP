/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store trigger details
 * 
 * @author Infosys
 *
 */
public class TriggerDetails {

	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;
	@SerializedName("jobBuildId")
	@Expose
	private String jobBuildId;
	@SerializedName("releaseNumber")
	@Expose
	private String releaseNumber;
	@SerializedName("usePreviousArtifact")
	@Expose
	private String usePreviousArtifact;
	@SerializedName("buildartifactNumber")
	@Expose
	private String buildartifactNumber;

	@SerializedName("gitTag")
	@Expose
	private String gitTag;

	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("errorCode")
	@Expose
	private String errorCode;

	@SerializedName("dashBoardLink")
	@Expose
	private String dashBoardLink;

	@SerializedName("nugetPackaging")
	@Expose
	private Boolean nugetPackaging;

	@SerializedName("slaveName")
	@Expose
	private String slaveName;
	@SerializedName("testSlaveName")
	@Expose
	private String testSlaveName;
	@SerializedName("envSelected")
	@Expose
	private String envSelected;
	@SerializedName("build")
	@Expose
	private Build build;
	@SerializedName("deploy")
	@Expose
	private Deploy deploy;
	@SerializedName("testSelected")
	@Expose
	private String testSelected;
	@SerializedName("artifactorySelected")
	@Expose
	private String artifactorySelected;
	@SerializedName("emailed")
	@Expose
	private String emailed;
	@SerializedName("testStep")
	@Expose
	private ArrayList<String> testStep;

	@SerializedName("systemName")
	@Expose
	private String systemName;
	@SerializedName("lanscapeName")
	@Expose
	private String lanscapeName;

	@SerializedName("instance")
	@Expose
	private String instance;
//	@SerializedName("client")
//	@Expose
//	private String client;
//	@SerializedName("sapUserName")
//    @Expose
//    private String sapUserName;
//	@SerializedName("password")
//	@Expose
	private String password;
	@SerializedName("technology")
	@Expose
	private String technology;
	@SerializedName("language")
	@Expose
	private String language;

//	@SerializedName("userStories")
//	@Expose
//	private String userStories;
//
//	@SerializedName("transportRequest")
//	@Expose
//	private List<String> transportRequest;
	@SerializedName("sonardashBoardLink")
	@Expose
	private String sonardashBoardLink;

	@SerializedName("notify")
	@Expose
	private String notify;

	@SerializedName("branchOrTagList")
	@Expose
	private List<Boolean> branchOrTagList;

	@SerializedName("branchOrTagValue")
	@Expose
	private String branchOrTagValue;

	@SerializedName("rmAssemblies")
	@Expose
	private String rmAssemblies;

	@SerializedName("depParam")
	@Expose
	private String depParam;
	@SerializedName("repoDeployStatus")
	@Expose
	private String repoDeployStatus;
	@SerializedName("nonRepoDeployStatus")
	@Expose
	private String nonRepoDeployStatus;

	@SerializedName("branchOrTag")
	@Expose
	private String branchOrTag;
	@SerializedName("subApplicationName")
	@Expose
	private String subApplicationName;
//	@SerializedName("dbOperations")
//	@Expose
//	private String dbOperations;
//
//	@SerializedName("deployDB")
//	@Expose
//	private String deployDB;
//
//	@SerializedName("restoreDB")
//	@Expose
//	private String restoreDB;
	@SerializedName("testPlanId")
	@Expose
	private String testPlanId;
	@SerializedName("testSuitId")
	@Expose
	private String testSuitId;
	@SerializedName("mtmStepName")
	@Expose
	private String mtmStepName;

	@SerializedName("appId")
	@Expose
	private String appId;

	@SerializedName("pipId")
	@Expose
	private String pipId;

	/*
	 * Remove VSTS ALM
	 * 
	 * @SerializedName("tfsWorkItem")
	 * 
	 * @Expose private String tfsWorkItem;
	 */
	@SerializedName("triggerId")
	@Expose
	private Integer triggerId;

	@SerializedName("scmBranch")
	@Expose
	private String scmBranch;

	@SerializedName("mtmProjectName")
	@Expose
	private String mtmProjectName;

	@SerializedName("jobParam")
	@Expose
	private List<JobParamTrigger> jobParam;

	// ICTP
	/*
	 * Remove ICTP
	 * 
	 * @SerializedName("env")
	 * 
	 * @Expose private List<String> env;
	 * 
	 * /* Remove Jira ALM
	 * 
	 * @SerializedName("userStoryString")
	 * 
	 * @Expose private String userStoryString; / public List<String> getEnv() {
	 * return env; }
	 * 
	 * public void setEnv(List<String> env) { this.env = env; } /* Remove Jira ALM
	 * public String getUserStoryString() { return userStoryString; }
	 * 
	 * public void setUserStoryString(String userStoryString) { this.userStoryString
	 * = userStoryString; }
	 */
	// END
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPipId() {
		return pipId;
	}

	public void setPipId(String pipId) {
		this.pipId = pipId;
	}

	public String getMtmProjectName() {
		return mtmProjectName;
	}

	public void setMtmProjectName(String mtmProjectName) {
		this.mtmProjectName = mtmProjectName;
	}

	public String getScmBranch() {
		return scmBranch;
	}

	public void setScmBranch(String scmBranch) {
		this.scmBranch = scmBranch;
	}

	public Integer getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}
	/*
	 * Remove VSTS ALM public String getTfsWorkItem() { return tfsWorkItem; }
	 * 
	 * public void setTfsWorkItem(String tfsWorkItem) { this.tfsWorkItem =
	 * tfsWorkItem; }
	 */

	public String getMtmStepName() {
		return mtmStepName;
	}

	public void setMtmStepName(String mtmStepName) {
		this.mtmStepName = mtmStepName;
	}

	public String getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(String testPlanId) {
		this.testPlanId = testPlanId;
	}

	public String getTestSuitId() {
		return testSuitId;
	}

	public void setTestSuitId(String testSuitId) {
		this.testSuitId = testSuitId;
	}

	public List<JobParamTrigger> getJobParam() {
		return jobParam;
	}

	public void setJobParam(List<JobParamTrigger> jobParam) {
		this.jobParam = jobParam;
	}

	/*public String getDeployDB() {
		return deployDB;
	}

	public void setDeployDB(String deployDB) {
		this.deployDB = deployDB;
	}

	public String getRestoreDB() {
		return restoreDB;
	}

	public void setRestoreDB(String restoreDB) {
		this.restoreDB = restoreDB;
	}*/

	public String getBranchOrTag() {
		return branchOrTag;
	}

	public void setBranchOrTag(String branchOrTag) {
		this.branchOrTag = branchOrTag;
	}
/*
	public String getDbOperations() {
		return dbOperations;
	}

	public void setDbOperations(String dbOperations) {
		this.dbOperations = dbOperations;
	}*/

	public String getSubApplicationName() {
		return subApplicationName;
	}

	public void setSubApplicationName(String subApplicationName) {
		this.subApplicationName = subApplicationName;
	}

	public String getRmAssemblies() {
		return rmAssemblies;
	}

	public void setRmAssemblies(String rmAssemblies) {
		this.rmAssemblies = rmAssemblies;
	}

	public String getDepParam() {
		return depParam;
	}

	public void setDepParam(String depParam) {
		this.depParam = depParam;
	}

	public String getRepoDeployStatus() {
		return repoDeployStatus;
	}

	public void setRepoDeployStatus(String repoDeployStatus) {
		this.repoDeployStatus = repoDeployStatus;
	}

	public String getNonRepoDeployStatus() {
		return nonRepoDeployStatus;
	}

	public void setNonRepoDeployStatus(String nonRepoDeployStatus) {
		this.nonRepoDeployStatus = nonRepoDeployStatus;

	}

	public String getBranchOrTagValue() {
		return branchOrTagValue;
	}

	public Boolean getNugetPackaging() {
		return nugetPackaging;
	}

	public void setNugetPackaging(Boolean nugetPackaging) {
		this.nugetPackaging = nugetPackaging;
	}

	public void setBranchOrTagValue(String branchOrTagValue) {
		this.branchOrTagValue = branchOrTagValue;
	}

	public List<Boolean> getBranchOrTagList() {
		return branchOrTagList;
	}

	public void setBranchOrTagList(List<Boolean> branchOrTagList) {
		this.branchOrTagList = branchOrTagList;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public String getLanscapeName() {
		return lanscapeName;
	}

	public void setLanscapeName(String lanscapeName) {
		this.lanscapeName = lanscapeName;
	}

	public String getSonardashBoardLink() {
		return sonardashBoardLink;
	}

	public void setSonardashBoardLink(String sonardashBoardLink) {
		this.sonardashBoardLink = sonardashBoardLink;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

/*	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

		public String getSapUserName() {
		return sapUserName;
	}

	public void setSapUserName(String sapUserName) {
		this.sapUserName = sapUserName;
	}*/
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	/*public String getUserStories() {
		return userStories;
	}

	public void setUserStories(String userStories) {
		this.userStories = userStories;
	}*/

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

/*	public List<String> getTransportRequest() {
		return transportRequest;
	}

	public void setTransportRequest(List<String> transportRequest) {
		this.transportRequest = transportRequest;
	}*/

	public String getDashBoardLink() {
		return dashBoardLink;
	}

	public void setDashBoardLink(String dashBoardLink) {
		this.dashBoardLink = dashBoardLink;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getJobBuildId() {
		return jobBuildId;
	}

	public void setJobBuildId(String jobBuildId) {
		this.jobBuildId = jobBuildId;
	}

	public void setTestStep(ArrayList<String> testStep) {
		this.testStep = testStep;
	}

	public ArrayList<String> getTestStep() {
		return testStep;
	}

	public String getEmailed() {
		return emailed;
	}

	public void setEmailed(String emailed) {
		this.emailed = emailed;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSlaveName() {
		return slaveName;
	}

	public void setSlaveName(String slaveName) {
		this.slaveName = slaveName;
	}

	public String getEnvSelected() {
		return envSelected;
	}

	public void setEnvSelected(String envSelected) {
		this.envSelected = envSelected;
	}

	public Build getBuild() {
		return build;
	}

	public void setBuild(Build build) {
		this.build = build;
	}

	public Deploy getDeploy() {
		return deploy;
	}

	public void setDeploy(Deploy deploy) {
		this.deploy = deploy;
	}

	public String getTestSelected() {
		return testSelected;
	}

	public void setTestSelected(String testSelected) {
		this.testSelected = testSelected;
	}

	public String getGitTag() {
		return gitTag;
	}

	public void setGitTag(String gitTag) {
		this.gitTag = gitTag;
	}

	public String getUsePreviousArtifact() {
		return usePreviousArtifact;
	}

	public void setUsePreviousArtifact(String usePreviousArtifact) {
		this.usePreviousArtifact = usePreviousArtifact;
	}

	public String getBuildartifactNumber() {
		return buildartifactNumber;
	}

	public void setBuildartifactNumber(String buildartifactNumber) {
		this.buildartifactNumber = buildartifactNumber;
	}

	public String getArtifactorySelected() {
		return artifactorySelected;
	}

	public void setArtifactorySelected(String artifactorySelected) {
		this.artifactorySelected = artifactorySelected;
	}

	public String getTestSlaveName() {
		return testSlaveName;
	}

	public void setTestSlaveName(String testSlaveName) {
		this.testSlaveName = testSlaveName;
	}

	

}
