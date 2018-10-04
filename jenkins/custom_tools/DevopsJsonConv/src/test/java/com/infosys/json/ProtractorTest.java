
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class ProtractorTest {
	
	@Test
	public void errorstest(){
		Protractor p = new Protractor();
		
		p.setErrors(0);
		
		Assert.assertEquals((Integer)0, p.getErrors());
	}
	
	@Test
	public void failtest(){
		Protractor p = new Protractor();
		
		p.setFail(0);
		
		Assert.assertEquals((Integer)0, p.getFail());
	}
	
	@Test
	public void passtest(){
		Protractor p = new Protractor();
		
		p.setPass(0);
		
		Assert.assertEquals((Integer)0, p.getPass());
	}
	
	@Test
	public void skippedtest(){
		Protractor p = new Protractor();
		
		p.setSkipped(0);
		
		Assert.assertEquals((Integer)0, p.getSkipped());
	}
	
	@Test
	public void totaltest(){
		Protractor p = new Protractor();
		
		p.setTotaltest(0);
		
		Assert.assertEquals((Integer)0, p.getTotalTest());
	}
}
