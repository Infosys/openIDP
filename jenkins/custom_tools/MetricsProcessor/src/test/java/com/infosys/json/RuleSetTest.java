
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class RuleSetTest {

	@Test
	public void categorytest(){
		
		RuleSet rs = new RuleSet();
		
		rs.setcategory("new");
		
		Assert.assertEquals("new", rs.getcategory());
	}
	
	@Test
	public void idtest(){
		
		RuleSet rs = new RuleSet();
		
		rs.setId("new");
		
		Assert.assertEquals("new", rs.getId());
	}
	
	@Test
	public void linetest(){
		
		RuleSet rs = new RuleSet();
		
		rs.setLine("new");
		
		Assert.assertEquals("new", rs.getLine());
	}
	
	@Test
	public void msgtest(){
		
		RuleSet rs = new RuleSet();
		
		rs.setMessage("new");
		
		Assert.assertEquals("new", rs.getMessage());
	}
	
	@Test
	public void ruletest(){
		
		RuleSet rs = new RuleSet();
		
		rs.setruleName("new");
		
		Assert.assertEquals("new", rs.getruleName());
	}
	
	@Test
	public void test(){
		
		RuleSet rs = new RuleSet();
		
		rs.setSeverity("new");
		
		Assert.assertEquals("new", rs.getSeverity());
	}
}
