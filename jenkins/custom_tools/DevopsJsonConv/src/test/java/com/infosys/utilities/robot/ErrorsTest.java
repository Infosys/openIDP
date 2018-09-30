package com.infosys.utilities.robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ErrorsTest {

	@Test
	public void testEquals()
	{
		Errors errors=new Errors();
		assertEquals(0,errors.getMsg().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Errors errors=new Errors();
		assertNotEquals(1,errors.getMsg().size());
	}
}
