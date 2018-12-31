package com.infosys.utilities.robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TagsTest {

	@Test
	public void testEquals()
	{
		Tags tags=new Tags();
		
		assertEquals(0,tags.getTag().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Tags tags=new Tags();
		
		assertNotEquals(1,tags.getTag().size());
	}
}
