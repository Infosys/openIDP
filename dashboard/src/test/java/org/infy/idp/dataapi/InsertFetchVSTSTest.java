/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;

import org.infy.idp.AppContext;
import org.infy.idp.entities.VSTSDataBean;
import org.infy.idp.utils.ConfigurationManager;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*This test case is used for Insert fetch*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class InsertFetchVSTSTest {

	@Spy
	@InjectMocks
	private IDPPostGreSqlDbContext idppostGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private InsertFetchVSTS insertFetchVSTS;
	
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
			JSONObject temp=insertFetchVSTS.getTriggerJSON(trigegrid);
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateJobStatustEST() throws Throwable {
		try 
		{
			VSTSDataBean vstsDataBean=new VSTSDataBean();
			Integer trigegrid=1;
			String temp=insertFetchVSTS.updateJobStatus(vstsDataBean,"tfsWorkItem","jiraProjectKey","userStoryString",trigegrid);
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void getPipelinesForWorkitemTest() throws Throwable {
		try 
		{
			//ResultSet result=Mockito.mock(ResultSet.class);
			//Mockito.when(Mockito.any(ResultSet.class).next()).thenReturn(true);
			String workItem="id";
			List<VSTSDataBean> temp=insertFetchVSTS.getPipelinesForWorkitem("406");
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
