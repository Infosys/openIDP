/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.entities.artifact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DockerArtifact {
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("createdAt")
	@Expose
	private String createdAt;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}
