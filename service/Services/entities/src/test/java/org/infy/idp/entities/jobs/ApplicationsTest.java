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

import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.junit.Test;

/**
 * ApplicationsTest is a test class for Applications
 *
 * @see org.infy.idp.entities.jobs.applicationinfo.Applications
 * 
 */
public class ApplicationsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ApplicationsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Applications().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Applications#Applications()
	 * 
	 * 
	 */
	@Test
	public void testApplicationsValues() throws Throwable {
		Applications testedObject = new Applications();
		List<Application> applications = new ArrayList<Application>();
		testedObject.setApplications(applications);
		assertEquals(applications, testedObject.getApplications());
	}

	/**
	 * Test for method Applications().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Applications#Applications()
	 * 
	 * 
	 */
	@Test
	public void testApplicationsNullValues() throws Throwable {
		Applications testedObject = new Applications();
		testedObject.setApplications((List<Application>) null);

		assertNull(testedObject.getApplications());
	}

	/**
	 * Test for method Applications().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Applications#Applications()
	 * 
	 * 
	 */
	@Test
	public void testApplicationsNull() throws Throwable {
		Applications testedObject = new Applications();
		assertNull(testedObject.getApplications());
	}

	/**
	 * Test for method getApplications().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Applications#getApplications()
	 * 
	 * 
	 */
	@Test
	public void testGetApplicationsNull2() throws Throwable {
		Applications testedObject = new Applications();
		List<Application> result = testedObject.getApplications();
		assertNull(result);
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ApplicationsTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.ApplicationsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return Applications.class;
	}
}
