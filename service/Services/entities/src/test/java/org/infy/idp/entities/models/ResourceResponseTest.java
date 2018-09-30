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
 * ResourceResponseTest is a test class for ResourceResponse
 *
 * @see org.infy.idp.entities.models.ResourceResponse
 * 
 */
public class ResourceResponseTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ResourceResponseTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getResource().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ResourceResponse#getResource()
	 * 
	 * 
	 */
	@Test
	public void testGetResourceNull() throws Throwable {
		@SuppressWarnings("rawtypes")
		ResourceResponse testedObject = new ResourceResponse();
		Object result = testedObject.getResource();
		assertNull(result);
	}

	/**
	 * Test for method ResourceResponse().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ResourceResponse#ResourceResponse()
	 * 
	 * 
	 */
	@Test
	public void testResourceResponseValues() throws Throwable {
		ResourceResponse testedObject = new ResourceResponse();
		Object resource = new Object();
		testedObject.setResource(resource);
		assertEquals(resource, testedObject.getResource());
		assertNull(testedObject.getStatus());
		assertEquals(0, testedObject.getStatusCode());
		assertNull(testedObject.getErrorMessage());
	}

	/**
	 * Test for method ResourceResponse().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ResourceResponse#ResourceResponse()
	 * 
	 * 
	 */
	@Test
	public void testResourceResponseNull() throws Throwable {
		ResourceResponse testedObject = new ResourceResponse();
		testedObject.setResource(null);
		assertEquals(null, testedObject.getResource());
		assertEquals(null, testedObject.getStatus());
		assertEquals(0, testedObject.getStatusCode());
		assertEquals(null, testedObject.getErrorMessage());
	}

	/**
	 * Test for method ResourceResponse().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ResourceResponse#ResourceResponse()
	 * 
	 * 
	 */
	@Test
	public void testResourceResponseNull2() throws Throwable {
		ResourceResponse testedObject = new ResourceResponse();
		assertEquals(null, testedObject.getResource());
		assertEquals(null, testedObject.getStatus());
		assertEquals(0, testedObject.getStatusCode());
		assertEquals(null, testedObject.getErrorMessage());
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ResourceResponseTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.models.ResourceResponseTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<ResourceResponse> getTestedClass() {
		return ResourceResponse.class;
	}
}
