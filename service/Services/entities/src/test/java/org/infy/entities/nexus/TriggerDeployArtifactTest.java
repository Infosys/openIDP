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
import org.infy.idp.entities.nexus.TriggerDeployArtifact;
import org.junit.Test;

public class TriggerDeployArtifactTest {

	@Test
	public void testTriggerDeployArtifactValues() {
		TriggerDeployArtifact testObj = new TriggerDeployArtifact();
		List<DeployArtifact> list = new ArrayList<DeployArtifact>();
		DeployArtifact artifact = new DeployArtifact();
		artifact.setArtifactID("aId");
		artifact.setArtifactName("aName");
		artifact.setDownloadURL("http://downloads");
		artifact.setEnvironment("dev");
		artifact.setGroupId("G123");
		artifact.setnexusURL("http://nexus");
		artifact.setRepoName("idp_Nexus");
		artifact.setTimestamp("12:08:53");
		list.add(artifact);
		testObj.setArtifactList(list);

		assertEquals(list, testObj.getArtifactList());
	}

	@Test
	public void testTriggerDeployArtifactNull() {
		TriggerDeployArtifact testObj = new TriggerDeployArtifact();
		List<DeployArtifact> list = new ArrayList<DeployArtifact>();
		DeployArtifact artifact = new DeployArtifact();
		artifact.setArtifactID("aId");
		artifact.setArtifactName("aName");
		artifact.setDownloadURL("http://downloads");
		artifact.setEnvironment("dev");
		artifact.setGroupId("G123");
		artifact.setnexusURL("http://nexus");
		artifact.setRepoName("idp_Nexus");
		artifact.setTimestamp("12:08:53");
		list.add(artifact);

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

}
