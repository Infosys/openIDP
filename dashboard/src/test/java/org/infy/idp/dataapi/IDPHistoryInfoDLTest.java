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


/*This test case is used history test*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class IDPHistoryInfoDLTest {

	
	
	@Spy
	@InjectMocks
	
	private IDPPostGreSqlDbContext idppostGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private IDPHistoryInfoDL idpHistoryInfoDL;
	
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
			
			
			int temp=idpHistoryInfoDL.insertHistoryInfo("appName","pipName","stage","1","status");
			assertEquals(1, temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
