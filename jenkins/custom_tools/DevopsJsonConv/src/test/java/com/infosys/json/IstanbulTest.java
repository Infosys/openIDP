
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class IstanbulTest {

	@Test
	public void linetest(){
		Istanbul il = new Istanbul();
		
		il.setLineCoverage("100");
		
		Assert.assertEquals("100", il.getLineCoverage());
	}
	
	@Test
	public void branchtest(){
		Istanbul il = new Istanbul();
		
		il.setBranchCoverage("100");
		
		Assert.assertEquals("100", il.getBranchCoverage());
	}
	
	@Test
	public void classtest(){
		Istanbul il = new Istanbul();
		
		il.setClassCoverage("100");
		
		Assert.assertEquals("100", il.getClassCoverage());
	}
	
	@Test
	public void cnametest(){
		Istanbul il = new Istanbul();
		
		il.setClassName("cn");
		
		Assert.assertEquals("cn", il.getClassName());
	}
	
	@Test
	public void methodtest(){
		Istanbul il = new Istanbul();
		
		il.setMethodCoverage("100");
		
		Assert.assertEquals("100", il.getMethodCoverage());
	}
}
