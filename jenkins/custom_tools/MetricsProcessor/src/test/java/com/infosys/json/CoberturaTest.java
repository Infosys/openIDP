
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class CoberturaTest {
	@Test
	
	public void branchcoveragetest(){
		Cobertura c = new Cobertura();
		
		c.setBranchCoverage("10");
		Assert.assertEquals("10", c.getBranchCoverage());
	}
	
	@Test
	
	public void classcoveragetest(){
		Cobertura c = new Cobertura();
		
		c.setClassCoverage("10");
		Assert.assertEquals("10", c.getClassCoverage());
	}

	@Test

	public void complexitytest(){
		Cobertura c = new Cobertura();
		
		c.setComplexityScore("10");
		Assert.assertEquals("10", c.getComplexityScore());
	}
	@Test

	public void instructiontest(){
		Cobertura c = new Cobertura();
		
		c.setInstructionCoverage("10");
		Assert.assertEquals("10", c.getInstructionCoverage());
	}	
	
	@Test
	
	public void methodcoveragetest(){
		Cobertura c = new Cobertura();
		
		c.setMethodCoverage("100");
		
		Assert.assertEquals("100", c.getMethodCoverage());
	}
	
	@Test
	
	public void methodlinecoverage(){
		Cobertura c = new Cobertura();
		
		c.setLineCoverage("100");
		
		Assert.assertEquals("100", c.getLineCoverage());
	}
	
}
