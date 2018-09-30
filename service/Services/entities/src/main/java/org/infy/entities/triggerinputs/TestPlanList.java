/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import java.util.List;

import org.infy.idp.entities.jobs.TestPlans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store test plan list 
 * 
 * @author Infosys
 *
 */
public class TestPlanList {

	@SerializedName("testPlanList")
	@Expose
	private List<TestPlans> testPlanList;

	public List<TestPlans> getTestPlanList() {
		return testPlanList;
	}

	public void setTestPlanList(List<TestPlans> testPlanList) {
		this.testPlanList = testPlanList;
	}

}
