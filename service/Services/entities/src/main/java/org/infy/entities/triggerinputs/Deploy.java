/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to deploy artifact and environment detail
 * 
 * @author Infosys
 *
 */
public class Deploy {

	@SerializedName("deployArtifacts")
	@Expose
	private List<DeployArtifact> deployArtifacts = null;
	@SerializedName("deployEnv")
	@Expose
	private List<String> deployEnv = null;

	@SerializedName("deploymentType")
	@Expose
	private String deploymentType;
	@SerializedName("bizobj")
	@Expose
	private List<EnvironmentObj> bizobj;

	@SerializedName("approveBuild")
	@Expose
	private List<ApproveBuild> approveBuild = null;

	@SerializedName("jobType")
	@Expose
	private String jobType;

	@SerializedName("workEnvApprovalList")
	@Expose
	private HashMap<String, List<ApproveBuild>> workEnvApprovalList;

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public HashMap<String, List<ApproveBuild>> getWorkEnvApprovalList() {
		return workEnvApprovalList;
	}

	public void setWorkEnvApprovalList(HashMap<String, List<ApproveBuild>> workEnvApprovalList) {
		this.workEnvApprovalList = workEnvApprovalList;
	}

	public List<ApproveBuild> getApproveBuild() {
		return approveBuild;
	}

	public void setApproveBuild(List<ApproveBuild> approveBuild) {
		this.approveBuild = approveBuild;
	}

	public List<EnvironmentObj> getBizobj() {
		return bizobj;
	}

	public void setBizobj(List<EnvironmentObj> bizobj) {
		this.bizobj = bizobj;
	}

	public String getDeploymentType() {
		return deploymentType;
	}

	public void setDeploymentType(String deploymentType) {
		this.deploymentType = deploymentType;
	}

	public List<DeployArtifact> getDeployArtifacts() {
		return deployArtifacts;
	}

	public void setDeployArtifacts(List<DeployArtifact> deployArtifacts) {
		this.deployArtifacts = deployArtifacts;
	}

	public List<String> getDeployEnv() {
		return deployEnv;
	}

	public void setDeployEnv(List<String> deployEnv) {
		this.deployEnv = deployEnv;
	}
}
