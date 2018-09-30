/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

/**
 * DeployTest is a test class for Deploy
 *
 * @see org.infy.entities.triggerinputs.Deploy
 * 
 */
public class DeployTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public DeployTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Deploy().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Deploy#Deploy()
	 * 
	 * 
	 */
	@Test
	public void testDeploy0() throws Throwable {
		Deploy testedObject = new Deploy();
		testedObject.setDeployArtifacts((List) null);
		testedObject.setDeployEnv((List) null);
		assertEquals(null, testedObject.getDeployEnv()); 
		assertEquals(null, testedObject.getDeployArtifacts()); 
		// No exception thrown
		
	}

	/**
	 * Test for method getDeployArtifacts().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Deploy#getDeployArtifacts()
	 * 
	 * 
	 */
	@Test
	public void testGetDeployArtifacts0() throws Throwable {
		Deploy testedObject = new Deploy();
		List result = testedObject.getDeployArtifacts();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	@Test
	public void testDeploy() throws Throwable {
		Deploy testObject = new Deploy();
		EnvironmentObj envObject = new EnvironmentObj();
		testObject.setJobType("pipeline");
		ApproveBuild approveBuild = new ApproveBuild();
		approveBuild.setApprBuildNo("build1");
		approveBuild.setEnvName("dev");
		approveBuild.setModuleList("module1");
		approveBuild.setReleaseIdentifier("April2");
		approveBuild.setUserInfo("userName");
		ArrayList<ApproveBuild> listApprove = new ArrayList<>();
		listApprove.add(approveBuild);
		testObject.setApproveBuild(listApprove);
		envObject.setBizCheck("checked");
		envObject.setEnvName("dev");
		ArrayList<EnvironmentObj> envobj = new ArrayList<>();
		envobj.add(envObject);
		testObject.setBizobj(envobj);
		testObject.setDeploymentType("target");
		HashMap<String, List<ApproveBuild>> hash = new HashMap<>();
		hash.put("first", listApprove);
		testObject.setWorkEnvApprovalList(hash);
		assertEquals("pipeline", testObject.getJobType());
		assertEquals(hash, testObject.getWorkEnvApprovalList());
		assertEquals(listApprove, testObject.getApproveBuild());
		assertEquals(envobj, testObject.getBizobj());
		assertEquals("target", testObject.getDeploymentType());

	}

	/**
	 * Test for method getDeployEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Deploy#getDeployEnv()
	 * 
	 * 
	 */
	@Test
	public void testGetDeployEnv0() throws Throwable {
		Deploy testedObject = new Deploy();
		List result = testedObject.getDeployEnv();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java DeployTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.DeployTest");
	}

}
