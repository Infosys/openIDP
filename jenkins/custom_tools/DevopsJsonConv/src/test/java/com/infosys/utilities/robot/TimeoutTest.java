package com.infosys.utilities.robot;

import org.junit.Test;
import static org.junit.Assert.*;

public class TimeoutTest {
	@Test
	public void testEquals()
	{
		Timeout timeout=new Timeout();
		
		timeout.setValue("value");
		timeout.setValueAttribute("value");
		
		assertEquals("value",timeout.getValue());
		assertEquals("value",timeout.getValue());
		assertEquals(0,timeout.getOtherAttributes().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Timeout timeout=new Timeout();
		
		timeout.setValue("value");
		timeout.setValueAttribute("value");
		
		assertNotEquals("value1",timeout.getValue());
		assertNotEquals("value1",timeout.getValue());
		assertNotEquals(1,timeout.getOtherAttributes().size());
	}

}
