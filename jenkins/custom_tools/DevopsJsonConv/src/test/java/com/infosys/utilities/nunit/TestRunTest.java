
package com.infosys.utilities.nunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TestRunTest {

	@Test
	public void testEquals()
	{
		TestRun testRun=new TestRun();
		TestSuite testSuite=new TestSuite();
		Environment environment=new Environment();
		Failure failure=new Failure();
		Properties properties=new Properties();
		Settings settings=new Settings();
		
		testSuite.setAsserts(new Byte((byte) 1));
		testSuite.setClassname("value");
		testSuite.setDuration((float) 1);
		testSuite.setEndTime("value");
		testSuite.setEnvironment(environment);
		testSuite.setFailed(new Byte((byte) 1));
		testSuite.setFailure(failure);
		testSuite.setFullname("value");
		testSuite.setId("value");
		testSuite.setInconclusive(new Byte((byte) 1));
		testSuite.setName("value");
		testSuite.setPassed(new Byte((byte) 1));
		testSuite.setProperties(properties);
		testSuite.setResult("value");
		testSuite.setRunstate("value");
		testSuite.setSettings(settings);
		testSuite.setSite("value");
		testSuite.setSkipped(new Byte((byte) 1));
		testSuite.setStartTime("value");
		testSuite.setTestcasecount(new Byte((byte) 1));
		testSuite.setTotal(new Byte((byte) 1));
		testSuite.setType("value");
		
		testRun.setAsserts(new Byte((byte) 1));
		testRun.setClrVersion("value");
		testRun.setCommandLine("value");
		testRun.setDuration((float) 1);
		testRun.setEndTime("value");
		testRun.setEngineVersion("value");
		testRun.setFailed(new Byte((byte) 1));
		testRun.setId(new Byte((byte) 1));
		testRun.setInconclusive(new Byte((byte) 1));
		testRun.setPassed(new Byte((byte) 1));
		testRun.setResult("value");
		testRun.setSkipped(new Byte((byte) 1));
		testRun.setStartTime("value");
		testRun.setTestcasecount(new Byte((byte) 1));
		testRun.setTestSuite(testSuite);
		testRun.setTotal(new Byte((byte) 1));
		
		assertEquals(new Byte((byte) 1),testRun.getAsserts());
		assertEquals("value",testRun.getClrVersion());
		assertEquals("value",testRun.getCommandLine());
		assertEquals((float) 1,testRun.getDuration(),0.01);
		assertEquals("value",testRun.getEndTime());
		assertEquals("value",testRun.getEngineVersion());
		assertEquals(new Byte((byte) 1),testRun.getFailed());
		assertEquals(new Byte((byte) 1),testRun.getId());
		assertEquals(new Byte((byte) 1),testRun.getInconclusive());
		assertEquals(new Byte((byte) 1),testRun.getPassed());
		assertEquals("value",testRun.getResult());
		assertEquals(new Byte((byte) 1),testRun.getSkipped());
		assertEquals("value",testRun.getStartTime());
		assertEquals(new Byte((byte) 1),testRun.getTestcasecount());
		assertEquals(new Byte((byte) 1),testRun.getTotal());
		assertEquals(new Byte((byte) 1),testRun.getTestSuite().getAsserts());
		assertEquals("value",testRun.getTestSuite().getClassname());
		assertEquals((float) 1,testRun.getTestSuite().getDuration(),0.01);
		assertEquals("value",testRun.getTestSuite().getEndTime());
		
		assertEquals(new Byte((byte) 1),testRun.getTestSuite().getFailed());
		
		assertEquals("value",testRun.getTestSuite().getFullname());
		assertEquals("value",testRun.getTestSuite().getId());
		assertEquals(new Byte((byte) 1),testRun.getTestSuite().getInconclusive());
		assertEquals("value",testRun.getTestSuite().getName());
		assertEquals(new Byte((byte) 1),testRun.getTestSuite().getPassed());
		
		assertEquals("value",testRun.getTestSuite().getResult());
		assertEquals("value",testRun.getTestSuite().getRunstate());
		
		assertEquals("value",testRun.getTestSuite().getSite());
		assertEquals(new Byte((byte) 1),testRun.getTestSuite().getSkipped());
		assertEquals("value",testRun.getTestSuite().getStartTime());
		assertEquals("value",testRun.getTestSuite().getTestCase().size());
		assertEquals(new Byte((byte) 1),testRun.getTestSuite().getTestcasecount());
		assertEquals("value",testRun.getTestSuite().getTestSuite().size());
		assertEquals(new Byte((byte) 1),testRun.getTestSuite().getTotal());
		assertEquals("value",testRun.getTestSuite().getType());
		
		
		
	}
	
	@Test
	public void testNotEquals()
	{
		TestRun testRun=new TestRun();
		TestSuite testSuite=new TestSuite();
		Environment environment=new Environment();
		Failure failure=new Failure();
		Properties properties=new Properties();
		Settings settings=new Settings();
		
		testSuite.setAsserts(new Byte((byte) 1));
		testSuite.setClassname("value");
		testSuite.setDuration((float) 1);
		testSuite.setEndTime("value");
		testSuite.setEnvironment(environment);
		testSuite.setFailed(new Byte((byte) 1));
		testSuite.setFailure(failure);
		testSuite.setFullname("value");
		testSuite.setId("value");
		testSuite.setInconclusive(new Byte((byte) 1));
		testSuite.setName("value");
		testSuite.setPassed(new Byte((byte) 1));
		testSuite.setProperties(properties);
		testSuite.setResult("value");
		testSuite.setRunstate("value");
		testSuite.setSettings(settings);
		testSuite.setSite("value");
		testSuite.setSkipped(new Byte((byte) 1));
		testSuite.setStartTime("value");
		testSuite.setTestcasecount(new Byte((byte) 1));
		testSuite.setTotal(new Byte((byte) 1));
		testSuite.setType("value");
		
		testRun.setAsserts(new Byte((byte) 1));
		testRun.setClrVersion("value");
		testRun.setCommandLine("value");
		testRun.setDuration((float) 1);
		testRun.setEndTime("value");
		testRun.setEngineVersion("value");
		testRun.setFailed(new Byte((byte) 1));
		testRun.setId(new Byte((byte) 1));
		testRun.setInconclusive(new Byte((byte) 1));
		testRun.setPassed(new Byte((byte) 1));
		testRun.setResult("value");
		testRun.setSkipped(new Byte((byte) 1));
		testRun.setStartTime("value");
		testRun.setTestcasecount(new Byte((byte) 1));
		testRun.setTestSuite(testSuite);
		testRun.setTotal(new Byte((byte) 1));
		
		assertNotEquals(new Byte((byte) 2),testRun.getAsserts());
		assertNotEquals("value1",testRun.getClrVersion());
		assertNotEquals("value1",testRun.getCommandLine());
		assertNotEquals((float) 2,testRun.getDuration(),0.01);
		assertNotEquals("value1",testRun.getEndTime());
		assertNotEquals("value1",testRun.getEngineVersion());
		assertNotEquals(new Byte((byte) 2),testRun.getFailed());
		assertNotEquals(new Byte((byte) 2),testRun.getId());
		assertNotEquals(new Byte((byte) 2),testRun.getInconclusive());
		assertNotEquals(new Byte((byte) 2),testRun.getPassed());
		assertNotEquals("value1",testRun.getResult());
		assertNotEquals(new Byte((byte) 2),testRun.getSkipped());
		assertNotEquals("value1",testRun.getStartTime());
		assertNotEquals(new Byte((byte) 2),testRun.getTestcasecount());
		assertNotEquals(new Byte((byte) 2),testRun.getTotal());
		assertNotEquals(new Byte((byte) 2),testRun.getTestSuite().getAsserts());
		assertNotEquals("value1",testRun.getTestSuite().getClassname());
		assertNotEquals((float) 2,testRun.getTestSuite().getDuration(),0.01);
		assertNotEquals("value1",testRun.getTestSuite().getEndTime());
		
		assertNotEquals(new Byte((byte) 2),testRun.getTestSuite().getFailed());
		
		assertNotEquals("value1",testRun.getTestSuite().getFullname());
		assertNotEquals("value1",testRun.getTestSuite().getId());
		assertNotEquals(new Byte((byte) 2),testRun.getTestSuite().getInconclusive());
		assertNotEquals("value1",testRun.getTestSuite().getName());
		assertNotEquals(new Byte((byte) 2),testRun.getTestSuite().getPassed());
		
		assertNotEquals("value1",testRun.getTestSuite().getResult());
		assertNotEquals("value1",testRun.getTestSuite().getRunstate());
		
		assertNotEquals("value1",testRun.getTestSuite().getSite());
		assertNotEquals(new Byte((byte) 2),testRun.getTestSuite().getSkipped());
		assertNotEquals("value1",testRun.getTestSuite().getStartTime());
		assertNotEquals("value1",testRun.getTestSuite().getTestCase().size());
		assertNotEquals(new Byte((byte) 2),testRun.getTestSuite().getTestcasecount());
		assertNotEquals("value1",testRun.getTestSuite().getTestSuite().size());
		assertNotEquals(new Byte((byte) 2),testRun.getTestSuite().getTotal());
		assertNotEquals("value1",testRun.getTestSuite().getType());
		
		
		
	}
}
