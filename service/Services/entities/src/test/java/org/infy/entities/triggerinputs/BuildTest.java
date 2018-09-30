/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BuildTest  {

	/**
	 * Constructor for test class.
	 *
	 */
	public BuildTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Build().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Build#Build()
	 * 
	 * 
	 */
	@Test
	public void testBuild0() throws Throwable {
		Build testedObject = new Build();
		testedObject.setGitBranches((List) null);
		testedObject.setModules((List) null);
		assertEquals(null, testedObject.getGitBranches()); 
		assertEquals(null, testedObject.getModules()); 
		// No exception thrown
	}

	/**
	 * Test for method getGitBranches().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Build#getGitBranches()
	 * 
	 * 
	 */
	@Test
	public void testGetGitBranches0() throws Throwable {
		Build testedObject = new Build();
		List result = testedObject.getGitBranches();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	/**
	 * Test for method getModules().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Build#getModules()
	 * 
	 * 
	 */
	@Test
	public void testGetModules0() throws Throwable {
		Build testedObject = new Build();
		List result = testedObject.getModules();
		assertEquals(null, result); 
		// No exception thrown
		
	}

	@Test
	public void testBuild() throws Throwable {
		Build testObject = new Build();
		testObject.setJobType("build");
		testObject.setWorkEnv("dev");
		ArrayList<ApproveBuild> listBuild = new ArrayList<>();
		ApproveBuild appBuild = new ApproveBuild();
		appBuild.setApprBuildNo("build1");
		appBuild.setEnvName("sit");
		appBuild.setModuleList("module1");
		appBuild.setReleaseIdentifier("April2");
		appBuild.setUserInfo("userName");
		listBuild.add(appBuild);
		testObject.setApproveBuild(listBuild);
		SubModule subModule = new SubModule();
		subModule.setDefaultModule("module");
		subModule.setModuleName("module");
		ArrayList<SubModule> listModule = new ArrayList<>();
		listModule.add(subModule);
		testObject.setSubModules(listModule);
		ArrayList<String> listEnv = new ArrayList<>();
		listEnv.add("dev");
		listEnv.add("prod");

		testObject.setBuildEnv(listEnv);
		testObject.setGitTag("build1");
		testObject.setCodeAnalysis("sonar");
		testObject.setUnitTesting("pmd");
		testObject.setCast("sap");

		assertEquals("build", testObject.getJobType());
		assertEquals("dev", testObject.getWorkEnv());
		assertEquals(listBuild, testObject.getApproveBuild());
		assertEquals(listModule, testObject.getSubModules());
		assertEquals(listEnv, testObject.getBuildEnv());
		assertEquals("build1", testObject.getGitTag());
		assertEquals("sonar", testObject.getCodeAnalysis());
		assertEquals("pmd", testObject.getUnitTesting());
		assertEquals("sap", testObject.getCast());

	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java BuildTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.BuildTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return Build.class;
	}
}
