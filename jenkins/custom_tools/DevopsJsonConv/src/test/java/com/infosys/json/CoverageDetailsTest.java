
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class CoverageDetailsTest {

	@Test
	public void Categorytest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setCategory("infosys");
		
		Assert.assertEquals("infosys", cd.getCategory());
	}
	@Test
	public void Classnametest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setClassName("infosys");
		
		Assert.assertEquals("infosys", cd.getClassName());
	}
	
	@Test
	public void LineCoveragetest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setLineCoverage("100");
		
		Assert.assertEquals("100", cd.getLineCoverage());
	}
	
	@Test
	public void test(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setPckage("infosys");
		
		Assert.assertEquals("infosys", cd.getPckage());
	}
	
	@Test
	public void branchtest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setBranchCoverage("100");
		
		Assert.assertEquals("100", cd.getBranchCoverage());
	}
}
