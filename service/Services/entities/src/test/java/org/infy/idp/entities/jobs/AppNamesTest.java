/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * AppNamesTest is a test class for AppNames
 *
 * @see org.infy.idp.entities.jobs.AppNames
 * 
 */
public class AppNamesTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public AppNamesTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method AppNames().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see AppNames#AppNames()
	 * 
	 * 
	 */
	@Test
	public void testAppNamesValues() throws Throwable {
		AppNames testedObject = new AppNames();
		List<String> applicationNames = new ArrayList<>();
		testedObject.setApplicationNames(applicationNames);

		assertEquals(applicationNames, testedObject.getApplicationNames());
	}

	/**
	 * Test for method AppNames().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see AppNames#AppNames()
	 * 
	 * 
	 */
	@Test
	public void testAppNamesNullValues() throws Throwable {
		AppNames testedObject = new AppNames();
		testedObject.setApplicationNames((List<String>) null);

		assertEquals(null, testedObject.getApplicationNames());
	}

	/**
	 * Test for method AppNames().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see AppNames#AppNames()
	 * 
	 * 
	 */
	@Test
	public void testAppNamesNull() throws Throwable {
		AppNames testedObject = new AppNames();
		assertEquals(null, testedObject.getApplicationNames());
	}

	/**
	 * Test for method getApplicationNames().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see AppNames#getApplicationNames()
	 * 
	 * 
	 */
	@Test
	public void testGetApplicationNamesNull() throws Throwable {
		AppNames testedObject = new AppNames();
		List<String> result = testedObject.getApplicationNames();
		assertEquals(null, result);
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java AppNamesTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.AppNamesTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<AppNames> getTestedClass() {
		return AppNames.class;
	}
}
