/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store approve build and it's details
 * 
 * @author Infosys
 *
 */
public class ApproveBuild {

	@SerializedName("ApprBuildNO")
	@Expose
	private String apprBuildNo;

	@SerializedName("moduleList")
	@Expose
	private String moduleList;

	@SerializedName("userInfo")
	@Expose
	private String userInfo;

	@SerializedName("releaseIdentifier")
	@Expose
	private String releaseIdentifier;

	@SerializedName("envName")
	@Expose
	private String envName;

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getReleaseIdentifier() {
		return releaseIdentifier;
	}

	public void setReleaseIdentifier(String releaseIdentifier) {
		this.releaseIdentifier = releaseIdentifier;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getModuleList() {
		return moduleList;
	}

	public void setModuleList(String moduleList) {
		this.moduleList = moduleList;
	}

	public String getApprBuildNo() {
		return apprBuildNo;
	}

	public void setApprBuildNo(String apprBuildNo) {
		this.apprBuildNo = apprBuildNo;
	}

}
