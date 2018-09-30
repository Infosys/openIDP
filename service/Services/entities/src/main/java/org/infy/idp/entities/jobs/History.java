/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store pipeline details for user
 * 
 * @author Infosys
 *
 */
public class History {
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("roles")
	@Expose
	private String roles;
	@SerializedName("pipelineDetails")
	@Expose
	private List<PipelineDetail> pipelineDetails = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<PipelineDetail> getPipelineDetails() {
		return pipelineDetails;
	}

	public void setPipelineDetails(List<PipelineDetail> pipelineDetails) {
		this.pipelineDetails = pipelineDetails;
	}
}