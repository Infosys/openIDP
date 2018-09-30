package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class CodecoveragedetailsTest {

	@Test
	public void coveragetest(){
		CodeCoverageDetails ccd = new CodeCoverageDetails();
		
		ccd.setBranchCoverage("100");
		
		Assert.assertEquals("100", ccd.getBranchCoverage());
	}
	
	@Test
	public void classtest(){
		CodeCoverageDetails ccd = new CodeCoverageDetails();
		
		ccd.setClassCoverage("100");
		
		Assert.assertEquals("100", ccd.getClassCoverage());
	}
	
	@Test
	public void scoretest(){
		CodeCoverageDetails ccd = new CodeCoverageDetails();
		
		ccd.setComplexityScore("100");
		
		Assert.assertEquals("100", ccd.getComplexityScore());
	}
	
	@Test
	public void insttest(){
		CodeCoverageDetails ccd = new CodeCoverageDetails();
		
		ccd.setInstructionCoverage("100");
		
		Assert.assertEquals("100", ccd.getInstructionCoverage());
	}
	
	@Test
	public void linetest(){
		CodeCoverageDetails ccd = new CodeCoverageDetails();
		
		ccd.setLineCoverage("100");
		
		Assert.assertEquals("100", ccd.getLineCoverage());
	}
	
	@Test
	public void classcovtest(){
		CodeCoverageDetails ccd = new CodeCoverageDetails();
		
		ccd.setClassCoverage("100");
		
		Assert.assertEquals("100", ccd.getClassCoverage());
	}
	
}
