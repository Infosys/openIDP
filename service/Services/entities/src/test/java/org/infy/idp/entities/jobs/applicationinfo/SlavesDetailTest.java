/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.applicationinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * SlavesDetailTest is a test class for SlavesDetail
 *
 * @see org.infy.idp.entities.jobs.applicationinfo.SlavesDetail
 * 
 */
public class SlavesDetailTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public SlavesDetailTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method SlavesDetail().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see SlavesDetail#SlavesDetail()
	 * 
	 * 
	 */
	@Test
	public void testSlavesDetailValues() throws Throwable {
		SlavesDetail testedObject = new SlavesDetail();
		testedObject.setSlaveName("slaveName12");
		testedObject.setBuildServerOS("buildServerOS12");
		testedObject.setWorkspacePath("workspacePath12");
		testedObject.setCreateNewSlave("createNewSlave12");
		testedObject.setLabels("labels12");
		testedObject.setSSHKeyPath("sSHKeyPath12");
		testedObject.setSlaveUsage("slaveUsage11");
		testedObject.setBuild("build");
		testedObject.setDeploy("deploy");
		testedObject.setTest("test");

		assertEquals("sSHKeyPath12", testedObject.getSSHKeyPath());
		assertEquals("workspacePath12", testedObject.getWorkspacePath());
		assertEquals("slaveName12", testedObject.getSlaveName());
		assertEquals("buildServerOS12", testedObject.getBuildServerOS());
		assertEquals("labels12", testedObject.getLabels());
		assertEquals("slaveUsage11", testedObject.getSlaveUsage());
		assertEquals("createNewSlave12", testedObject.getCreateNewSlave());
		assertEquals("build", testedObject.getBuild());
		assertEquals("deploy", testedObject.getDeploy());
		assertEquals("test", testedObject.getTest());

	}

	/**
	 * Test for method SlavesDetail().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see SlavesDetail#SlavesDetail()
	 * 
	 * 
	 */
	@Test
	public void testSlavesDetailNull() throws Throwable {
		SlavesDetail testedObject = new SlavesDetail();
		assertNull(testedObject.getSSHKeyPath());
		assertNull(testedObject.getWorkspacePath());
		assertNull(testedObject.getSlaveName());
		assertNull(testedObject.getBuildServerOS());
		assertNull(testedObject.getLabels());
		assertNull(testedObject.getSlaveUsage());
		assertNull(testedObject.getCreateNewSlave());
		assertNull(testedObject.getBuild());
		assertNull(testedObject.getDeploy());
		assertNull(testedObject.getTest());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java SlavesDetailTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.applicationInfo.SlavesDetailTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<SlavesDetail> getTestedClass() {
		return SlavesDetail.class;
	}
}
