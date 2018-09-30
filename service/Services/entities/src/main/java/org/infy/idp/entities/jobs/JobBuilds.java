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
 * Entity to store job build details
 * 
 * @author Infosys
 *
 */
public class JobBuilds {

	@SerializedName("jobName")
	@Expose
	private String jobName;
	@SerializedName("buildJSON")
	@Expose
	private String buildJSON;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getBuildJSON() {
		return buildJSON;
	}

	public void setBuildJSON(String buildJSON) {
		this.buildJSON = buildJSON;
	}

}
