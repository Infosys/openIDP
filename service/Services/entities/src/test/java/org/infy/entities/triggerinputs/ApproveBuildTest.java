/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ApproveBuildTest {

	/**
	 * Constructor for test class.
	 *
	 */
	public ApproveBuildTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testApprovebuild() throws Throwable {
		ApproveBuild testObject = new ApproveBuild();
		testObject.setApprBuildNo("build1");
		testObject.setEnvName("sit");
		testObject.setModuleList("module1");
		testObject.setReleaseIdentifier("April2");
		testObject.setUserInfo("userName");

		assertEquals("build1", testObject.getApprBuildNo());
		assertEquals("sit", testObject.getEnvName());
		assertEquals("module1", testObject.getModuleList());
		assertEquals("April2", testObject.getReleaseIdentifier());
		assertEquals("userName", testObject.getUserInfo());
	}

	@Test
	public void testNullApproveBuild() throws Throwable {

		ApproveBuild testObject = new ApproveBuild();
		testObject.setApprBuildNo(null);
		testObject.setEnvName(null);
		testObject.setModuleList(null);
		testObject.setReleaseIdentifier(null);
		testObject.setUserInfo(null);

		assertNull(testObject.getApprBuildNo());
		assertNull(testObject.getEnvName());
		assertNull(testObject.getModuleList());
		assertNull(testObject.getReleaseIdentifier());
		assertNull(testObject.getUserInfo());

	}

	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.ApproveBuildTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return ApproveBuild.class;
	}

}
