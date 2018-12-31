
package com.infosys.utilities.acceleratest;

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

import com.infosys.utilities.acceleratest.Acceleratest.TestSuite;
import com.infosys.utilities.acceleratest.Acceleratest.TestSuite.TestCase;
import com.infosys.utilities.acceleratest.Acceleratest.TestSuite.TestCase.TCIteration;
  
public class AcceleratestTest {

	@Test
	public void testEqual() throws ParseException, DatatypeConfigurationException
	{
		Acceleratest acceleratest =new Acceleratest();
		TestSuite testSuite=new TestSuite();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = format.parse("2014-04-24 11:15:00");
		Date endDate = format.parse("2014-04-25 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(startDate);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		GregorianCalendar cal2 = new GregorianCalendar();
		cal2.setTime(endDate);

		XMLGregorianCalendar xmlGregCal2 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal2);
		
		TestCase testCase=new TestCase();
		TCIteration tcIteration=new TCIteration();
		
		
		testCase.setTCIteration(tcIteration);
		testCase.setDesc("desc");
		testCase.setEndTime(xmlGregCal2);
		testCase.setName("name");
		testCase.setStartTime(xmlGregCal1);
		testCase.setStatus(1);
		
		testSuite.setBrowser("browser");
		testSuite.setDesc("desc");
		testSuite.setEndTime(xmlGregCal2);
		testSuite.setIterationType("type");
		testSuite.setName("name");
		testSuite.setStartTime(xmlGregCal1);
		testSuite.setStatus(1);
		
		testSuite.setTSExecutionType("tsexecutiontype");
		testSuite.setTestCase(testCase);
		
		acceleratest.setTestSuite(testSuite);
		acceleratest.setBrowserExecutionType("browserExecutionType");
		acceleratest.setEndTime(xmlGregCal2);
		acceleratest.setExecutionEnvironment("executionEnvironment");
		acceleratest.setName("name");
		acceleratest.setStartTime(xmlGregCal1);
		
		assertEquals("browserExecutionType",acceleratest.getBrowserExecutionType());
		assertEquals("executionEnvironment", acceleratest.getExecutionEnvironment());
		assertEquals("name", acceleratest.getName());
		assertEquals("2014-04-24 11:15:00", acceleratest.getStartTime().toString());
		assertEquals("2014-04-25 11:15:00",acceleratest.getEndTime().toString());
		assertEquals("browser", acceleratest.getTestSuite().getBrowser());
		assertEquals("desc", acceleratest.getTestSuite().getDesc());
		assertEquals("2014-04-25 11:15:00", acceleratest.getTestSuite().getEndTime().toString());
		assertEquals("2014-04-24 11:15:00", acceleratest.getTestSuite().getStartTime().toString());
		assertEquals("type", acceleratest.getTestSuite().getIterationType());
		assertEquals("name", acceleratest.getTestSuite().getName());
		assertEquals((Integer)1, acceleratest.getTestSuite().getStatus());
		assertEquals("tsexecutiontype", acceleratest.getTestSuite().getTSExecutionType());
		
		assertEquals("desc", acceleratest.getTestSuite().getTestCase().getDesc());
		assertEquals("2014-04-25 11:15:00", acceleratest.getTestSuite().getTestCase().getEndTime().toString());
		assertEquals("2014-04-24 11:15:00", acceleratest.getTestSuite().getTestCase().getStartTime().toString());
	
		assertEquals("name", acceleratest.getTestSuite().getTestCase().getName());
		assertEquals((Integer)1, acceleratest.getTestSuite().getTestCase().getStatus());
		
	}
	
	@Test
	public void testNotEqual() throws ParseException, DatatypeConfigurationException
	{
		Acceleratest acceleratest =new Acceleratest();
		TestSuite testSuite=new TestSuite();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = format.parse("2014-04-24 11:15:00");
		Date endDate = format.parse("2014-04-25 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(startDate);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		GregorianCalendar cal2 = new GregorianCalendar();
		cal2.setTime(endDate);

		XMLGregorianCalendar xmlGregCal2 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal2);
		
		TestCase testCase=new TestCase();
		TCIteration tcIteration=new TCIteration();
		
		
		testCase.setTCIteration(tcIteration);
		testCase.setDesc("desc");
		testCase.setEndTime(xmlGregCal2);
		testCase.setName("name");
		testCase.setStartTime(xmlGregCal1);
		testCase.setStatus(1);
		
		testSuite.setBrowser("browser");
		testSuite.setDesc("desc");
		testSuite.setEndTime(xmlGregCal2);
		testSuite.setIterationType("type");
		testSuite.setName("name");
		testSuite.setStartTime(xmlGregCal1);
		testSuite.setStatus(1);
		
		testSuite.setTSExecutionType("tsexecutiontype");
		testSuite.setTestCase(testCase);
		
		acceleratest.setTestSuite(testSuite);
		acceleratest.setBrowserExecutionType("browserExecutionType");
		acceleratest.setEndTime(xmlGregCal2);
		acceleratest.setExecutionEnvironment("executionEnvironment");
		acceleratest.setName("name");
		acceleratest.setStartTime(xmlGregCal1);
		
		assertNotEquals("browserExecutionType1",acceleratest.getBrowserExecutionType());
		assertNotEquals("executionEnvironment1", acceleratest.getExecutionEnvironment());
		assertNotEquals("name1", acceleratest.getName());
		assertNotEquals("2014-04-24 11:15:12", acceleratest.getStartTime().toString());
		assertNotEquals("2014-04-25 11:15:12",acceleratest.getEndTime().toString());
		assertNotEquals("browser1", acceleratest.getTestSuite().getBrowser());
		assertNotEquals("desc1", acceleratest.getTestSuite().getDesc());
		assertNotEquals("2014-04-25 11:15:12", acceleratest.getTestSuite().getEndTime().toString());
		assertNotEquals("2014-04-24 11:15:12", acceleratest.getTestSuite().getStartTime().toString());
		assertNotEquals("type1", acceleratest.getTestSuite().getIterationType());
		assertNotEquals("name1", acceleratest.getTestSuite().getName());
		assertNotEquals((Integer)2, acceleratest.getTestSuite().getStatus());
		assertNotEquals("tsexecutiontype1", acceleratest.getTestSuite().getTSExecutionType());
		
		assertNotEquals("desc", acceleratest.getTestSuite().getTestCase().getDesc());
		assertNotEquals("2014-04-25 11:15:10", acceleratest.getTestSuite().getTestCase().getEndTime().toString());
		assertNotEquals("2014-04-24 11:15:10", acceleratest.getTestSuite().getTestCase().getStartTime().toString());
	
		assertNotEquals("name1", acceleratest.getTestSuite().getTestCase().getName());
		assertNotEquals((Integer)2, acceleratest.getTestSuite().getTestCase().getStatus());
		
	}
}
