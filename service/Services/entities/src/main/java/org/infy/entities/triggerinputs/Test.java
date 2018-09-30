/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store test environment and process details
 * 
 * @author Infosys
 *
 */
public class Test {

	@SerializedName("testEnv")
	@Expose
	private List<String> testEnv = null;

	@SerializedName("testProcess")
	@Expose
	private HashMap<String, List<List<String>>> testProcess = null;

	public HashMap<String, List<List<String>>> getTestProcess() {
		return testProcess;
	}

	public void setTestProcess(HashMap<String, List<List<String>>> testProcess) {
		this.testProcess = testProcess;
	}

	public List<String> getTestEnv() {
		return testEnv;
	}

	public void setTestEnv(List<String> testEnv) {
		this.testEnv = testEnv;
	}

}
