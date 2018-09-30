/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IBMMaximoDeployDetails {

	@SerializedName("deployStepName")
    @Expose
    private String deployStepName;
	
	@SerializedName("deployStepType")
    @Expose
    private String deployStepType;
	
	@SerializedName("hostName")
    @Expose
    private String hostName;
	
	@SerializedName("artifactName")
    @Expose
    private String artifactName;

	@SerializedName("status")
    @Expose
    private String status;
	
	@SerializedName("duration")
    @Expose
    private String duration;
	public IBMMaximoDeployDetails(){
		this.artifactName="NA";
		this.deployStepName="NA";
		this.deployStepType="NA";
		this.duration="NA";
		this.hostName="NA";
		this.status="NA";
	}
	public String getDeployStepName() {
		return deployStepName;
	}

	public void setDeployStepName(String deployStepName) {
		this.deployStepName = deployStepName;
	}

	public String getDeployStepType() {
		return deployStepType;
	}

	public void setDeployStepType(String deployStepType) {
		this.deployStepType = deployStepType;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	
	
	
	
}
