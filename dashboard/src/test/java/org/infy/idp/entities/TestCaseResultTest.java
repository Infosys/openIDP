package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class TestCaseResultTest {

	@Test
	public void categorytest(){
		TestCaseResult tc = new TestCaseResult();
		
		tc.setCategory("try");
		
		Assert.assertEquals("try",tc.getCategory());
	}
	
	@Test
	public void classtest(){
		TestCaseResult tc = new TestCaseResult();
		
		tc.setClassName("try");
		
		Assert.assertEquals("try",tc.getClassName());
	}
	
	@Test
	public void durationtest(){
		TestCaseResult tc = new TestCaseResult();
		
		tc.setDuration("10");
		
		Assert.assertEquals("10",tc.getDuration());
	}
	
	@Test
	public void idtest(){
		TestCaseResult tc = new TestCaseResult();
		
		tc.setId("1");
		
		Assert.assertEquals("1",tc.getId());
	}
	
	@Test
	public void msgtest(){
		TestCaseResult tc = new TestCaseResult();
		
		tc.setMessage("Welcome");
		
		Assert.assertEquals("Welcome",tc.getMessage());
	}
	
	@Test
	public void timetest(){
		TestCaseResult tc = new TestCaseResult();
		
		tc.setStartTime("10");
		
		Assert.assertEquals("10",tc.getStartTime());
	}
	
	@Test
	public void statustest(){
		TestCaseResult tc = new TestCaseResult();
		
		tc.setStatus("pass");
		
		Assert.assertEquals("pass",tc.getStatus());
	}
	
	@Test
	public void tstest(){
		TestCaseResult tc = new TestCaseResult();
		
		tc.settestSuiteName("ts");
		
		Assert.assertEquals("ts",tc.gettestSuiteName());
	}
}
