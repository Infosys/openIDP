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

import org.junit.Test;

/**
 * TestStepTest is a test class for TestStep
 *
 * @see org.infy.idp.entities.jobs.testinfo.TestStep
 * 
 */
public class TestStepTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TestStepTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getRunScript().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestStep#getRunScript()
	 * 
	 * 
	 */
	@Test
	public void testGetRunScriptNull() throws Throwable {
		TestStep testedObject = new TestStep();
		TestScript result = testedObject.getRunScript();
		assertNull(result);
	}

	/**
	 * Test for method TestStep().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestStep#TestStep()
	 * 
	 * 
	 */
	@Test
	public void testTestStepNullValues() throws Throwable {
		TestStep testedObject = new TestStep();
		testedObject.setRunScript(null);
		testedObject.setStepName(null);
		testedObject.setTest(null);

		assertNull(testedObject.getTest());
		assertNull(testedObject.getRunScript());
		assertNull(testedObject.getStepName());
	}

	/**
	 * Test for method TestStep().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestStep#TestStep()
	 * 
	 * 
	 */
	@Test
	public void testTestStepNull() throws Throwable {
		TestStep testedObject = new TestStep();

		assertNull(testedObject.getTest());
		assertNull(testedObject.getRunScript());
		assertNull(testedObject.getStepName());
	}

	/**
	 * Test for method TestStep().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestStep#TestStep()
	 * 
	 * 
	 */
	@Test
	public void testTestStepValues() throws Throwable {
		TestStep testedObject = new TestStep();
		TestScript tsObj = new TestScript();
		org.infy.idp.entities.jobs.testinfo.Test testObj = new org.infy.idp.entities.jobs.testinfo.Test();
		testedObject.setRunScript(tsObj);
		testedObject.setStepName("test");
		testedObject.setTest(testObj);

		assertEquals(testObj, testedObject.getTest());
		assertEquals(tsObj, testedObject.getRunScript());
		assertEquals("test", testedObject.getStepName());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TestStepTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.testInfo.TestStepTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<TestStep> getTestedClass() {
		return TestStep.class;
	}
}
