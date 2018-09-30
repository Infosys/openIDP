/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;

import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.junit.Test;

/**
 * ApplicationTest is a test class for Application
 *
 * @see org.infy.idp.entities.jobs.applicationinfo.Application
 *  
 */
public class ApplicationTest  {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	public ApplicationTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Application().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Application#Application()
	 *  
	 * 
	 */
	@Test
	public void testApplicationValues() throws Throwable {
		Application testedObject = new Application();
		ApplicationInfo appJson = new ApplicationInfo();
		testedObject.setAppJson(appJson);
		testedObject.setApplicationName("applicationName21");

		assertEquals(appJson, testedObject.getAppJson());
		assertEquals("applicationName21", testedObject.getApplicationName());
	}

	/**
	 * Test for method Application().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Application#Application()
	 *  
	 * 
	 */
	@Test
	public void testApplicationValues2() throws Throwable {
		Application testedObject = new Application();
		testedObject.setAppJson((ApplicationInfo) null);
		testedObject.setApplicationName("applicationName0");

		assertEquals(null, testedObject.getAppJson());
		assertEquals("applicationName0", testedObject.getApplicationName());
	}

	/**
	 * Test for method Application().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Application#Application()
	 *  
	 * 
	 */
	@Test
	public void testApplicationNullValues() throws Throwable {
		Application testedObject = new Application();
		testedObject.setAppJson((ApplicationInfo) null);
		testedObject.setApplicationName((String) null);

		assertEquals(null, testedObject.getAppJson());
		assertEquals(null, testedObject.getApplicationName());
	}

	/**
	 * Test for method Application().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Application#Application()
	 *  
	 * 
	 */
	@Test
	public void testApplicationNull() throws Throwable {
		Application testedObject = new Application();

		assertEquals(null, testedObject.getAppJson());
		assertEquals(null, testedObject.getApplicationName());
	}

	/**
	 * Test for method getAppJson().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Application#getAppJson()
	 *  
	 * 
	 */
	@Test
	public void testGetAppJsonNull() throws Throwable {
		Application testedObject = new Application();
		ApplicationInfo result = testedObject.getAppJson();

		assertEquals(null, result);
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ApplicationTest
	 * 
	 * @param args command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.ApplicationTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return Application.class;
	}
}
