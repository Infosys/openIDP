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
 * Entity to store test suite detail
 * 
 * @author Infosys
 *
 */
public class TestSuits {

	@SerializedName("testSuitId")
	@Expose
	private Integer testSuitId;

	@SerializedName("testSuitName")
	@Expose
	private String testSuitName;

	@SerializedName("testSuitParent")
	@Expose
	private String testSuitParent;

	public String getTestSuitParent() {
		return testSuitParent;
	}

	public void setTestSuitParent(String testSuitParent) {
		this.testSuitParent = testSuitParent;
	}

	public Integer getTestSuitId() {
		return testSuitId;
	}

	public void setTestSuitId(Integer testSuitId) {
		this.testSuitId = testSuitId;
	}

	public String getTestSuitName() {
		return testSuitName;
	}

	public void setTestSuitName(String testSuitName) {
		this.testSuitName = testSuitName;
	}

}
