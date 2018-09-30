package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class coberturatest {

	@Test
	public void bcoveragetest(){
		Cobertura c = new Cobertura();
		
		c.setBranchCoverage("10");
		
		Assert.assertEquals("10", c.getBranchCoverage());
	}
	
	@Test
	public void classtest(){
		Cobertura c = new Cobertura();
		
		c.setClassCoverage("10");;
		
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
	public void coveragetest(){
		Cobertura c = new Cobertura();
		
		c.setLineCoverage("10");
		
		Assert.assertEquals("10", c.getLineCoverage());
	}
	
	
	@Test
	public void mcoveragetest(){
		Cobertura c = new Cobertura();
		
		c.setMethodCoverage("10");
		
		Assert.assertEquals("10", c.getMethodCoverage());
	}
}
