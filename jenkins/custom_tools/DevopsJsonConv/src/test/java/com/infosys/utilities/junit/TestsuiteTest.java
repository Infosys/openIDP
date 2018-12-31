
package com.infosys.utilities.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.junit.Testsuite.Properties;
import com.infosys.utilities.junit.Testsuite.Properties.Property;
import com.infosys.utilities.junit.Testsuite.Testcase;
import com.infosys.utilities.junit.Testsuite.Testcase.Failure;
import com.infosys.utilities.junit.Testsuite.Testcase.Skipped;

public class TestsuiteTest {

	@Test
	public void testEquals()
	{
		Testsuite testsuite=new Testsuite();
		Properties properties=new Properties();
		
		testsuite.setErrors(new Byte((byte) 1));
		testsuite.setFailures(new Byte((byte) 1));
		testsuite.setName("value");
		testsuite.setProperties(properties);
		testsuite.setSkipped(new Byte((byte) 1));
		testsuite.setSystemErr("value");
		testsuite.setSystemOut("value");
		testsuite.setTests(new Byte((byte) 1));
		testsuite.setTime((float) 1);
		
		assertEquals(new Byte((byte) 1),testsuite.getErrors());
		assertEquals(new Byte((byte) 1),testsuite.getFailures());
		assertEquals("value",testsuite.getName());
		assertEquals(0,testsuite.getProperties().getProperty().size());
		assertEquals(new Byte((byte) 1),testsuite.getSkipped());
		assertEquals("value",testsuite.getSystemErr());
		assertEquals("value",testsuite.getSystemOut());
		assertEquals(new Byte((byte) 1),testsuite.getTests());
		assertEquals((float)1,testsuite.getTime(),0.01);
		
		Property property=new Property();
		property.setName("value");
		property.setValue("value");
		
		assertEquals("value",property.getName());
		assertEquals("value",property.getValue());
		
		Testcase testcase=new Testcase();
		
		Testcase.Error error=new Testcase.Error();
		error.setMessage("value");
		error.setType("value");
		error.setValue("value");
		
		Failure failure=new Failure();
		failure.setMessage("value");
		failure.setType("value");
		failure.setValue("value");
		
		Skipped skipped=new Skipped();
		
		
		testcase.setClassname("value");
		testcase.setError(error);
		testcase.setFailure(failure);
		testcase.setName("value");
		testcase.setSkipped(skipped);
		testcase.setTime("value");
		
		assertEquals("value",testcase.getName());
		assertEquals("value",testcase.getClassname());
		assertEquals("value",testcase.getTime());
		assertEquals("value",testcase.getError().getMessage());
		assertEquals("value",testcase.getError().getType());
		assertEquals("value",testcase.getError().getValue());
		assertEquals("value",testcase.getFailure().getMessage());
		assertEquals("value",testcase.getFailure().getType());
		assertEquals("value",testcase.getFailure().getValue());
		
		
		
	}
	@Test
	public void testNotEquals()
	{
		Testsuite testsuite=new Testsuite();
		Properties properties=new Properties();
		
		testsuite.setErrors(new Byte((byte) 1));
		testsuite.setFailures(new Byte((byte) 1));
		testsuite.setName("value");
		testsuite.setProperties(properties);
		testsuite.setSkipped(new Byte((byte) 1));
		testsuite.setSystemErr("value");
		testsuite.setSystemOut("value");
		testsuite.setTests(new Byte((byte) 1));
		testsuite.setTime((float) 1);
		
		assertNotEquals(new Byte((byte) 1),testsuite.getErrors());
		assertNotEquals(new Byte((byte) 1),testsuite.getFailures());
		assertNotEquals("value",testsuite.getName());
		assertNotEquals(0,testsuite.getProperties().getProperty().size());
		assertNotEquals(new Byte((byte) 1),testsuite.getSkipped());
		assertNotEquals("value",testsuite.getSystemErr());
		assertNotEquals("value",testsuite.getSystemOut());
		assertNotEquals(new Byte((byte) 1),testsuite.getTests());
		assertNotEquals((float)1,testsuite.getTime(),0.01);
		
		Property property=new Property();
		property.setName("value");
		property.setValue("value");
		
		assertNotEquals("value",property.getName());
		assertNotEquals("value",property.getValue());
		
		Testcase testcase=new Testcase();
		
		Testcase.Error error=new Testcase.Error();
		error.setMessage("value");
		error.setType("value");
		error.setValue("value");
		
		Failure failure=new Failure();
		failure.setMessage("value");
		failure.setType("value");
		failure.setValue("value");
		
		Skipped skipped=new Skipped();
		
		
		testcase.setClassname("value");
		testcase.setError(error);
		testcase.setFailure(failure);
		testcase.setName("value");
		testcase.setSkipped(skipped);
		testcase.setTime("value");
		
		assertNotEquals("value",testcase.getName());
		assertNotEquals("value",testcase.getClassname());
		assertNotEquals("value",testcase.getTime());
		assertNotEquals("value",testcase.getError().getMessage());
		assertNotEquals("value",testcase.getError().getType());
		assertNotEquals("value",testcase.getError().getValue());
		assertNotEquals("value",testcase.getFailure().getMessage());
		assertNotEquals("value",testcase.getFailure().getType());
		assertNotEquals("value",testcase.getFailure().getValue());
		
		
		
	}
}
