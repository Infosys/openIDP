package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class StatusTest {

	@Test
	public void testEquals()
	{
		Status status=new Status();
		
		status.setCritical("value");
		status.setElapsedtime("value");
		status.setEndtime("value");
		status.setStarttime("value");
		status.setStatus("value");
		status.setValue("value");
		
		assertEquals("value",status.getCritical());
		assertEquals("value",status.getElapsedtime());
		assertEquals("value",status.getEndtime());
		assertEquals("value",status.getStarttime());
		assertEquals("value",status.getStatus());
		assertEquals("value",status.getValue());
		assertEquals(0,status.getOtherAttributes().size());
		
	}
	
	@Test
	public void testNotEquals()
	{
		Status status=new Status();
		
		status.setCritical("value");
		status.setElapsedtime("value");
		status.setEndtime("value");
		status.setStarttime("value");
		status.setStatus("value");
		status.setValue("value");
		
		assertNotEquals("value1",status.getCritical());
		assertNotEquals("value1",status.getElapsedtime());
		assertNotEquals("value1",status.getEndtime());
		assertNotEquals("value1",status.getStarttime());
		assertNotEquals("value1",status.getStatus());
		assertNotEquals("value1",status.getValue());
		assertNotEquals(1,status.getOtherAttributes().size());
		
	}
}
