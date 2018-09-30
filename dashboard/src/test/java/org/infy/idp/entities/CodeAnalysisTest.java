package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class CodeAnalysisTest {

	@Test
	public void categorytest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setcategory("ts");
		
		Assert.assertEquals("ts", ca.getcategory());
	}
	
	@Test
	public void clstest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setClassName("ts");
		
		Assert.assertEquals("ts", ca.getClassName());
	}
	
	@Test
	public void idtest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setId("1");
		
		Assert.assertEquals("1", ca.getId());
	}
	
	@Test
	public void linetest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setLine("10");
		
		Assert.assertEquals("10", ca.getLine());
	}
	
	@Test
	public void msgtest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setMessage("ts");
		
		Assert.assertEquals("ts", ca.getMessage());
	}
	
	@Test
	public void recommendationtest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setRecommendation("ts");
		
		Assert.assertEquals("ts", ca.getRecommendation());
	}
	@Test
	public void ruletest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setruleName("ts");
		
		Assert.assertEquals("ts", ca.getruleName());
	}
	
	@Test
	public void test(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setSeverity("100");;
		
		Assert.assertEquals("100", ca.getSeverity());
	}
}
