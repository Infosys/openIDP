
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class CodeCoverageTest {

	@Test
	
	public void cobertest(){
		
		Codecoverage cc = new Codecoverage();
		
		cc.setCobertura(null);
		
		Assert.assertEquals(null, cc.getCobertura());
	}
	
	@Test
	
	public void dstest(){
		
		Codecoverage cc = new Codecoverage();
		
		cc.setDsPriv(null);
		
		Assert.assertEquals(null, cc.getDsPriv());
	}
	
	@Test
	
	public void istanbultest(){
		
		Codecoverage cc = new Codecoverage();
		
		cc.setIstanbul(null);;
		
		Assert.assertEquals(null, cc.getIstanbul());
	}
	
	@Test
	
	public void jacocotest(){
		
		Codecoverage cc = new Codecoverage();
		
		cc.setJacoco(null);
		
		Assert.assertEquals(null, cc.getJacoco());
	}
}
