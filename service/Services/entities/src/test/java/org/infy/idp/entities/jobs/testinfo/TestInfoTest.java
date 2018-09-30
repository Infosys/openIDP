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
 * TestInfoTest is a test class for TestInfo
 *
 * @see org.infy.idp.entities.jobs.testinfo.TestInfo
 * 
 */
public class TestInfoTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TestInfoTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getTestEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestInfo#getTestEnv()
	 * 
	 * 
	 */
	@Test
	public void testGetTestEnvNull() throws Throwable {
		TestInfo testedObject = new TestInfo();
		List<TestEnv> result = testedObject.getTestEnv();
		assertNull(result);
	}

	/**
	 * Test for method TestInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestInfo#TestInfo()
	 * 
	 * 
	 */
	@Test
	public void testTestInfoValues() throws Throwable {
		TestInfo testedObject = new TestInfo();
		List<TestEnv> testEnv = new ArrayList<TestEnv>();
		testedObject.setTestEnv(testEnv);
		assertEquals(testEnv, testedObject.getTestEnv());
	}

	/**
	 * Test for method TestInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestInfo#TestInfo()
	 * 
	 * 
	 */
	@Test
	public void testTestInfoNullValue() throws Throwable {
		TestInfo testedObject = new TestInfo();
		testedObject.setTestEnv((List<TestEnv>) null);
		assertEquals(null, testedObject.getTestEnv());
	}

	/**
	 * Test for method TestInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestInfo#TestInfo()
	 * 
	 * 
	 */
	@Test
	public void testTestInfoNull() throws Throwable {
		TestInfo testedObject = new TestInfo();
		assertEquals(null, testedObject.getTestEnv());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TestInfoTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.testInfo.TestInfoTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<TestInfo> getTestedClass() {
		return TestInfo.class;
	}
}
