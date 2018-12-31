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

		// Add Jira ALM
//		testedObject.setJiraProjKey("jiraProjKey");
//		testedObject.setJiraAssigneeName("jiraAssigneeName");

		testedObject.setJavaModules("javaModules");
		testedObject.setEjbModules("ejbModules");
		testedObject.setWebModules("webModules");
		testedObject.setNexusType("nexusType");
		testedObject.setNexusUrl("nexusUrl");
		testedObject.setUserName("userName");
		testedObject.setPassword("password");
		testedObject.setSonarUrl("sonar_url");
		testedObject.setSonarPassword("sonar_password");
		testedObject.setSonarUserName("sonar_user");
		ArrayList<SubModule> subModule = new ArrayList<>();
		SubModule sub = new SubModule();
		sub.setDefaultModule("module1");
		sub.setModuleName("module1");
		subModule.add(sub);
		testedObject.setSubModule(subModule);
		RunScript postBuildScript = new RunScript();
		testedObject.setPostBuildScript(postBuildScript);
		testedObject.setBuildtool("buildtool");
		ArtifactToStage artifactToStage = new ArtifactToStage();
		testedObject.setArtifactToStage(artifactToStage);
		List<Module> modules = new ArrayList<>();
		testedObject.setModules(modules);
//		CastAnalysis castObj = new CastAnalysis();
//		testedObject.setCastAnalysis(castObj);
		assertEquals(modules, testedObject.getModules());
		assertEquals("sonar_url", testedObject.getSonarUrl());
		assertEquals("sonar_password", testedObject.getSonarPassword());
		assertEquals("sonar_user", testedObject.getSonarUserName());
		assertEquals(artifactToStage, testedObject.getArtifactToStage());
		assertEquals(postBuildScript, testedObject.getPostBuildScript());
		assertEquals("ejbModules", testedObject.getEjbModules());
		assertEquals("userName", testedObject.getUserName());
		assertEquals("javaModules", testedObject.getJavaModules());

		assertEquals("nexusType", testedObject.getNexusType());
		assertEquals("password", testedObject.getPassword());
		assertEquals("webModules", testedObject.getWebModules());
		assertEquals("buildtool", testedObject.getBuildtool());
		assertEquals("nexusUrl", testedObject.getNexusUrl());
		assertEquals(subModule, testedObject.getSubModule());
//		assertEquals("jiraProjKey", testedObject.getJiraProjKey());
//		assertEquals("jiraAssigneeName", testedObject.getJiraAssigneeName());
//		assertEquals(castObj, testedObject.getCastAnalysis());
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
//		testedObject.setCastAnalysis(null);
//		testedObject.setJiraProjKey("jiraProjKey");
		
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
//		assertNull(testedObject.getCastAnalysis());
//		assertEquals("jiraProjKey", testedObject.getJiraProjKey());
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
//		assertEquals(null, testedObject.getJiraProjKey());
//		assertEquals(null, testedObject.getJiraAssigneeName());
//		assertNull(testedObject.getCastAnalysis());
		
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
	 * Test for method getCastAnalysis().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInfo#getCastAnalysis()
	 * 
	 * 
	 */
//	@Test
//	public void testGetCastAnalysis() throws Throwable {
//		BuildInfo testedObject = new BuildInfo();
//		CastAnalysis result = testedObject.getCastAnalysis();
//		assertNull(result);
//		// No exception thrown
//	}

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
