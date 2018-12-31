
package com.infosys.utilities.qualitia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
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

import com.infosys.utilities.qualitia.QualitiaTest.Suite;
import com.infosys.utilities.qualitia.QualitiaTest.Suite.TCs;
import com.infosys.utilities.qualitia.QualitiaTest.Suite.TCs.TC;
import com.infosys.utilities.qualitia.QualitiaTest.Suite.TCs.TC.TCIteration;

public class QualitiaTestTest {

	@Test
	public void testEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		QualitiaTest qualitiaTest=new QualitiaTest();
		List<QualitiaTest.Suite> suites=new ArrayList<>();
		
		qualitiaTest.setBrowser("value");
		qualitiaTest.setBrowserVersion("value");
		qualitiaTest.setBuildNumber(BigDecimal.valueOf(1.0));
		qualitiaTest.setCreateDebugLog(true);
		qualitiaTest.setCreateErrorLog(true);
		qualitiaTest.setCreateInfoLog(true);
		qualitiaTest.setExecutionType("value");
		qualitiaTest.setHostName("value");
		qualitiaTest.setOS("value");
		qualitiaTest.setOSVersion(BigDecimal.valueOf(1.0));
		qualitiaTest.setProjectName("value");
		qualitiaTest.setReleaseNumber(BigDecimal.valueOf(1.0));
		qualitiaTest.setSuite(suites);
		qualitiaTest.setSuiteExecutionID("value");
		qualitiaTest.setSuiteIterationNumber(1);
		qualitiaTest.setTool("value");
		qualitiaTest.setUserName(1);
		
		assertEquals("value",qualitiaTest.getBrowser());
		assertEquals("value",qualitiaTest.getBrowserVersion());
		assertEquals(BigDecimal.valueOf(1.0),qualitiaTest.getBuildNumber());
		assertEquals("value",qualitiaTest.getExecutionType());
		assertEquals("value",qualitiaTest.getHostName());
		assertEquals("value",qualitiaTest.getOS());
		assertEquals(BigDecimal.valueOf(1.0),qualitiaTest.getOSVersion());
		assertEquals("value",qualitiaTest.getProjectName());
		assertEquals(BigDecimal.valueOf(1.0),qualitiaTest.releaseNumber);
		assertEquals("value",qualitiaTest.getSuiteExecutionID());
		assertEquals("value",qualitiaTest.getTool());
		assertEquals(0,qualitiaTest.getSuite().size());
		
		Suite suite=new Suite();
		List<QualitiaTest.Suite.TCs> tCss =new ArrayList<>();
		
		suite.setEndTime(xmlGregCal1);
		suite.setExecutionTime(xmlGregCal1);
		suite.setId("value");
		suite.setName("value");
		suite.setScheduleName("value");
		suite.setStartTime(xmlGregCal1);
		suite.settCs(tCss);
		
		assertEquals((Integer) 24,(Integer)suite.getEndTime().getDay());
		assertEquals((Integer) 24,(Integer)suite.getExecutionTime().getDay());
		assertEquals("value",suite.getId());
		assertEquals("value",suite.getName());
		assertEquals("value",suite.getScheduleName());
		assertEquals((Integer) 24,(Integer)suite.getStartTime().getDay());
		assertEquals(0,suite.gettCs().size());
		
		TCs tCs=new TCs();
		assertEquals(0,tCs.getTC().size());
		
		TC tC=new TC();
		TCIteration tCIteration=new TCIteration();
		
		tCIteration.setExecutionTime(xmlGregCal1);
		tCIteration.setIterationNo(1);
		tCIteration.setStatus(1);
		tCIteration.setTCDataSetTag("value");
		
		tC.setDesc("value");
		tC.setEndTime(xmlGregCal1);
		tC.setExecutionTime(xmlGregCal1);
		tC.setManualTCId("value");
		tC.setOnError("value");
		tC.setReportLink("value");
		tC.setRunId("value");
		tC.setScenarioId("value");
		tC.setScenarioName("value");
		tC.setStartTime(xmlGregCal1);
		tC.setStatus(1);
		tC.setTcId("value");
		tC.setTCIteration(tCIteration);
		tC.setTCName("value");
		tC.setTCSeqId(1);
		
