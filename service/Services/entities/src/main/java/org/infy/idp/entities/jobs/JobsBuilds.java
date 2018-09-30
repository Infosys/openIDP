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
 * Entity to store job build list 
 * 
 * @author Infosys
 *
 */
public class JobsBuilds {

	@SerializedName("JobBuilds")
	@Expose
	private List<JobBuilds> jobBuilds = null;
	@SerializedName("jobName")
	@Expose
	private String jobName;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<JobBuilds> getJobBuilds() {
		return jobBuilds;
	}

	public void setJobBuilds(List<JobBuilds> jobBuilds) {
		this.jobBuilds = jobBuilds;
	}

}
