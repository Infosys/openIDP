/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * LoginRequestTest is a test class for LoginRequest
 *
 * @see org.infy.idp.entities.models.LoginRequest
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
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method LoginRequest(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see LoginRequest#LoginRequest(java.lang.String,java.lang.String)
	 * 
	 * 
	 */
	@Test
	public void testLoginRequestValues() throws Throwable {
		LoginRequest testedObject = new LoginRequest("username22", "password21");
		testedObject.setUsername("username21");
		testedObject.setPassword("password20");
		assertEquals("password20", testedObject.getPassword());
		assertEquals("username21", testedObject.getUsername());
	}

	/**
	 * Test for method LoginRequest(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see LoginRequest#LoginRequest(java.lang.String,java.lang.String)
	 * 
	 * 
	 */
	@Test
	public void testLoginRequestValues2() throws Throwable {
		LoginRequest testedObject = new LoginRequest("username0", "password0");
		testedObject.setUsername("username1");
		testedObject.setPassword((String) null);
		assertNull(testedObject.getPassword());
		assertEquals("username1", testedObject.getUsername());
	}

	/**
	 * Test for method LoginRequest(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see LoginRequest#LoginRequest(java.lang.String,java.lang.String)
	 * 
	 * 
	 */
	@Test
	public void testLoginRequestValues3() throws Throwable {
		LoginRequest testedObject = new LoginRequest("username11", "password11");
		assertEquals("password11", testedObject.getPassword());
		assertEquals("username11", testedObject.getUsername());
	}

	/**
	 * Test for method LoginRequest(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see LoginRequest#LoginRequest(java.lang.String,java.lang.String)
	 * 
	 * 
	 */
	@Test
	public void testLoginRequestNull() throws Throwable {
		LoginRequest testedObject = new LoginRequest((String) null, (String) null);
		assertNull(testedObject.getPassword());
		assertNull(testedObject.getUsername());
	}

	/**
	 * Test for method LoginRequest().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see LoginRequest#LoginRequest()
	 * 
	 * 
	 */
	@Test
	public void testLoginRequestNull2() throws Throwable {
		LoginRequest testedObject = new LoginRequest();
		assertNull(testedObject.getPassword());
		assertNull(testedObject.getUsername());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java LoginRequestTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.models.LoginRequestTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<LoginRequest> getTestedClass() {
		return LoginRequest.class;
	}
}
