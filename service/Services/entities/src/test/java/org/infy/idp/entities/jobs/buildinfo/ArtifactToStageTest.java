/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * ArtifactToStageTest is a test class for ArtifactToStage
 *
 * @see org.infy.idp.entities.jobs.buildinfo.ArtifactToStage
 * 
 */
public class ArtifactToStageTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ArtifactToStageTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method ArtifactToStage().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ArtifactToStage#ArtifactToStage()
	 * 
	 * 
	 */
	@Test
	public void testArtifactToStage0() throws Throwable {
		ArtifactToStage testedObject = new ArtifactToStage();
		testedObject.setFlattenFileStructure("flattenFileStructure12");
		testedObject.setArtifact("artifact11");
		ArtifactRepo artifactRepo = new ArtifactRepo();
		artifactRepo.setRepoName("idp_Nexus");
		artifactRepo.setRepoPassword("dummy");
		artifactRepo.setRepoURL("server1:8081");
		artifactRepo.setRepoUsername("admin");
		testedObject.setArtifactRepo(artifactRepo);
		testedObject.setArtifactRepoName("idp_Nexus");
		testedObject.setnuspecFilePath("file1");
		testedObject.setnexusAPIKey("dummy56");
		assertEquals("artifact11", testedObject.getArtifact());
		assertEquals("flattenFileStructure12", testedObject.getFlattenFileStructure());
		assertEquals(artifactRepo, testedObject.getArtifactRepo());
		assertEquals("idp_Nexus", testedObject.getArtifactRepoName());
		assertEquals("file1", testedObject.getnuspecFilePath());
		assertEquals("dummy56", testedObject.getnexusAPIKey());
		// No exception thrown

	}

	/**
	 * Test for method ArtifactToStage().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ArtifactToStage#ArtifactToStage()
	 * 
	 * 
	 */
	@Test
	public void testArtifactToStage2() throws Throwable {
		ArtifactToStage testedObject = new ArtifactToStage();
		testedObject.setFlattenFileStructure("flattenFileStructure0");
		testedObject.setArtifact((String) null);
		assertEquals(null, testedObject.getArtifact());
		assertEquals("flattenFileStructure0", testedObject.getFlattenFileStructure());
		// No exception thrown

	}

	/**
	 * Test for method ArtifactToStage().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ArtifactToStage#ArtifactToStage()
	 * 
	 * 
	 */
	@Test
	public void testArtifactToStage3() throws Throwable {
		ArtifactToStage testedObject = new ArtifactToStage();
		assertEquals(null, testedObject.getArtifact());
		assertEquals(null, testedObject.getFlattenFileStructure());
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ArtifactToStageTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.buildInfo.ArtifactToStageTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return ArtifactToStage.class;
	}
}
