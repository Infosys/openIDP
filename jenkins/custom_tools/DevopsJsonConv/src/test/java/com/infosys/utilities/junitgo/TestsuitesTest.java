
package com.infosys.utilities.junitgo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.infosys.utilities.junitgo.Testsuites.Testsuite;
import com.infosys.utilities.junitgo.Testsuites.Testsuite.Properties;
import com.infosys.utilities.junitgo.Testsuites.Testsuite.Properties.Property;
import com.infosys.utilities.junitgo.Testsuites.Testsuite.Testcase;
import com.infosys.utilities.junitgo.Testsuites.Testsuite.Testcase.Failure;

public class TestsuitesTest {

	@Test 
	public void testEquals()
	{
		Testsuites testsuites=new Testsuites();
		
		assertEquals(0,testsuites.getTestsuite().size());
		
		Testsuite testsuite=new Testsuite();
		
		Properties properties=new Properties();
		Property property=new Property();
		property.setName("value");
		property.setValue("value");
		properties.setProperty(property);
		
		testsuite.setFailures(1);
		testsuite.setName("value");
		testsuite.setProperties(properties);
		testsuite.setTests(1);
		testsuite.setTime(BigDecimal.valueOf(1.0));
		
		assertEquals((Integer)1,testsuite.getFailures());
		assertEquals("value",testsuite.getName());
		assertEquals((Integer)1,testsuite.getTests());
		assertEquals(BigDecimal.valueOf(1.0),testsuite.getTime());
		assertEquals("value",testsuite.getProperties().getProperty().getName());
		assertEquals("value",testsuite.getProperties().getProperty().getValue());
		
		Testcase testcase=new Testcase();
		Failure failure=new Failure();
		failure.setMessage("value");
		failure.setType("value");
		
		testcase.setClassname("value");
		testcase.setFailure(failure);
		testcase.setName("value");
		testcase.setTime(BigDecimal.valueOf(1.0));
		
		assertEquals("value",testcase.getClassname());
		assertEquals("value",testcase.getName());
		assertEquals(BigDecimal.valueOf(1.0),testcase.getTime());
		assertEquals("value",testcase.getFailure().getMessage());
		assertEquals("value",testcase.getFailure().getType());
	}
	
	@Test 
	public void testNotEquals()
	{
		Testsuites testsuites=new Testsuites();
		
		assertNotEquals(0,testsuites.getTestsuite().size());
		
		Testsuite testsuite=new Testsuite();
		
		Properties properties=new Properties();
		Property property=new Property();
		property.setName("value");
		property.setValue("value");
		properties.setProperty(property);
		
		testsuite.setFailures(1);
		testsuite.setName("value");
		testsuite.setProperties(properties);
		testsuite.setTests(1);
		testsuite.setTime(BigDecimal.valueOf(1.0));
		
		assertNotEquals((Integer)1,testsuite.getFailures());
		assertNotEquals("value",testsuite.getName());
		assertNotEquals((Integer)1,testsuite.getTests());
		assertNotEquals(BigDecimal.valueOf(1.0),testsuite.getTime());
		assertNotEquals("value",testsuite.getProperties().getProperty().getName());
		assertNotEquals("value",testsuite.getProperties().getProperty().getValue());
		
		Testcase testcase=new Testcase();
		Failure failure=new Failure();
		failure.setMessage("value");
		failure.setType("value");
		
		testcase.setClassname("value");
		testcase.setFailure(failure);
		testcase.setName("value");
		testcase.setTime(BigDecimal.valueOf(1.0));
		
		assertNotEquals("value",testcase.getClassname());
		assertNotEquals("value",testcase.getName());
		assertNotEquals(BigDecimal.valueOf(1.0),testcase.getTime());
		assertNotEquals("value",testcase.getFailure().getMessage());
		assertNotEquals("value",testcase.getFailure().getType());
	}
}
