/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.checkmarx;

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



public class CxXMLResultsTest {

	@Test
	public void testEquals() throws ParseException, DatatypeConfigurationException
	{
		CxXMLResults cxXMLResults=new CxXMLResults();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		cxXMLResults.setCheckmarxVersion(xmlGregCal1);
		cxXMLResults.setDeepLink("deeplink");
		cxXMLResults.setFilesScanned(1);
		cxXMLResults.setInitiatorName("name");
		cxXMLResults.setLinesOfCodeScanned(10);
		cxXMLResults.setOwner("owner");
		cxXMLResults.setPreset("preset");
		cxXMLResults.setProjectId(1);
		cxXMLResults.setProjectName("name");
		
		cxXMLResults.setReportCreationTime(xmlGregCal1);
		cxXMLResults.setScanComments("comments");
		cxXMLResults.setScanId(1);
		cxXMLResults.setScanStart(xmlGregCal1);
		cxXMLResults.setScanTime("time");
		cxXMLResults.setScanType("type");
		cxXMLResults.setSourceOrigin("source");
		cxXMLResults.setTeam("team");
		cxXMLResults.setTeamFullPathOnReportDate("path");
		cxXMLResults.setVisibility("visibility");
		
		assertEquals("2014-04-24 11:15:00",cxXMLResults.getCheckmarxVersion().toString());
		assertEquals("deeplink",cxXMLResults.getDeepLink());
		assertEquals((Integer)1,cxXMLResults.getFilesScanned());
		assertEquals("name",cxXMLResults.getInitiatorName());
		assertEquals((Integer)10,cxXMLResults.getLinesOfCodeScanned());
		assertEquals("owner",cxXMLResults.getOwner());
		assertEquals("preset",cxXMLResults.getPreset());
		assertEquals((Integer)1,cxXMLResults.getProjectId());
		assertEquals("name",cxXMLResults.getProjectName());
		
		assertEquals("2014-04-24 11:15:00",cxXMLResults.getReportCreationTime().toString());
		assertEquals("comments",cxXMLResults.getScanComments());
		assertEquals((Integer)1,cxXMLResults.getScanId());
		assertEquals("2014-04-24 11:15:00",cxXMLResults.getScanStart().toString());
		assertEquals("time",cxXMLResults.getScanTime());
		assertEquals("type",cxXMLResults.getScanType());
		assertEquals("source",cxXMLResults.getSourceOrigin());
		assertEquals("team",cxXMLResults.getTeam());
		assertEquals("path",cxXMLResults.getTeamFullPathOnReportDate());
		assertEquals("visibility",cxXMLResults.getVisibility());
		
	}
	
	@Test
	public void testNotEquals() throws ParseException, DatatypeConfigurationException
	{
		CxXMLResults cxXMLResults=new CxXMLResults();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		cxXMLResults.setCheckmarxVersion(xmlGregCal1);
		cxXMLResults.setDeepLink("deeplink");
		cxXMLResults.setFilesScanned(1);
		cxXMLResults.setInitiatorName("name");
		cxXMLResults.setLinesOfCodeScanned(10);
		cxXMLResults.setOwner("owner");
		cxXMLResults.setPreset("preset");
		cxXMLResults.setProjectId(1);
		cxXMLResults.setProjectName("name");
		
		cxXMLResults.setReportCreationTime(xmlGregCal1);
		cxXMLResults.setScanComments("comments");
		cxXMLResults.setScanId(1);
		cxXMLResults.setScanStart(xmlGregCal1);
		cxXMLResults.setScanTime("time");
		cxXMLResults.setScanType("type");
		cxXMLResults.setSourceOrigin("source");
		cxXMLResults.setTeam("team");
		cxXMLResults.setTeamFullPathOnReportDate("path");
		cxXMLResults.setVisibility("visibility");
		
		assertNotEquals("2014-04-24 11:15:12",cxXMLResults.getCheckmarxVersion().toString());
		assertNotEquals("deeplink1",cxXMLResults.getDeepLink());
		assertNotEquals((Integer)11,cxXMLResults.getFilesScanned());
		assertNotEquals("name1",cxXMLResults.getInitiatorName());
		assertNotEquals((Integer)12,cxXMLResults.getLinesOfCodeScanned());
		assertNotEquals("owner1",cxXMLResults.getOwner());
		assertNotEquals("preset1",cxXMLResults.getPreset());
		assertNotEquals((Integer)11,cxXMLResults.getProjectId());
		assertNotEquals("name1",cxXMLResults.getProjectName());
		
		assertNotEquals("2014-04-24 11:15:12",cxXMLResults.getReportCreationTime().toString());
		assertNotEquals("comments1",cxXMLResults.getScanComments());
		assertNotEquals((Integer)11,cxXMLResults.getScanId());
		assertNotEquals("2014-04-24 11:15:12",cxXMLResults.getScanStart().toString());
		assertNotEquals("time1",cxXMLResults.getScanTime());
		assertNotEquals("type1",cxXMLResults.getScanType());
		assertNotEquals("source1",cxXMLResults.getSourceOrigin());
		assertNotEquals("team1",cxXMLResults.getTeam());
		assertNotEquals("path1",cxXMLResults.getTeamFullPathOnReportDate());
		assertNotEquals("visibility1",cxXMLResults.getVisibility());
		
	}
}
