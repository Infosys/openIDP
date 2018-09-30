/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * BuildTest is a test class for Build
 *
 * @see org.infy.idp.entities.triggerparameter.Build
 * 
 */
public class BuildTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public BuildTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Build().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Build#Build()
	 * 
	 * 
	 */
	@Test
	public void testBuild0() throws Throwable {
		Build testedObject = new Build();
		testedObject.setBranchSelected("branchSelected11");
		List module = new ArrayList();
		testedObject.setModule(module);
		testedObject.setSrcEnv("dev");
		testedObject.setManifestFileName("export");
		testedObject.setOldVersion("1.0");
		testedObject.setCodeAnalysis("sonar");
		testedObject.setUnitTest("test");
		testedObject.setCast("cast");
		testedObject.setCurrentDate("date");
		testedObject.setNewVersion("1.1");

		assertEquals("dev", testedObject.getSrcEnv());
		assertEquals("export", testedObject.getManifestFileName());
		assertEquals("1.0", testedObject.getOldVersion());
		assertEquals("sonar", testedObject.getCodeAnalysis());
		assertEquals("test", testedObject.getUnitTest());
		assertEquals("cast", testedObject.getCast());
		assertEquals("date", testedObject.getCurrentDate());
		assertEquals("1.1", testedObject.getNewVersion());
		assertEquals(module, testedObject.getModule());
		assertEquals("branchSelected11", testedObject.getBranchSelected());
		// No exception thrown

	}

	/**
	 * Test for method Build().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Build#Build()
	 * 
	 * 
	 */
	@Test
	public void testBuild1() throws Throwable {
		Build testedObject = new Build();
		testedObject.setBranchSelected("branchSelected0");
		testedObject.setModule((List) null);
		assertEquals(null, testedObject.getModule());
		assertEquals("branchSelected0", testedObject.getBranchSelected());
		// No exception thrown

	}

	/**
	 * Test for method Build().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Build#Build()
	 * 
	 * 
	 */
	@Test
	public void testBuild2() throws Throwable {
		Build testedObject = new Build();
		assertEquals(null, testedObject.getModule());
		assertEquals(null, testedObject.getBranchSelected());
		// No exception thrown

	}

	/**
	 * Test for method getModule().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Build#getModule()
	 * 
	 * 
	 */
	@Test
	public void testGetModule0() throws Throwable {
		Build testedObject = new Build();
		List result = testedObject.getModule();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java BuildTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.triggerparameter.BuildTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return Build.class;
	}
}
