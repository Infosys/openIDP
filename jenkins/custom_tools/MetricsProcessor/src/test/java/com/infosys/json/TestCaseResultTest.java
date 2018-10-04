
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class TestCaseResultTest {

	@Test
	public void categorytest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setCategory("infosys");
		
		Assert.assertEquals("infosys", tcr.getCategory());
	}
	
	@Test
	public void classnametest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setClassName("infosys");
		
		Assert.assertEquals("infosys", tcr.getClassName());
	}
	
	@Test
	public void durationtest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setDuration("01");
		
		Assert.assertEquals("01", tcr.getDuration());
	}
	
	@Test
	public void idtest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setId("01");
		
		Assert.assertEquals("01", tcr.getId());
	}
	
	@Test
	public void msgtest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setMessage("infosys");
		
		Assert.assertEquals("infosys", tcr.getMessage());
	}
	
	@Test
	public void starttimetest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setStartTime("01");
		
		Assert.assertEquals("01", tcr.getStartTime());
	}
	
	@Test
	public void pendingtest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.setStatus("pending");
		
		Assert.assertEquals("pending", tcr.getStatus());
	}
	
	@Test
	public void suitenametest(){
		TestCaseResult tcr = new TestCaseResult();
		
		tcr.settestSuiteName("infosys");
		
		Assert.assertEquals("infosys", tcr.gettestSuiteName());
	}
}
