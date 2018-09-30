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
 * Entity to store artifact input details
 * 
 * @author Infosys
 *
 */
public class DownloadArtifactInputs {
	@SerializedName("jobName")
	@Expose
	private String jobName;
	@SerializedName("subJobName")
	@Expose
	private String subJobName;
	@SerializedName("buildNumber")
	@Expose
	private String buildNumber;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSubJobName() {
		return subJobName;
	}

	public void setSubJobName(String subJobName) {
		this.subJobName = subJobName;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

}
