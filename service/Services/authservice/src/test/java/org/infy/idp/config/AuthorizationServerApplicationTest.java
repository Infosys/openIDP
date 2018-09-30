/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import java.util.List;

import org.junit.Test;

/**
 * AuthorizationServerApplicationTest is a test class for
 * AuthorizationServerApplication
 *
 * @see org.infy.idp.config.AuthorizationServerApplication
 *  
 */

public class AuthorizationServerApplicationTest {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	
	
	public AuthorizationServerApplicationTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}


	/**
	 * Test for method serviceInstancesByApplicationName(java.lang.String).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see ServiceInstanceRestController#serviceInstancesByApplicationName(java.lang.String)
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testServiceInstancesByApplicationName0() throws Throwable {
		ServiceInstanceRestController testedObject = new ServiceInstanceRestController();
		List result = testedObject.serviceInstancesByApplicationName("applicationName");

	}

	


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java AuthorizationServerApplicationTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.config.AuthorizationServerApplicationTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return AuthorizationServerApplication.class;
	}
}
