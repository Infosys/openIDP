
package com.infosys.utilities.nunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
