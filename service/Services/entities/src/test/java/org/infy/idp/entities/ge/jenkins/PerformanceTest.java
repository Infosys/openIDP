/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.ge.jenkins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * PerformanceTest is a test class for Performance
 *
 * @see org.infy.idp.entities.ge.jenkins.Performance
 * 
 */
public class PerformanceTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public PerformanceTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Performance().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Performance#Performance()
	 * 
	 * 
	 */
	@Test
	public void testPerformanceValues() throws Throwable {
		Performance testedObject = new Performance();
		testedObject.setStatus("status0");
		testedObject.setTool("tool0");
		testedObject.setThroughput(5);
		testedObject.setAvgrestime(-10);
		assertEquals("tool0", testedObject.getTool());
		assertEquals(5, testedObject.getThroughput());
		assertEquals(-10, testedObject.getAvgrestime());
		assertEquals("status0", testedObject.getStatus());
	}

	/**
	 * Test for method Performance().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Performance#Performance()
	 * 
	 * 
	 */
	@Test
	public void testPerformanceNull() throws Throwable {
		Performance testedObject = new Performance();

		assertNull(testedObject.getTool());
		assertEquals(0, testedObject.getThroughput());
		assertEquals(0, testedObject.getAvgrestime());
		assertNull(testedObject.getStatus());
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java PerformanceTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.ge.jenkins.PerformanceTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<Performance> getTestedClass() {
		return Performance.class;
	}
}
