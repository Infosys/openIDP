/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.packagecontent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store package content of artifact
 * 
 * @author Infosys
 *
 */
public class PackageContent {

	@SerializedName("artifactName")
	@Expose
	private String artifactName;

	@SerializedName("dotNet")
	@Expose
	private Module dotNet;
	@SerializedName("ant")
	@Expose
	private Module ant;

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	public Module getDotNet() {
		return dotNet;
	}

	public void setDotNet(Module dotNet) {
		this.dotNet = dotNet;
	}

	public Module getAnt() {
		return ant;
	}

	public void setAnt(Module ant) {
		this.ant = ant;
	}

}
