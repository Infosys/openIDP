package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class JacocoTest {

	@Test
	public void branchtest(){
		Jacoco jc = new Jacoco();
		
		jc.setBranchCoverage("100");
		Assert.assertEquals("100", jc.getBranchCoverage());
	}
	
	@Test
	public void classcoveragetest(){
		Jacoco jc = new Jacoco();
		
		jc.setClassCoverage("100");
		Assert.assertEquals("100", jc.getClassCoverage());
	}
	
	@Test
	public void complexitytest(){
		Jacoco jc = new Jacoco();
		
		jc.setComplexityScore("100");
		Assert.assertEquals("100", jc.getComplexityScore());
	}
	
	@Test
	public void insttest(){
		Jacoco jc = new Jacoco();
		
		jc.setInstructionCoverage("100");
		Assert.assertEquals("100", jc.getInstructionCoverage());
	}
	
	@Test
	public void linecoveragetest(){
		Jacoco jc = new Jacoco();
		
		jc.setLineCoverage("100");
		Assert.assertEquals("100", jc.getLineCoverage());
	}
	
	@Test
	public void test(){
		Jacoco jc = new Jacoco();
		
		jc.setMethodCoverage("100");
		Assert.assertEquals("100", jc.getMethodCoverage());
	}
}
