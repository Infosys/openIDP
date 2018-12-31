package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class CoverageDSPrivjsonTest {

	@Test
	public void covtest(){
		CoverageDSPrivJson cd = new CoverageDSPrivJson();
		cd.setBlockCoverage("100");
		
		Assert.assertEquals("100", cd.getBlockCoverage());
	}
	
	@Test
	public void linecovtest(){
		CoverageDSPrivJson cd = new CoverageDSPrivJson();
		cd.setLineCoverage("100");
		
		Assert.assertEquals("100", cd.getLineCoverage());
	}
	
	@Test
	public void test(){
		CoverageDSPrivJson cd = new CoverageDSPrivJson();
		cd.setModuleName("module");
	
		Assert.assertEquals("module", cd.getModuleName());
	}
}
