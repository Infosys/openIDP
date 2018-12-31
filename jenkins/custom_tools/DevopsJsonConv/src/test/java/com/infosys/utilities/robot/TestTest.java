package com.infosys.utilities.robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
public class TestTest {

	@Test
	public void testEquals()
	{
		com.infosys.utilities.robot.Test test=new com.infosys.utilities.robot.Test();
		test.setId("value");
		test.setName("value");
		assertEquals("value",test.getId());
		assertEquals("value",test.getName());
		assertEquals(0,test.getKwOrMsgOrDoc().size());
		assertEquals(0,test.getOtherAttributes().size());
	}
	
	@Test
	public void testNotEquals()
	{
		com.infosys.utilities.robot.Test test=new com.infosys.utilities.robot.Test();
		test.setId("value");
		test.setName("value");
		assertNotEquals("value1",test.getId());
		assertNotEquals("value1",test.getName());
		assertNotEquals(1,test.getKwOrMsgOrDoc().size());
		assertNotEquals(1,test.getOtherAttributes().size());
	}
}
