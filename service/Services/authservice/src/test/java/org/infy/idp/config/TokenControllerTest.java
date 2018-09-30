/*
 * TokenControllerTest.java
 * Created by Jtest on 9/26/17 10:13:15 AM.
 */

package org.infy.idp.config;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

/**
 * TokenControllerTest is a test class for TokenController
 *
 * @see org.infy.idp.config.TokenController
 *  
 */
public class TokenControllerTest  {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	public TokenControllerTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getTokens().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see TokenController#getTokens()
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testGetTokens0() throws Throwable {
		TokenController testedObject = new TokenController();
		List result = testedObject.getTokens();
	}

	/**
	 * Test for method revokeRefreshToken(java.lang.String).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see TokenController#revokeRefreshToken(java.lang.String)
	 *  
	 * 
	 */
	@Test
	public void testRevokeRefreshToken0() throws Throwable {
		TokenController testedObject = new TokenController();
		String result = testedObject.revokeRefreshToken("Str 1.2 #");
		assertEquals("Str 1.2 #", result); 
		// No exception thrown
		
	}

	/**
	 * Test for method
	 * revokeToken(javax.servlet.http.HttpServletRequest,java.lang.String).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see TokenController#revokeToken(javax.servlet.http.HttpServletRequest,java.lang.String)
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testRevokeToken0() throws Throwable {
		TokenController testedObject = new TokenController();
		testedObject.revokeToken((HttpServletRequest) null, "Str 1.2 #");

	}

	/**
	 * Test for method TokenController().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see TokenController#TokenController()
	 *  
	 * 
	 */
	@Test
	public void testTokenController0() throws Throwable {
		TokenController testedObject = new TokenController();
		// No exception thrown
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TokenControllerTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.config.TokenControllerTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return TokenController.class;
	}
}
