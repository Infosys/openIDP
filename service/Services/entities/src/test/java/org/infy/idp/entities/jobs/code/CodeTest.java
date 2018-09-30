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

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.common.RunScript;
import org.junit.Test;

/**
 * CodeTest is a test class for Code
 *
 * @see org.infy.idp.entities.jobs.code.Code
 * 
 */
public class CodeTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public CodeTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Code().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Code#Code()
	 * 
	 * 
	 */
	@Test
	public void testCodeValues() throws Throwable {
		Code testedObject = new Code();
		RunScript rsObj = new RunScript();
		List<RunScript> buildScript = new ArrayList<RunScript>();
		buildScript.add(rsObj);
		testedObject.setBuildScript(buildScript);
		testedObject.setCategory("category11");
		testedObject.setTechnology("technology11");
		testedObject.setConnType("connType");
		testedObject.setPassword("password");
		testedObject.setPort("port");
		testedObject.setUsername("username");

		Scm scmObj = new Scm();
		List<Scm> scm = new ArrayList<Scm>();
		scm.add(scmObj);
		testedObject.setScm(scm);
		JobParam jpObj = new JobParam();
		List<JobParam> jobParam = new ArrayList<>();
		jobParam.add(jpObj);
		testedObject.setJobParam(jobParam);
		testedObject.setSubApplication("subApplication");
		testedObject.setServerName("serverName");

		assertEquals(buildScript, testedObject.getBuildScript());
		assertEquals(scm, testedObject.getScm());
		assertEquals("category11", testedObject.getCategory());
		assertEquals("technology11", testedObject.getTechnology());
		assertEquals("technology11", testedObject.getTechnology());
		assertEquals("subApplication", testedObject.getSubApplication());
		assertEquals(jobParam, testedObject.getJobParam());
		assertEquals("connType", testedObject.getConnType());
		assertEquals("password", testedObject.getPassword());
		assertEquals("port", testedObject.getPort());
		assertEquals("username", testedObject.getUsername());
		assertEquals("serverName", testedObject.getServerName());
	}

	/**
	 * Test for method Code().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Code#Code()
	 * 
	 * 
	 */
	@Test
	public void testCodeValues2() throws Throwable {
		Code testedObject = new Code();
		testedObject.setBuildScript((List<RunScript>) null);
		testedObject.setCategory("category0");
		testedObject.setTechnology("technology0");
		testedObject.setScm((List<Scm>) null);
		assertNull(testedObject.getBuildScript());
		assertNull(testedObject.getScm());
		assertEquals("category0", testedObject.getCategory());
		assertEquals("technology0", testedObject.getTechnology());
		assertNull(testedObject.getSubApplication());
		assertNull(testedObject.getConnType());
		assertNull(testedObject.getPassword());
		assertNull(testedObject.getUsername());
		assertNull(testedObject.getServerName());
	}

	/**
	 * Test for method Code().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Code#Code()
	 * 
	 * 
	 */
	@Test
	public void testCodeNull() throws Throwable {
		Code testedObject = new Code();
		assertNull(testedObject.getBuildScript());
		assertNull(testedObject.getScm());
		assertNull(testedObject.getCategory());
		assertNull(testedObject.getTechnology());
		assertNull(testedObject.getSubApplication());
		assertNull(testedObject.getJobParam());
	}

	/**
	 * Test for method getBuildScript().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Code#getBuildScript()
	 * 
	 * 
	 */
	@Test
	public void testGetBuildScriptNull() throws Throwable {
		Code testedObject = new Code();
		List<RunScript> result = testedObject.getBuildScript();
		assertNull(result);
	}

	/**
	 * Test for method getScm().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Code#getScm()
	 * 
	 * 
	 */
	@Test
	public void testGetScmNull() throws Throwable {
		Code testedObject = new Code();
		List<Scm> result = testedObject.getScm();
		assertNull(result);
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java CodeTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.code.CodeTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<Code> getTestedClass() {
		return Code.class;
	}
}
