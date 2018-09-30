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

import java.util.ArrayList;

import org.junit.Test;

public class StepsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public StepsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testSteps() throws Throwable {
		Steps testObject = new Steps();
		TestSteps testSteps = new TestSteps();
		testObject.setEnvName("dev");
		ArrayList<String> deploySteps = new ArrayList<>();
		ArrayList<TestSteps> testStep = new ArrayList<>();
		deploySteps.add("step1");
		deploySteps.add("step2");
		testObject.setDeploySteps(deploySteps);
		testSteps.setStepName("stepTest");
		testSteps.setToolName("rft");
		testStep.add(testSteps);
		testObject.setTestSteps(testStep);

		assertEquals("dev", testObject.getEnvName());
		assertEquals(deploySteps, testObject.getDeploySteps());
		assertEquals(testStep, testObject.getTestSteps());

	}

	@Test
	public void testNullSteps() throws Throwable {
		Steps testObject = new Steps();
		TestSteps testSteps = new TestSteps();
		testObject.setEnvName(null);
		ArrayList<String> deploySteps = null;
		ArrayList<TestSteps> testStep = null;
		testSteps.setStepName(null);
		testSteps.setToolName(null);
		testObject.setDeploySteps(deploySteps);
		testObject.setTestSteps(testStep);

		assertNull(testObject.getEnvName());
		assertNull(testObject.getDeploySteps());
		assertNull(testObject.getTestSteps());

	}

	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.StepsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return Steps.class;
	}

}
