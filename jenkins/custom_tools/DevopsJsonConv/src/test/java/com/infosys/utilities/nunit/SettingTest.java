
package com.infosys.utilities.nunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class SettingTest {

	@Test
	public void testEquals()
	{
		Setting setting=new Setting();
		
		setting.setName("value");
		setting.setValue("value");
		
		assertEquals("value",setting.getName());
		assertEquals("value",setting.getValue());
	}
	
	@Test
	public void testNotEquals()
	{
		Setting setting=new Setting();
		
		setting.setName("value");
		setting.setValue("value");
		
		assertNotEquals("value1",setting.getName());
		assertNotEquals("value1",setting.getValue());
	}
}
