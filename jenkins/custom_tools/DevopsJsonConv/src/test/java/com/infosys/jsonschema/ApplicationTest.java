/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.jsonschema;

import static org.junit.Assert.*;

import org.junit.Test;

public class ApplicationTest {

	@Test
	public void testEqual()
	{
		Application application =new Application();
		application.setID("5");
		assertEquals("5",application.getID());
		
	}
	
	@Test
	public void testNotEqual()
	{
		Application application =new Application();
		application.setID("5");
		assertNotEquals("6",application.getID());
		
	}
	
	@Test
	public void testNull()
	{
		Application application =new Application();
		application.setID(null);
		assertNull(application.getID());
	}
}
