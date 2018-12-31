/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.triggerparameter.ApplicationDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store input require to trigger pipeline
 * 
 * @author Infosys
 *
 */
public class TriggerInputs {

	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;

	@SerializedName("releaseNumber")
	@Expose
	private List<String> releaseNumber;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("technology")
	@Expose
	private String technology;

	// pipeline of pipelines
	@SerializedName("pipelines")
	@Expose
	private List<ApplicationDetails> pipelines;
	@SerializedName("environment")
	@Expose
	private String environment;

	@SerializedName("slaves")
	@Expose
	private List<String> slaves = null;

	@SerializedName("appSlaves")
	@Expose
	private List<SlavesDetail> appSlaves = null;
	@SerializedName("roles")
	@Expose
	private List<String> roles = null;
	@SerializedName("build")
	@Expose
	private Build build;
	@SerializedName("deploy")
	@Expose
	private Deploy deploy;
	@SerializedName("test")
	@Expose
	private Test test;
	@SerializedName("deployTestEnv")
	@Expose
	private DeployTestEnv deployTestEnv;
	@SerializedName("buildDeployEnv")
	@Expose
	private List<BuildDeployEnv> buildDeployEnv;
	@SerializedName("permissions")
	@Expose
	private List<String> permissions;
	@SerializedName("jobStatus")
	@Expose
	private String jobStatus;
	@SerializedName("sshAndDependent")
	@Expose
	private String sshAndDependent;
	@SerializedName("systemNames")
	@Expose
	private List<String> systemNames;

	@SerializedName("relaseList")
	@Expose
	private Set<String> relaseList;
	@SerializedName("artifactList")
	@Expose
	private List<DeployArtifact> artifactList;
	@SerializedName("nexusURL")
	@Expose
	private String NexusURL;

	@SerializedName("branchList")
	@Expose
	private ArrayList<String> branchList;

	@SerializedName("tagList")
	@Expose
	private ArrayList<String> tagList;

	@SerializedName("scmType")
	@Expose
	private String scmType;

	@SerializedName("subApplicationName")
	@Expose
	private String subApplicationName;


	@SerializedName("deployStep")
	@Expose
	private String deployStep;

	@SerializedName("releaseBranches")
	@Expose
	private HashMap<String, List<String>> releaseBranches;

	@SerializedName("buildDeploy")
	@Expose
	private String buildDeploy;

	@Expose
	private HashMap<String, List<List<String>>> envPropertyList;

	@SerializedName("itawDetails")
	@Expose
	private HashMap<String, Names> itawDetails;

	@SerializedName("hpalmTestSets")
	@Expose
	private HashMap<String, Names> hpalmTestSets;

	@SerializedName("repoName")
	@Expose
	private String repoName;

	@SerializedName("crRequests")
	@Expose
	private List<String> crRequests;

	public String getDeployStep() {
		return deployStep;
	}

	public void setDeployStep(String deployStep) {
		this.deployStep = deployStep;
	}

	public String getBuildDeploy() {
		return buildDeploy;
	}

	public void setBuildDeploy(String buildDeploy) {
		this.buildDeploy = buildDeploy;
	}

	public HashMap<String, List<List<String>>> getEnvPropertyList() {
		return envPropertyList;
	}

	public void setEnvPropertyList(HashMap<String, List<List<String>>> envPropertyList) {
		this.envPropertyList = envPropertyList;
	}

	public HashMap<String, Names> getItawDetails() {
		return itawDetails;
	}

	public void setItawDetails(HashMap<String, Names> itawDetails) {
		this.itawDetails = itawDetails;
	}

	public HashMap<String, Names> getHpalmTestSets() {
		return hpalmTestSets;
	}

	public void setHpalmTestSets(HashMap<String, Names> hpalmTestSets) {
		this.hpalmTestSets = hpalmTestSets;
	}

	public HashMap<String, List<String>> getReleaseBranches() {
		return releaseBranches;
	}

	public void setReleaseBranches(HashMap<String, List<String>> releaseBranches) {
		this.releaseBranches = releaseBranches;
	}

	public String getScmType() {
		return scmType;
	}

	public void setScmType(String scmType) {
		this.scmType = scmType;
	}

	public ArrayList<String> getBranchList() {
		return branchList;
	}

	public String getSubApplicationName() {
		return subApplicationName;
	}

	public void setSubApplicationName(String subApplicationName) {
		this.subApplicationName = subApplicationName;
	}

	public void setBranchList(ArrayList<String> branchList) {
		this.branchList = branchList;
	}

	public ArrayList<String> getTagList() {
		return tagList;
	}

	public void setTagList(ArrayList<String> tagList) {
		this.tagList = tagList;
	}

	public String getNexusURL() {
		return NexusURL;
	}

	public void setNexusURL(String nexusURL) {
		NexusURL = nexusURL;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public List<DeployArtifact> getArtifactList() {
		return artifactList;
	}

	public void setArtifactList(List<DeployArtifact> artifactList) {
		this.artifactList = artifactList;
	}

	public List<String> getSystemNames() {
		return systemNames;
	}

	public void setSystemNames(List<String> systemNames) {
		this.systemNames = systemNames;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getSshAndDependent() {
		return sshAndDependent;
	}

	public void setSshAndDependent(String sshAndDependent) {
		this.sshAndDependent = sshAndDependent;
	}

	public Set<String> getRelaseList() {
		return relaseList;
	}

	public void setRelaseList(Set<String> relaseList) {
		this.relaseList = relaseList;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
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

	public List<String> getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(List<String> releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<String> slaves) {
		this.slaves = slaves;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
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

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public DeployTestEnv getDeployTestEnv() {
		return deployTestEnv;
	}

	public void setDeployTestEnv(DeployTestEnv deployTestEnv) {
		this.deployTestEnv = deployTestEnv;
	}

	public List<BuildDeployEnv> getBuildDeployEnv() {
		return buildDeployEnv;
	}

	public void setBuildDeployEnv(List<BuildDeployEnv> buildDeployEnv) {
		this.buildDeployEnv = buildDeployEnv;
	}

	public List<SlavesDetail> getAppSlaves() {
		return appSlaves;
	}

	public void setAppSlaves(List<SlavesDetail> appSlaves) {
		this.appSlaves = appSlaves;
	}

	public List<ApplicationDetails> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<ApplicationDetails> pipeSequenceList) {
		this.pipelines = pipeSequenceList;
	}

	public List<String> getCrRequests() {
		return crRequests;
	}

	public void setCrRequests(List<String> crRequests) {
		this.crRequests = crRequests;
	}

}
