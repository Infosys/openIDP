/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.configure;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * PostGreSqlDbContextTest is a test class for PostGreSqlDbContext
 *
 * @see org.infy.idp.dataapi.PostGreSqlDbContext
 */
@RunWith(MockitoJUnitRunner.class)
public class PostGresSqlDbContextTest {

	@Mock
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	private ConfigurationManager configmanager;

	@Test
	public void testGetConnection_CheckNull() throws Throwable {

		configmanager.setUrl("jdbc:postgresql://localhost:5400/IDP");
		assertNull(postGreSqlDbContext.getConnection());

	}

	/**
	 * Used to set up the test. This method is called by JUnit before each of the
	 * tests are executed.
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		/*
		 * Add any necessary initialization code here (e.g., open a socket). Call
		 * Repository.putTemporary() to provide initialized instances of objects to be
		 * used when testing.
		 */

		try {
			configmanager.setPostgresqldatabase("IDP");
			configmanager.setPostgresqlinitialsize("5");
			configmanager.setPostgresqlpassword("root");
			configmanager.setPostgresqlschemaname("public");
			configmanager.setPostgresqlusername("postgres");
			configmanager.setUrl("jdbc:postgresql://localhost:5432/IDP");

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
