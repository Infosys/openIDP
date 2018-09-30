/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * GitHubBrachModelTest is a test class for GitHubBrachModel
 *
 * @see org.infy.idp.entities.models.GitHubBrachModel
 * 
 */
public class GitHubBranchModelTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public GitHubBranchModelTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testGitHubBrachModelValues() throws Throwable {
		GitHubBrachModel testObj = new GitHubBrachModel("repoUrl", "username", "pwd", "projectUrl", "proxy", "port");
		testObj.setRepoUrl("repoUrl");
		testObj.setUsername("username");
		testObj.setPwd("pwd");
		testObj.setProjectUrl("projectUrl");
		testObj.setProxy("proxy");
		testObj.setPort("port");

		assertEquals("repoUrl", testObj.getRepoUrl());
		assertEquals("username", testObj.getUsername());
		assertEquals("pwd", testObj.getPwd());
		assertEquals("projectUrl", testObj.getProjectUrl());
		assertEquals("proxy", testObj.getProxy());
		assertEquals("port", testObj.getPort());
	}

	/**
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see GitHubBrachModel#GitHubBrachModel()
	 * 
	 * 
	 */
	@Test
	public void testGitHubBrachModelNull() throws Throwable {
		GitHubBrachModel testedObject = new GitHubBrachModel();
		assertNull(testedObject.getUsername());
		assertNull(testedObject.getPort());
		assertNull(testedObject.getProjectUrl());
		assertNull(testedObject.getProxy());
		assertNull(testedObject.getPwd());
		assertNull(testedObject.getRepoUrl());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java GitHubBrachModelTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.models.GitHubBrachModelTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<GitHubBrachModel> getTestedClass() {
		return GitHubBrachModel.class;
	}
}
