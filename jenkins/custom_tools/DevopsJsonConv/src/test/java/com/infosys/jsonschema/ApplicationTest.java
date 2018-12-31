
package com.infosys.jsonschema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

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
