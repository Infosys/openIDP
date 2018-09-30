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
 * HistoryTest is a test class for History
 *
 * @see org.infy.idp.entities.jobs.History
 * 
 */
public class HistoryTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public HistoryTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getPipelineDetails().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see History#getPipelineDetails()
	 * 
	 * 
	 */
	@Test
	public void testGetPipelineDetailsNull() throws Throwable {
		History testedObject = new History();
		List<PipelineDetail> result = testedObject.getPipelineDetails();
		assertNull(result);
	}

	/**
	 * Test for method History().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see History#History()
	 * 
	 * 
	 */
	@Test
	public void testHistoryValues() throws Throwable {
		History testedObject = new History();
		testedObject.setUserName("userName11");
		testedObject.setRoles("roles11");
		List<PipelineDetail> pipelineDetails = new ArrayList<>();
		testedObject.setPipelineDetails(pipelineDetails);

		assertEquals(pipelineDetails, testedObject.getPipelineDetails());
		assertEquals("userName11", testedObject.getUserName());
		assertEquals("roles11", testedObject.getRoles());
	}

	/**
	 * Test for method History().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see History#History()
	 * 
	 * 
	 */
	@Test
	public void testHistoryValues2() throws Throwable {
		History testedObject = new History();
		testedObject.setUserName("userName0");
		testedObject.setRoles("roles0");
		testedObject.setPipelineDetails((List<PipelineDetail>) null);
		assertNull(testedObject.getPipelineDetails());
		assertEquals("userName0", testedObject.getUserName());
		assertEquals("roles0", testedObject.getRoles());
	}

	/**
	 * Test for method History().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see History#History()
	 * 
	 * 
	 */
	@Test
	public void testHistoryNull() throws Throwable {
		History testedObject = new History();
		assertNull(testedObject.getPipelineDetails());
		assertNull(testedObject.getUserName());
		assertNull(testedObject.getRoles());
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java HistoryTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.HistoryTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<History> getTestedClass() {
		return History.class;
	}
}
