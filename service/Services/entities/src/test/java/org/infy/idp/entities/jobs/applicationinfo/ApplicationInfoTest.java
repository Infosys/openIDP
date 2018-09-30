/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.applicationinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.buildinfo.ArtifactToStage;
import org.junit.Test;

/**
 * ApplicationInfoTest is a test class for ApplicationInfo
 *
 * @see org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo
 * 
 */
public class ApplicationInfoTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ApplicationInfoTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method ApplicationInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ApplicationInfo#ApplicationInfo()
	 * 
	 * 
	 */
	@Test
	public void testApplicationInfoValues() throws Throwable {
		ApplicationInfo testedObject = new ApplicationInfo();
		testedObject.setApplicationName("applicationName11");
		testedObject.setDevelopers("developers11");
		testedObject.setPipelineAdmins("pipelineAdmins11");
		testedObject.setReleaseManager("releaseManager11");
		List<EnvironmentOwnerDetail> environmentOwnerDetails = new ArrayList<EnvironmentOwnerDetail>();
		testedObject.setEnvironmentOwnerDetails(environmentOwnerDetails);
		List<SlavesDetail> slavesDetails = new ArrayList<SlavesDetail>();
		testedObject.setSlavesDetails(slavesDetails);
		ArtifactToStage stageArtifact = new ArtifactToStage();
		testedObject.setArtifactToStage(stageArtifact);

		assertEquals(environmentOwnerDetails, testedObject.getSlavesDetails());
		assertEquals(environmentOwnerDetails, testedObject.getEnvironmentOwnerDetails());
		assertEquals("developers11", testedObject.getDevelopers());
		assertEquals("releaseManager11", testedObject.getReleaseManager());
		assertEquals("pipelineAdmins11", testedObject.getPipelineAdmins());
		assertEquals("applicationName11", testedObject.getApplicationName());
		assertEquals(stageArtifact, testedObject.getArtifactToStage());
	}

	/**
	 * Test for method ApplicationInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ApplicationInfo#ApplicationInfo()
	 * 
	 * 
	 */
	@Test
	public void testApplicationInfoNull() throws Throwable {
		ApplicationInfo testedObject = new ApplicationInfo();
		assertNull(testedObject.getSlavesDetails());
		assertNull(testedObject.getEnvironmentOwnerDetails());
		assertNull(testedObject.getDevelopers());
		assertNull(testedObject.getReleaseManager());
		assertNull(testedObject.getPipelineAdmins());
		assertNull(testedObject.getApplicationName());
		assertNull(testedObject.getArtifactToStage());
	}

	/**
	 * Test for method getEnvironmentOwnerDetails().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ApplicationInfo#getEnvironmentOwnerDetails()
	 * 
	 * 
	 */
	@Test
	public void testGetEnvironmentOwnerDetailsNull() throws Throwable {
		ApplicationInfo testedObject = new ApplicationInfo();
		List<EnvironmentOwnerDetail> result = testedObject.getEnvironmentOwnerDetails();
		assertNull(result);
	}

	/**
	 * Test for method getSlavesDetails().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ApplicationInfo#getSlavesDetails()
	 * 
	 * 
	 */
	@Test
	public void testGetSlavesDetailsNull() throws Throwable {
		ApplicationInfo testedObject = new ApplicationInfo();
		List<SlavesDetail> result = testedObject.getSlavesDetails();
		assertNull(result);
	}

	/**
	 * Test for method getSlavesDetails().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ApplicationInfo#getSlavesDetails()
	 * 
	 * 
	 */
	@Test
	public void testGetArtifactToStageNull() throws Throwable {
		ApplicationInfo testedObject = new ApplicationInfo();
		ArtifactToStage result = testedObject.getArtifactToStage();
		assertNull(result);
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ApplicationInfoTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.applicationInfo.ApplicationInfoTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<ApplicationInfo> getTestedClass() {
		return ApplicationInfo.class;
	}
}
