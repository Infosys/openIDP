/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * JobBuildsTest is a test class for JobBuilds
 *
 * @see org.infy.idp.entities.jobs.JobBuilds
 * 
 */
public class JobBuildsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public JobBuildsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method JobBuilds().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobBuilds#JobBuilds()
	 * 
	 * 
	 */
	@Test
	public void testJobBuildsValues() throws Throwable {
		JobBuilds testedObject = new JobBuilds();
		testedObject.setJobName("jobName12");
		testedObject.setBuildJSON("buildJSON11");

		assertEquals("jobName12", testedObject.getJobName());
		assertEquals("buildJSON11", testedObject.getBuildJSON());
	}

	/**
	 * Test for method JobBuilds().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobBuilds#JobBuilds()
	 * 
	 * 
	 */
	@Test
	public void testJobBuildsValues2() throws Throwable {
		JobBuilds testedObject = new JobBuilds();
		testedObject.setJobName("jobName0");
		testedObject.setBuildJSON((String) null);

		assertEquals("jobName0", testedObject.getJobName());
		assertNull(testedObject.getBuildJSON());
	}

	/**
	 * Test for method JobBuilds().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobBuilds#JobBuilds()
	 * 
	 * 
	 */
	@Test
	public void testJobBuildsNullValues() throws Throwable {
		JobBuilds testedObject = new JobBuilds();
		assertNull(testedObject.getJobName());
		assertNull(testedObject.getBuildJSON());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java JobBuildsTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.JobBuildsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return JobBuilds.class;
	}
}
