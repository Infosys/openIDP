/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.csvparser;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeanTest {

	@Test
	public void testEquals()
	{
		bean bean=new bean();
		
		bean.setCC("cc");
		bean.setCP("cP");
		bean.setDP("dP");
		bean.setMI("mI");
		bean.setPackageName("packageName");
		
		assertEquals("cc",bean.getCC());
		assertEquals("cP",bean.getCP());
		assertEquals("dP",bean.getDP());
		assertEquals("mI",bean.getMI());
		assertEquals("packageName",bean.getPackageName());
	}
	
	@Test
	public void testNotEquals()
	{
		bean bean=new bean();
		
		bean.setCC("cc");
		bean.setCP("cP");
		bean.setDP("dP");
		bean.setMI("mI");
		bean.setPackageName("packageName");
		
		assertNotEquals("cc1",bean.getCC());
		assertNotEquals("cP1",bean.getCP());
		assertNotEquals("dP1",bean.getDP());
		assertNotEquals("mI1",bean.getMI());
		assertNotEquals("packageName1",bean.getPackageName());
	}
}
