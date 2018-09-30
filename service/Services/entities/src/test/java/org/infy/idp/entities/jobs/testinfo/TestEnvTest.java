/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.testinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * TestEnvTest is a test class for TestEnv
 *
 * @see org.infy.idp.entities.jobs.testinfo.TestEnv
 * 
 */
public class TestEnvTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TestEnvTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getTestSteps().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestEnv#getTestSteps()
	 * 
	 * 
	 */
	@Test
	public void testGetTestStepsNull() throws Throwable {
		TestEnv testedObject = new TestEnv();
		List<TestStep> result = testedObject.getTestSteps();
		assertNull(result);
	}

	/**
	 * Test for method TestEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestEnv#TestEnv()
	 * 
	 * 
	 */
	@Test
	public void testTestEnvValues() throws Throwable {
		TestEnv testedObject = new TestEnv();
		testedObject.setEnvName("envName11");
		List<TestStep> testSteps = new ArrayList<TestStep>();
		testedObject.setTestSteps(testSteps);
		testedObject.setEnvFlag("on");
		assertEquals(testSteps, testedObject.getTestSteps());
		assertEquals("envName11", testedObject.getEnvName());
		assertEquals("on", testedObject.getEnvFlag());
	}

	/**
	 * Test for method TestEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestEnv#TestEnv()
	 * 
	 * 
	 */
	@Test
	public void testTestEnvValues2() throws Throwable {
		TestEnv testedObject = new TestEnv();
		testedObject.setEnvName("envName0");
		testedObject.setTestSteps((List<TestStep>) null);
		testedObject.setEnvFlag("on");
		assertEquals(null, testedObject.getTestSteps());
		assertEquals("envName0", testedObject.getEnvName());
		assertEquals("on", testedObject.getEnvFlag());
	}

	/**
	 * Test for method TestEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestEnv#TestEnv()
	 * 
	 * 
	 */
	@Test
	public void testTestEnvNull() throws Throwable {
		TestEnv testedObject = new TestEnv();
		assertNull(testedObject.getTestSteps());
		assertNull(testedObject.getEnvName());
		assertNull(testedObject.getEnvFlag());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TestEnvTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.testInfo.TestEnvTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<TestEnv> getTestedClass() {
		return TestEnv.class;
	}
}
