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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * JobsBuildsTest is a test class for JobsBuilds
 *
 * @see org.infy.idp.entities.jobs.JobsBuilds
 * 
 */
public class JobsBuildsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public JobsBuildsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getJobBuilds().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobsBuilds#getJobBuilds()
	 * 
	 * 
	 */
	@Test
	public void testGetJobBuildsNull() throws Throwable {
		JobsBuilds testedObject = new JobsBuilds();
		List<JobBuilds> result = testedObject.getJobBuilds();
		assertNull(result);
	}

	/**
	 * Test for method JobsBuilds().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobsBuilds#JobsBuilds()
	 * 
	 * 
	 */
	@Test
	public void testJobsBuildsValues() throws Throwable {
		JobsBuilds testedObject = new JobsBuilds();
		testedObject.setJobName("jobName11");
		List<JobBuilds> jobBuilds = new ArrayList<>();
		testedObject.setJobBuilds(jobBuilds);

		assertEquals(jobBuilds, testedObject.getJobBuilds());
		assertEquals("jobName11", testedObject.getJobName());
	}

	/**
	 * Test for method JobsBuilds().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobsBuilds#JobsBuilds()
	 * 
	 * 
	 */
	@Test
	public void testJobsBuildsValues2() throws Throwable {
		JobsBuilds testedObject = new JobsBuilds();
		testedObject.setJobName("jobName0");
		testedObject.setJobBuilds((List<JobBuilds>) null);

		assertNull(testedObject.getJobBuilds());
		assertEquals("jobName0", testedObject.getJobName());
	}

	/**
	 * Test for method JobsBuilds().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobsBuilds#JobsBuilds()
	 * 
	 * 
	 */
	@Test
	public void testJobsBuildsNull() throws Throwable {
		JobsBuilds testedObject = new JobsBuilds();
		assertNull(testedObject.getJobBuilds());
		assertNull(testedObject.getJobName());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java JobsBuildsTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.JobsBuildsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<JobsBuilds> getTestedClass() {
		return JobsBuilds.class;
	}
}
// JTEST_CURRENT_ID=-1952964628.