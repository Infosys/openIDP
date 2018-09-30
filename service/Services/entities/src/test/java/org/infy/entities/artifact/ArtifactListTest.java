/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.artifact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArtifactListTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArtifactListValues() {
		ArtifactList testObj = new ArtifactList();
		testObj.setApplicationName("applicationName");
		testObj.setEnvironmentName("environmentName");
		testObj.setPipelineName("pipelineName");
		testObj.setReleaseNumber("releaseNumber");
		Artifact artifact = new Artifact();
		List<Artifact> aaList = new ArrayList<>();
		List<Artifact> iaList = new ArrayList<>();
		aaList.add(artifact);
		iaList.add(artifact);
		testObj.setApprovedArtifact(aaList);
		testObj.setImportedArtifact(iaList);

		assertEquals("applicationName", testObj.getApplicationName());
		assertEquals("environmentName", testObj.getEnvironmentName());
		assertEquals("pipelineName", testObj.getPipelineName());
		assertEquals("releaseNumber", testObj.getReleaseNumber());
		assertEquals(aaList, testObj.getApprovedArtifact());
		assertEquals(iaList, testObj.getImportedArtifact());
	}

	@Test
	public void testArtifactListNull() {
		ArtifactList testObj = new ArtifactList();

		assertNull(testObj.getApplicationName());
		assertNull(testObj.getApprovedArtifact());
		assertNull(testObj.getEnvironmentName());
		assertNull(testObj.getImportedArtifact());
		assertNull(testObj.getPipelineName());
		assertNull(testObj.getReleaseNumber());
	}

}
