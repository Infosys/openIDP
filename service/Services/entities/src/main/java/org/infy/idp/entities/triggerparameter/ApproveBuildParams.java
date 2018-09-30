/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store approve build parameter
 * 
 * @author Infosys
 *
 */
public class ApproveBuildParams {

	@SerializedName("applicationName")
	@Expose
	private String applicationName;

	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;

	@SerializedName("jobType")
	@Expose
	private String jobType;

	@SerializedName("apprInput")
	@Expose
	private String apprInput;

	@SerializedName("apprComment")
	@Expose
	private String apprComment;

	@SerializedName("apprBuildNo")
	@Expose
	private String apprBuildNo;

	@SerializedName("envName")
	@Expose
	private String envName;

	@SerializedName("userName")
	@Expose
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
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

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getApprInput() {
		return apprInput;
	}

	public void setApprInput(String apprInput) {
		this.apprInput = apprInput;
	}

	public String getApprComment() {
		return apprComment;
	}

	public void setApprComment(String apprComment) {
		this.apprComment = apprComment;
	}

	public String getApprBuildNo() {
		return apprBuildNo;
	}

	public void setApprBuildNo(String apprBuildNo) {
		this.apprBuildNo = apprBuildNo;
	}

}
