/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;

import org.infy.idp.AppContext;
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

/*This test case is used for jira*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class InsertFetchJiraTest {

	@Spy
	@InjectMocks
	private IDPPostGreSqlDbContext idppostGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private InsertFetchJira insertFetchJiraoj;
	
	@Before
	public void setUp() throws Exception {

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = IDPPostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(idppostGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void insertHistoryInfoTest() throws Throwable {
		try 
		{
			
			Integer trigegrid=1;
			String temp=insertFetchJiraoj.getDetailsFromDB(trigegrid);
			assertEquals(null,temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
