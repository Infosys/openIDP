/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TriggerDeployArtifact {

	@SerializedName("artifactList")
    @Expose
    private List<DeployArtifact> artifactList;

	public List<DeployArtifact> getArtifactList() {
		return artifactList;
	}

	public void setArtifactList(List<DeployArtifact> artifactList) {
		this.artifactList = artifactList;
	}
	
	
	
}
