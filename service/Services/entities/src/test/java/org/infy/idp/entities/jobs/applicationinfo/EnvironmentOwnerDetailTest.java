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
 * EnvironmentOwnerDetailTest is a test class for EnvironmentOwnerDetail
 *
 * @see org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail
 * 
 */
public class EnvironmentOwnerDetailTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public EnvironmentOwnerDetailTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method EnvironmentOwnerDetail().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvironmentOwnerDetail#EnvironmentOwnerDetail()
	 * 
	 * 
	 */
	@Test
	public void testEnvironmentOwnerDetailValues() throws Throwable {
		EnvironmentOwnerDetail testedObject = new EnvironmentOwnerDetail();
		testedObject.setEnvironmentName("environmentName12");
		testedObject.setEnvironmentOwners("environmentOwners12");
		testedObject.setdBOwners("dBOwners11");
		testedObject.setSystemId("systemId");
		testedObject.setQa("qa");
		testedObject.setClient("client");
		testedObject.setHostName("hostName");
		testedObject.setInstanceNumber("instanceNumber");
		testedObject.setUserName("userName");
		testedObject.setLanguage("language");
		testedObject.setPassword("password");
		testedObject.setServerName("serverName");
		testedObject.setLandscapeType("landscapeType");

		assertEquals("dBOwners11", testedObject.getdBOwners());
		assertEquals("environmentName12", testedObject.getEnvironmentName());
		assertEquals("environmentOwners12", testedObject.getEnvironmentOwners());
		assertEquals("systemId", testedObject.getSystemId());
		assertEquals("qa", testedObject.getQa());
		assertEquals("client", testedObject.getClient());
		assertEquals("hostName", testedObject.getHostName());
		assertEquals("instanceNumber", testedObject.getInstanceNumber());
		assertEquals("userName", testedObject.getUserName());
		assertEquals("language", testedObject.getLanguage());
		assertEquals("password", testedObject.getPassword());
		assertEquals("serverName", testedObject.getServerName());
		assertEquals("landscapeType", testedObject.getLandscapeType());
	}

	/**
	 * Test for method EnvironmentOwnerDetail().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvironmentOwnerDetail#EnvironmentOwnerDetail()
	 * 
	 * 
	 */
	@Test
	public void testEnvironmentOwnerDetailNull() throws Throwable {
		EnvironmentOwnerDetail testedObject = new EnvironmentOwnerDetail();
		assertNull(testedObject.getdBOwners());
		assertNull(testedObject.getEnvironmentName());
		assertNull(testedObject.getEnvironmentOwners());
		assertNull(testedObject.getSystemId());
		assertNull(testedObject.getQa());
		assertNull(testedObject.getClient());
		assertNull(testedObject.getHostName());
		assertNull(testedObject.getInstanceNumber());
		assertNull(testedObject.getUserName());
		assertNull(testedObject.getLanguage());
		assertNull(testedObject.getPassword());
		assertNull(testedObject.getServerName());
		assertNull(testedObject.getLandscapeType());
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
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.applicationInfo.EnvironmentOwnerDetailTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return EnvironmentOwnerDetail.class;
	}
}
