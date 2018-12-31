
package com.infosys.utilities.itops;

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

import com.infosys.utilities.itops.Itops.Suite;
import com.infosys.utilities.itops.Itops.Suite.Test.Class.TestMethod;

public class ItopsTest {

	@Test
	public void testEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Itops itops=new Itops();
		
		Suite suite=new Suite();
		
		Suite.Test test=new Suite.Test();
		
		test.setDurationMs(1);
		test.setFinishedAt(xmlGregCal1);
		test.setName("value");
		test.setStartedAt(xmlGregCal1);
		
		suite.setDurationMs(1);
		suite.setFinishedAt(xmlGregCal1);
		suite.setGroups("value");
		suite.setName("value");
		suite.setStartedAt(xmlGregCal1);
		suite.setTest(test);
		
		itops.setFailed(1);
		itops.setPassed(1);
		itops.setReporterOutput("value");
		itops.setSkipped(1);
		itops.setTotal(1);
		itops.setSuite(suite);
		
		assertEquals((Integer)1,itops.getFailed());
		assertEquals((Integer)1,itops.getPassed());
		assertEquals("value",itops.getReporterOutput());
		assertEquals((Integer)1,itops.getSkipped());
		assertEquals((Integer)1,itops.getTotal());
		
		assertEquals((Integer)1,itops.getSuite().getDurationMs());
		assertEquals((Integer)24,(Integer)itops.getSuite().getFinishedAt().getDay());
		assertEquals("value",itops.getSuite().getGroups());
		assertEquals("value",itops.getSuite().getName());
		assertEquals((Integer)24,(Integer)itops.getSuite().getStartedAt().getDay());
		
		assertEquals((Integer)1,itops.getSuite().getTest().getDurationMs());
		assertEquals((Integer)24,(Integer)itops.getSuite().getTest().getFinishedAt().getDay());
		assertEquals("value",itops.getSuite().getTest().getName());
		assertEquals((Integer)24,(Integer)itops.getSuite().getTest().getStartedAt().getDay());
		assertEquals(0,itops.getSuite().getTest().getClazz().size());
		
		Suite.Test.Class clazz=new Suite.Test.Class();
		
		clazz.setName("value");
		assertEquals("value", clazz.getName());
		assertEquals(0, clazz.getTestMethod().size());
		
		TestMethod testMethod=new TestMethod();
		TestMethod.Params params=new TestMethod.Params();
		TestMethod.Params.Param param=new TestMethod.Params.Param();
		
		param.setIndex(1);
		param.setValue("value");
		
		params.setParam(param);
		testMethod.setDurationMs(1);
		testMethod.setFinishedAt(xmlGregCal1);
		testMethod.setIsConfig(true);
		testMethod.setName("value");
		testMethod.setParams(params);
		testMethod.setReporterOutput("value");
		testMethod.setSignature("value");
		testMethod.setStartedAt(xmlGregCal1);
		testMethod.setStatus("value");
		
		assertEquals((Integer)1,testMethod.getDurationMs());
		assertEquals((Integer)24,(Integer)testMethod.getFinishedAt().getDay());
		assertEquals("value",testMethod.getName());
		assertEquals("value",testMethod.getSignature());
		assertEquals("value",testMethod.getReporterOutput());
		assertEquals((Integer)24,(Integer)testMethod.getStartedAt().getDay());
		assertEquals("value",testMethod.getStatus());
		assertEquals((Integer)1,testMethod.getParams().getParam().getIndex());
		assertEquals("value",testMethod.getParams().getParam().getValue());
		
		
	}
	
	@Test
	public void testNotEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Itops itops=new Itops();
		
		Suite suite=new Suite();
		
		Suite.Test test=new Suite.Test();
		
		test.setDurationMs(1);
		test.setFinishedAt(xmlGregCal1);
		test.setName("value");
		test.setStartedAt(xmlGregCal1);
		
		suite.setDurationMs(1);
		suite.setFinishedAt(xmlGregCal1);
		suite.setGroups("value");
		suite.setName("value");
		suite.setStartedAt(xmlGregCal1);
		suite.setTest(test);
		
		itops.setFailed(1);
		itops.setPassed(1);
		itops.setReporterOutput("value");
		itops.setSkipped(1);
		itops.setTotal(1);
		itops.setSuite(suite);
		
		assertNotEquals((Integer)2,itops.getFailed());
		assertNotEquals((Integer)2,itops.getPassed());
		assertNotEquals("value2",itops.getReporterOutput());
		assertNotEquals((Integer)2,itops.getSkipped());
		assertNotEquals((Integer)2,itops.getTotal());
		
		assertNotEquals((Integer)2,itops.getSuite().getDurationMs());
		assertNotEquals((Integer)14,(Integer)itops.getSuite().getFinishedAt().getDay());
		assertNotEquals("value2",itops.getSuite().getGroups());
		assertNotEquals("value2",itops.getSuite().getName());
		assertNotEquals((Integer)14,(Integer)itops.getSuite().getStartedAt().getDay());
		
		assertNotEquals((Integer)2,itops.getSuite().getTest().getDurationMs());
		assertNotEquals((Integer)14,(Integer)itops.getSuite().getTest().getFinishedAt().getDay());
		assertNotEquals("value2",itops.getSuite().getTest().getName());
		assertNotEquals((Integer)14,(Integer)itops.getSuite().getTest().getStartedAt().getDay());
		assertNotEquals(2,itops.getSuite().getTest().getClazz().size());
		
		Suite.Test.Class clazz=new Suite.Test.Class();
		
		clazz.setName("value");
		assertEquals("value", clazz.getName());
		assertEquals(0, clazz.getTestMethod().size());
		
		TestMethod testMethod=new TestMethod();
		TestMethod.Params params=new TestMethod.Params();
		TestMethod.Params.Param param=new TestMethod.Params.Param();
		
		param.setIndex(1);
		param.setValue("value");
		
		params.setParam(param);
		testMethod.setDurationMs(1);
		testMethod.setFinishedAt(xmlGregCal1);
		testMethod.setIsConfig(true);
		testMethod.setName("value");
		testMethod.setParams(params);
		testMethod.setReporterOutput("value");
		testMethod.setSignature("value");
		testMethod.setStartedAt(xmlGregCal1);
		testMethod.setStatus("value");
		
		assertNotEquals((Integer)2,testMethod.getDurationMs());
		assertNotEquals((Integer)14,(Integer)testMethod.getFinishedAt().getDay());
		assertNotEquals("value2",testMethod.getName());
		assertNotEquals("value2",testMethod.getSignature());
		assertNotEquals("value2",testMethod.getReporterOutput());
		assertNotEquals((Integer)14,(Integer)testMethod.getStartedAt().getDay());
		assertNotEquals("value2",testMethod.getStatus());
		assertNotEquals((Integer)2,testMethod.getParams().getParam().getIndex());
		assertNotEquals("value2",testMethod.getParams().getParam().getValue());
		
		
	}
}
