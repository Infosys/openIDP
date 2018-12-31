/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * EnvNameTest is a test class for ObjectDetails
 *
 * @see org.infy.idp.entities.jobs.ObjectDetails
 * 
 */
public class ObjectDetailsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ObjectDetailsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method ObjectDetails().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvName#ObjectDetails()
	 * 
	 * 
	 */
	@Test
	public void testGetObjectDetailsValues() throws Throwable {
		ObjectDetails testedObject = new ObjectDetails();
		testedObject.setTask("task");
		testedObject.setUser("user");
		testedObject.setObjectName("objectName");
		testedObject.setObjectType("objectType");
		testedObject.setTransportRequest("transportRequest");
		Timestamp time = new Timestamp(0);
		testedObject.setTimeStamp(time);
		
		assertEquals("task", testedObject.getTask());
		assertEquals("user", testedObject.getUser());
		assertEquals("objectName", testedObject.getObjectName());
		assertEquals("objectType", testedObject.getObjectType());
		assertEquals("transportRequest", testedObject.getTransportRequest());
		assertEquals(time, testedObject.getTimeStamp());
	}

	/**
	 * Test for method ObjectDetails().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see EnvName#ObjectDetails()
	 * 
	 * 
	 */
	@Test
	public void testGetObjectDetailsNull() throws Throwable {
		ObjectDetails testedObject = new ObjectDetails();
		
		assertNull(testedObject.getTask());
		assertNull(testedObject.getUser());
		assertNull(testedObject.getObjectName());
		assertNull(testedObject.getObjectType());
		assertNull(testedObject.getTransportRequest());
		assertNull(testedObject.getTimeStamp());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ObjectDetails
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.ObjectDetailsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<ObjectDetails> getTestedClass() {
		return ObjectDetails.class;
	}
}