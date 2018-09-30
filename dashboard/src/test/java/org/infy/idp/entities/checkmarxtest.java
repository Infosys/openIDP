package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class checkmarxtest {

	@Test
	public void hightest(){
		Checkmarx cm = new Checkmarx();
		cm.setHigh(10);
		
		Assert.assertEquals((Integer)10, cm.getHigh());
	}
	
	@Test
	public void midtest(){
		Checkmarx cm = new Checkmarx();
		cm.setMedium(5);
		
		Assert.assertEquals((Integer)5, cm.getMedium());
	}
	
	@Test
	public void lowtest(){
		Checkmarx cm = new Checkmarx();
		cm.setLow(0);
		
		Assert.assertEquals((Integer)0, cm.getLow());
	}
}
