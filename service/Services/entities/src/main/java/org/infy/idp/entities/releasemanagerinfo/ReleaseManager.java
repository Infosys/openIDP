/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.releasemanagerinfo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store release manager information
 * 
 * @author Infosys
 *
 */
public class ReleaseManager {

	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("environmentList")
	@Expose
	private List<String> environmentList;
	@SerializedName("accessEnvironmentList")
	@Expose
	private List<String> accessEnvironmentList;
	@SerializedName("releasePipelineInfo")
	@Expose
	private ReleasePipeline releasePipelineInfo;
	@SerializedName("releasePipeline")
	@Expose
	private List<ReleasePipeline> releasePipeline;

	@SerializedName("applicationId")
	@Expose
	private long applicationId;

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public List<String> getAccessEnvironmentList() {
		return accessEnvironmentList;
	}

	public void setAccessEnvironmentList(List<String> accessEnvironmentList) {
		this.accessEnvironmentList = accessEnvironmentList;
	}

	public ReleasePipeline getReleasePipelineInfo() {
		return releasePipelineInfo;
	}

	public void setReleasePipelineInfo(ReleasePipeline releasePipelineInfo) {
		this.releasePipelineInfo = releasePipelineInfo;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public List<ReleasePipeline> getReleasePipeline() {
		return releasePipeline;
	}

	public void setReleasePipeline(List<ReleasePipeline> releasePipeline) {
		this.releasePipeline = releasePipeline;
	}

	public List<String> getEnvironmentList() {
		return environmentList;
	}

	public void setEnvironmentList(List<String> environmentList) {
		this.environmentList = environmentList;
	}

}
