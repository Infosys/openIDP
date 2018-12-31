
package com.infosys.utilities.pmd;

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

import com.infosys.utilities.pmd.Pmd.File;
import com.infosys.utilities.pmd.Pmd.File.Violation;

public class PmdTest {

	@Test
	public void testEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Pmd pmd=new Pmd();
		Pmd.Error error=new Pmd.Error();
		
		error.setFilename("value");
		error.setMsg("value");
		error.setValue("value");
		
		pmd.setError(error);
		pmd.setTimestamp(xmlGregCal1);
		pmd.setVersion("value");
		
		assertEquals("value",pmd.getError().getFilename());
		assertEquals("value",pmd.getError().getMsg());
		assertEquals("value",pmd.getError().getValue());
		assertEquals(0,pmd.getFile().size());
		assertEquals((Integer) 24,(Integer)pmd.getTimestamp().getDay());
		assertEquals("value",pmd.getVersion());
		
		File file=new File();
		file.setName("value");
		assertEquals(0,file.getViolation().size());
		assertEquals("value",file.getName());
		
		Violation violation=new Violation();
		
		violation.setBegincolumn((short) 1);
		violation.setBeginline((short) 1);
		violation.setClazz("value");
		violation.setEndcolumn((short) 1);
		violation.setEndline((short) 1);
		violation.setExternalInfoUrl("value");
		violation.setMethod("value");
		violation.setPackage("value");
		violation.setPriority(new Byte((byte) 1));
		violation.setRule("value");
		violation.setRuleset("value");
		violation.setValue("value");
		violation.setVariable("value");
		
		assertEquals((short) 1,(short)violation.getBegincolumn());
		assertEquals((short) 1,(short)violation.getBeginline());
		assertEquals("value",violation.getClazz());
		assertEquals((short) 1,(short)violation.getEndcolumn());
		assertEquals((short) 1,(short)violation.getEndline());
		assertEquals("value",violation.getExternalInfoUrl());
		assertEquals("value",violation.getMethod());
		assertEquals("value",violation.getPackage());
		assertEquals(new Byte((byte) 1),violation.getPriority());
		assertEquals("value",violation.getRule());
		assertEquals("value",violation.getRuleset());
		assertEquals("value",violation.getValue());
		assertEquals("value",violation.getVariable());
		
		
	}
	
	@Test
	public void testNotEquals() throws ParseException, DatatypeConfigurationException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Pmd pmd=new Pmd();
		Pmd.Error error=new Pmd.Error();
		
		error.setFilename("value");
		error.setMsg("value");
		error.setValue("value");
		
		pmd.setError(error);
		pmd.setTimestamp(xmlGregCal1);
		pmd.setVersion("value");
		
		assertNotEquals("value1",pmd.getError().getFilename());
		assertNotEquals("value1",pmd.getError().getMsg());
		assertNotEquals("value1",pmd.getError().getValue());
		assertNotEquals(1,pmd.getFile().size());
		assertNotEquals((Integer) 25,(Integer)pmd.getTimestamp().getDay());
		assertNotEquals("value1",pmd.getVersion());
		
		File file=new File();
		file.setName("value");
		assertNotEquals(1,file.getViolation().size());
		assertNotEquals("value1",file.getName());
		
		Violation violation=new Violation();
		
		violation.setBegincolumn((short) 1);
		violation.setBeginline((short) 1);
		violation.setClazz("value");
		violation.setEndcolumn((short) 1);
		violation.setEndline((short) 1);
		violation.setExternalInfoUrl("value");
		violation.setMethod("value");
		violation.setPackage("value");
		violation.setPriority(new Byte((byte) 1));
		violation.setRule("value");
		violation.setRuleset("value");
		violation.setValue("value");
		violation.setVariable("value");
		
		assertNotEquals((short) 2,(short)violation.getBegincolumn());
		assertNotEquals((short) 2,(short)violation.getBeginline());
		assertNotEquals("value1",violation.getClazz());
		assertNotEquals((short) 2,(short)violation.getEndcolumn());
		assertNotEquals((short) 2,(short)violation.getEndline());
		assertNotEquals("value1",violation.getExternalInfoUrl());
		assertNotEquals("value1",violation.getMethod());
		assertNotEquals("value1",violation.getPackage());
		assertNotEquals(new Byte((byte) 2),violation.getPriority());
		assertNotEquals("value1",violation.getRule());
		assertNotEquals("value1",violation.getRuleset());
		assertNotEquals("value1",violation.getValue());
		assertNotEquals("value1",violation.getVariable());
		
		
	}
}
