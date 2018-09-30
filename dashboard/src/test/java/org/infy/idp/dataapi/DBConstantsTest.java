/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


/*This test case is used for database query*/
@RunWith(MockitoJUnitRunner.class)
public class DBConstantsTest {

	@Test
	public void testEquals()
	{
		DBConstants dbconstants=new DBConstants();
		
		List<String> temp=dbconstants.getQuery("pipelineIdForApplication");
		
		
		assertEquals("select count(id),max(created_at) from appinfo where application_name=?;",temp.get(0));
		assertEquals("DASHBOARD",temp.get(1));
	}
}
