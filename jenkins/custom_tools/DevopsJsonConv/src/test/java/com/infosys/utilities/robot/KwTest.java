package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
public class KwTest {

	@Test
	public void testEquals()
	{
		Kw kw=new Kw();
		
		kw.setName("value");
		kw.setType("value");
		
		assertEquals(0,kw.getTagsOrDocOrArguments().size());
		assertEquals(0,kw.getOtherAttributes().size());
		assertEquals("value",kw.getType());
		assertEquals("value",kw.getName());
	}
	
	@Test
	public void testNotEquals()
	{
		Kw kw=new Kw();
		
		kw.setName("value");
		kw.setType("value");
		
		assertNotEquals(1,kw.getTagsOrDocOrArguments().size());
		assertNotEquals(1,kw.getOtherAttributes().size());
		assertNotEquals("value1",kw.getType());
		assertNotEquals("value1",kw.getName());
	}
}
