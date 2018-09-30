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

/*This test case is used for database tables*/
@RunWith(MockitoJUnitRunner.class)
public class DBTableConstantsTest {

	
	@Test
	public void testEquals()
	{
		DBTableConstants dbTableConstants=new DBTableConstants();
		
		List<String> temp=dbTableConstants.getQuery("idpnoofapplications");
		
		
		assertEquals("Select count(distinct application_name) from appinfo",temp.get(0));
		assertEquals("DASHBOARD",temp.get(1));
	}
}
