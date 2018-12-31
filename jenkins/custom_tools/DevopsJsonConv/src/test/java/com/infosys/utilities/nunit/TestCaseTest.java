
package com.infosys.utilities.nunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TestCaseTest {

	@Test
	public void testEquals()
	{
		TestCase testCase=new TestCase();
		
		testCase.setAsserts(new Byte((byte) 1));
		testCase.setClassname("value");
		testCase.setDuration((float) 1);
		testCase.setEndTime("value");
		testCase.setFullname("value");
		testCase.setId("value");
		testCase.setLabel("value");
		testCase.setMethodname("value");
		testCase.setName("value");
		testCase.setResult("value");
		testCase.setRunstate("value");
		testCase.setSeed(1);
		testCase.setStartTime("value");
		
		assertEquals(new Byte((byte) 1),testCase.getAsserts());
		assertEquals("value",testCase.getClassname());
		assertEquals("value",testCase.getContent());
		assertEquals((float) 1,(float)testCase.getDuration(),0.01);
		assertEquals("value",testCase.getEndTime());
		assertEquals("value",testCase.getFullname());
		assertEquals("value",testCase.getId());
		assertEquals("value",testCase.getLabel());
		assertEquals("value",testCase.getMethodname());
		assertEquals("value",testCase.getName());
		assertEquals("value",testCase.getResult());
		assertEquals((Integer) 1,testCase.getSeed());
		assertEquals("value",testCase.getRunstate());
		assertEquals("value",testCase.getStartTime());
		
	}
	
	@Test
	public void testNotEquals()
	{
		TestCase testCase=new TestCase();
		
		testCase.setAsserts(new Byte((byte) 1));
		testCase.setClassname("value");
		testCase.setDuration((float) 1);
		testCase.setEndTime("value");
		testCase.setFullname("value");
		testCase.setId("value");
		testCase.setLabel("value");
		testCase.setMethodname("value");
		testCase.setName("value");
		testCase.setResult("value");
		testCase.setRunstate("value");
		testCase.setSeed(1);
		testCase.setStartTime("value");
		
		assertNotEquals(new Byte((byte) 2),testCase.getAsserts());
		assertNotEquals("value2",testCase.getClassname());
		assertNotEquals("value2",testCase.getContent());
		assertNotEquals((float) 2,(float)testCase.getDuration(),0.01);
		assertNotEquals("value2",testCase.getEndTime());
		assertNotEquals("value2",testCase.getFullname());
		assertNotEquals("value2",testCase.getId());
		assertNotEquals("value2",testCase.getLabel());
		assertNotEquals("value2",testCase.getMethodname());
		assertNotEquals("value2",testCase.getName());
		assertNotEquals("value2",testCase.getResult());
		assertNotEquals((Integer) 2,testCase.getSeed());
		assertNotEquals("value2",testCase.getRunstate());
		assertNotEquals("value2",testCase.getStartTime());
		
	}
}
