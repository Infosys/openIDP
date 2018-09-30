/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.getJob;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.infy.idp.entities.getjob.GetJob;
import org.junit.Test;

/**
 * GetJobTest is a test class for GetJob
 *
 * @see org.infy.idp.entities.getjob.GetJob
 * 
 */
public class GetJobTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public GetJobTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method GetJob().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see GetJob#GetJob()
	 * 
	 * 
	 */
	@Test
	public void testGetJobValues() throws Throwable {
		GetJob testedObject = new GetJob();
		testedObject.setBuildNumber("buildNumber12");
		testedObject.setApplicationName("applicationName12");
		testedObject.setPipelineName("pipelineName11");
		testedObject.setJenkinsBuildNumber("jenkinsBuildNumber");
		assertEquals("jenkinsBuildNumber", testedObject.getJenkinsBuildNumber());
		assertEquals("buildNumber12", testedObject.getBuildNumber());
		assertEquals("applicationName12", testedObject.getApplicationName());
		assertEquals("pipelineName11", testedObject.getPipelineName());
	}

	/**
	 * Test for method GetJob().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see GetJob#GetJob()
	 * 
	 * 
	 */
	@Test
	public void testGetJobValues2() throws Throwable {
		GetJob testedObject = new GetJob();
		testedObject.setBuildNumber("buildNumber12");
		testedObject.setApplicationName("applicationName12");
		testedObject.setPipelineName("pipelineName11");
		testedObject.setJenkinsBuildNumber((String) null);
		assertNull(testedObject.getJenkinsBuildNumber());
		assertEquals("buildNumber12", testedObject.getBuildNumber());
		assertEquals("applicationName12", testedObject.getApplicationName());
		assertEquals("pipelineName11", testedObject.getPipelineName());
	}

	/**
	 * Test for method GetJob().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see GetJob#GetJob()
	 * 
	 * 
	 */
	@Test
	public void testGetJobNull() throws Throwable {
		GetJob testedObject = new GetJob();
		assertNull(testedObject.getJenkinsBuildNumber());
		assertNull(testedObject.getBuildNumber());
		assertNull(testedObject.getApplicationName());
		assertNull(testedObject.getPipelineName());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java GetJobTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.getJob.GetJobTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return GetJob.class;
	}
}