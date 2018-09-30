/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store build details
 * 
 * @author Infosys
 *
 */
public class Build {

	@SerializedName("gitBranches")
	@Expose
	private List<String> gitBranches = null;

	@SerializedName("modules")
	@Expose
	private List<Module> modules = null;
	@SerializedName("codeAnalysis")
	@Expose
	private String codeAnalysis;
	@SerializedName("unitTesting")
	@Expose
	private String unitTesting;
	@SerializedName("cast")
	@Expose
	private String cast;
	@SerializedName("gitTag")
	@Expose
	private String gitTag;

	@SerializedName("buildEnv")
	@Expose
	private List<String> buildEnv;

	@SerializedName("subModules")
	@Expose
	private List<SubModule> subModules = null;

	@SerializedName("approveBuild")
	@Expose
	private List<ApproveBuild> approveBuild = null;

	@SerializedName("jobType")
	@Expose
	private String jobType;

	@SerializedName("workEnv")
	@Expose
	private String workEnv;

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getWorkEnv() {
		return workEnv;
	}

	public void setWorkEnv(String workEnv) {
		this.workEnv = workEnv;
	}

	public List<ApproveBuild> getApproveBuild() {
		return approveBuild;
	}

	public void setApproveBuild(List<ApproveBuild> approveBuild) {
		this.approveBuild = approveBuild;
	}

	public List<SubModule> getSubModules() {
		return subModules;
	}

	public void setSubModules(List<SubModule> subModules) {
		this.subModules = subModules;
	}

	public List<String> getBuildEnv() {
		return buildEnv;
	}

	public void setBuildEnv(List<String> buildEnv) {
		this.buildEnv = buildEnv;
	}

	public String getGitTag() {
		return gitTag;
	}

	public void setGitTag(String gitTag) {
		this.gitTag = gitTag;
	}

	public String getCodeAnalysis() {
		return codeAnalysis;
	}

	public void setCodeAnalysis(String codeAnalysis) {
		this.codeAnalysis = codeAnalysis;
	}

	public String getUnitTesting() {
		return unitTesting;
	}

	public void setUnitTesting(String unitTesting) {
		this.unitTesting = unitTesting;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public List<String> getGitBranches() {
		return gitBranches;
	}

	public void setGitBranches(List<String> gitBranches) {
		this.gitBranches = gitBranches;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

}
