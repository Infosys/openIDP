/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.testinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to complete test step details for creating the pipeline
 * 
 * @author Infosys
 *
 */
public class TestStep {

	@SerializedName("stepName")
	@Expose
	private String stepName;
	@SerializedName("runScript")
	@Expose
	private TestScript runScript;
	@SerializedName("test")
	@Expose
	private Test test;

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public TestScript getRunScript() {
		return runScript;
	}

	public void setRunScript(TestScript runScript) {
		this.runScript = runScript;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

}
