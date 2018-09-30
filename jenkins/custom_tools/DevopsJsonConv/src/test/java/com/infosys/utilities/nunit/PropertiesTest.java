/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.nunit;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertiesTest {

	@Test
	public void testEquals()
	{
		Properties properties=new Properties();
		assertEquals(0, properties.getProperty().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Properties properties=new Properties();
		assertNotEquals(1, properties.getProperty().size());
	}
}
