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
 * PipelineDetailTest is a test class for PipelineDetail
 *
 * @see org.infy.idp.entities.jobs.PipelineDetail
 * 
 */
public class PipelineDetailTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public PipelineDetailTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getSrNumber().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see PipelineDetail#getSrNumber()
	 * 
	 * 
	 */
	@Test
	public void testGetSrNumber() throws Throwable {
		PipelineDetail testedObject = new PipelineDetail();
		Integer result = testedObject.getSrNumber();
		assertNull(result);
	}

	/**
	 * Test for method PipelineDetail().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see PipelineDetail#PipelineDetail()
	 * 
	 * 
	 */
	@Test
	public void testPipelineDetailValues() throws Throwable {
		PipelineDetail testedObject = new PipelineDetail();
		testedObject.setCreationDate("creationDate12");
		testedObject.setPipelineName("pipelineName12");
		Integer srNumber = new Integer(5);
		testedObject.setSrNumber(srNumber);
		testedObject.setApplicationName("applicationName11");
		testedObject.setBuildTool("buildTool");
		List<String> permissions = new ArrayList<String>();
		permissions.add("permissions");
		testedObject.setPermissions(permissions);

		assertEquals(srNumber, testedObject.getSrNumber());
		assertEquals("applicationName11", testedObject.getApplicationName());
		assertEquals("pipelineName12", testedObject.getPipelineName());
		assertEquals("creationDate12", testedObject.getCreationDate());
		assertEquals(permissions, testedObject.getPermissions());
		assertEquals("buildTool", testedObject.getBuildTool());
	}

	/**
	 * Test for method PipelineDetail().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see PipelineDetail#PipelineDetail()
	 * 
	 * 
	 */
	@Test
	public void testPipelineDetailValues2() throws Throwable {
		PipelineDetail testedObject = new PipelineDetail();
		testedObject.setCreationDate("creationDate1");
		testedObject.setPipelineName("pipelineName1");
		testedObject.setSrNumber((Integer) null);
		testedObject.setApplicationName("applicationName0");
		testedObject.setBuildTool("buildTool");
		testedObject.setPermissions((List<String>) null);

		assertNull(testedObject.getSrNumber());
		assertEquals("applicationName0", testedObject.getApplicationName());
		assertEquals("pipelineName1", testedObject.getPipelineName());
		assertEquals("creationDate1", testedObject.getCreationDate());
		assertNull(testedObject.getPermissions());
		assertEquals("buildTool", testedObject.getBuildTool());
	}

	/**
	 * Test for method PipelineDetail().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see PipelineDetail#PipelineDetail()
	 * 
	 * 
	 */
	@Test
	public void testPipelineDetailNull() throws Throwable {
		PipelineDetail testedObject = new PipelineDetail();

		assertNull(testedObject.getSrNumber());
		assertNull(testedObject.getApplicationName());
		assertNull(testedObject.getPipelineName());
		assertNull(testedObject.getCreationDate());
		assertNull(testedObject.getPermissions());
		assertNull(testedObject.getBuildTool());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java PipelineDetailTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.PipelineDetailTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<PipelineDetail> getTestedClass() {
		return PipelineDetail.class;
	}
}
