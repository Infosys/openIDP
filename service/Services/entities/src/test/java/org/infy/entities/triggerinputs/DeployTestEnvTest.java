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
import java.util.List;

import org.junit.Test;

/**
 * DeployTestEnvTest is a test class for DeployTestEnv
 *
 * @see org.infy.entities.triggerinputs.DeployTestEnv
 * 
 */
public class DeployTestEnvTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public DeployTestEnvTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method DeployTestEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployTestEnv#DeployTestEnv()
	 * 
	 * 
	 */
	@Test
	public void testDeployTestEnv0() throws Throwable {
		DeployTestEnv testedObject = new DeployTestEnv();
		List env = new ArrayList();
		testedObject.setEnv(env);
		EnvironmentObj env1 = new EnvironmentObj();
		EnvironmentObj env2 = new EnvironmentObj();
		EnvironmentObj env3 = new EnvironmentObj();
		ArrayList<EnvironmentObj> listEnvObj = new ArrayList<>();
		listEnvObj.add(env1);
		listEnvObj.add(env2);
		listEnvObj.add(env3);
		testedObject.setEnvObj(listEnvObj);

		assertEquals(env, testedObject.getEnv());
		assertEquals(listEnvObj, testedObject.getEnvObj());
		// No exception thrown

	}

	/**
	 * Test for method DeployTestEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployTestEnv#DeployTestEnv()
	 * 
	 * 
	 */
	@Test
	public void testDeployTestEnv1() throws Throwable {
		DeployTestEnv testedObject = new DeployTestEnv();
		testedObject.setEnv((List) null);
		ArrayList<EnvironmentObj> listEnvObj = null;
		testedObject.setEnvObj(listEnvObj);
		assertNull(testedObject.getEnvObj());
		assertEquals(null, testedObject.getEnv());
		// No exception thrown

	}

	/**
	 * Test for method DeployTestEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployTestEnv#DeployTestEnv()
	 * 
	 * 
	 */
	@Test
	public void testDeployTestEnv2() throws Throwable {
		DeployTestEnv testedObject = new DeployTestEnv();
		assertEquals(null, testedObject.getEnv());
	}

	/**
	 * Test for method getEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployTestEnv#getEnv()
	 * 
	 * 
	 */
	@Test
	public void testGetEnv0() throws Throwable {
		DeployTestEnv testedObject = new DeployTestEnv();
		List result = testedObject.getEnv();
		assertEquals(null, result);
		// No exception thrown
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java DeployTestEnvTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.DeployTestEnvTest");
	}

}
