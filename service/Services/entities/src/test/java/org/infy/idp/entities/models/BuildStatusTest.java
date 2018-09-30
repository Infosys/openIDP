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

import org.infy.idp.entities.models.BuildStatus.State;
import org.junit.Test;

/**
 * BuildStatusTest is a test class for BuildStatus
 *
 * @see org.infy.idp.entities.models.BuildStatus
 * 
 */
public class BuildStatusTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public BuildStatusTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method BuildStatus(java.lang.String,int).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildStatus#BuildStatus(java.lang.String,int)
	 * 
	 * 
	 */
	@Test
	public void testBuildStatusValues() throws Throwable {
		BuildStatus testedObject = new BuildStatus("California", 100);
		assertEquals("BuildStatus{state=California, buildNumber=100}", testedObject.toString());
		assertEquals("California", testedObject.getState());
		assertEquals(100, testedObject.getBuildNumber());
	}

	/**
	 * Test for method BuildStatus(java.lang.String,int).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildStatus#BuildStatus(java.lang.String,int)
	 * 
	 * 
	 */
	@Test
	public void testBuildStatusValues2() throws Throwable {
		BuildStatus testedObject = new BuildStatus((String) null, 5);
		assertEquals("BuildStatus{state=null, buildNumber=5}", testedObject.toString());
		assertEquals(null, testedObject.getState());
		assertEquals(5, testedObject.getBuildNumber());
	}

	/**
	 * Test for method getBuildNumber().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildStatus#getBuildNumber()
	 * 
	 * 
	 */
	@Test
	public void testGetBuildNumberNull() throws Throwable {
		BuildStatus testedObject = new BuildStatus("California", 0);
		int result = testedObject.getBuildNumber();
		assertEquals(0, result);
	}

	@Test
	public void testEnum() throws Throwable {

		State.valueOf(org.infy.idp.entities.models.BuildStatus.State.BUILDING.toString());
		State.values();

		assertEquals("BUILDING", org.infy.idp.entities.models.BuildStatus.State.BUILDING.toString());
		assertEquals("CANCELLED_IN_QUEUE",
				org.infy.idp.entities.models.BuildStatus.State.CANCELLED_IN_QUEUE.toString());
		assertEquals("STUCK_IN_QUEUE", org.infy.idp.entities.models.BuildStatus.State.STUCK_IN_QUEUE.toString());
		assertEquals("TIMED_OUT", org.infy.idp.entities.models.BuildStatus.State.TIMED_OUT.toString());

	}

	/**
	 * Test for method getState().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildStatus#getState()
	 * 
	 * 
	 */
	@Test
	public void testGetStateNull() throws Throwable {
		BuildStatus testedObject = new BuildStatus((String) null, 5);
		String result = testedObject.getState();
		assertNull(result);
	}

	/**
	 * Test for method toString().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildStatus#toString()
	 * 
	 * 
	 */
	@Test
	public void testToString() throws Throwable {
		BuildStatus testedObject = new BuildStatus("California", -10);
		String result = testedObject.toString();
		assertEquals("BuildStatus{state=California, buildNumber=-10}", result);
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java BuildStatusTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.models.BuildStatusTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<BuildStatus> getTestedClass() {
		return BuildStatus.class;
	}
}
// JTEST_CURRENT_ID=2010127818.