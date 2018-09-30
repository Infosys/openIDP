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

import org.infy.idp.entities.packagecontent.PackageContent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArtifactTest {

	public ArtifactTest() {

	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArtifactValues() {
		Artifact testObj = new Artifact();
		testObj.setArtifactName("artifactName");
		testObj.setDashboardUrl("dashboardUrl");
		PackageContent packageContent = new PackageContent();
		testObj.setPackageContent(packageContent);
		List<ArtifactDetails> artifactDetails = new ArrayList<ArtifactDetails>();
		ArtifactDetails artifactObj = new ArtifactDetails();
		artifactDetails.add(artifactObj);
		testObj.setArtifactDetails(artifactDetails);

		assertEquals("artifactName", testObj.getArtifactName());
		assertEquals("dashboardUrl", testObj.getDashboardUrl());
		assertEquals(packageContent, testObj.getPackageContent());
		assertEquals(artifactDetails, testObj.getArtifactDetails());
	}

	@Test
	public void testArtifactNull() {
		Artifact testObj = new Artifact();

		assertNull(testObj.getArtifactName());
		assertNull(testObj.getDashboardUrl());
		assertNull(testObj.getPackageContent());
		assertNull(testObj.getArtifactDetails());
	}

}
