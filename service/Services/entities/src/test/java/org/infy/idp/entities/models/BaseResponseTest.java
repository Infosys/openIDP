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
 * BaseResponseTest is a test class for BaseResponse
 *
 * @see org.infy.idp.entities.models.BaseResponse
 * 
 */
public class BaseResponseTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public BaseResponseTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method BaseResponse().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BaseResponse#BaseResponse()
	 * 
	 * 
	 */
	@Test
	public void testBaseResponseValues() throws Throwable {
		BaseResponse testedObject = new BaseResponse();
		testedObject.setErrorMessage("errorMessage12");
		testedObject.setStatusCode(100);
		testedObject.setStatus("status11");
		assertEquals("status11", testedObject.getStatus());
		assertEquals(100, testedObject.getStatusCode());
		assertEquals("errorMessage12", testedObject.getErrorMessage());
	}

	/**
	 * Test for method BaseResponse().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BaseResponse#BaseResponse()
	 * 
	 * 
	 */
	@Test
	public void testBaseResponseValues2() throws Throwable {
		BaseResponse testedObject = new BaseResponse();
		testedObject.setErrorMessage("errorMessage0");
		testedObject.setStatusCode(5);
		testedObject.setStatus((String) null);
		assertNull(testedObject.getStatus());
		assertEquals(5, testedObject.getStatusCode());
		assertEquals("errorMessage0", testedObject.getErrorMessage());
	}

	/**
	 * Test for method BaseResponse().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BaseResponse#BaseResponse()
	 * 
	 * 
	 */
	@Test
	public void testBaseResponse3() throws Throwable {
		BaseResponse testedObject = new BaseResponse();
		assertNull(testedObject.getStatus());
		assertEquals(0, testedObject.getStatusCode());
		assertNull(testedObject.getErrorMessage());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java BaseResponseTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.models.BaseResponseTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<BaseResponse> getTestedClass() {
		return BaseResponse.class;
	}
}
