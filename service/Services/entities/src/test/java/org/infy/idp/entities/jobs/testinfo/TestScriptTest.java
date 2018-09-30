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

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.common.AntProperties;
import org.junit.Test;

/**
 * TestScriptTest is a test class for TestScript
 *
 * @see org.infy.idp.entities.jobs.testinfo.TestScript
 * 
 */
public class TestScriptTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TestScriptTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method TestScript().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestScript#TestScript()
	 * 
	 * 
	 */
	@Test
	public void testTestScriptValues() throws Throwable {
		TestScript testedObject = new TestScript();
		List<AntProperties> list = new ArrayList<AntProperties>();
		AntProperties antObj = new AntProperties();
		list.add(antObj);
		testedObject.setReportType("reportType12");
		testedObject.setPathOfReports("pathOfReports12");
		testedObject.setScriptType("scriptType12");
		testedObject.setPathOfFile("pathOfFile12");
		testedObject.setTargets("targets12");
		testedObject.setHost("host12");
		testedObject.setUserName("userName12");
		testedObject.setPassword("password12");
		testedObject.setScript("script12");
		testedObject.setPathToFiles("pathToFiles12");
		testedObject.setDestinationDir("destinationDir12");
		testedObject.setFlatternFilePath("flatternFilePath11");
		testedObject.setAntPropertiesArr(list);
		testedObject.setJavaOptions("javaOpt");
		testedObject.setSshKey("sshKey");
		testedObject.setSshPathToKey("SSHPath");

		assertEquals("host12", testedObject.getHost());
		assertEquals("script12", testedObject.getScript());
		assertEquals("reportType12", testedObject.getReportType());
		assertEquals("userName12", testedObject.getUserName());
		assertEquals("pathOfReports12", testedObject.getPathOfReports());
		assertEquals("pathOfFile12", testedObject.getPathOfFile());
		assertEquals("targets12", testedObject.getTargets());
		assertEquals("scriptType12", testedObject.getScriptType());
		assertEquals("password12", testedObject.getPassword());
		assertEquals("pathToFiles12", testedObject.getPathToFiles());
		assertEquals("destinationDir12", testedObject.getDestinationDir());
		assertEquals("flatternFilePath11", testedObject.getFlatternFilePath());
		assertEquals(list, testedObject.getAntPropertiesArr());
		assertEquals("javaOpt", testedObject.getJavaOptions());
		assertEquals("sshKey", testedObject.getSshKey());
		assertEquals("SSHPath", testedObject.getSshPathToKey());
	}

	/**
	 * Test for method TestScript().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestScript#TestScript()
	 * 
	 * 
	 */
	@Test
	public void testTestScriptValues2() throws Throwable {
		TestScript testedObject = new TestScript();
		List<AntProperties> list = new ArrayList<AntProperties>();
		AntProperties antObj = new AntProperties();
		list.add(antObj);
		testedObject.setReportType("reportType0");
		testedObject.setPathOfReports("pathOfReports0");
		testedObject.setScriptType("scriptType0");
		testedObject.setPathOfFile("pathOfFile0");
		testedObject.setTargets("targets0");
		testedObject.setHost("host0");
		testedObject.setUserName("userName0");
		testedObject.setPassword("password0");
		testedObject.setScript("script0");
		testedObject.setPathToFiles("pathToFiles0");
		testedObject.setDestinationDir("destinationDir0");
		testedObject.setFlatternFilePath((String) null);
		testedObject.setAntPropertiesArr(list);
		testedObject.setJavaOptions("javaOpt");
		testedObject.setSshKey("sshKey");
		testedObject.setSshPathToKey("SSHPath");

		assertEquals("host0", testedObject.getHost());
		assertEquals("script0", testedObject.getScript());
		assertEquals("reportType0", testedObject.getReportType());
		assertEquals("userName0", testedObject.getUserName());
		assertEquals("pathOfReports0", testedObject.getPathOfReports());
		assertEquals("pathOfFile0", testedObject.getPathOfFile());
		assertEquals("targets0", testedObject.getTargets());
		assertEquals("scriptType0", testedObject.getScriptType());
		assertEquals("password0", testedObject.getPassword());
		assertEquals("pathToFiles0", testedObject.getPathToFiles());
		assertEquals("destinationDir0", testedObject.getDestinationDir());
		assertNull(testedObject.getFlatternFilePath());
		assertEquals(list, testedObject.getAntPropertiesArr());
		assertEquals("javaOpt", testedObject.getJavaOptions());
		assertEquals("sshKey", testedObject.getSshKey());
		assertEquals("SSHPath", testedObject.getSshPathToKey());
	}

	/**
	 * Test for method TestScript().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TestScript#TestScript()
	 * 
	 * 
	 */
	@Test
	public void testTestScriptNull() throws Throwable {
		TestScript testedObject = new TestScript();
		assertNull(testedObject.getHost());
		assertNull(testedObject.getScript());
		assertNull(testedObject.getReportType());
		assertNull(testedObject.getUserName());
		assertNull(testedObject.getPathOfReports());
		assertNull(testedObject.getPathOfFile());
		assertNull(testedObject.getTargets());
		assertNull(testedObject.getScriptType());
		assertNull(testedObject.getPassword());
		assertNull(testedObject.getPathToFiles());
		assertNull(testedObject.getDestinationDir());
		assertNull(testedObject.getFlatternFilePath());
		assertNull(testedObject.getAntPropertiesArr());
		assertNull(testedObject.getJavaOptions());
		assertNull(testedObject.getSshKey());
		assertNull(testedObject.getSshPathToKey());
	}

	@Test
	public void testAntPropertiesValues() throws Throwable {
		TestScript testedObject = new TestScript();
		List<AntProperties> list = new ArrayList<AntProperties>();
		AntProperties antObj = new AntProperties();
		antObj.setAntKey("antKey");
		antObj.setAntValue("antValue");
		list.add(antObj);
		testedObject.setAntPropertiesArr(list);

		assertEquals("antKey", antObj.getAntKey());
		assertEquals("antValue", antObj.getAntValue());
		assertEquals(list, testedObject.getAntPropertiesArr());
	}

	@Test
	public void testAntPropertiesNull() throws Throwable {
		TestScript testedObject = new TestScript();
		List<AntProperties> list = new ArrayList<AntProperties>();
		AntProperties antObj = new AntProperties();
		list.add(antObj);
		testedObject.setAntPropertiesArr(list);

		assertNull(antObj.getAntKey());
		assertNull(antObj.getAntValue());
		assertEquals(list, testedObject.getAntPropertiesArr());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TestScriptTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.testInfo.TestScriptTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<TestScript> getTestedClass() {
		return TestScript.class;
	}
}
