/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageContent {
	@SerializedName("artifactName")
	@Expose
	private String artifactName;
	@SerializedName("informatica")
	@Expose
	private Informatica informatica;
	@SerializedName("dotNet")
	@Expose
	private Module dotNet;
	@SerializedName("ant")
	@Expose
	private Module ant;
	@SerializedName("siebel")
	@Expose
	private Siebel siebel;
	@SerializedName("pega")
	@Expose
	private Pega pega;
	@SerializedName("bigData")
	@Expose
	private Module bigData;

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	public Informatica getInformatica() {
		return informatica;
	}

	public void setInformatica(Informatica informatica) {
		this.informatica = informatica;
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

	public Siebel getSiebel() {
		return siebel;
	}

	public void setSiebel(Siebel siebel) {
		this.siebel = siebel;
	}

	public Pega getPega() {
		return pega;
	}

	public void setPega(Pega pega) {
		this.pega = pega;
	}

	public Module getBigData() {
		return bigData;
	}

	public void setBigData(Module bigData) {
		this.bigData = bigData;
	}
}
