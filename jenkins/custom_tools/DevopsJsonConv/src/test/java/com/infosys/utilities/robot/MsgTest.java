package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
public class MsgTest {

	@Test
	public void testEquals()
	{
		Msg msg=new Msg();
		
		msg.setHtml("value");
		msg.setLevel("value");
		msg.setTimestamp("value");
		msg.setValue("value");
		
		assertEquals("value",msg.getHtml());
		assertEquals("value",msg.getLevel());
		assertEquals("value",msg.getTimestamp());
		assertEquals("value",msg.getValue());
		assertEquals(0,msg.getOtherAttributes().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Msg msg=new Msg();
		
		msg.setHtml("value");
		msg.setLevel("value");
		msg.setTimestamp("value");
		msg.setValue("value");
		
		assertNotEquals("value1",msg.getHtml());
		assertNotEquals("value1",msg.getLevel());
		assertNotEquals("value1",msg.getTimestamp());
		assertNotEquals("value1",msg.getValue());
		assertNotEquals(1,msg.getOtherAttributes().size());
	}
}
