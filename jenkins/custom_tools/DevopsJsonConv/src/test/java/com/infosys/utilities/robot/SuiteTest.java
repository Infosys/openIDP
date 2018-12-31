package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class SuiteTest {

	@Test
	public void testEquals()
	{
		Suite suite=new Suite();
		
		suite.setId("value");
		suite.setName("value");
		suite.setSource("value");
		
		assertEquals("value",suite.getId());
		assertEquals("value",suite.getName());
		assertEquals("value",suite.getSource());
		assertEquals(0,suite.getKwOrSuiteOrTest().size());
		assertEquals(0,suite.getOtherAttributes().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Suite suite=new Suite();
		
		suite.setId("value");
		suite.setName("value");
		suite.setSource("value");
		
		assertNotEquals("value1",suite.getId());
		assertNotEquals("value1",suite.getName());
		assertNotEquals("value1",suite.getSource());
		assertNotEquals(1,suite.getKwOrSuiteOrTest().size());
		assertNotEquals(1,suite.getOtherAttributes().size());
	}
}