		assertEquals("value",tC.getDesc());
		assertEquals((Integer) 24,(Integer)tC.getEndTime().getDay());
		assertEquals((Integer) 24,(Integer)tC.getExecutionTime().getDay());
		assertEquals("value",tC.getManualTCId());
		assertEquals("value",tC.getOnError());
		assertEquals("value",tC.getReportLink());
		assertEquals("value",tC.getRunId());
		assertEquals("value",tC.getScenarioId());
		assertEquals("value",tC.getScenarioName());
		assertEquals((Integer) 24,(Integer)tC.getStartTime().getDay());
		assertEquals((Integer) 1,tC.getStatus());
		assertEquals("value",tC.getTcId());
		assertEquals((Integer) 24,(Integer)tC.getTCIteration().getExecutionTime().getDay());
		assertEquals("value",tC.getTCIteration().getTCDataSetTag());
		assertEquals((Integer) 1,tC.getTCIteration().getIterationNo());
		assertEquals((Integer) 1,tC.getTCIteration().getStatus());
		assertEquals("value",tC.getTCName());
		assertEquals((Integer) 1,tC.getTCSeqId());
	}
	
	@Test
	public void testNotEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		QualitiaTest qualitiaTest=new QualitiaTest();
		List<QualitiaTest.Suite> suites=new ArrayList<>();
		
		qualitiaTest.setBrowser("value");
		qualitiaTest.setBrowserVersion("value");
		qualitiaTest.setBuildNumber(BigDecimal.valueOf(1.0));
		qualitiaTest.setCreateDebugLog(true);
		qualitiaTest.setCreateErrorLog(true);
		qualitiaTest.setCreateInfoLog(true);
		qualitiaTest.setExecutionType("value");
		qualitiaTest.setHostName("value");
		qualitiaTest.setOS("value");
		qualitiaTest.setOSVersion(BigDecimal.valueOf(1.0));
		qualitiaTest.setProjectName("value");
		qualitiaTest.setReleaseNumber(BigDecimal.valueOf(1.0));
		qualitiaTest.setSuite(suites);
		qualitiaTest.setSuiteExecutionID("value");
		qualitiaTest.setSuiteIterationNumber(1);
		qualitiaTest.setTool("value");
		qualitiaTest.setUserName(1);
		
		assertNotEquals("value1",qualitiaTest.getBrowser());
		assertNotEquals("value1",qualitiaTest.getBrowserVersion());
		assertNotEquals(BigDecimal.valueOf(2.0),qualitiaTest.getBuildNumber());
		assertNotEquals("value1",qualitiaTest.getExecutionType());
		assertNotEquals("value1",qualitiaTest.getHostName());
		assertNotEquals("value1",qualitiaTest.getOS());
		assertNotEquals(BigDecimal.valueOf(2.0),qualitiaTest.getOSVersion());
		assertNotEquals("value1",qualitiaTest.getProjectName());
		assertNotEquals(BigDecimal.valueOf(2.0),qualitiaTest.releaseNumber);
		assertNotEquals("value1",qualitiaTest.getSuiteExecutionID());
		assertNotEquals("value1",qualitiaTest.getTool());
		assertNotEquals(1,qualitiaTest.getSuite().size());
		
		Suite suite=new Suite();
		List<QualitiaTest.Suite.TCs> tCss =new ArrayList<>();
		
		suite.setEndTime(xmlGregCal1);
		suite.setExecutionTime(xmlGregCal1);
		suite.setId("value");
		suite.setName("value");
		suite.setScheduleName("value");
		suite.setStartTime(xmlGregCal1);
		suite.settCs(tCss);
		
		assertNotEquals((Integer) 23,(Integer)suite.getEndTime().getDay());
		assertNotEquals((Integer) 23,(Integer)suite.getExecutionTime().getDay());
		assertNotEquals("value1",suite.getId());
		assertNotEquals("value1",suite.getName());
		assertNotEquals("value1",suite.getScheduleName());
		assertNotEquals((Integer) 23,(Integer)suite.getStartTime().getDay());
		assertNotEquals(1,suite.gettCs().size());
		
		TCs tCs=new TCs();
		assertNotEquals(0,tCs.getTC().size());
		
		TC tC=new TC();
		TCIteration tCIteration=new TCIteration();
		
		tCIteration.setExecutionTime(xmlGregCal1);
		tCIteration.setIterationNo(1);
		tCIteration.setStatus(1);
		tCIteration.setTCDataSetTag("value");
		
		tC.setDesc("value");
		tC.setEndTime(xmlGregCal1);
		tC.setExecutionTime(xmlGregCal1);
		tC.setManualTCId("value");
		tC.setOnError("value");
		tC.setReportLink("value");
		tC.setRunId("value");
		tC.setScenarioId("value");
		tC.setScenarioName("value");
		tC.setStartTime(xmlGregCal1);
		tC.setStatus(1);
		tC.setTcId("value");
		tC.setTCIteration(tCIteration);
		tC.setTCName("value");
		tC.setTCSeqId(1);
		
		assertNotEquals("value1",tC.getDesc());
		assertNotEquals((Integer) 23,(Integer)tC.getEndTime().getDay());
		assertNotEquals((Integer) 23,(Integer)tC.getExecutionTime().getDay());
		assertNotEquals("value1",tC.getManualTCId());
		assertNotEquals("value1",tC.getOnError());
		assertNotEquals("value1",tC.getReportLink());
		assertNotEquals("value1",tC.getRunId());
		assertNotEquals("value1",tC.getScenarioId());
		assertNotEquals("value1",tC.getScenarioName());
		assertNotEquals((Integer) 23,(Integer)tC.getStartTime().getDay());
		assertNotEquals((Integer) 0,tC.getStatus());
		assertNotEquals("value1",tC.getTcId());
		assertNotEquals((Integer) 24,(Integer)tC.getTCIteration().getExecutionTime().getDay());
		assertNotEquals("value1",tC.getTCIteration().getTCDataSetTag());
		assertNotEquals((Integer) 0,tC.getTCIteration().getIterationNo());
		assertNotEquals((Integer) 0,tC.getTCIteration().getStatus());
		assertNotEquals("value1",tC.getTCName());
		assertNotEquals((Integer) 0,tC.getTCSeqId());
	}
}
