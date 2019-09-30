/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertNull;

import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;


/*This test case is used for Postgre SQL*/
public class IDPPostGreSqlDbContextTest {


	
	@InjectMocks
	private IDPPostGreSqlDbContext idppostGreSqlDbContext;
	
	@Spy
	private ConfigurationManager configmanager;
	
	/**
	 * Constructor for test class.
	 *
	 * @author Parasoft Jtest 9.6
	 */
	public IDPPostGreSqlDbContextTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method createDataSource().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see PostGreSqlDbContext#createDataSource()
	 * @author Parasoft Jtest 9.6
	 * 
	 */
	@Test
	public void testCreateDataSource0() throws Throwable {
		
	}

	/**
	 * Test for method init().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see PostGreSqlDbContext#init()
	 * @author Parasoft Jtest 9.6
	 * 
	 */
	@Test
	public void testInit0() throws Throwable {
	
	}

	/**
	 * Test for method PostGreSqlDbContext().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see PostGreSqlDbContext#PostGreSqlDbContext()
	 * @author Parasoft Jtest 9.6
	 * 
	 */
	@Test
	public void testPostGreSqlDbContext0() throws Throwable {


	}


	
	
	@Test
	public void testGetConnection_CheckNull() throws Throwable{
		
		configmanager.setUrl("jdbc:postgresql://dummyuser:5400/IDP");
		
		assertNull(idppostGreSqlDbContext.getConnection());
		
	}
	
	/**
	 * Used to set up the test. This method is called by JUnit before each of
	 * the tests are executed.
	 * 
	 * @author Parasoft Jtest 9.6
	 */
	@Before
	public void setUp() throws Exception {
		/*
		 * Add any necessary initialization code here (e.g., open a socket).
		 * Call Repository.putTemporary() to provide initialized instances of
		 * objects to be used when testing.
		 */
		//super.setUp();
		// jtest.Repository.putTemporary("name", object);
		
try {
			
			configmanager.setPostgresqldatabase("IDP");
			configmanager.setPostgresqlinitialsize("5");
			configmanager.setPostgresqlpassword("root");
			configmanager.setPostgresqlschemaname("public");
			configmanager.setPostgresqlusername("postgres");
			configmanager.setUrl("jdbc:postgresql://dummyuser:5432/IDP");
			
			
			
		
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Used to clean up after the test. This method is called by JUnit after
	 * each of the tests have been completed.
	 * 
	 * @author Parasoft Jtest 9.6
	 */
/*	@After
	public void tearDown() throws Exception {
		try {
			
			 * Add any necessary cleanup code here (e.g., close a socket).
			 
		} finally {
			super.tearDown();
		}
	}
*/
	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java PostGreSqlDbContextTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 * @author Parasoft Jtest 9.6
	 */
	public static void main(String[] args) {
		// junit.textui.TestRunner will print the test results to stdout.

		org.junit.runner.JUnitCore.main("org.infy.idp.datapai.base.PostGreSqlDbContextTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * @author Parasoft Jtest 9.6
	 */
	public Class getTestedClass() {
		return PostGreSqlDbContext.class;
	}


}
