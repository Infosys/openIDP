
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class EcattTest {

	@Test
	public void Failtest(){
		Ecatt et = new Ecatt();
		
		et.setFail(0);
		
		Assert.assertEquals((Integer)0, et.getFail());
		
	}
	
	@Test
	public void Passtest(){
		Ecatt et = new Ecatt();
		
		et.setPass(100);
		
		Assert.assertEquals((Integer)100, et.getPass());
	}
	
	@Test
	public void Totaltest(){
		Ecatt et = new Ecatt();
		
		et.setTotalTest(100);
		
		Assert.assertEquals((Integer)100, et.getTotalTest());
	}
}
