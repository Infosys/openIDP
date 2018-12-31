
package com.infosys.utilities.nunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ReasonTest {

	@Test
	public void testEquals()
	{
		Reason reason=new Reason();
		
		reason.setMessage("value");
		
		assertEquals("value",reason.getMessage());
	}
	
	@Test
	public void testNotEquals()
	{
		Reason reason=new Reason();
		
		reason.setMessage("value");
		
		assertNotEquals("value1",reason.getMessage());
	}
}
