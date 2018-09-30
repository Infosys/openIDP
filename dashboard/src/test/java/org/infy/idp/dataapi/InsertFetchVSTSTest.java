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
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.List;

import org.infy.idp.entities.VSTSDataBean;
import org.infy.idp.utils.ConfigurationManager;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

/*This test case is used for Insert fetch*/

@RunWith(MockitoJUnitRunner.class)
public class InsertFetchVSTSTest {

	@Spy
	@InjectMocks
	@Autowired
	private IDPPostGreSqlDbContext idppostGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configurationManager;
	
	@InjectMocks
	private InsertFetchVSTS insertFetchVSTS;
	
	@Before
	public void setUp() throws Exception {

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
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
			assertNull(temp);
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
			assertNull(temp);
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
			
			String workItem="id";
			List<VSTSDataBean> temp=insertFetchVSTS.getPipelinesForWorkitem(workItem);
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
