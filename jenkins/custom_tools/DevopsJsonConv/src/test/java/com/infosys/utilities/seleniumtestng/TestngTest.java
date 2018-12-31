package com.infosys.utilities.seleniumtestng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import com.infosys.utilities.seleniumtestng.Testng.Suite;

public class TestngTest {

	@Test
	public void testEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Testng testng=new Testng();
		Suite suite=new Suite();
		Suite.Test test=new Suite.Test();
		List<Testng.Suite.Test.Class> clazz=new ArrayList<>();
		
		test.setClazz(clazz);
		test.setDurationMs(1);
		test.setFinishedAt(xmlGregCal1);
		test.setName("value");
		test.setStartedAt(xmlGregCal1);
		
		suite.setGroups("value");
		suite.setName("value");
		suite.setStartedAt(xmlGregCal1);
		suite.setTest(test);
		
		suite.setDurationMs(1);
		suite.setFinishedAt(xmlGregCal1);
		
		testng.setFailed(1);
		testng.setPassed(1);
		testng.setReporterOutput("value");
		testng.setSkipped(1);
		testng.setSuite(suite);
		testng.setTotal(1);
		
		assertEquals((Integer) 1,testng.getFailed());
		assertEquals((Integer) 1,testng.getPassed());
		assertEquals("value",testng.getReporterOutput());
		assertEquals((Integer) 1,testng.getSkipped());
		assertEquals((Integer) 1,testng.getSuite().getDurationMs());
		assertEquals((Integer) 24,(Integer)testng.getSuite().getFinishedAt().getDay());
		assertEquals("value",testng.getSuite().getGroups());
		assertEquals("value",testng.getSuite().getName());
		assertEquals((Integer) 24,(Integer)testng.getSuite().getStartedAt().getDay());
		assertEquals(0,testng.getSuite().getTest().getClazz().size());
		assertEquals((Integer) 1,testng.getSuite().getTest().getDurationMs());
		assertEquals((Integer) 24,(Integer)testng.getSuite().getTest().getFinishedAt().getDay());
		assertEquals("value",testng.getSuite().getTest().getName());
		assertEquals((Integer) 24,(Integer)testng.getSuite().getTest().getStartedAt().getDay());
		assertEquals((Integer) 1,testng.getTotal());
	}
	
	@Test
	public void testNotEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Testng testng=new Testng();
		Suite suite=new Suite();
		Suite.Test test=new Suite.Test();
		List<Testng.Suite.Test.Class> clazz=new ArrayList<>();
		
		test.setClazz(clazz);
		test.setDurationMs(1);
		test.setFinishedAt(xmlGregCal1);
		test.setName("value");
		test.setStartedAt(xmlGregCal1);
		
		suite.setGroups("value");
		suite.setName("value");
		suite.setStartedAt(xmlGregCal1);
		suite.setTest(test);
		
		suite.setDurationMs(1);
		suite.setFinishedAt(xmlGregCal1);
		
		testng.setFailed(1);
		testng.setPassed(1);
		testng.setReporterOutput("value");
		testng.setSkipped(1);
		testng.setSuite(suite);
		testng.setTotal(1);
		
		assertNotEquals((Integer) 2,testng.getFailed());
		assertNotEquals((Integer) 2,testng.getPassed());
		assertNotEquals("value1",testng.getReporterOutput());
		assertNotEquals((Integer) 2,testng.getSkipped());
		assertNotEquals((Integer) 2,testng.getSuite().getDurationMs());
		assertNotEquals((Integer) 23,(Integer)testng.getSuite().getFinishedAt().getDay());
		assertNotEquals("value1",testng.getSuite().getGroups());
		assertNotEquals("value1",testng.getSuite().getName());
		assertNotEquals((Integer) 23,(Integer)testng.getSuite().getStartedAt().getDay());
		assertNotEquals(0,testng.getSuite().getTest().getClazz().size());
		assertNotEquals((Integer) 2,testng.getSuite().getTest().getDurationMs());
		assertNotEquals((Integer) 23,(Integer)testng.getSuite().getTest().getFinishedAt().getDay());
		assertNotEquals("value1",testng.getSuite().getTest().getName());
		assertNotEquals((Integer) 23,(Integer)testng.getSuite().getTest().getStartedAt().getDay());
		assertNotEquals((Integer) 2,testng.getTotal());
	}
}
