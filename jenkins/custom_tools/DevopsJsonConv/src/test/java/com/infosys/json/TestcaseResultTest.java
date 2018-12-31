package com.infosys.json;
import org.junit.Test;

import junit.framework.Assert;

public class TestcaseResultTest {

	@Test
	public void cattest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setCategory("cat");
		
		Assert.assertEquals("cat", tcr.getCategory());
	}
	
	@Test
	public void durtest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setDuration("dur");
		
		Assert.assertEquals("dur", tcr.getDuration());
	}
	
	@Test
	public void idtest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setId("1");;
		
		Assert.assertEquals("1", tcr.getId());
	}
	
	@Test
	public void msgtest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setMessage("msg");
		
		Assert.assertEquals("msg", tcr.getMessage());
	}
	
	@Test
	public void timetest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setStartTime("10");
		
		Assert.assertEquals("10", tcr.getStartTime());
	}
	
	@Test
	public void statustest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setStatus("success");
		
		Assert.assertEquals("success", tcr.getStatus());
	}
	
	@Test
	public void tstest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.settestSuiteName("ts");
		
		Assert.assertEquals("ts", tcr.gettestSuiteName());
	}
}
