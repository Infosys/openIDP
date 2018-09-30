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

import org.junit.Test;

/**
 * DeployArtifactTest is a test class for DeployArtifact
 *
 * @see org.infy.entities.triggerinputs.DeployArtifact
 * 
 */
public class DeployArtifactTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public DeployArtifactTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method DeployArtifact().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see DeployArtifact#DeployArtifact()
	 * 
	 * 
	 */
	@Test
	public void testDeployArtifact0() throws Throwable {
		DeployArtifact testedObject = new DeployArtifact();
		testedObject.setVersion("version1");
		testedObject.setArtifactID("artifactID0");
		ArrayList<String> buildModuleList = new ArrayList<>();

		buildModuleList.add("build1");
		buildModuleList.add("build2");
		buildModuleList.add("build3");
		testedObject.setBuildModulesList(buildModuleList);
		String userInfo = "userName";
		testedObject.setUserInfo(userInfo);

		assertEquals("version1", testedObject.getVersion()); 
		assertEquals("artifactID0", testedObject.getArtifactID()); 
		assertEquals(buildModuleList, testedObject.getBuildModulesList());
		assertEquals(userInfo, testedObject.getUserInfo());
		// No exception thrown
		
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java DeployArtifactTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.DeployArtifactTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return DeployArtifact.class;
	}
}
