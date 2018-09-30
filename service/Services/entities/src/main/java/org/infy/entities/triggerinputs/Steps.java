/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store deploy and test steps list
 * 
 * @author Infosys
 *
 */
public class Steps {

	@SerializedName("envName")
	@Expose
	private String envName;
	@SerializedName("deploySteps")
	@Expose
	private List<String> deploySteps = null;
	@SerializedName("testSteps")
	@Expose
	private List<TestSteps> testSteps = null;

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public List<String> getDeploySteps() {
		return deploySteps;
	}

	public void setDeploySteps(List<String> deploySteps) {
		this.deploySteps = deploySteps;
	}

	public List<TestSteps> getTestSteps() {
		return testSteps;
	}

	public void setTestSteps(List<TestSteps> testSteps) {
		this.testSteps = testSteps;
	}

}
