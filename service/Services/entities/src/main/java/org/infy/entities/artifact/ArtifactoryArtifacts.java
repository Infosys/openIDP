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
 * Entity to store JFrog Artifactory artifact
 * 
 * @author Infosys
 *
 */
public class ArtifactoryArtifacts {
	@SerializedName("uri")
	@Expose
	private String uri;
	@SerializedName("created")
	@Expose
	private String created;

	@SerializedName("files")
	@Expose
	private List<ArtifactoryFile> files;
	
	@SerializedName("continuationToken")
	@Expose
	private String continuationToken;

	public String getContinuationToken() {
		return continuationToken;
	}

	public void setContinuationToken(String continuationToken) {
		this.continuationToken = continuationToken;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public List<ArtifactoryFile> getFiles() {
		return files;
	}

	public void setFiles(List<ArtifactoryFile> files) {
		this.files = files;
	}

}
