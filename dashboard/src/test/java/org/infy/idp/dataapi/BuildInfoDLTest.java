/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.infy.idp.AppContext;
import org.infy.idp.entities.BuildInfoDetails;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*This test case is used for build information*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class BuildInfoDLTest {

	
	
	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
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
			BuildInfoDetails buildInfoDetails=new BuildInfoDetails();
			buildInfoDetails.setAppid(1);
			buildInfoDetails.setBuildtime(1.0);
			buildInfoDetails.setBuildstatus("value");
			buildInfoDetails.setBuildid(1);
			buildInfoDetails.setLastcompletebuildid(1);
			buildInfoDetails.setLastsuccessfulbuildid(1);
			buildInfoDetails.setLastunstablebuildid(1);
			buildInfoDetails.setLastunsuccessfulbuildid(1);
			buildInfoDetails.setLastfailedbuildid(1);
			buildInfoDetails.setPipelinebuildno(1);
			buildInfoDetails.setScore(1.0);
			buildInfoDetails.setStagename("value");
			
			
			int returnValue=buildInfoDL.insertbuilddetails(buildInfoDetails);
			assertEquals(1, returnValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
