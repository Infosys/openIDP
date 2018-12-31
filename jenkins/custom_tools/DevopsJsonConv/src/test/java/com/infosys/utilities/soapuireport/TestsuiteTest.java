package com.infosys.utilities.soapuireport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.soapuireport.Testsuite.Properties;
import com.infosys.utilities.soapuireport.Testsuite.Properties.Property;
import com.infosys.utilities.soapuireport.Testsuite.Testcase;
import com.infosys.utilities.soapuireport.Testsuite.Testcase.Failure;

public class TestsuiteTest {

	@Test
	public void testEquals()
	{
		Testsuite testsuite=new Testsuite();
		
		testsuite.setErrors("value");
		testsuite.setFailures("value");
		testsuite.setName("value");
		testsuite.setTests("value");
		testsuite.setTime("value");
		
		assertEquals("value",testsuite.getErrors());
		assertEquals("value",testsuite.getFailures());
		assertEquals("value",testsuite.getName());
		assertEquals(0,testsuite.getProperties().size());
		assertEquals(0,testsuite.getTestcase().size());
		assertEquals("value",testsuite.getTests());
		assertEquals("value",testsuite.getTime());
		
		Properties properties=new Properties();
		assertEquals(0,properties.getProperty().size());
		
		Property property=new Property();
		
		property.setName("value");
		property.setValue("value");
		
		assertEquals("value",property.getName());
		assertEquals("value",property.getValue());
		
		Testcase testcase=new Testcase();
		testcase.setName("value");
		testcase.setTime("value");
		
		assertEquals("value",testcase.getName());
		assertEquals("value",testcase.getTime());
		assertEquals(0,testcase.getFailure().size());
		
		Failure failure=new Failure();
		
		failure.setMessage("value");
		failure.setType("value");
		failure.setValue("value");
		
		assertEquals("value",failure.getMessage());
		assertEquals("value",failure.getType());
		assertEquals("value",failure.getValue());
		
		
	}
	
	@Test
	public void testNotEquals()
	{
		Testsuite testsuite=new Testsuite();
		
		testsuite.setErrors("value");
		testsuite.setFailures("value");
		testsuite.setName("value");
		testsuite.setTests("value");
		testsuite.setTime("value");
		
		assertNotEquals("value1",testsuite.getErrors());
		assertNotEquals("value1",testsuite.getFailures());
		assertNotEquals("value1",testsuite.getName());
		assertNotEquals(1,testsuite.getProperties().size());
		assertNotEquals(1,testsuite.getTestcase().size());
		assertNotEquals("value1",testsuite.getTests());
		assertNotEquals("value1",testsuite.getTime());
		
		Properties properties=new Properties();
		assertEquals(1,properties.getProperty().size());
		
		Property property=new Property();
		
		property.setName("value");
		property.setValue("value");
		
		assertNotEquals("value1",property.getName());
		assertNotEquals("value1",property.getValue());
		
		Testcase testcase=new Testcase();
		testcase.setName("value");
		testcase.setTime("value");
		
		assertNotEquals("value1",testcase.getName());
		assertNotEquals("value1",testcase.getTime());
		assertNotEquals(1,testcase.getFailure().size());
		
		Failure failure=new Failure();
		
		failure.setMessage("value");
		failure.setType("value");
		failure.setValue("value");
		
		assertNotEquals("value1",failure.getMessage());
		assertNotEquals("value1",failure.getType());
		assertNotEquals("value1",failure.getValue());
		
		
	}
}
