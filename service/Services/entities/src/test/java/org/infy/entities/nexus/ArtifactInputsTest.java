/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.nexus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.infy.entities.triggerinputs.DeployArtifact;
import org.infy.idp.entities.nexus.ArtifactInputs;
import org.junit.Test;

public class ArtifactInputsTest {

	@Test
	public void testArtifactInputsValues() throws Throwable {
		ArtifactInputs testObj = new ArtifactInputs();
		DeployArtifact artifact = new DeployArtifact();
		testObj.setApplicationName("appName");
		testObj.setPipelineName("pipline_name");
		testObj.setEnvironmentName("envName");
		testObj.setReleaseNumber("1");
		List<DeployArtifact> as = new ArrayList<DeployArtifact>();
		as.add(artifact);
		testObj.setArtifactList(as);

		assertEquals("appName", testObj.getApplicationName());
		assertEquals("pipline_name", testObj.getPipelineName());
		assertEquals("envName", testObj.getEnvironmentName());
		assertEquals("1", testObj.getReleaseNumber());
		assertEquals(as, testObj.getArtifactList());

	}

	@Test
	public void testArtifactInputsNull() {
		ArtifactInputs testObj = new ArtifactInputs();
		assertNull(testObj.getApplicationName());
		assertNull(testObj.getPipelineName());
		assertNull(testObj.getEnvironmentName());
		assertNull(testObj.getReleaseNumber());
		assertNull(testObj.getArtifactList());

	}

	@Test
	public void testgetDeployArtifactValues() {
		DeployArtifact artifact = new DeployArtifact();
		artifact.setArtifactID("aId");
		artifact.setArtifactName("aName");
		artifact.setDownloadURL("http://downloads");
		artifact.setEnvironment("dev");
		artifact.setGroupId("G123");
		artifact.setnexusURL("http://nexus");
		artifact.setRepoName("idp_Nexus");
		artifact.setTimestamp("12:08:53");

		assertEquals("aId", artifact.getArtifactID());
		assertEquals("aName", artifact.getArtifactName());
		assertEquals("http://downloads", artifact.getDownloadURL());
		assertEquals("dev", artifact.getEnvironment());
		assertEquals("http://nexus", artifact.getnexusURL());
		assertEquals("G123", artifact.getGroupId());
		assertEquals("idp_Nexus", artifact.getRepoName());
		assertEquals("12:08:53", artifact.getTimestamp());
	}

	@Test
	public void testgetDeployArtifactNull() {
		DeployArtifact artifact = new DeployArtifact();

		assertNull(artifact.getArtifactID());
		assertNull(artifact.getArtifactName());
		assertNull(artifact.getDownloadURL());
		assertNull(artifact.getEnvironment());
		assertNull(artifact.getnexusURL());
		assertNull(artifact.getGroupId());
		assertNull(artifact.getRepoName());
		assertNull(artifact.getTimestamp());
		assertNull(artifact.getUserInfo());
		assertNull(artifact.getVersion());
	}

	public Class<ArtifactInputs> getTestedClass() {
		return ArtifactInputs.class;
	}
}
