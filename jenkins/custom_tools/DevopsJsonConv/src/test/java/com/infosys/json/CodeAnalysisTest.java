
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class CodeAnalysisTest {

	@Test
	public void idtest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setId("1");
	
		Assert.assertEquals("1", ca.getId());
	}
	
	@Test
	public void cattest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setCategory("cat");
	
		Assert.assertEquals("cat", ca.getCategory());
	}
	
	@Test
	public void catetest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setcategory("ts");
	
		Assert.assertEquals("ts", ca.getcategory());
	}
	
	@Test
	public void classtest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setClassName("clsname");
	
		Assert.assertEquals("clsname", ca.getClassName());
	}

	@Test
	public void linetest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setLine("100");
	
		Assert.assertEquals("100", ca.getLine());
	}
	
	@Test
	public void msgtest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setMessage("msg");
	
		Assert.assertEquals("msg", ca.getMessage());
	}
	
	@Test
	public void recotest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setRecommendation("reco");
	
		Assert.assertEquals("reco", ca.getRecommendation());
	}
	
	@Test
	public void ruletest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setruleName("rulename");
	
		Assert.assertEquals("rulename", ca.getruleName());
	}
	
	@Test
	public void rulestest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setRuleName("rulename");
	
		Assert.assertEquals("rulename", ca.getRuleName());
	}
	
	@Test
	public void severity(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setSeverity("100");
	
		Assert.assertEquals("100", ca.getSeverity());
	}
}
