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
 * Entity to store pipeline details
 * 
 * @author Infosys
 *
 */
public class PipelineDetail {
	@SerializedName("srNumber")
	@Expose
	private Integer srNumber;

	@SerializedName("applicationName")
	@Expose
	private String applicationName;

	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;

	@SerializedName("creationDate")
	@Expose
	private String creationDate;

	@SerializedName("permissions")
	@Expose
	private List<String> permissions;

	@SerializedName("buildTool")
	@Expose
	private String buildTool;

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public Integer getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(Integer srNumber) {
		this.srNumber = srNumber;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getBuildTool() {
		return buildTool;
	}

	public void setBuildTool(String buildTool) {
		this.buildTool = buildTool;
	}

}