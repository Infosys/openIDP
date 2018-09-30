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

public class ArtifactoryArtifactTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArtifactoryArtifactstValues() {
		ArtifactoryArtifacts testObj = new ArtifactoryArtifacts();
		
		ArtifactoryFile artFile = new ArtifactoryFile();
		List<ArtifactoryFile> artFileList =  new ArrayList<>();
		artFileList.add(artFile);
		testObj.setCreated("dateString");
		testObj.setFiles(artFileList);
		testObj.setUri("http://localhost:8081/artifactory");

		assertEquals("dateString", testObj.getCreated());
		assertEquals("http://localhost:8081/artifactory", testObj.getUri());
		assertEquals(artFileList, testObj.getFiles());
	}

	@Test
	public void testArtifactListNull() {
		ArtifactoryArtifacts testObj = new ArtifactoryArtifacts();

		assertNull(testObj.getUri());
		assertNull(testObj.getCreated());
		assertNull(testObj.getFiles());
	}

}
