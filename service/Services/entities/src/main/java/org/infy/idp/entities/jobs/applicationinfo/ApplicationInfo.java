/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.applicationinfo;

import java.util.List;

import org.infy.idp.entities.jobs.buildinfo.ArtifactToStage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store application information
 * 
 * @author Infosys
 *
 */
public class ApplicationInfo {
	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("developers")
	@Expose
	private String developers;
	@SerializedName("pipelineAdmins")
	@Expose
	private String pipelineAdmins;
	@SerializedName("releaseManager")
	@Expose
	private String releaseManager;
	@SerializedName("environmentOwnerDetails")
	@Expose
	private List<EnvironmentOwnerDetail> environmentOwnerDetails = null;
	@SerializedName("environmentProvDetails")
	@Expose
	private List<EnvironmentProvDetails> environmentProvDetails = null;

	@SerializedName("slavesDetails")
	@Expose
	private List<SlavesDetail> slavesDetails = null;

	@SerializedName("artifactToStage")
	@Expose
	private ArtifactToStage artifactToStage;

	public List<EnvironmentProvDetails> getEnvironmentProvDetails() {
		return environmentProvDetails;
	}

	public void setEnvironmentProvDetails(List<EnvironmentProvDetails> environmentProvDetails) {
		this.environmentProvDetails = environmentProvDetails;
	}

	public ArtifactToStage getArtifactToStage() {
		return artifactToStage;
	}

	public void setArtifactToStage(ArtifactToStage artifactToStage) {
		this.artifactToStage = artifactToStage;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}

	public String getPipelineAdmins() {
		return pipelineAdmins;
	}

	public void setPipelineAdmins(String pipelineAdmins) {
		this.pipelineAdmins = pipelineAdmins;
	}

	public String getReleaseManager() {
		return releaseManager;
	}

	public void setReleaseManager(String releaseManager) {
		this.releaseManager = releaseManager;
	}

	public List<EnvironmentOwnerDetail> getEnvironmentOwnerDetails() {
		return environmentOwnerDetails;
	}

	public void setEnvironmentOwnerDetails(List<EnvironmentOwnerDetail> environmentOwnerDetails) {
		this.environmentOwnerDetails = environmentOwnerDetails;
	}

	public List<SlavesDetail> getSlavesDetails() {
		return slavesDetails;
	}

	public void setSlavesDetails(List<SlavesDetail> slavesDetails) {
		this.slavesDetails = slavesDetails;
	}
}
