/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store deploy environment details
 * 
 * @author Infosys
 *
 */
public class DeployEnv {

	@SerializedName("envName")
	@Expose
	private String envName;

	@SerializedName("approver")
	@Expose
	private String approver;

	@SerializedName("envFlag")
	@Expose
	private String envFlag;
	@SerializedName("scriptType")
	@Expose
	private String scriptType;

	@SerializedName("deploySteps")
	@Expose
	private List<DeployStep> deploySteps = null;

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public List<DeployStep> getDeploySteps() {
		return deploySteps;
	}

	public void setDeploySteps(List<DeployStep> deploySteps) {
		this.deploySteps = deploySteps;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public String getScriptType() {
		return scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

}
