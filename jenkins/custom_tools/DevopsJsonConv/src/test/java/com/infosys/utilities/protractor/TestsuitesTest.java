
package com.infosys.utilities.protractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import com.infosys.utilities.protractor.Testsuites.Testsuite;
import com.infosys.utilities.protractor.Testsuites.Testsuite.Testcase;

public class TestsuitesTest {

	@Test
	public void testEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Testsuites testsuites=new Testsuites();
		
		assertEquals(0,testsuites.getTestsuite().size());
		
		Testsuite testsuite=new Testsuite();
		Testcase testcase=new Testcase();
		
		testcase.setClassname("value");
		testcase.setName("value");
		testcase.setTime(1.0);
		
		testsuite.setErrors(1);
		testsuite.setFailures(1);
		testsuite.setHostname("value");
		testsuite.setName("value");
		testsuite.setSkipped(1);
		testsuite.setTestcase(testcase);
		testsuite.setTests(1);
		testsuite.setTime(1.0);
		testsuite.setTimestamp(xmlGregCal1);
		
		assertEquals((Integer) 1,testsuite.getErrors());
		assertEquals((Integer) 1,testsuite.getFailures());
		assertEquals("value",testsuite.getHostname());
		assertEquals("value",testsuite.getName());
		assertEquals((Integer) 1,testsuite.getSkipped());
		assertEquals("value",testsuite.getTestcase().getClassname());
		assertEquals("value",testsuite.getTestcase().getName());
		assertEquals((Double) 1.0,testsuite.getTestcase().getTime());
		assertEquals((Integer) 1,testsuite.getTests());
		assertEquals((Double) 1.0,testsuite.getTime());
		assertEquals((Integer) 24,(Integer)testsuite.getTimestamp().getDay());
	}
	
	@Test
	public void testNotEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Testsuites testsuites=new Testsuites();
		
		assertNotEquals(1,testsuites.getTestsuite().size());
		
		Testsuite testsuite=new Testsuite();
		Testcase testcase=new Testcase();
		
		testcase.setClassname("value");
		testcase.setName("value");
		testcase.setTime(1.0);
		
		testsuite.setErrors(1);
		testsuite.setFailures(1);
		testsuite.setHostname("value");
		testsuite.setName("value");
		testsuite.setSkipped(1);
		testsuite.setTestcase(testcase);
		testsuite.setTests(1);
		testsuite.setTime(1.0);
		testsuite.setTimestamp(xmlGregCal1);
		
		assertNotEquals((Integer) 2,testsuite.getErrors());
		assertNotEquals((Integer) 2,testsuite.getFailures());
		assertNotEquals("value1",testsuite.getHostname());
		assertNotEquals("value1",testsuite.getName());
		assertNotEquals((Integer) 2,testsuite.getSkipped());
		assertNotEquals("value1",testsuite.getTestcase().getClassname());
		assertNotEquals("value1",testsuite.getTestcase().getName());
		assertNotEquals((Double) 2.0,testsuite.getTestcase().getTime());
		assertNotEquals((Integer) 2,testsuite.getTests());
		assertNotEquals((Double) 2.0,testsuite.getTime());
		assertNotEquals((Integer) 23,(Integer)testsuite.getTimestamp().getDay());
	}
}
