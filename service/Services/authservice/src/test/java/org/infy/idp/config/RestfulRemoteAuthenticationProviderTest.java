/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import org.junit.Test;

/**
 * RestfulRemoteAuthenticationProviderTest is a test class for
 * RestfulRemoteAuthenticationProvider
 *
 * @see org.infy.idp.config.RestfulRemoteAuthenticationProvider
 * 
 */
public class RestfulRemoteAuthenticationProviderTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public RestfulRemoteAuthenticationProviderTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}


	/**
	 * Test for method RestfulRemoteAuthenticationProvider().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see RestfulRemoteAuthenticationProvider#RestfulRemoteAuthenticationProvider()
	 * 
	 * 
	 */
	@Test
	public void testRestfulRemoteAuthenticationProvider0() throws Throwable {
		RestfulRemoteAuthenticationProvider testedObject = new RestfulRemoteAuthenticationProvider();
		// No exception thrown
	}

	/**
	 * Test for method supports(java.lang.Class).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see RestfulRemoteAuthenticationProvider#supports(java.lang.Class)
	 * 
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testSupports1() throws Throwable {
		RestfulRemoteAuthenticationProvider testedObject = new RestfulRemoteAuthenticationProvider();
		boolean result = testedObject.supports((Class) null);
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java RestfulRemoteAuthenticationProviderTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.config.RestfulRemoteAuthenticationProviderTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return RestfulRemoteAuthenticationProvider.class;
	}
}
