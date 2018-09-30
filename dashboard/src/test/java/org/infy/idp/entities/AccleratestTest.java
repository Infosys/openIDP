package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class AccleratestTest {

	@Test
	public void failed(){
		
		Accleratest at = new Accleratest();
		at.setFailed(0);
		
		Assert.assertEquals((Integer)0, at.getFailed());
	}
	
	@Test
	public void pass(){
		
		Accleratest at = new Accleratest();
		at.setPassed(100);
		
		Assert.assertEquals((Integer)100, at.getPassed());
	}
	
	@Test
	public void total(){
		
		Accleratest at = new Accleratest();
		at.setTotalTest(100);;
		
		Assert.assertEquals((Integer)100, at.getTotalTest());
	}
}
