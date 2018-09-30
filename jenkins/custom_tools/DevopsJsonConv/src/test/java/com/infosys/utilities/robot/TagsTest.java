package com.infosys.utilities.robot;

import org.junit.Test;
import static org.junit.Assert.*;

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
