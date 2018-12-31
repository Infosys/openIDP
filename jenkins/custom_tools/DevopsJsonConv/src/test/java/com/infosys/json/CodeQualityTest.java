
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class CodeQualityTest {
	
	@Test
	
	public void styletest(){
		CodeQuality cq = new CodeQuality();
		
		cq.setCheckstyle(null);
		
		Assert.assertEquals(null, cq.getCheckstyle());
	}
	
	@Test
	
	public void bugstest(){
		CodeQuality cq = new CodeQuality();
		
		cq.setFindbugs(null);
		
		Assert.assertEquals(null, cq.getFindbugs());
	}
	
	@Test
	
	public void fxtest(){
		CodeQuality cq = new CodeQuality();
		
		cq.setFxCopReport(null);
		
		Assert.assertEquals(null, cq.getFxCopReport());
	}
	
	@Test
	
	public void cstyletest(){
		CodeQuality cq = new CodeQuality();
		
		cq.setLint(null);
		
		Assert.assertEquals(null, cq.getLint());
	}
	
	@Test
	
	public void pmdtest(){
		CodeQuality cq = new CodeQuality();
		
		cq.setPmd(null);;
		
		Assert.assertEquals(null, cq.getPmd());
	}
	
	@Test
	
	public void scitest(){
		CodeQuality cq = new CodeQuality();
		
		cq.setSci(null);
		
		Assert.assertEquals(null, cq.getSci());
	}
	
	@Test
	
	public void sonartest(){
		CodeQuality cq = new CodeQuality();
		
		cq.setSonar(null);
		
		Assert.assertEquals(null, cq.getSonar());
	}
	
	@Test
	
	public void linttest(){
		CodeQuality cq = new CodeQuality();
		
		cq.setTsLint(null);
		
		Assert.assertEquals(null, cq.getTsLint());
	}
}
