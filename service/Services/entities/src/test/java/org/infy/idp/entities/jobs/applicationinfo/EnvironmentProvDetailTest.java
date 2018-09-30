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
 * EnvironmentProvDetailTest is a test class for EnvironmentProvDetail
 *
 * @see org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail
 * 
 */
public class EnvironmentProvDetailTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public EnvironmentProvDetailTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method EnvironmentProvDetails().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvironmentProvDetails#EnvironmentProvDetails()
	 * 
	 * 
	 */
	@Test
	public void testEnvironmentOwnerDetailValues() throws Throwable {

		EnvironmentProvDetails testedObject = new EnvironmentProvDetails();
		testedObject.setAnsPassword("ansPassword");
		testedObject.setAnsUserName("ansUserName");
		testedObject.setEnvironmentProvName("environmentProvName");
		testedObject.setInvName("invName");
		testedObject.setPlayName("playName");
		testedObject.setPlayPath("playPath");
		testedObject.setServerName("serverName");
		testedObject.setToolName("toolName");

		assertEquals("ansPassword", testedObject.getAnsPassword());
		assertEquals("ansUserName", testedObject.getAnsUserName());
		assertEquals("environmentProvName", testedObject.getEnvironmentProvName());
		assertEquals("invName", testedObject.getInvName());
		assertEquals("playName", testedObject.getPlayName());
		assertEquals("playPath", testedObject.getPlayPath());
		assertEquals("serverName", testedObject.getServerName());
		assertEquals("toolName", testedObject.getToolName());
	}

	/**
	 * Test for method EnvironmentProvDetails().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvironmentProvDetails#EnvironmentProvDetails()
	 * 
	 * 
	 */
	@Test
	public void testEnvironmentProvDetailsNull() throws Throwable {
		EnvironmentProvDetails testedObject = new EnvironmentProvDetails();
		assertNull(testedObject.getAnsPassword());
		assertNull(testedObject.getAnsUserName());
		assertNull(testedObject.getEnvironmentProvName());
		assertNull(testedObject.getInvName());
		assertNull(testedObject.getPlayName());
		assertNull(testedObject.getPlayPath());
		assertNull(testedObject.getServerName());
		assertNull(testedObject.getToolName());
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java EnvironmentOwnerDetailTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.applicationInfo.EnvironmentProvDetailTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return EnvironmentProvDetails.class;
	}
}
