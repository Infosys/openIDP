package com.infosys.utilities.soapuireport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class NewDataSetTest {

	@Test
	public void testEquals()
	{
		NewDataSet newDataSet=new NewDataSet();
		
		assertEquals(0,newDataSet.getTestsuite().size());
	}
	
	@Test
	public void testNotEquals()
	{
		NewDataSet newDataSet=new NewDataSet();
		
		assertNotEquals(1,newDataSet.getTestsuite().size());
	}
}
