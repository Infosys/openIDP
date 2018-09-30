/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.testinfo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store test environment list
 * 
 * @author Infosys
 *
 */
public class TestInfo {
	@SerializedName("testEnv")
	@Expose
	private List<TestEnv> testEnv = null;

	public List<TestEnv> getTestEnv() {
		return testEnv;
	}

	public void setTestEnv(List<TestEnv> testEnv) {
		this.testEnv = testEnv;
	}
}
