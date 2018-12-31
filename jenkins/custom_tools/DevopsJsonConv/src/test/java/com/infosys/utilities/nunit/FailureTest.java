
package com.infosys.utilities.nunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class FailureTest {

	@Test
	public void testEquals()
	{
		Failure failure=new Failure();
		
		failure.setMessage("value");
		failure.setStackTrace("value");
		
		assertEquals("value",failure.getMessage());
		assertEquals("value",failure.getStackTrace());
	}
	
	@Test
	public void testNotEquals()
	{
		Failure failure=new Failure();
		
		failure.setMessage("value");
		failure.setStackTrace("value");
		
		assertNotEquals("value1",failure.getMessage());
		assertNotEquals("value1",failure.getStackTrace());
	}
}
