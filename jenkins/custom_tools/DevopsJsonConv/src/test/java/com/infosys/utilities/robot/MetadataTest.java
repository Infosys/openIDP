package com.infosys.utilities.robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class MetadataTest {

	@Test
	public void testEquals()
	{
		Metadata metadata=new Metadata();
		assertEquals(0,metadata.getItem().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Metadata metadata=new Metadata();
		assertNotEquals(1,metadata.getItem().size());
	}
}
