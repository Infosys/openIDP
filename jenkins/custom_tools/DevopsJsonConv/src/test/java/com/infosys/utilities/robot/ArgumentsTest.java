package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
public class ArgumentsTest {

	@Test
	public void testEquals()
	{
		Arguments arguments=new Arguments();
		assertEquals(0,arguments.getArg().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Arguments arguments=new Arguments();
		assertNotEquals(1,arguments.getArg().size());
	}
}
