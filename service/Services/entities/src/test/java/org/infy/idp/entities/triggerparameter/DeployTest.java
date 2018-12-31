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

import org.infy.entities.triggerinputs.DeployArtifact;
import org.junit.Test;

/**
 * DeployTest is a test class for Deploy
 *
 * @see org.infy.idp.entities.triggerparameter.Deploy
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
		testedObject.setVersion("version12");
		testedObject.setArtifactID("artifactID12");
		testedObject.setNexusId("nexusId11");
		testedObject.setDestEnvOwner("owner");
		testedObject.setPassword("pwd");
		testedObject.setSrcEnv("dev");
		testedObject.setAllEnvOwner("owner");
		testedObject.setDestLandscape("land");
		testedObject.setPairName("dev-sit");
		testedObject.setSourceEnvName("dev");
		testedObject.setManifestFileName("export");
		ArrayList<String> deployModule = new ArrayList<>();
		testedObject.setDeployModule(deployModule);
		ArrayList<String> deployStep = new ArrayList<>();
		testedObject.setDeployStep(deployStep);
		DeployArtifact deployArtifact = new DeployArtifact();
		testedObject.setDeployArtifact(deployArtifact);
		testedObject.setDeploymentType("type");
		testedObject.setDbDeployOperation("deploy");
		testedObject.setDbDeployRollbackType("type");
		testedObject.setDbDeployRollbackValue("value");
		testedObject.setDbDeployFailSafe("safe");
		testedObject.setDbDeployPipelineOwners("owner");
		testedObject.setDbDeployPipelineList("pepiline");
		testedObject.setDeployOperationSelected("release");

		assertEquals("owner", testedObject.getDestEnvOwner());
		assertEquals("pwd", testedObject.getPassword());
		assertEquals("dev", testedObject.getSrcEnv());
		assertEquals("owner", testedObject.getAllEnvOwner());
		assertEquals("land", testedObject.getDestLandscape());
		assertEquals("dev-sit", testedObject.getPairName());
		assertEquals("dev", testedObject.getSourceEnvName());
		assertEquals("export", testedObject.getManifestFileName());
		assertEquals(deployModule, testedObject.getDeployModule());
		assertEquals(deployStep, testedObject.getDeployStep());
		assertEquals(deployArtifact, testedObject.getDeployArtifact());
		assertEquals("type", testedObject.getDeploymentType());
		assertEquals("deploy", testedObject.getDbDeployOperation());
		assertEquals("type", testedObject.getDbDeployRollbackType());
		assertEquals("value", testedObject.getDbDeployRollbackValue());
		assertEquals("safe", testedObject.getDbDeployFailSafe());
		assertEquals("owner", testedObject.getDbDeployPipelineOwners());
		assertEquals("pepiline", testedObject.getDbDeployPipelineList());
		ArrayList<String> subModule = new ArrayList<>();
		testedObject.setSubModule(subModule);
		assertEquals(subModule, testedObject.getSubModule());

		assertEquals("nexusId11", testedObject.getNexusId());
		assertEquals("version12", testedObject.getVersion());
		assertEquals("artifactID12", testedObject.getArtifactID());
		assertEquals("release", testedObject.getDeployOperationSelected());
		// No exception thrown

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
	public void testDeploy2() throws Throwable {
		Deploy testedObject = new Deploy();
		testedObject.setVersion("version0");
		testedObject.setArtifactID("artifactID0");
		testedObject.setNexusId((String) null);
		assertEquals(null, testedObject.getNexusId());
		assertEquals("version0", testedObject.getVersion());
		assertEquals("artifactID0", testedObject.getArtifactID());
		// No exception thrown

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
	public void testDeploy3() throws Throwable {
		Deploy testedObject = new Deploy();
		assertEquals(null, testedObject.getNexusId());
		assertEquals(null, testedObject.getVersion());
		assertEquals(null, testedObject.getArtifactID());
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

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.triggerparameter.DeployTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return Deploy.class;
	}
}
