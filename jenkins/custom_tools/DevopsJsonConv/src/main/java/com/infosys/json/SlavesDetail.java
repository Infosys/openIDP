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

public class SlavesDetail {
	@SerializedName("slaveName")
	@Expose
	private String slaveName;
	@SerializedName("buildServerOS")
	@Expose
	private String buildServerOS;
	@SerializedName("workspacePath")
	@Expose
	private String workspacePath;
	@SerializedName("createNewSlave")
	@Expose
	private String createNewSlave;
	@SerializedName("labels")
	@Expose
	private String labels;
	@SerializedName("sshKeyPath")
	@Expose
	private String sSHKeyPath;
	@SerializedName("slaveUsage")
	@Expose
	private String slaveUsage;
	// Slave usage for different stages
	@SerializedName("build")
	@Expose
	private String build;
	@SerializedName("deploy")
	@Expose
	private String deploy;
	@SerializedName("test")
	@Expose
	private String test;

	public String getSlaveName() {
		return slaveName;
	}

	public void setSlaveName(String slaveName) {
		this.slaveName = slaveName;
	}

	public String getBuildServerOS() {
		return buildServerOS;
	}

	public void setBuildServerOS(String buildServerOS) {
		this.buildServerOS = buildServerOS;
	}

	public String getWorkspacePath() {
		return workspacePath;
	}

	public void setWorkspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
	}

	public String getCreateNewSlave() {
		return createNewSlave;
	}

	public void setCreateNewSlave(String createNewSlave) {
		this.createNewSlave = createNewSlave;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getSSHKeyPath() {
		return sSHKeyPath;
	}

	public void setSSHKeyPath(String sSHKeyPath) {
		this.sSHKeyPath = sSHKeyPath;
	}

	public String getSlaveUsage() {
		return slaveUsage;
	}

	public void setSlaveUsage(String slaveUsage) {
		this.slaveUsage = slaveUsage;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getDeploy() {
		return deploy;
	}

	public void setDeploy(String deploy) {
		this.deploy = deploy;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}