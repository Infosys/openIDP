/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.sql.*;

import org.infy.idp.dataapi.PostGreSqlDbContext;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

/*This test case is used for build information*/
@RunWith(MockitoJUnitRunner.class)
public class BuildInfoDLTest {

	
	
	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;
	
	@InjectMocks
	private BuildInfoDL buildInfoDL;
	
//	@Before
//	public void setUp() throws Exception {
//		assertNotNull(postGreSqlDbContext);
//		Mockito.when(c.prepareStatement(Mockito.anyString())).thenReturn(stmt);
//		Mockito.when(postGreSqlDbContext.getConnection()).thenReturn(c);
//		
//		
//		Mockito.when(stmt.executeQuery()).thenReturn(rs);
//	}
	
	@Before
	public void setUp() throws Exception {

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void insertbuilddetailsTest() throws Throwable {
		try {
			int returnValue=buildInfoDL.insertbuilddetails(1,1.0,"buildstatus",1,1,1,1,1,1,1,1.0,"stagename");
			assertEquals(1, returnValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
