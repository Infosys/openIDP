/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.triggerparameter.ApplicationDetails;
import org.junit.Test;

/**
 * TriggerInputsTest is a test class for TriggerInputs
 *
 * @see org.infy.entities.triggerinputs.TriggerInputs
 * 
 */
public class TriggerInputsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TriggerInputsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getBuild().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerInputs#getBuild()
	 * 
	 * 
	 */
	@Test
	public void testGetBuild0() throws Throwable {
		TriggerInputs testedObject = new TriggerInputs();
		Build result = testedObject.getBuild();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	/**
	 * 
	 * Test for Set Function
	 * 
	 */

	@Test
	public void testSet() throws Throwable {

		TriggerInputs testedObject = new TriggerInputs();
		testedObject.setApplicationName(null);
		testedObject.setBuild(null);
		testedObject.setDeploy(null);
		testedObject.setDeployTestEnv(null);
		testedObject.setJobStatus(null);
		testedObject.setPermissions(null);
		testedObject.setReleaseNumber(null);
		testedObject.setRoles(null);
		testedObject.setSlaves(null);
		testedObject.setTest(null);
		testedObject.setUserName(null);
		testedObject.setPipelineName(null);
		testedObject.setCrRequests(null);
//		testedObject.setReleaseTransportInfo(null);
//		testedObject.setUserStory(null);
//		testedObject.setJiraProjectKey(null);

	}

	/**
	 * Test for method getDeploy().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerInputs#getDeploy()
	 * 
	 * 
	 */
	@Test
	public void testGetDeploy0() throws Throwable {
		TriggerInputs testedObject = new TriggerInputs();
		Deploy result = testedObject.getDeploy();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	/**
	 * Test for method getDeployTestEnv().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerInputs#getDeployTestEnv()
	 * 
	 * 
	 */
	@Test
	public void testGetDeployTestEnv0() throws Throwable {
		TriggerInputs testedObject = new TriggerInputs();
		DeployTestEnv result = testedObject.getDeployTestEnv();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	/**
	 * Test for method getPermissions().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerInputs#getPermissions()
	 * 
	 * 
	 */
	@Test
	public void testGetPermissions0() throws Throwable {
		TriggerInputs testedObject = new TriggerInputs();
		List result = testedObject.getPermissions();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	/**
	 * Test for method getRoles().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerInputs#getRoles()
	 * 
	 * 
	 */
	@Test
	public void testGetRoles0() throws Throwable {
		TriggerInputs testedObject = new TriggerInputs();
		List result = testedObject.getRoles();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	/**
	 * Test for method getSlaves().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerInputs#getSlaves()
	 * 
	 * 
	 */
	@Test
	public void testGetSlaves0() throws Throwable {
		TriggerInputs testedObject = new TriggerInputs();
		List result = testedObject.getSlaves();
		assertEquals(null, result); 
		// No exception thrown
		
	}
	
	/**
	 * Test for method getReleaseTransportInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerInputs#getReleaseTransportInfo()
	 * 
	 * 
	 */
//	@Test
//	public void testGetReleaseTransportInfo0() throws Throwable {
//		TriggerInputs testedObject = new TriggerInputs();
//		List<ReleaseTransportInfo> result = testedObject.getReleaseTransportInfo();
//		assertEquals(null, result); 
//		// No exception thrown
//		
//	}

	/**
	 * Test for method TriggerInputs().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TriggerInputs#TriggerInputs()
	 * 
	 * 
	 */
	@Test
	public void testTriggerInputs12() throws Throwable {
		TriggerInputs testedObject = new TriggerInputs();
		assertEquals(null, testedObject.getPermissions()); 
		assertEquals(null, testedObject.getDeployTestEnv()); 
		assertEquals(null, testedObject.getBuild()); 
		assertEquals(null, testedObject.getSlaves()); 
		assertEquals(null, testedObject.getDeploy()); 
		assertEquals(null, testedObject.getRoles()); 
		assertEquals(null, testedObject.getTest()); 
		assertEquals(null, testedObject.getJobStatus()); 
		assertEquals(null, testedObject.getUserName()); 
		assertEquals(null, testedObject.getPipelineName()); 
		assertEquals(null, testedObject.getReleaseNumber()); 
		assertEquals(null, testedObject.getApplicationName()); 
		assertNull(testedObject.getCrRequests());
//		assertNull(testedObject.getReleaseTransportInfo());
//		assertNull(testedObject.getUserStory());
//		assertNull(testedObject.getJiraProjectKey());
		// No exception thrown
		
	}

	@Test
	public void testTriggerInputs() throws Throwable {
		TriggerInputs testedObject = new TriggerInputs();
		DeployArtifact artifact = new DeployArtifact();
//		testedObject.setJiraProjectKey("MCAIN");
		testedObject.setDeployStep("tomcat");
		ArrayList<String> listString = new ArrayList<>();
		listString.add("release1");
		HashMap<String, List<String>> hashrelease = new HashMap<>();
		hashrelease.put("one", listString);
		testedObject.setReleaseBranches(hashrelease);
		testedObject.setScmType("git");
		testedObject.setSubApplicationName("app");
		ArrayList<String> listBranch = new ArrayList<>();
		listBranch.add("master");
		testedObject.setBranchList(listBranch);
		testedObject.setTagList(listString);
		testedObject.setNexusURL("server1:8081");
		testedObject.setRepoName("idp_Nexus");
		artifact.setArtifactID("101");
		artifact.setArtifactName("artifact1");
		ArrayList<DeployArtifact> deployArtifact = new ArrayList<>();
		deployArtifact.add(artifact);
		testedObject.setArtifactList(deployArtifact);
		testedObject.setSystemNames(listString);
		testedObject.setEnvironment("dev");
		testedObject.setSshAndDependent("ssh");
		Set<String> set = new HashSet<>();
		String[] array = { "foo", "bar", "baz" };

		Collections.addAll(set, array);
		testedObject.setRelaseList(set);
//		testedObject.setJiraProjectKey("MCAIN");
//		testedObject.setUserStory("55918");
		testedObject.setTechnology("Angular");
		ArrayList<BuildDeployEnv> listBuildDeploy = new ArrayList<>();
		BuildDeployEnv be = new BuildDeployEnv();
		be.setEnv(listString);
		be.setReleaseNumber("101");
		listBuildDeploy.add(be);
		testedObject.setBuildDeployEnv(listBuildDeploy);
		SlavesDetail slaveDetail = new SlavesDetail();
		slaveDetail.setBuildServerOS("linux");
		slaveDetail.setBuild("build");
		slaveDetail.setDeploy("deploy");
		ArrayList<SlavesDetail> listSlaveDetails = new ArrayList<>();
		listSlaveDetails.add(slaveDetail);
		testedObject.setAppSlaves(listSlaveDetails);
		ArrayList<String> crRequests = new ArrayList<>();
		testedObject.setCrRequests(crRequests);

		ApplicationDetails appDetails = new ApplicationDetails();

		ArrayList<ApplicationDetails> listTrigger = new ArrayList<>();
		listTrigger.add(appDetails);
		testedObject.setPipelines(listTrigger);

		assertEquals("tomcat", testedObject.getDeployStep());
		assertEquals(hashrelease, testedObject.getReleaseBranches());
		assertEquals("git", testedObject.getScmType());
		assertEquals(listBranch, testedObject.getBranchList());
		assertEquals("app", testedObject.getSubApplicationName());
		assertEquals(listString, testedObject.getTagList());
		assertEquals("server1:8081", testedObject.getNexusURL());
		assertEquals("idp_Nexus", testedObject.getRepoName());
		assertEquals(deployArtifact, testedObject.getArtifactList());
		assertEquals(listString, testedObject.getSystemNames());
		assertEquals("dev", testedObject.getEnvironment());
		assertEquals("ssh", testedObject.getSshAndDependent());
		assertEquals(set, testedObject.getRelaseList());
		assertEquals("Angular", testedObject.getTechnology());
		assertEquals(listBuildDeploy, testedObject.getBuildDeployEnv());
		assertEquals(listSlaveDetails, testedObject.getAppSlaves());
		assertEquals(listTrigger, testedObject.getPipelines());
		assertEquals(crRequests, testedObject.getCrRequests());
//		assertEquals(releaseTransportInfo, testedObject.getReleaseTransportInfo());
//		assertEquals("55918",testedObject.getUserStory());
//		assertEquals("MCAIN",testedObject.getJiraProjectKey());

	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TriggerInputsTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.TriggerInputsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return TriggerInputs.class;
	}
}
