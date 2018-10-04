
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class ItopsTest {

	@Test
	public void failtest(){
		
		Itops it = new Itops();
		
		it.setFail(0);
		
		Assert.assertEquals((Integer)0, it.getFail());
	}
	
	@Test
	public void passtest(){
		
		Itops it = new Itops();
		
		it.setPass(100);
		
		Assert.assertEquals((Integer)100, it.getPass());
	}
	
	@Test
	public void totaltest(){
		
		Itops it = new Itops();
		
		it.setTotalTest(100);
		
		Assert.assertEquals((Integer)100, it.getTotalTest());
	}
}
