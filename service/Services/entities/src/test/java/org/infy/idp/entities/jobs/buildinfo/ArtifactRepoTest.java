/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ArtifactRepoTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ArtifactRepoTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testArtifactRepo() throws Throwable {
		ArtifactRepo artifact = new ArtifactRepo();
		artifact.setRepoName("idp_Nexus");
		artifact.setRepoPassword("dummy");
		artifact.setRepoURL("server1:8081");
		artifact.setRepoUsername("admin");

		assertEquals("idp_Nexus", artifact.getRepoName());
		assertEquals("dummy", artifact.getRepoPassword());
		assertEquals("server1:8081", artifact.getRepoURL());
		assertEquals("admin", artifact.getRepoUsername());
	}

	@Test
	public void testNullArtifactRepo() throws Throwable {
		ArtifactRepo artifact = new ArtifactRepo();
		artifact.setRepoName(null);
		artifact.setRepoPassword(null);
		artifact.setRepoURL(null);
		artifact.setRepoUsername(null);

		assertNull(artifact.getRepoName());
		assertNull(artifact.getRepoPassword());
		assertNull(artifact.getRepoURL());
		assertNull(artifact.getRepoUsername());
	}

	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.buildInfo.ArtifactRepoTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return ArtifactRepo.class;
	}
}
