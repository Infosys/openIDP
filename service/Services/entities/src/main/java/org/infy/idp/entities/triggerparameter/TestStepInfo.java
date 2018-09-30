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
 * Entity to store test step information
 * 
 * @author Infosys
 *
 */
public class TestStepInfo {

	@SerializedName("testStepName")
	@Expose
	private String testStepName;

	@SerializedName("testStepTool")
	@Expose
	private String testStepTool;

	public String getTestStepName() {
		return testStepName;
	}

	public void setTestStepName(String testStepName) {
		this.testStepName = testStepName;
	}

	public String getTestStepTool() {
		return testStepTool;
	}

	public void setTestStepTool(String testStepTool) {
		this.testStepTool = testStepTool;
	}

}
