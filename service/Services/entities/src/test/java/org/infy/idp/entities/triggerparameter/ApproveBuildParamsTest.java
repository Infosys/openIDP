/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ApproveBuildParamsTest {

	public ApproveBuildParamsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testApproveBuild() throws Throwable {
		ApproveBuildParams ap = new ApproveBuildParams();
		ap.setApplicationName("app");
		ap.setApprBuildNo("1");
		ap.setApprComment("comment");
		ap.setApprInput("input");
		ap.setEnvName("dev");
		ap.setJobType("type");
		ap.setPipelineName("pipeline");
		ap.setUserName("admin");

		assertEquals("app", ap.getApplicationName());
		assertEquals("1", ap.getApprBuildNo());
		assertEquals("comment", ap.getApprComment());
		assertEquals("input", ap.getApprInput());
		assertEquals("dev", ap.getEnvName());
		assertEquals("type", ap.getJobType());
		assertEquals("pipeline", ap.getPipelineName());
		assertEquals("admin", ap.getUserName());
	}

	@Test
	public void testNullApproveBuild() throws Throwable {
		ApproveBuildParams ap = new ApproveBuildParams();
		ap.setApplicationName(null);
		ap.setApprBuildNo(null);
		ap.setApprComment(null);
		ap.setApprInput(null);
		ap.setEnvName(null);
		ap.setJobType(null);
		ap.setPipelineName(null);
		ap.setUserName(null);

		assertNull(ap.getApplicationName());
		assertNull(ap.getApprBuildNo());
		assertNull(ap.getApprComment());
		assertNull(ap.getApprInput());
		assertNull(ap.getEnvName());
		assertNull(ap.getJobType());
		assertNull(ap.getPipelineName());
		assertNull(ap.getUserName());
	}

	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.triggerparameter.ApproveBuildParamsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return ApproveBuildParams.class;
	}

}
