package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class RuleSetTest {

	@Test
	public void cattest(){
		RuleSet rs = new RuleSet();
		
		rs.setcategory("category");
		Assert.assertEquals("category", rs.getcategory());
	}
	
	@Test
	public void idtest(){
		RuleSet rs = new RuleSet();
		
		rs.setId("1");
		Assert.assertEquals("1", rs.getId());
	}
	
	@Test
	public void linetest(){
		RuleSet rs = new RuleSet();
		
		rs.setLine("10");
		Assert.assertEquals("10", rs.getLine());
	}
	
	@Test
	public void msgtest(){
		RuleSet rs = new RuleSet();
		
		rs.setMessage("msg");
		Assert.assertEquals("msg", rs.getMessage());
	}
	
	@Test
	public void rnametest(){
		RuleSet rs = new RuleSet();
		
		rs.setruleName("rname");
		Assert.assertEquals("rname", rs.getruleName());
	}
	
	@Test
	public void severitytest(){
		RuleSet rs = new RuleSet();
		
		rs.setSeverity("100");
		Assert.assertEquals("100", rs.getSeverity());
	}
}
