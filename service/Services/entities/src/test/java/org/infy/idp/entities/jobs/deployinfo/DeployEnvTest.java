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
 * DeployEnvTest is a test class for DeployEnv
 *
 * @see org.infy.idp.entities.jobs.deployinfo.DeployEnv
 * 
 */
public class DeployEnvTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public DeployEnvTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method DeployEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployEnv#DeployEnv()
	 * 
	 * 
	 */
	@Test
	public void testDeployEnv0() throws Throwable {
		DeployEnv testedObject = new DeployEnv();
		testedObject.setEnvName("envName12");
		List deploySteps = new ArrayList();
		testedObject.setDeploySteps(deploySteps);
		testedObject.setApprover("approver11");
		testedObject.setEnvFlag("envFlag12");
		testedObject.setScriptType("scriptType11");
		assertEquals(deploySteps, testedObject.getDeploySteps());
		assertEquals("envName12", testedObject.getEnvName());
		assertEquals("approver11", testedObject.getApprover());
		assertEquals("envFlag12", testedObject.getEnvFlag());
		assertEquals("scriptType11", testedObject.getScriptType());
		// No exception thrown

	}

	/**
	 * Test for method DeployEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployEnv#DeployEnv()
	 * 
	 * 
	 */
	@Test
	public void testDeployEnv1() throws Throwable {
		DeployEnv testedObject = new DeployEnv();
		testedObject.setEnvName("envName1");
		testedObject.setDeploySteps((List) null);
		testedObject.setApprover("approver0");
		assertEquals(null, testedObject.getDeploySteps());
		assertEquals("envName1", testedObject.getEnvName());
		assertEquals("approver0", testedObject.getApprover());
		// No exception thrown

	}

	/**
	 * Test for method DeployEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployEnv#DeployEnv()
	 * 
	 * 
	 */
	@Test
	public void testDeployEnv2() throws Throwable {
		DeployEnv testedObject = new DeployEnv();
		testedObject.setEnvName("envName0");
		testedObject.setDeploySteps((List) null);
		testedObject.setApprover((String) null);
		assertEquals(null, testedObject.getDeploySteps());
		assertEquals("envName0", testedObject.getEnvName());
		assertEquals(null, testedObject.getApprover());
		// No exception thrown

	}

	/**
	 * Test for method DeployEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployEnv#DeployEnv()
	 * 
	 * 
	 */
	@Test
	public void testDeployEnv3() throws Throwable {
		DeployEnv testedObject = new DeployEnv();
		assertEquals(null, testedObject.getDeploySteps());
		assertEquals(null, testedObject.getEnvName());
		assertEquals(null, testedObject.getApprover());
		// No exception thrown

	}

	/**
	 * Test for method getDeploySteps().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployEnv#getDeploySteps()
	 * 
	 * 
	 */
	@Test
	public void testGetDeploySteps0() throws Throwable {
		DeployEnv testedObject = new DeployEnv();
		List result = testedObject.getDeploySteps();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java DeployEnvTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.deployInfo.DeployEnvTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return DeployEnv.class;
	}
}
