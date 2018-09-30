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

public class SettingsTest {

	@Test
	public void testEquals()
	{
		Settings settings=new Settings();
		
		assertEquals(0, settings.getSetting().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Settings settings=new Settings();
		
		assertNotEquals(1, settings.getSetting().size());
	}
}
