/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.ge.jenkins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * SonarAPITest is a test class for SonarAPI
 *
 * @see org.infy.idp.entities.ge.jenkins.SonarAPI
 *  
 */
public class SonarAPITest  {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	public SonarAPITest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getComponentKeys().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see SonarAPI#getComponentKeys()
	 *  
	 * 
	 */
	@Test
	public void testGetComponentKeysNull() throws Throwable {
		SonarAPI testedObject = new SonarAPI();
		List<String> result = testedObject.getComponentKeys();
		assertNull(result);
	}

	/**
	 * Test for method SonarAPI().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see SonarAPI#SonarAPI()
	 *  
	 * 
	 */
	@Test
	public void testSonarAPIValues() throws Throwable {
		SonarAPI testedObject = new SonarAPI();
		List<String> componentKeys = new ArrayList<String>();
		testedObject.setComponentKeys(componentKeys);
		assertEquals(componentKeys, testedObject.getComponentKeys());
	}

	/**
	 * Test for method SonarAPI().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see SonarAPI#SonarAPI()
	 *  
	 * 
	 */
	@Test
	public void testSonarAPINullValue() throws Throwable {
		SonarAPI testedObject = new SonarAPI();
		testedObject.setComponentKeys((List<String>) null);
		assertNull(testedObject.getComponentKeys());
	}

	/**
	 * Test for method SonarAPI().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see SonarAPI#SonarAPI()
	 *  
	 * 
	 */
	@Test
	public void testSonarAPINull() throws Throwable {
		SonarAPI testedObject = new SonarAPI();
		assertNull(testedObject.getComponentKeys());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java SonarAPITest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.ge.jenkins.SonarAPITest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class<SonarAPI> getTestedClass() {
		return SonarAPI.class;
	}
}
