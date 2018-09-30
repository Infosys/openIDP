/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.infy.idp.entities.jobs.common.Migration;
import org.junit.Test;

/**
 * MigrationTest is a test class for Migration
 *
 * @see org.infy.idp.entities.jobs.Migration
 * 
 */
public class MigrationTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public MigrationTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Migration().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Migration#Migration()
	 * 
	 * 
	 */
	@Test
	public void testMigrationValues() throws Throwable {
		Migration testedObject = new Migration();
		testedObject.setFileName("fileName12");
		testedObject.setRootPath("rootPath12");
		testedObject.setEnvPath("envPath12");
		testedObject.setAppPassword("appPassword12");
		testedObject.setUploadOrDownload("uploadOrDownload12");
		testedObject.setHostName("hostName12");
		testedObject.setSrcPath("srcPath12");
		testedObject.setProjName("projName12");
		testedObject.setUserName("userName12");
		testedObject.setPassword("password12");
		testedObject.setEnvFile("envFile12");
		testedObject.setDbNameOfIndex("dbNameOfIndex12");
		testedObject.setDbPassword("dbPassword12");
		testedObject.setFtpLocation("ftpLocation12");
		testedObject.setFmxLocation("fmxLocation12");
		testedObject.setCtlName("ctlName12");
		testedObject.setPropertyFile("propertyFile11");

		assertEquals("hostName12", testedObject.getHostName());
		assertEquals("fileName12", testedObject.getFileName());
		assertEquals("password12", testedObject.getPassword());
		assertEquals("appPassword12", testedObject.getAppPassword());
		assertEquals("rootPath12", testedObject.getRootPath());
		assertEquals("projName12", testedObject.getProjName());
		assertEquals("ftpLocation12", testedObject.getFtpLocation());
		assertEquals("envPath12", testedObject.getEnvPath());
		assertEquals("dbPassword12", testedObject.getDbPassword());
		assertEquals("envFile12", testedObject.getEnvFile());
		assertEquals("propertyFile11", testedObject.getPropertyFile());
		assertEquals("fmxLocation12", testedObject.getFmxLocation());
		assertEquals("srcPath12", testedObject.getSrcPath());
		assertEquals("userName12", testedObject.getUserName());
		assertEquals("dbNameOfIndex12", testedObject.getDbNameOfIndex());
		assertEquals("ctlName12", testedObject.getCtlName());
		assertEquals("uploadOrDownload12", testedObject.getUploadOrDownload());
	}

	/**
	 * Test for method Migration().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Migration#Migration()
	 * 
	 * 
	 */
	@Test
	public void testMigrationValues2() throws Throwable {
		Migration testedObject = new Migration();
		testedObject.setFileName("fileName0");
		testedObject.setRootPath("rootPath0");
		testedObject.setEnvPath("envPath0");
		testedObject.setAppPassword("appPassword0");
		testedObject.setUploadOrDownload("uploadOrDownload0");
		testedObject.setHostName("hostName0");
		testedObject.setSrcPath("srcPath0");
		testedObject.setProjName("projName0");
		testedObject.setUserName("userName0");
		testedObject.setPassword("password0");
		testedObject.setEnvFile("envFile0");
		testedObject.setDbNameOfIndex("dbNameOfIndex0");
		testedObject.setDbPassword("dbPassword0");
		testedObject.setFtpLocation("ftpLocation0");
		testedObject.setFmxLocation("fmxLocation0");
		testedObject.setCtlName("ctlName0");
		testedObject.setPropertyFile((String) null);
		assertEquals("hostName0", testedObject.getHostName());
		assertEquals("fileName0", testedObject.getFileName());
		assertEquals("password0", testedObject.getPassword());
		assertEquals("appPassword0", testedObject.getAppPassword());
		assertEquals("rootPath0", testedObject.getRootPath());
		assertEquals("projName0", testedObject.getProjName());
		assertEquals("ftpLocation0", testedObject.getFtpLocation());
		assertEquals("envPath0", testedObject.getEnvPath());
		assertEquals("dbPassword0", testedObject.getDbPassword());
		assertEquals("envFile0", testedObject.getEnvFile());
		assertNull(testedObject.getPropertyFile());
		assertEquals("fmxLocation0", testedObject.getFmxLocation());
		assertEquals("srcPath0", testedObject.getSrcPath());
		assertEquals("userName0", testedObject.getUserName());
		assertEquals("dbNameOfIndex0", testedObject.getDbNameOfIndex());
		assertEquals("ctlName0", testedObject.getCtlName());
		assertEquals("uploadOrDownload0", testedObject.getUploadOrDownload());
	}

	/**
	 * Test for method Migration().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Migration#Migration()
	 * 
	 * 
	 */
	@Test
	public void testMigrationNull() throws Throwable {
		Migration testedObject = new Migration();

		assertNull(testedObject.getHostName());
		assertNull(testedObject.getFileName());
		assertNull(testedObject.getPassword());
		assertNull(testedObject.getAppPassword());
		assertNull(testedObject.getRootPath());
		assertNull(testedObject.getProjName());
		assertNull(testedObject.getFtpLocation());
		assertNull(testedObject.getEnvPath());
		assertNull(testedObject.getDbPassword());
		assertNull(testedObject.getEnvFile());
		assertNull(testedObject.getPropertyFile());
		assertNull(testedObject.getFmxLocation());
		assertNull(testedObject.getSrcPath());
		assertNull(testedObject.getUserName());
		assertNull(testedObject.getDbNameOfIndex());
		assertNull(testedObject.getCtlName());
		assertNull(testedObject.getUploadOrDownload());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java MigrationTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.MigrationTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<Migration> getTestedClass() {
		return Migration.class;
	}
}
