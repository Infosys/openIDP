
package com.infosys.utilities.jmeter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.infosys.utilities.jmeter.TestResults.HttpSample;
import com.infosys.utilities.jmeter.TestResults.HttpSample.AssertionResult;
import com.infosys.utilities.jmeter.TestResults.Sample;

public class TestResultsTest {

	@Test
	public void testEquals()
	{
		TestResults testResults=new TestResults();
		testResults.setVersion(BigDecimal.valueOf(1.0));
		assertEquals(BigDecimal.valueOf(1.0),testResults.getVersion());
		
		HttpSample httpSample=new HttpSample();
		
		AssertionResult assertionResult=new AssertionResult();
		assertionResult.setError(true);
		assertionResult.setFailure(true);
		assertionResult.setName("value");
		
		httpSample.setAssertionResult(assertionResult);
		httpSample.setBy(1);
		httpSample.setCt(1);
		httpSample.setDt("value");
		httpSample.setIt(1);
		httpSample.setLb("value");
		httpSample.setLt(1);
		httpSample.setNa(1);
		httpSample.setNg(1);
		httpSample.setRc("value");
		httpSample.setRm("value");
		httpSample.setS(true);
		httpSample.setSby(1);
		httpSample.setT(1);
		httpSample.setTn("value");
		httpSample.setTs(BigDecimal.valueOf(1.0));
		
		assertEquals("value",httpSample.getAssertionResult().getName());
		assertEquals((Integer)1,httpSample.getBy());
		assertEquals((Integer)1,httpSample.getCt());
		assertEquals("value",httpSample.getDt());
		assertEquals((Integer)1,httpSample.getIt());
		assertEquals("value",httpSample.getLb());
		assertEquals((Integer)1,httpSample.getLt());
		assertEquals((Integer)1,httpSample.getNa());
		assertEquals((Integer)1,httpSample.getNg());
		assertEquals("value",httpSample.getRc());
		assertEquals("value",httpSample.getRm());
		assertEquals((Integer)1,httpSample.getSby());
		assertEquals((Integer)1,httpSample.getT());
		assertEquals("value",httpSample.getTn());
		assertEquals("value",httpSample.getTs());
		assertEquals(BigDecimal.valueOf(1.0),httpSample.getAssertionResult().getName());
		
		Sample sample=new Sample();
		
		sample.setBy(1);
		sample.setCt(1);
		sample.setDt("value");
		sample.setIt(1);
		sample.setLb("value");
		sample.setLt(1);
		sample.setNa(1);
		sample.setNg(1);
		sample.setRc("value");
		sample.setRm("value");
		sample.setS(true);
		sample.setSby(1);
		sample.setT(1);
		sample.setTn("value");
		sample.setTs(BigDecimal.valueOf(1.0));
		
		assertEquals((Integer)1,sample.getBy());
		assertEquals((Integer)1,sample.getCt());
		assertEquals((Integer)1,sample.getIt());
		assertEquals((Integer)1,sample.getLt());
		assertEquals((Integer)1,sample.getNa());
		assertEquals((Integer)1,sample.getNg());
		assertEquals((Integer)1,sample.getSby());
		assertEquals((Integer)1,sample.getT());
		assertEquals("value",sample.getDt());
		assertEquals("value",sample.getLb());
		assertEquals("value",sample.getRc());
		assertEquals("value",sample.getRm());
		assertEquals("value",sample.getTn());
		assertEquals(BigDecimal.valueOf(1.0),sample.getTs());
		
		
	}
	
	@Test
	public void testNotEquals()
	{
		TestResults testResults=new TestResults();
		testResults.setVersion(BigDecimal.valueOf(1.0));
		assertNotEquals(BigDecimal.valueOf(1.0),testResults.getVersion());
		
		HttpSample httpSample=new HttpSample();
		
		AssertionResult assertionResult=new AssertionResult();
		assertionResult.setError(true);
		assertionResult.setFailure(true);
		assertionResult.setName("value");
		
		httpSample.setAssertionResult(assertionResult);
		httpSample.setBy(1);
		httpSample.setCt(1);
		httpSample.setDt("value");
		httpSample.setIt(1);
		httpSample.setLb("value");
		httpSample.setLt(1);
		httpSample.setNa(1);
		httpSample.setNg(1);
		httpSample.setRc("value");
		httpSample.setRm("value");
		httpSample.setS(true);
		httpSample.setSby(1);
		httpSample.setT(1);
		httpSample.setTn("value");
		httpSample.setTs(BigDecimal.valueOf(1.0));
		
		assertNotEquals("value",httpSample.getAssertionResult().getName());
		assertNotEquals((Integer)1,httpSample.getBy());
		assertNotEquals((Integer)1,httpSample.getCt());
		assertNotEquals("value",httpSample.getDt());
		assertNotEquals((Integer)1,httpSample.getIt());
		assertNotEquals("value",httpSample.getLb());
		assertNotEquals((Integer)1,httpSample.getLt());
		assertNotEquals((Integer)1,httpSample.getNa());
		assertNotEquals((Integer)1,httpSample.getNg());
		assertNotEquals("value",httpSample.getRc());
		assertNotEquals("value",httpSample.getRm());
		assertNotEquals((Integer)1,httpSample.getSby());
		assertNotEquals((Integer)1,httpSample.getT());
		assertNotEquals("value",httpSample.getTn());
		assertNotEquals("value",httpSample.getTs());
		assertNotEquals(BigDecimal.valueOf(1.0),httpSample.getAssertionResult().getName());
		
		Sample sample=new Sample();
		
		sample.setBy(1);
		sample.setCt(1);
		sample.setDt("value");
		sample.setIt(1);
		sample.setLb("value");
		sample.setLt(1);
		sample.setNa(1);
		sample.setNg(1);
		sample.setRc("value");
		sample.setRm("value");
		sample.setS(true);
		sample.setSby(1);
		sample.setT(1);
		sample.setTn("value");
		sample.setTs(BigDecimal.valueOf(1.0));
		
		assertNotEquals((Integer)1,sample.getBy());
		assertNotEquals((Integer)1,sample.getCt());
		assertNotEquals((Integer)1,sample.getIt());
		assertNotEquals((Integer)1,sample.getLt());
		assertNotEquals((Integer)1,sample.getNa());
		assertNotEquals((Integer)1,sample.getNg());
		assertNotEquals((Integer)1,sample.getSby());
		assertNotEquals((Integer)1,sample.getT());
		assertNotEquals("value",sample.getDt());
		assertNotEquals("value",sample.getLb());
		assertNotEquals("value",sample.getRc());
		assertNotEquals("value",sample.getRm());
		assertNotEquals("value",sample.getTn());
		assertNotEquals(BigDecimal.valueOf(1.0),sample.getTs());
		
		
	}
}
