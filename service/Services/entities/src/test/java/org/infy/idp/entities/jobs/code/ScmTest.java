/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.code;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * ScmTest is a test class for Scm
 *
 * @see org.infy.idp.entities.jobs.code.Scm
 * 
 */
public class ScmTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ScmTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Scm().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Scm#Scm()
	 * 
	 * 
	 */
	@Test
	public void testScmValues() throws Throwable {
		Scm testedObject = new Scm();
		testedObject.setExclude("exclude12");
		testedObject.setVersion("version12");
		testedObject.setHostName("hostName12");
		testedObject.setPort("port12");
		testedObject.setServer("server12");
		testedObject.setType("type12");
		testedObject.setUrl("url12");
		testedObject.setUserName("userName12");
		testedObject.setPassword("password12");
		testedObject.setRepositoryBrowser("repositoryBrowser12");
		testedObject.setBrowserUrl("browserUrl12");
		testedObject.setProjectName("projectName12");
		testedObject.setBranch("branch12");
		testedObject.setProjPath("projPath12");
		testedObject.setModuleName("moduleName12");
		testedObject.setClearcaseType("clearcaseType12");
		testedObject.setVobName("vobName12");
		testedObject.setSnapshotViewName("snapshotViewName12");
		testedObject.setConfigSpec("configSpec12");
		testedObject.setDevelopmentStreamName("developmentStreamName12");
		testedObject.setBuildConfiguration("buildConfiguration12");
		testedObject.setBuildDefinition("buildDefinition12");
		testedObject.setRepositoryWorkspace("repositoryWorkspace12");
		testedObject.setProjArea("projArea11");
		testedObject.setAppRepo("appRepo");
		testedObject.setDeployRepo("deploYRepo");
		testedObject.setTestRepo("testRepo");
		testedObject.setProxy("proxy");
		testedObject.setProxyPort("proxyPort");

		assertEquals("exclude12", testedObject.getExclude());
		assertEquals("password12", testedObject.getPassword());
		assertEquals("clearcaseType12", testedObject.getClearcaseType());
		assertEquals("vobName12", testedObject.getVobName());
		assertEquals("version12", testedObject.getVersion());
		assertEquals("userName12", testedObject.getUserName());
		assertEquals("configSpec12", testedObject.getConfigSpec());
		assertEquals("moduleName12", testedObject.getModuleName());
		assertEquals("projPath12", testedObject.getProjPath());
		assertEquals("url12", testedObject.getUrl());
		assertEquals("branch12", testedObject.getBranch());
		assertEquals("projArea11", testedObject.getProjArea());
		assertEquals("server12", testedObject.getServer());
		assertEquals("projectName12", testedObject.getProjectName());
		assertEquals("browserUrl12", testedObject.getBrowserUrl());
		assertEquals("repositoryBrowser12", testedObject.getRepositoryBrowser());
		assertEquals("developmentStreamName12", testedObject.getDevelopmentStreamName());
		assertEquals("buildConfiguration12", testedObject.getBuildConfiguration());
		assertEquals("buildDefinition12", testedObject.getBuildDefinition());
		assertEquals("repositoryWorkspace12", testedObject.getRepositoryWorkspace());
		assertEquals("snapshotViewName12", testedObject.getSnapshotViewName());
		assertEquals("type12", testedObject.getType());
		assertEquals("hostName12", testedObject.getHostName());
		assertEquals("port12", testedObject.getPort());
		assertEquals("appRepo", testedObject.getAppRepo());
		assertEquals("deploYRepo", testedObject.getDeployRepo());
		assertEquals("testRepo", testedObject.getTestRepo());
		assertEquals("proxy", testedObject.getProxy());
		assertEquals("proxyPort", testedObject.getProxyPort());
	}

	/**
	 * Test for method Scm().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Scm#Scm()
	 * 
	 * 
	 */
	@Test
	public void testScmNull() throws Throwable {
		Scm testedObject = new Scm();
		assertNull(testedObject.getExclude());
		assertNull(testedObject.getPassword());
		assertNull(testedObject.getClearcaseType());
		assertNull(testedObject.getVobName());
		assertNull(testedObject.getVersion());
		assertNull(testedObject.getUserName());
		assertNull(testedObject.getConfigSpec());
		assertNull(testedObject.getModuleName());
		assertNull(testedObject.getProjPath());
		assertNull(testedObject.getUrl());
		assertNull(testedObject.getBranch());
		assertNull(testedObject.getProjArea());
		assertNull(testedObject.getServer());
		assertNull(testedObject.getProjectName());
		assertNull(testedObject.getBrowserUrl());
		assertNull(testedObject.getRepositoryBrowser());
		assertNull(testedObject.getDevelopmentStreamName());
		assertNull(testedObject.getBuildConfiguration());
		assertNull(testedObject.getBuildDefinition());
		assertNull(testedObject.getRepositoryWorkspace());
		assertNull(testedObject.getSnapshotViewName());
		assertNull(testedObject.getType());
		assertNull(testedObject.getHostName());
		assertNull(testedObject.getPort());
		assertNull(testedObject.getAppRepo());
		assertNull(testedObject.getDeployRepo());
		assertNull(testedObject.getTestRepo());
		assertNull(testedObject.getProxy());
		assertNull(testedObject.getProxyPort());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ScmTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.code.ScmTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return Scm.class;
	}
}
