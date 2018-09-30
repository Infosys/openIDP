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
 * ResponseTest is a test class for Response
 *
 * @see org.infy.idp.config.Response
 * 
 */
public class ResponseTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ResponseTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method fail().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Response#fail()
	 * 
	 * 
	 */
	@Test
	public void testFail0() throws Throwable {
		Response result = Response.fail();
		assertNotNull(result); 
		assertEquals(false, result.isOk()); 
		// No exception thrown
	}

	/**
	 * Test for method isOk().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Response#isOk()
	 * 
	 * 
	 */
	@Test
	public void testIsOk0() throws Throwable {
		Response testedObject = Response.fail();
		boolean result = testedObject.isOk();
		assertEquals(false, result); 
		// No exception thrown
		
	}

	/**
	 * Test for method ok().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Response#ok()
	 * 
	 * 
	 */
	@Test
	public void testOk0() throws Throwable {
		Response result = Response.ok();
		assertNotNull(result); 
		assertEquals(true, result.isOk()); 
		// No exception thrown
		
	}

	/**
	 * Test for method Response(boolean).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Response#Response(boolean)
	 * 
	 * 
	 */
	@Test
	public void testResponse0() throws Throwable {
		Response response = Response.fail();
		response.setOk(false);
		// No exception thrown
		
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ResponseTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.config.ResponseTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return Response.class;
	}
}
