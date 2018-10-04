
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class SeleniumTest {

	@Test
	public void failtest(){
		Selenium s = new Selenium();
		
		s.setFail(0);
		
		Assert.assertEquals((Integer)0, s.getFail());
	}
	
	@Test
	public void passtest(){
		Selenium s = new Selenium();
		
		s.setPass(0);
		
		Assert.assertEquals((Integer)0, s.getPass());
	}
	
	@Test
	public void totaltest(){
		Selenium s = new Selenium();
		
		s.setTotalTest(0);;
		
		Assert.assertEquals((Integer)0, s.getTotalTest());
	}
}
