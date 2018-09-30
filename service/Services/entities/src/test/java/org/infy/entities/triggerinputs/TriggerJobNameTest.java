/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * TriggerJobNameTest is a test class for TriggerJobName
 *
 * @see org.infy.entities.triggerinputs.TriggerJobName
 * 
 */
public class TriggerJobNameTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TriggerJobNameTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method TriggerJobName().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerJobName#TriggerJobName()
	 * 
	 * 
	 */
	@Test
	public void testTriggerJobName0() throws Throwable {
		TriggerJobName testedObject = new TriggerJobName();
		testedObject.setEnvName("dev");
		testedObject.setReleaseNumber("march18");
		testedObject.setMailBody("MailBody12");
		testedObject.setMethod("method12");
		testedObject.setApplicationName("applicationName12");
		testedObject.setPipelineName("pipelineName12");
		testedObject.setUserName("userName11");
		assertEquals("method12", testedObject.getMethod()); 
		assertEquals("applicationName12", testedObject.getApplicationName()); 
		assertEquals("pipelineName12", testedObject.getPipelineName()); 
		assertEquals("MailBody12", testedObject.getMailBody()); 
		assertEquals("userName11", testedObject.getUserName()); 
		assertEquals("dev", testedObject.getEnvName());
		assertEquals("march18", testedObject.getReleaseNumber());
		// No exception thrown
		
	}

	/**
	 * Test for method TriggerJobName().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerJobName#TriggerJobName()
	 * 
	 * 
	 */
	@Test
	public void testTriggerJobName2() throws Throwable {
		TriggerJobName testedObject = new TriggerJobName();
		testedObject.setMailBody("MailBody0");
		testedObject.setMethod("method0");
		testedObject.setApplicationName("applicationName0");
		testedObject.setPipelineName("pipelineName0");
		testedObject.setUserName((String) null);
		assertEquals("method0", testedObject.getMethod()); 
		assertEquals("applicationName0", testedObject.getApplicationName()); 
		assertEquals("pipelineName0", testedObject.getPipelineName()); 
		assertEquals("MailBody0", testedObject.getMailBody()); 
		assertEquals(null, testedObject.getUserName()); 
		// No exception thrown
		
	}

	/**
	 * Test for method TriggerJobName().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerJobName#TriggerJobName()
	 * 
	 * 
	 */
	@Test
	public void testTriggerJobName3() throws Throwable {
		TriggerJobName testedObject = new TriggerJobName();
		assertEquals(null, testedObject.getMethod()); 
		assertEquals(null, testedObject.getApplicationName()); 
		assertEquals(null, testedObject.getPipelineName()); 
		assertEquals(null, testedObject.getMailBody()); 
		assertEquals(null, testedObject.getUserName()); 
		// No exception thrown
		
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TriggerJobNameTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.TriggerJobNameTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return TriggerJobName.class;
	}
}
