
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class IFastTest {

	@Test
	public void failtest(){
		IFast ifast = new IFast();
		
		ifast.setFail(0);
		
		Assert.assertEquals((Integer)0, ifast.getFail());
	}
	
	@Test
	public void passtest(){
		IFast ifast = new IFast();
		
		ifast.setPass(100);
		
		Assert.assertEquals((Integer)100, ifast.getPass());
	}
	
	@Test
	public void totaltest(){
		IFast ifast = new IFast();
		
		ifast.setTotalTest(100);
		
		Assert.assertEquals((Integer)100, ifast.getTotalTest());
	}
}
