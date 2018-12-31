
package com.infosys.utilities.oats;

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

import com.infosys.utilities.oats.Script.Finish;
import com.infosys.utilities.oats.Script.I18NProperty;
import com.infosys.utilities.oats.Script.Initialize;

public class ScriptTest {

	@Test
	public void testEquals() throws DatatypeConfigurationException, ParseException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Script script=new Script();
		
		I18NProperty i18NProperty=new I18NProperty();
		
		Initialize initialize=new Initialize();
		
		Finish finish=new Finish();
		
		finish.setDuration(1);
		finish.setName("value");
		finish.setResult("value");
		finish.setSummary("value");
		finish.setType("value");
		
		initialize.setDuration(1);
		initialize.setName("value");
		initialize.setResult("value");
		initialize.setSummary("value");
		initialize.setType("value");
		
		i18NProperty.setDuration("value");
		i18NProperty.setName("value");
		i18NProperty.setRecid("value");
		i18NProperty.setResult("value");
		i18NProperty.setSummary("value");
		i18NProperty.setType("value");
		
		script.setDuration(1);
		script.setFinish(finish);
		script.setI18NProperty(i18NProperty);
		script.setInitialize(initialize);
		script.setIterationNum(1);
		script.setName("value");
		script.setReportTime(xmlGregCal1);
		script.setResult("value");
		script.setTestDate(xmlGregCal1);
		script.setType("value");
		script.setWorkspace("value");
		
		assertEquals((Integer) 1,script.getDuration());
		assertEquals((Integer) 1,script.getIterationNum());
		assertEquals("value",script.getName());
		assertEquals("value",script.getResult());
		assertEquals("value",script.getType());
		assertEquals("value",script.getWorkspace());
		assertEquals((Integer) 24,(Integer)script.getReportTime().getDay());
		assertEquals((Integer) 24,(Integer)script.getTestDate().getDay());
		assertEquals((Integer) 1,script.getFinish().getDuration());
		assertEquals("value",script.getFinish().getName());
		assertEquals("value",script.getFinish().getResult());
		assertEquals("value",script.getFinish().getSummary());
		assertEquals("value",script.getFinish().getType());
		assertEquals("value",script.getI18NProperty().getDuration());
		assertEquals("value",script.getI18NProperty().getName());
		assertEquals("value",script.getI18NProperty().getRecid());
		assertEquals("value",script.getI18NProperty().getResult());
		assertEquals("value",script.getI18NProperty().getSummary());
		assertEquals("value",script.getI18NProperty().getType());
		
		assertEquals((Integer) 1,script.getInitialize().getDuration());
		assertEquals("value",script.getInitialize().getName());
		assertEquals("value",script.getInitialize().getResult());
		assertEquals("value",script.getInitialize().getSummary());
		assertEquals("value",script.getInitialize().getType());
		
	}
	
	@Test
	public void testNotEquals() throws DatatypeConfigurationException, ParseException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		Script script=new Script();
		
		I18NProperty i18NProperty=new I18NProperty();
		
		Initialize initialize=new Initialize();
		
		Finish finish=new Finish();
		
		finish.setDuration(1);
		finish.setName("value");
		finish.setResult("value");
		finish.setSummary("value");
		finish.setType("value");
		
		initialize.setDuration(1);
		initialize.setName("value");
		initialize.setResult("value");
		initialize.setSummary("value");
		initialize.setType("value");
		
		i18NProperty.setDuration("value");
		i18NProperty.setName("value");
		i18NProperty.setRecid("value");
		i18NProperty.setResult("value");
		i18NProperty.setSummary("value");
		i18NProperty.setType("value");
		
		script.setDuration(1);
		script.setFinish(finish);
		script.setI18NProperty(i18NProperty);
		script.setInitialize(initialize);
		script.setIterationNum(1);
		script.setName("value");
		script.setReportTime(xmlGregCal1);
		script.setResult("value");
		script.setTestDate(xmlGregCal1);
		script.setType("value");
		script.setWorkspace("value");
		
		assertNotEquals((Integer) 2,script.getDuration());
		assertNotEquals((Integer) 2,script.getIterationNum());
		assertNotEquals("value1",script.getName());
		assertNotEquals("value1",script.getResult());
		assertNotEquals("value1",script.getType());
		assertNotEquals("value1",script.getWorkspace());
		assertNotEquals((Integer) 23,(Integer)script.getReportTime().getDay());
		assertNotEquals((Integer) 23,(Integer)script.getTestDate().getDay());
		assertNotEquals((Integer) 2,script.getFinish().getDuration());
		assertNotEquals("value1",script.getFinish().getName());
		assertNotEquals("value1",script.getFinish().getResult());
		assertNotEquals("value1",script.getFinish().getSummary());
		assertNotEquals("value1",script.getFinish().getType());
		assertNotEquals("value1",script.getI18NProperty().getDuration());
		assertNotEquals("value1",script.getI18NProperty().getName());
		assertNotEquals("value1",script.getI18NProperty().getRecid());
		assertNotEquals("value1",script.getI18NProperty().getResult());
		assertNotEquals("value1",script.getI18NProperty().getSummary());
		assertNotEquals("value1",script.getI18NProperty().getType());
		
		assertNotEquals((Integer) 2,script.getInitialize().getDuration());
		assertNotEquals("value1",script.getInitialize().getName());
		assertNotEquals("value1",script.getInitialize().getResult());
		assertNotEquals("value1",script.getInitialize().getSummary());
		assertNotEquals("value1",script.getInitialize().getType());
		
	}
}
