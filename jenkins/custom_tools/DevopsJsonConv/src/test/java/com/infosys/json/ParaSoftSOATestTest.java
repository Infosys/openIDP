package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class ParaSoftSOATestTest {

	@Test
	public void failtest(){
		ParasoftSOATest psoa = new ParasoftSOATest();
		
		psoa.setFail(0);
		
		Assert.assertEquals((Integer)0, psoa.getFail());
	}
	
	@Test
	public void passtest(){
		ParasoftSOATest psoa = new ParasoftSOATest();
		
		psoa.setPass(100);
		
		Assert.assertEquals((Integer)100, psoa.getPass());
	}

	
	@Test
	public void totaltest(){
		ParasoftSOATest psoa = new ParasoftSOATest();
		
		psoa.setTotal(100);
		
		Assert.assertEquals((Integer)100, psoa.getTotal());
	}

}
