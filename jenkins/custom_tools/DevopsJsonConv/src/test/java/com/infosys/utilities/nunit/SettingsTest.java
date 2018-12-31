
package com.infosys.utilities.nunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
