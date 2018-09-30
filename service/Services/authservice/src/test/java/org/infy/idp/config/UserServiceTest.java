/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * UserServiceTest is a test class for UserService
 *
 * @see org.infy.idp.config.UserService
 *  
 */
public class UserServiceTest {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	public UserServiceTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method authenticate(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see UserService#authenticate(java.lang.String,java.lang.String)
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testAuthenticate0() throws Throwable {
		UserService testedObject = new UserService();
		Response result = testedObject.authenticate("Str 1.2 #", "Mr. Bob Smith");

		// NullPointerException thrown
		// at org.infy.idp.config.UserService.authenticate(UserService.java:48)
	}

	/**
	 * Test for method loadUser(java.lang.String).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see UserService#loadUser(java.lang.String)
	 *  
	 * 
	 */
	@Test
	public void testLoadUser0() throws Throwable {
		UserService testedObject = new UserService();
		Response result = testedObject.loadUser("Str 1.2 #");
		assertNotNull(result); 
		assertEquals(true, result.isOk()); 
		// No exception thrown
		
	}

	/**
	 * Test for method UserService().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see UserService#UserService()
	 *  
	 * 
	 */
	@Test
	public void testUserService0() throws Throwable {
		UserService testedObject = new UserService();
		// No exception thrown
		
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java UserServiceTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.config.UserServiceTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return UserService.class;
	}
}
