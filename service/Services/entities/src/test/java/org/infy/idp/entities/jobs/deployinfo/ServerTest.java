/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * ServerTest is a test class for Server
 *
 * @see org.infy.idp.entities.jobs.deployinfo.Server
 * 
 */
public class ServerTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ServerTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method Server().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Server#Server()
	 * 
	 * 
	 */
	@Test
	public void testServer0() throws Throwable {
		Server testedObject = new Server();
		testedObject.setHost("host12");
		testedObject.setDbName("dbName12");
		testedObject.setHostUserName("hostUserName12");
		testedObject.setHostPassword("hostPassword12");
		testedObject.setDbOwner("dbOwner12");
		testedObject.setRepo("repo12");
		testedObject.setDatFilePath("datFilePath12");
		testedObject.setDbNameOfIndex("dbNameOfIndex12");
		testedObject.setHostName("hostName12");
		testedObject.setSrcPath("srcPath12");
		testedObject.setDestinationPath("destinationPath12");
		testedObject.setUserName("userName12");
		testedObject.setPassword("password12");
		testedObject.setServerName("serverName11");
		assertEquals("host12", testedObject.getHost());
		assertEquals("hostName12", testedObject.getHostName());
		assertEquals("dbName12", testedObject.getDbName());
		assertEquals("hostUserName12", testedObject.getHostUserName());
		assertEquals("hostPassword12", testedObject.getHostPassword());
		assertEquals("dbOwner12", testedObject.getDbOwner());
		assertEquals("repo12", testedObject.getRepo());
		assertEquals("srcPath12", testedObject.getSrcPath());
		assertEquals("password12", testedObject.getPassword());
		assertEquals("dbNameOfIndex12", testedObject.getDbNameOfIndex());
		assertEquals("userName12", testedObject.getUserName());
		assertEquals("datFilePath12", testedObject.getDatFilePath());
		assertEquals("serverName11", testedObject.getServerName());
		assertEquals("destinationPath12", testedObject.getDestinationPath());
		// No exception thrown

	}

	/**
	 * Test for method Server().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Server#Server()
	 * 
	 * 
	 */
	@Test
	public void testServer2() throws Throwable {
		Server testedObject = new Server();
		testedObject.setHost("host0");
		testedObject.setDbName("dbName0");
		testedObject.setHostUserName("hostUserName0");
		testedObject.setHostPassword("hostPassword0");
		testedObject.setDbOwner("dbOwner0");
		testedObject.setRepo("repo0");
		testedObject.setDatFilePath("datFilePath0");
		testedObject.setDbNameOfIndex("dbNameOfIndex0");
		testedObject.setHostName("hostName0");
		testedObject.setSrcPath("srcPath0");
		testedObject.setDestinationPath("destinationPath0");
		testedObject.setUserName("userName0");
		testedObject.setPassword("password0");
		testedObject.setServerName((String) null);
		assertEquals("host0", testedObject.getHost());
		assertEquals("hostName0", testedObject.getHostName());
		assertEquals("dbName0", testedObject.getDbName());
		assertEquals("hostUserName0", testedObject.getHostUserName());
		assertEquals("hostPassword0", testedObject.getHostPassword());
		assertEquals("dbOwner0", testedObject.getDbOwner());
		assertEquals("repo0", testedObject.getRepo());
		assertEquals("srcPath0", testedObject.getSrcPath());
		assertEquals("password0", testedObject.getPassword());
		assertEquals("dbNameOfIndex0", testedObject.getDbNameOfIndex());
		assertEquals("userName0", testedObject.getUserName());
		assertEquals("datFilePath0", testedObject.getDatFilePath());
		assertEquals(null, testedObject.getServerName());
		assertEquals("destinationPath0", testedObject.getDestinationPath());
		// No exception thrown

	}

	/**
	 * Test for method Server().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see Server#Server()
	 * 
	 * 
	 */
	@Test
	public void testServer3() throws Throwable {
		Server testedObject = new Server();
		assertEquals(null, testedObject.getHost());
		assertEquals(null, testedObject.getHostName());
		assertEquals(null, testedObject.getDbName());
		assertEquals(null, testedObject.getHostUserName());
		assertEquals(null, testedObject.getHostPassword());
		assertEquals(null, testedObject.getDbOwner());
		assertEquals(null, testedObject.getRepo());
		assertEquals(null, testedObject.getSrcPath());
		assertEquals(null, testedObject.getPassword());
		assertEquals(null, testedObject.getDbNameOfIndex());
		assertEquals(null, testedObject.getUserName());
		assertEquals(null, testedObject.getDatFilePath());
		assertEquals(null, testedObject.getServerName());
		assertEquals(null, testedObject.getDestinationPath());
		// No exception thrown

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ServerTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.deployInfo.ServerTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return Server.class;
	}
}
