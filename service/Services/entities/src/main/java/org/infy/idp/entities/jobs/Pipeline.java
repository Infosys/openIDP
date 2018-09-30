/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store pipeline json details
 * 
 * @author Infosys
 *
 */
public class Pipeline {
	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;
	@SerializedName("pipelineJson")
	@Expose
	private IDPJob pipelineJson;
	@SerializedName("method")
	@Expose
	private String method;

	@SerializedName("applicationName")
	@Expose
	private String applicationName;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public IDPJob getPipelineJson() {
		return pipelineJson;
	}

	public void setPipelineJson(IDPJob pipelineJson) {
		this.pipelineJson = pipelineJson;
	}

}
