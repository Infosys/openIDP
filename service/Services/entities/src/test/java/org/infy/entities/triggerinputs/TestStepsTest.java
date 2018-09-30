/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestStepsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TestStepsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testTestSteps() throws Throwable {
		TestSteps testObject = new TestSteps();
		testObject.setStepName("step1");
		testObject.setToolName("selenium");
		assertEquals("step1", testObject.getStepName());
		assertEquals("selenium", testObject.getToolName());
	}

	@Test
	public void testNullTestSteps() throws Throwable {
		TestSteps testObject = new TestSteps();
		testObject.setStepName(null);
		testObject.setToolName(null);
		assertNull(testObject.getStepName());
		assertNull(testObject.getToolName());
	}

	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.TestPlanListTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return SubModule.class;
	}

}
