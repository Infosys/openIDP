/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.artifact;

import java.util.List;

import org.infy.idp.entities.packagecontent.PackageContent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Artifact entity to store artifact information
 * 
 * @author Infosys
 *
 */
public class Artifact {

	@SerializedName("artifactName")
	@Expose
	private String artifactName;
	@SerializedName("dashboardUrl")
	@Expose
	private String dashboardUrl;
	@SerializedName("packageContent")
	@Expose
	private PackageContent packageContent;
	@SerializedName("artifactDetails")
	@Expose
	private List<ArtifactDetails> artifactDetails;

	public PackageContent getPackageContent() {
		return packageContent;
	}

	public void setPackageContent(PackageContent packageContent) {
		this.packageContent = packageContent;
	}

	public String getDashboardUrl() {
		return dashboardUrl;
	}

	public void setDashboardUrl(String dashboardUrl) {
		this.dashboardUrl = dashboardUrl;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	public List<ArtifactDetails> getArtifactDetails() {
		return artifactDetails;
	}

	public void setArtifactDetails(List<ArtifactDetails> artifactDetails) {
		this.artifactDetails = artifactDetails;
	}

}
