/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.testinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * TestTest is a test class for Test
 *
 * @see org.infy.idp.entities.jobs.testinfo.Test
 * 
 */
public class TestTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TestTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for set and get function()
	 * 
	 * 
	 */
	@Test
	public void testTestValues() throws Exception {

		org.infy.idp.entities.jobs.testinfo.Test test = new org.infy.idp.entities.jobs.testinfo.Test();
		test.setAuthenticationCode("temp");
		test.setBrowserName("browserName");
		test.setCustomParameters("customParameters");
		test.setDataPool("dataPool");
		test.setDomain("domain");
		test.setEnvironment("environment");
		test.setExternalFilePath("externalFilePath");
		test.setFolderUrl("folderUrl");
		test.setFrameWork("frameWork");
		test.setFullIteration("fullIteration");
		Integer iteration = new Integer(1);
		test.setIteration(1);
		test.setParameters("parameters");
		test.setPassword("password");
		test.setPath("path");
		test.setProjectName("projectName");
		test.setProjectLocation("projectLocation");
		test.setRitVirtulizationVersion("ritVirtulizationVersion");
		test.setRootDir("rootDir");
		test.setScriptPath("scriptPath");
		test.setServerName("serverName");
		test.setServerUrl("serverUrl");
		test.setServiceName("serviceName");
		test.setStubName("stubName");
		test.setStubVersion("stubVersion");
		test.setTargets("targets");
		test.setTestCase("testCase");
		test.setTestCategory("testCategory");
		test.setTestPlan("testPlan");
		test.setTestSuiteName("testSuiteName");
		test.setTestTypeName("testTypeName");
		Integer timeout = new Integer(100);
		test.setTimeout(100);
		test.setUserName("userName");
		test.setVersion("version");
		test.setArg("arg");
		test.setBuildDefId("BuildId");
		test.setRPTworkspace("D:/rPTWorkspace");
		test.setTestScript("testscript");
		test.setUrl("http://url");
		test.setSlaveName("Slave");

		assertEquals("temp", test.getAuthenticationCode());
		assertEquals("browserName", test.getBrowserName());
		assertEquals("customParameters", test.getCustomParameters());
		assertEquals("dataPool", test.getDataPool());
		assertEquals("domain", test.getDomain());
		assertEquals("environment", test.getEnvironment());
		assertEquals("externalFilePath", test.getExternalFilePath());
		assertEquals("folderUrl", test.getFolderUrl());
		assertEquals("frameWork", test.getFrameWork());
		assertEquals("fullIteration", test.getFullIteration());
		assertEquals(iteration, test.getIteration());
		assertEquals("parameters", test.getParameters());
		assertEquals("password", test.getPassword());
		assertEquals("path", test.getPath());
		assertEquals("projectLocation", test.getProjectLocation());
		assertEquals("projectName", test.getProjectName());
		assertEquals("ritVirtulizationVersion", test.getRitVirtulizationVersion());
		assertEquals("rootDir", test.getRootDir());
		assertEquals("scriptPath", test.getScriptPath());
		assertEquals("serverName", test.getServerName());
		assertEquals("serverUrl", test.getServerUrl());
		assertEquals("serviceName", test.getServiceName());
		assertEquals("stubName", test.getStubName());
		assertEquals("stubVersion", test.getStubVersion());
		assertEquals("targets", test.getTargets());
		assertEquals("testCase", test.getTestCase());
		assertEquals("testCategory", test.getTestCategory());
		assertEquals("testPlan", test.getTestPlan());
		assertEquals("testSuiteName", test.getTestSuiteName());
		assertEquals("testTypeName", test.getTestTypeName());
		assertEquals(timeout, test.getTimeout());
		assertEquals("userName", test.getUserName());
		assertEquals("version", test.getVersion());
		assertEquals("arg", test.getArg());
		assertEquals("BuildId", test.getBuildDefId());
		assertEquals("D:/rPTWorkspace", test.getRPTworkspace());
		assertEquals("testscript", test.getTestScript());
		assertEquals("http://url", test.getUrl());
		assertEquals("Slave", test.getSlaveName());

	}

	@Test
	public void testTestNullValues() throws Exception {

		org.infy.idp.entities.jobs.testinfo.Test test = new org.infy.idp.entities.jobs.testinfo.Test();
		test.setAuthenticationCode(null);
		test.setBrowserName(null);
		test.setCustomParameters(null);
		test.setDataPool(null);
		test.setDomain(null);
		test.setEnvironment(null);
		test.setExternalFilePath(null);
		test.setFolderUrl(null);
		test.setFrameWork(null);
		test.setFullIteration(null);
		test.setIteration(null);
		test.setParameters(null);
		test.setPassword(null);
		test.setPath(null);
		test.setProjectName(null);
		test.setProjectLocation(null);
		test.setRitVirtulizationVersion(null);
		test.setRootDir(null);
		test.setScriptPath(null);
		test.setServerName(null);
		test.setServerUrl(null);
		test.setServerUrl(null);
		test.setServiceName(null);
		test.setStubName(null);
		test.setStubVersion(null);
		test.setTargets(null);
		test.setTestCase(null);
		test.setTestCategory(null);
		test.setTestPlan(null);
		test.setTestSuiteName(null);
		test.setTestTypeName(null);
		test.setTimeout(null);
		test.setUserName(null);
		test.setVersion(null);
		test.setArg(null);
		test.setBuildDefId(null);
		test.setRPTworkspace(null);
		test.setTestScript(null);
		test.setUrl(null);
		test.setSlaveName(null);

		assertNull(test.getAuthenticationCode());
		assertNull(test.getBrowserName());
		assertNull(test.getCustomParameters());
		assertNull(test.getDataPool());
		assertNull(test.getDomain());
		assertNull(test.getEnvironment());
		assertNull(test.getExternalFilePath());
		assertNull(test.getFolderUrl());
		assertNull(test.getFrameWork());
		assertNull(test.getFullIteration());
		assertNull(test.getIteration());
		assertNull(test.getParameters());
		assertNull(test.getPassword());
		assertNull(test.getPath());
		assertNull(test.getProjectLocation());
		assertNull(test.getProjectName());
		assertNull(test.getRitVirtulizationVersion());
		assertNull(test.getRootDir());
		assertNull(test.getScriptPath());
		assertNull(test.getServerName());
		assertNull(test.getServerUrl());
		assertNull(test.getServerUrl());
		assertNull(test.getServiceName());
		assertNull(test.getStubName());
		assertNull(test.getStubVersion());
		assertNull(test.getTargets());
		assertNull(test.getTestCase());
		assertNull(test.getTestCategory());
		assertNull(test.getTestPlan());
		assertNull(test.getTestSuiteName());
		assertNull(test.getTestTypeName());
		assertNull(test.getTimeout());
		assertNull(test.getUserName());
		assertNull(test.getVersion());
		assertNull(test.getArg());
		assertNull(test.getBuildDefId());
		assertNull(test.getRPTworkspace());
		assertNull(test.getTestScript());
		assertNull(test.getUrl());
		assertNull(test.getSlaveName());

	}

	@Test
	public void testTestNull() throws Exception {

		org.infy.idp.entities.jobs.testinfo.Test test = new org.infy.idp.entities.jobs.testinfo.Test();

		assertNull(test.getAuthenticationCode());
		assertNull(test.getBrowserName());
		assertNull(test.getCustomParameters());
		assertNull(test.getDataPool());
		assertNull(test.getDomain());
		assertNull(test.getEnvironment());
		assertNull(test.getExternalFilePath());
		assertNull(test.getFolderUrl());
		assertNull(test.getFrameWork());
		assertNull(test.getFullIteration());
		assertNull(test.getIteration());
		assertNull(test.getParameters());
		assertNull(test.getPassword());
		assertNull(test.getPath());
		assertNull(test.getProjectLocation());
		assertNull(test.getProjectName());
		assertNull(test.getRitVirtulizationVersion());
		assertNull(test.getRootDir());
		assertNull(test.getScriptPath());
		assertNull(test.getServerName());
		assertNull(test.getServerUrl());
		assertNull(test.getServerUrl());
		assertNull(test.getServiceName());
		assertNull(test.getStubName());
		assertNull(test.getStubVersion());
		assertNull(test.getTargets());
		assertNull(test.getTestCase());
		assertNull(test.getTestCategory());
		assertNull(test.getTestPlan());
		assertNull(test.getTestSuiteName());
		assertNull(test.getTestTypeName());
		assertNull(test.getTimeout());
		assertNull(test.getUserName());
		assertNull(test.getVersion());
		assertNull(test.getArg());
		assertNull(test.getBuildDefId());
		assertNull(test.getRPTworkspace());
		assertNull(test.getTestScript());
		assertNull(test.getUrl());
		assertNull(test.getSlaveName());

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TestTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.testInfo.TestTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<Test> getTestedClass() {
		return Test.class;
	}
}
