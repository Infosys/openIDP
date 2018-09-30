/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

/**
 * RevokeTokenEndpointTest is a test class for RevokeTokenEndpoint
 *
 * @see org.infy.idp.config.RevokeTokenEndpoint
 *  
 */
public class RevokeTokenEndpointTest {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	public RevokeTokenEndpointTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method revokeToken(javax.servlet.http.HttpServletRequest).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see RevokeTokenEndpoint#revokeToken(javax.servlet.http.HttpServletRequest)
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testRevokeToken0() throws Throwable {
		RevokeTokenEndpoint testedObject = new RevokeTokenEndpoint();
		testedObject.revokeToken((HttpServletRequest) null);
	}


	/**
	 * Test for method RevokeTokenEndpoint().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see RevokeTokenEndpoint#RevokeTokenEndpoint()
	 *  
	 * 
	 */
	@Test
	public void testRevokeTokenEndpoint0() throws Throwable {
		RevokeTokenEndpoint testedObject = new RevokeTokenEndpoint();
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java RevokeTokenEndpointTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.config.RevokeTokenEndpointTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return RevokeTokenEndpoint.class;
	}
}
