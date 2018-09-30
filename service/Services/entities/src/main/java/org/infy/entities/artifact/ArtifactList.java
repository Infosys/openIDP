/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.entities.artifact;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store artifact list
 * 
 * @author Infosys
 *
 */
public class ArtifactList {

	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;
	@SerializedName("releaseNumber")
	@Expose
	private String releaseNumber;
	@SerializedName("environmentName")
	@Expose
	private String environmentName;
	@SerializedName("approvedArtifact")
	@Expose
	private List<Artifact> approvedArtifact;
	@SerializedName("importedArtifact")
	@Expose
	private List<Artifact> importedArtifact;

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

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public List<Artifact> getApprovedArtifact() {
		return approvedArtifact;
	}

	public void setApprovedArtifact(List<Artifact> approvedArtifact) {
		this.approvedArtifact = approvedArtifact;
	}

	public List<Artifact> getImportedArtifact() {
		return importedArtifact;
	}

	public void setImportedArtifact(List<Artifact> importedArtifact) {
		this.importedArtifact = importedArtifact;
	}

}
