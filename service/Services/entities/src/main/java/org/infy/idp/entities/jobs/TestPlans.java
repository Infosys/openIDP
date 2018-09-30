/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store test plan
 * 
 * @author Infosys
 *
 */
public class TestPlans {

	@SerializedName("testPlanId")
	@Expose
	private Integer testPlanId;

	@SerializedName("testPlanName")
	@Expose
	private String testPlanName;

	public Integer getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(Integer testPlanId) {
		this.testPlanId = testPlanId;
	}

	public String getTestPlanName() {
		return testPlanName;
	}

	public void setTestPlanName(String testPlanName) {
		this.testPlanName = testPlanName;
	}

}
