/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store artifact details for stage
 * 
 * @author Infosys
 *
 */
public class ArtifactToStage {

	@SerializedName("artifact")
	@Expose
	private String artifact;
	@SerializedName("flattenFileStructure")
	@Expose
	private String flattenFileStructure;
	@SerializedName("artifactRepo")
	@Expose
	private ArtifactRepo artifactRepo;
	@SerializedName("artifactRepoName")
	@Expose
	private String artifactRepoName;
	@SerializedName("nuspecFilePath")
	@Expose
	private String nuspecFilePath;
	@SerializedName("nexusAPIKey")
	@Expose
	private String nexusAPIKey;

	public ArtifactRepo getArtifactRepo() {
		return artifactRepo;
	}

	public void setArtifactRepo(ArtifactRepo artifactRepo) {
		this.artifactRepo = artifactRepo;
	}

	public String getArtifactRepoName() {
		return artifactRepoName;
	}

	public void setArtifactRepoName(String artifactRepoName) {
		this.artifactRepoName = artifactRepoName;
	}

	public String getFlattenFileStructure() {
		return flattenFileStructure;
	}

	public void setFlattenFileStructure(String flattenFileStructure) {
		this.flattenFileStructure = flattenFileStructure;
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	public String getnuspecFilePath() {
		return nuspecFilePath;
	}

	public void setnuspecFilePath(String nuspecFilePath) {
		this.nuspecFilePath = nuspecFilePath;
	}

	public String getnexusAPIKey() {
		return nexusAPIKey;
	}

	public void setnexusAPIKey(String nexusAPIKey) {
		this.nexusAPIKey = nexusAPIKey;
	}
}
