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

import org.junit.Test;

/**
 * PipelineTest is a test class for Pipeline
 *
 * @see org.infy.idp.entities.jobs.Pipeline
 * 
 */
public class PipelineTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public PipelineTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getPipelineJson().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Pipeline#getPipelineJson()
	 * 
	 * 
	 */
	@Test
	public void testGetPipelineJsonNull() throws Throwable {
		Pipeline testedObject = new Pipeline();
		IDPJob result = testedObject.getPipelineJson();
		assertNull(result);
	}

	/**
	 * Test for method Pipeline().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Pipeline#Pipeline()
	 * 
	 * 
	 */
	@Test
	public void testPipelineValues() throws Throwable {
		Pipeline testedObject = new Pipeline();
		testedObject.setApplicationName("Ant22Dec");
		testedObject.setMethod("method21");
		testedObject.setPipelineName("pipelineName21");
		IDPJob pipelineJson = new IDPJob();
		testedObject.setPipelineJson(pipelineJson);
		assertEquals("method21", testedObject.getMethod());
		assertEquals(pipelineJson, testedObject.getPipelineJson());
		assertEquals("pipelineName21", testedObject.getPipelineName());
		assertEquals("Ant22Dec", testedObject.getApplicationName());
	}

	/**
	 * Test for method Pipeline().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Pipeline#Pipeline()
	 * 
	 * 
	 */
	@Test
	public void testPipelineValues2() throws Throwable {
		Pipeline testedObject = new Pipeline();
		testedObject.setMethod("method0");
		testedObject.setPipelineName("pipelineName0");
		testedObject.setPipelineJson((IDPJob) null);

		assertEquals("method0", testedObject.getMethod());
		assertNull(testedObject.getPipelineJson());
		assertEquals("pipelineName0", testedObject.getPipelineName());
	}

	/**
	 * Test for method Pipeline().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Pipeline#Pipeline()
	 * 
	 * 
	 */
	@Test
	public void testPipelineNull() throws Throwable {
		Pipeline testedObject = new Pipeline();

		assertNull(testedObject.getMethod());
		assertNull(testedObject.getPipelineJson());
		assertNull(testedObject.getPipelineName());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java PipelineTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.PipelineTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<Pipeline> getTestedClass() {
		return Pipeline.class;
	}
}
