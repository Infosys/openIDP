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
 * Entity to store relase details of pipeline
 * 
 * @author Infosys
 *
 */
public class ReleasePipeline {

	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;
	@SerializedName("release")
	@Expose
	private List<Release> release = null;
	@SerializedName("scmBranches")
	@Expose
	private List<String> scmBranches = null;
	@SerializedName("scmType")
	@Expose
	private String scmType;
	@SerializedName("pipelineId")
	@Expose
	private long pipelineId;

	public long getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(long pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getScmType() {
		return scmType;
	}

	public void setScmType(String scmType) {
		this.scmType = scmType;
	}

	public List<String> getScmBranches() {
		return scmBranches;
	}

	public void setScmBranches(List<String> scmBranches) {
		this.scmBranches = scmBranches;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public List<Release> getRelease() {
		return release;
	}

	public void setRelease(List<Release> release) {
		this.release = release;
	}

}
