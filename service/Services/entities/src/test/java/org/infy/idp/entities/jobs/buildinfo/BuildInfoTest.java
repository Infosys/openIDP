/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.common.RunScript;
import org.junit.Test;

/**
 * BuildInfoTest is a test class for BuildInfo
 *
 * @see org.infy.idp.entities.jobs.buildinfo.BuildInfo
 * 
 */
public class BuildInfoTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public BuildInfoTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method BuildInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInfo#BuildInfo()
	 * 
	 * 
	 */
	@Test
	public void testBuildInfo0() throws Throwable {
		BuildInfo testedObject = new BuildInfo();
		/*
		 * Remove Jira ALM testedObject.setJiraProjKey("jiraProjKey21");
		 * testedObject.setJiraAssigneeName("jiraAssigneeName21");
		 */
		testedObject.setJavaModules("javaModules21");
		testedObject.setEjbModules("ejbModules21");
		testedObject.setWebModules("webModules21");
		testedObject.setNexusType("nexusType21");
		testedObject.setNexusUrl("nexusUrl21");
		testedObject.setUserName("userName21");
		testedObject.setPassword("password21");
		testedObject.setSonarUrl("sonar_url21");
		testedObject.setSonarPassword("sonar_password21");
		testedObject.setSonarUserName("sonar_user21");
		ArrayList<SubModule> subModule = new ArrayList<>();
		SubModule sub = new SubModule();
		sub.setDefaultModule("module1");
		sub.setModuleName("module1");
		subModule.add(sub);
		testedObject.setSubModule(subModule);
		RunScript postBuildScript = new RunScript();
		testedObject.setPostBuildScript(postBuildScript);
		testedObject.setBuildtool("buildtool21");
		ArtifactToStage artifactToStage = new ArtifactToStage();
		testedObject.setArtifactToStage(artifactToStage);
		List<Module> modules = new ArrayList<>();
		testedObject.setModules(modules);
		assertEquals(modules, testedObject.getModules());
		assertEquals("sonar_url", testedObject.getSonarUrl());
		assertEquals("sonar_password", testedObject.getSonarPassword());
		assertEquals("sonar_user", testedObject.getSonarUserName());
		assertEquals(artifactToStage, testedObject.getArtifactToStage());
		assertEquals(postBuildScript, testedObject.getPostBuildScript());
		assertEquals("ejbModules21", testedObject.getEjbModules());
		assertEquals("userName21", testedObject.getUserName());
		assertEquals("javaModules21", testedObject.getJavaModules());

		assertEquals("nexusType21", testedObject.getNexusType());
		assertEquals("password21", testedObject.getPassword());
		assertEquals("webModules21", testedObject.getWebModules());
		assertEquals("buildtool21", testedObject.getBuildtool());
		assertEquals("nexusUrl21", testedObject.getNexusUrl());
		assertEquals(subModule, testedObject.getSubModule());

	}

	/**
	 * Test for method BuildInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInfo#BuildInfo()
	 * 
	 * 
	 */
	@Test
	public void testBuildInfo11() throws Throwable {
		BuildInfo testedObject = new BuildInfo();

		testedObject.setJavaModules("javaModules0");
		testedObject.setEjbModules("ejbModules0");
		testedObject.setWebModules("webModules0");
		testedObject.setNexusType("nexusType0");
		testedObject.setNexusUrl("nexusUrl0");
		testedObject.setUserName("userName0");
		testedObject.setPassword("password0");
		testedObject.setPostBuildScript((RunScript) null);
		testedObject.setBuildtool("buildtool0");
		testedObject.setArtifactToStage((ArtifactToStage) null);
		testedObject.setModules((List) null);
		assertEquals(null, testedObject.getModules());
		assertEquals(null, testedObject.getArtifactToStage());
		assertEquals(null, testedObject.getPostBuildScript());
		assertEquals("ejbModules0", testedObject.getEjbModules());
		assertEquals("userName0", testedObject.getUserName());
		assertEquals("javaModules0", testedObject.getJavaModules());
		assertEquals("nexusType0", testedObject.getNexusType());
		assertEquals("password0", testedObject.getPassword());
		assertEquals("webModules0", testedObject.getWebModules());
		assertEquals("buildtool0", testedObject.getBuildtool());
		assertEquals("nexusUrl0", testedObject.getNexusUrl());
	}

	/**
	 * Test for method BuildInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInfo#BuildInfo()
	 * 
	 * 
	 */
	@Test
	public void testBuildInfo12() throws Throwable {
		BuildInfo testedObject = new BuildInfo();
		assertEquals(null, testedObject.getModules());
		assertEquals(null, testedObject.getArtifactToStage());
		assertEquals(null, testedObject.getPostBuildScript());
		assertEquals(null, testedObject.getEjbModules());
		assertEquals(null, testedObject.getUserName());
		assertEquals(null, testedObject.getJavaModules());
		assertEquals(null, testedObject.getNexusType());
		assertEquals(null, testedObject.getPassword());
		assertEquals(null, testedObject.getWebModules());
		assertEquals(null, testedObject.getBuildtool());
		assertEquals(null, testedObject.getNexusUrl());
	}

	/**
	 * Test for method getArtifactToStage().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInfo#getArtifactToStage()
	 * 
	 * 
	 */
	@Test
	public void testGetArtifactToStage0() throws Throwable {
		BuildInfo testedObject = new BuildInfo();
		ArtifactToStage result = testedObject.getArtifactToStage();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Test for method getModules().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInfo#getModules()
	 * 
	 * 
	 */
	@Test
	public void testGetModules0() throws Throwable {
		BuildInfo testedObject = new BuildInfo();
		List result = testedObject.getModules();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Test for method getPostBuildScript().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInfo#getPostBuildScript()
	 * 
	 * 
	 */
	@Test
	public void testGetPostBuildScript0() throws Throwable {
		BuildInfo testedObject = new BuildInfo();
		RunScript result = testedObject.getPostBuildScript();
		assertEquals(null, result);
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java BuildInfoTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.buildInfo.BuildInfoTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return BuildInfo.class;
	}
}
