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
 * EnvNameTest is a test class for EnvName
 *
 * @see org.infy.idp.entities.jobs.EnvName
 * 
 */
public class EnvNameTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public EnvNameTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method EnvName().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvName#EnvName()
	 * 
	 * 
	 */
	@Test
	public void testEnvNameValues() throws Throwable {
		EnvName testedObject = new EnvName();
		List<String> EnvNames = new ArrayList<String>();
		testedObject.setEnvNames(EnvNames);

		assertEquals(EnvNames, testedObject.getEnvNames());
	}

	/**
	 * Test for method EnvName().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvName#EnvName()
	 * 
	 * 
	 */
	@Test
	public void testEnvNameNullValues() throws Throwable {
		EnvName testedObject = new EnvName();
		testedObject.setEnvNames((List<String>) null);
		assertNull(testedObject.getEnvNames());
	}

	/**
	 * Test for method EnvName().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvName#EnvName()
	 * 
	 * 
	 */
	@Test
	public void testEnvNameNull() throws Throwable {
		EnvName testedObject = new EnvName();

		assertEquals(null, testedObject.getEnvNames());
	}

	/**
	 * Test for method getEnvNames().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvName#getEnvNames()
	 * 
	 * 
	 */
	@Test
	public void testGetEnvNamesNull() throws Throwable {
		EnvName testedObject = new EnvName();
		List<String> result = testedObject.getEnvNames();

		assertEquals(null, result);
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java EnvNameTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.EnvNameTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<EnvName> getTestedClass() {
		return EnvName.class;
	}
}