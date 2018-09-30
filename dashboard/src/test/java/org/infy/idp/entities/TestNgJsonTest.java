package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class TestNgJsonTest {

	@Test
	public void failtest(){
		
		TestNgJson tn = new TestNgJson();
		
		tn.setFail(0);
		
		Assert.assertEquals((Integer)0, tn.getFail());
	}
	
	@Test
	public void passtest(){
		
		TestNgJson tn = new TestNgJson();
		
		tn.setPass(100);
		
		Assert.assertEquals((Integer)100, tn.getPass());
	}
	
	@Test
	public void totaltest(){
		
		TestNgJson tn = new TestNgJson();
		
		tn.setTotalTest(0);
		
		Assert.assertEquals((Integer)0, tn.getTotalTest());
	}
}
