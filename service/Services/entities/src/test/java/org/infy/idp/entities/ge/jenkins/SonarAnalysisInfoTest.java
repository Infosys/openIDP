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
 * SonarAnalysisInfoTest is a test class for SonarAnalysisInfo
 *
 * @see org.infy.idp.entities.ge.jenkins.SonarAnalysisInfo
 * 
 */
public class SonarAnalysisInfoTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public SonarAnalysisInfoTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method SonarAnalysisInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see SonarAnalysisInfo#SonarAnalysisInfo()
	 * 
	 * 
	 */
	@Test
	public void testSonarAnalysisInfoValues() throws Throwable {
		SonarAnalysisInfo testedObject = new SonarAnalysisInfo();
		testedObject.setMajorIssue(-2147483648);
		testedObject.setMinorIssue(-1000);
		testedObject.setLinesOfCode(256);
		testedObject.setStatus("status11");
		assertEquals("status11", testedObject.getStatus());
		assertEquals(-2147483648, testedObject.getMajorIssue());
		assertEquals(256, testedObject.getLinesOfCode());
		assertEquals(-1000, testedObject.getMinorIssue());
	}

	/**
	 * Test for method SonarAnalysisInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see SonarAnalysisInfo#SonarAnalysisInfo()
	 * 
	 * 
	 */
	@Test
	public void testSonarAnalysisInfoValues2() throws Throwable {
		SonarAnalysisInfo testedObject = new SonarAnalysisInfo();
		testedObject.setMajorIssue(1000);
		testedObject.setMinorIssue(858);
		testedObject.setLinesOfCode(2147483647);
		testedObject.setStatus("status0");
		assertEquals("status0", testedObject.getStatus());
		assertEquals(1000, testedObject.getMajorIssue());
		assertEquals(2147483647, testedObject.getLinesOfCode());
		assertEquals(858, testedObject.getMinorIssue());
	}

	/**
	 * Test for method SonarAnalysisInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see SonarAnalysisInfo#SonarAnalysisInfo()
	 * 
	 * 
	 */
	@Test
	public void testSonarAnalysisInfoValues3() throws Throwable {
		SonarAnalysisInfo testedObject = new SonarAnalysisInfo();
		testedObject.setMajorIssue(5);
		testedObject.setMinorIssue(-10);
		testedObject.setLinesOfCode(100);
		testedObject.setStatus((String) null);
		assertNull(testedObject.getStatus());
		assertEquals(5, testedObject.getMajorIssue());
		assertEquals(100, testedObject.getLinesOfCode());
		assertEquals(-10, testedObject.getMinorIssue());
	}

	/**
	 * Test for method SonarAnalysisInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see SonarAnalysisInfo#SonarAnalysisInfo()
	 * 
	 * 
	 */
	@Test
	public void testSonarAnalysisInfoNull() throws Throwable {
		SonarAnalysisInfo testedObject = new SonarAnalysisInfo();
		assertNull(testedObject.getStatus());
		assertEquals(0, testedObject.getMajorIssue());
		assertEquals(0, testedObject.getLinesOfCode());
		assertEquals(0, testedObject.getMinorIssue());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java SonarAnalysisInfoTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.ge.jenkins.SonarAnalysisInfoTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<SonarAnalysisInfo> getTestedClass() {
		return SonarAnalysisInfo.class;
	}
}
