/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store trigger test steps
 * 
 * @author Infosys
 *
 */
public class TriggerTestStep {
	@SerializedName("stepName")
	@Expose
	private String stepName;

	@SerializedName("testSlave")
	@Expose
	private String testSlave;

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getTestSlave() {
		return testSlave;
	}

	public void setTestSlave(String testSlave) {
		this.testSlave = testSlave;
	}

}
