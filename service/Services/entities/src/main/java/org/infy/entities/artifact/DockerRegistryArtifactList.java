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

public class DockerRegistryArtifactList {

	@SerializedName("name")
	@Expose
	private List <DockerArtifact> name;

	public List<DockerArtifact> getName() {
		return name;
	}

	public void setName(List<DockerArtifact> name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DockerRegistryArtifactList [name=" + name + "]";
	}
}
