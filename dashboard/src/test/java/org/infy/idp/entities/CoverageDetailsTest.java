package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class CoverageDetailsTest {

	@Test
	public void cattest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setCategory("infosys");
		
		Assert.assertEquals("infosys", cd.getCategory());
	}
	
	@Test
	public void clstest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setClassName("infosys");
		
		Assert.assertEquals("infosys", cd.getClassName());
	}
	
	@Test
	public void linetest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setLineCoverage("infosys");
		
		Assert.assertEquals("infosys", cd.getLineCoverage());
	}
	
	@Test
	public void pkgtest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setPckage("infosys");
		
		Assert.assertEquals("infosys", cd.getPckage());
	}
}
