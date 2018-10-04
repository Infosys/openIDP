/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.ge.jenkins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * JenkinsJobInfoTest is a test class for JenkinsJobInfo
 *
 * @see org.infy.idp.entities.ge.jenkins.JenkinsJobInfo
 * 
 */
public class JenkinsJobInfoTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public JenkinsJobInfoTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getNumber().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JenkinsJobInfo#getNumber()
	 * 
	 * 
	 */
	@Test
	public void testGetNumberNull() throws Throwable {
		JenkinsJobInfo testedObject = new JenkinsJobInfo();
		Integer result = testedObject.getNumber();
		assertNull(result);
	}

	/**
	 * Test for method getTimestamp().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JenkinsJobInfo#getTimestamp()
	 * 
	 * 
	 */
	@Test
	public void testGetTimestampNull() throws Throwable {
		JenkinsJobInfo testedObject = new JenkinsJobInfo();
		Long result = testedObject.getTimestamp();
		assertNull(result);
	}

	/**
	 * Test for method JenkinsJobInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JenkinsJobInfo#JenkinsJobInfo()
	 * 
	 * 
	 */
	@Test
	public void testJenkinsJobInfoValues() throws Throwable {
		JenkinsJobInfo testedObject = new JenkinsJobInfo();
		testedObject.setClazz("class11");
		testedObject.setBuilding("building11");
		testedObject.setFullDisplayName("fullDisplayName11");
		Integer number = new Integer(5);
		testedObject.setNumber(number);
		testedObject.setResult("result11");
		Long timestamp = new Long(5L);
		testedObject.setTimestamp(timestamp);
		assertEquals("result11", testedObject.getResult());
		assertEquals(number, testedObject.getNumber());
		assertEquals(timestamp, testedObject.getTimestamp());
		assertEquals("class11", testedObject.getClazz());
		assertEquals("building11", testedObject.getBuilding());
		assertEquals("fullDisplayName11", testedObject.getFullDisplayName());
	}

	/**
	 * Test for method JenkinsJobInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JenkinsJobInfo#JenkinsJobInfo()
	 * 
	 * 
	 */
	@Test
	public void testJenkinsJobInfoValues2() throws Throwable {
		JenkinsJobInfo testedObject = new JenkinsJobInfo();
		testedObject.setClazz("class0");
		testedObject.setBuilding("building0");
		testedObject.setFullDisplayName("fullDisplayName0");
		testedObject.setNumber((Integer) null);
		testedObject.setResult("result0");
		testedObject.setTimestamp((Long) null);

		assertEquals("result0", testedObject.getResult()); 
		assertNull(testedObject.getNumber()); 
		assertNull(testedObject.getTimestamp()); 
		assertEquals("class0", testedObject.getClazz()); 
		assertEquals("building0", testedObject.getBuilding()); 
		assertEquals("fullDisplayName0", testedObject.getFullDisplayName()); 
		// No exception thrown
		
	}

	/**
	 * Test for method JenkinsJobInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JenkinsJobInfo#JenkinsJobInfo()
	 * 
	 * 
	 */
	@Test
	public void testJenkinsJobInfoNull() throws Throwable {
		JenkinsJobInfo testedObject = new JenkinsJobInfo();
		assertNull(testedObject.getResult());
		assertNull(testedObject.getNumber());
		assertNull(testedObject.getTimestamp());
		assertNull(testedObject.getClazz());
		assertNull(testedObject.getBuilding());
		assertNull(testedObject.getFullDisplayName());
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java JenkinsJobInfoTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.ge.jenkins.JenkinsJobInfoTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<JenkinsJobInfo> getTestedClass() {
		return JenkinsJobInfo.class;
	}
}
