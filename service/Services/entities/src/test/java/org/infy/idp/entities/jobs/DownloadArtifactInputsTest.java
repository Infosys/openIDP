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
 * DownloadArtifactInputsTest is a test class for DownloadArtifactInputs
 *
 * @see org.infy.idp.entities.jobs.DownloadArtifactInputs
 * 
 */
public class DownloadArtifactInputsTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public DownloadArtifactInputsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method DownloadArtifactInputs().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DownloadArtifactInputs#DownloadArtifactInputs()
	 * 
	 * 
	 */
	@Test
	public void testDownloadArtifactInputsValues() throws Throwable {
		DownloadArtifactInputs testedObject = new DownloadArtifactInputs();
		testedObject.setJobName("jobName12");
		testedObject.setSubJobName("subJobName12");
		testedObject.setBuildNumber("buildNumber11");

		assertEquals("buildNumber11", testedObject.getBuildNumber());
		assertEquals("subJobName12", testedObject.getSubJobName());
		assertEquals("jobName12", testedObject.getJobName());
	}

	/**
	 * Test for method DownloadArtifactInputs().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DownloadArtifactInputs#DownloadArtifactInputs()
	 * 
	 * 
	 */
	@Test
	public void testDownloadArtifactInputsValues2() throws Throwable {
		DownloadArtifactInputs testedObject = new DownloadArtifactInputs();
		testedObject.setJobName("jobName0");
		testedObject.setSubJobName("subJobName0");
		testedObject.setBuildNumber((String) null);

		assertNull(testedObject.getBuildNumber());
		assertEquals("subJobName0", testedObject.getSubJobName());
		assertEquals("jobName0", testedObject.getJobName());
	}

	/**
	 * Test for method DownloadArtifactInputs().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DownloadArtifactInputs#DownloadArtifactInputs()
	 * 
	 * 
	 */
	@Test
	public void testDownloadArtifactInputsNull() throws Throwable {
		DownloadArtifactInputs testedObject = new DownloadArtifactInputs();

		assertNull(testedObject.getBuildNumber());
		assertNull(testedObject.getSubJobName());
		assertNull(testedObject.getJobName());
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java DownloadArtifactInputsTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.DownloadArtifactInputsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<DownloadArtifactInputs> getTestedClass() {
		return DownloadArtifactInputs.class;
	}
}
