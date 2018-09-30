/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * LoginRequestTest is a test class for LoginRequest
 *
 * @see org.infy.idp.config.entities.LoginRequest
 *  
 */
public class LoginRequestTest {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	public LoginRequestTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method LoginRequest(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see LoginRequest#LoginRequest(java.lang.String,java.lang.String)
	 *  
	 * 
	 */
	@Test
	public void testLoginRequest0() throws Throwable {
		LoginRequest testedObject = new LoginRequest("username3", "password2");
		testedObject.setUsername("username2");
		testedObject.setPassword("password1");
		assertEquals("username2", testedObject.getUsername()); 
		assertEquals("password1", testedObject.getPassword()); 
		// No exception thrown
		
	}

	/**
	 * Test for method LoginRequest().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see LoginRequest#LoginRequest()
	 *  
	 * 
	 */
	@Test
	public void testLoginRequest4() throws Throwable {
		LoginRequest testedObject = new LoginRequest();
		assertEquals(null, testedObject.getUsername()); 
		assertEquals(null, testedObject.getPassword()); 
		// No exception thrown
		
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java LoginRequestTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.config.entities.LoginRequestTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return LoginRequest.class;
	}
}
