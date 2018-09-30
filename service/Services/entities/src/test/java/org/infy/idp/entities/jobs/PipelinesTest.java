/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * PipelinesTest is a test class for Pipelines
 *
 * @see org.infy.idp.entities.jobs.Pipelines
 * 
 */
public class PipelinesTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public PipelinesTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getPipelines().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Pipelines#getPipelines()
	 * 
	 * 
	 */
	@Test
	public void testGetPipelinesNullValues() throws Throwable {
		Pipelines testedObject = new Pipelines();
		List<Pipeline> result = testedObject.getPipelines();

		assertNull(result);
	}

	/**
	 * Test for method Pipelines().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Pipelines#Pipelines()
	 * 
	 */
	@Test
	public void testPipelinesValues() throws Throwable {
		Pipelines testedObject = new Pipelines();
		List<Pipeline> pipelines = new ArrayList<>();
		testedObject.setPipelines(pipelines);

		assertEquals(pipelines, testedObject.getPipelines());
	}

	/**
	 * Test for method Pipelines().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Pipelines#Pipelines()
	 * 
	 */
	@Test
	public void testPipelinesNull() throws Throwable {
		Pipelines testedObject = new Pipelines();
		testedObject.setPipelines((List<Pipeline>) null);
		assertNull(testedObject.getPipelines());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java PipelinesTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.PipelinesTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class<Pipelines> getTestedClass() {
		return Pipelines.class;
	}
}
