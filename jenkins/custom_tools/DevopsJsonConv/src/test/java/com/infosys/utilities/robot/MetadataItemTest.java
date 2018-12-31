package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
public class MetadataItemTest {

	@Test
	public void testEquals()
	{
		MetadataItem metadataItem=new MetadataItem();
		
		metadataItem.setName("value");
		metadataItem.setValue("value");
		
		assertEquals("value",metadataItem.getName());
		assertEquals("value",metadataItem.getValue());
		assertEquals(0,metadataItem.getOtherAttributes().size());
		
		
	}
	
	@Test
	public void testNotEquals()
	{
		MetadataItem metadataItem=new MetadataItem();
		
		metadataItem.setName("value");
		metadataItem.setValue("value");
		
		assertNotEquals("value1",metadataItem.getName());
		assertNotEquals("value1",metadataItem.getValue());
		assertNotEquals(1,metadataItem.getOtherAttributes().size());
		
		
	}
}
