/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * DeployInfoTest is a test class for DeployInfo
 *
 * @see org.infy.idp.entities.jobs.deployinfo.DeployInfo
 * 
 */
public class DeployInfoTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public DeployInfoTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method DeployInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployInfo#DeployInfo()
	 * 
	 * 
	 */
	@Test
	public void testDeployInfo0() throws Throwable {
		DeployInfo testedObject = new DeployInfo();
		List deployEnv = new ArrayList();
		testedObject.setDeployEnv(deployEnv);
		assertEquals(deployEnv, testedObject.getDeployEnv());
		// No exception thrown

	}

	/**
	 * Test for method DeployInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployInfo#DeployInfo()
	 * 
	 * 
	 */
	@Test
	public void testDeployInfo1() throws Throwable {
		DeployInfo testedObject = new DeployInfo();
		testedObject.setDeployEnv((List) null);
		assertEquals(null, testedObject.getDeployEnv());
		// No exception thrown

	}

	/**
	 * Test for method DeployInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployInfo#DeployInfo()
	 * 
	 * 
	 */
	@Test
	public void testDeployInfo2() throws Throwable {
		DeployInfo testedObject = new DeployInfo();
		assertEquals(null, testedObject.getDeployEnv());
		// No exception thrown

	}

	/**
	 * Test for method getDeployEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployInfo#getDeployEnv()
	 * 
	 * 
	 */
	@Test
	public void testGetDeployEnv0() throws Throwable {
		DeployInfo testedObject = new DeployInfo();
		List result = testedObject.getDeployEnv();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java DeployInfoTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.deployInfo.DeployInfoTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return DeployInfo.class;
	}
}
// JTEST_CURRENT_ID=1431165992.