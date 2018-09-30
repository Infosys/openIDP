/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.getjob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store Jenkins  job details
 * 
 * @author Infosys
 *
 */
public class GetJob {
	@SerializedName("build_number")
    @Expose
    private String buildNumber;
	@SerializedName("application_name")
    @Expose
    private String applicationName;
	@SerializedName("pipeline_name")
    @Expose
    private String pipelineName;
	@SerializedName("jenkins_build_number")
    @Expose
    private String jenkinsBuildNumber;
	public String getJenkinsBuildNumber() {
		return jenkinsBuildNumber;
	}
	public void setJenkinsBuildNumber(String jenkinsBuildNumber) {
		this.jenkinsBuildNumber = jenkinsBuildNumber;
	}
	public String getBuildNumber() {
		return buildNumber;
	}
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
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
}
