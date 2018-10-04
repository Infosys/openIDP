
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class CheckmarkTest {
	@Test
	public void hightest(){
		Checkmarx cm = new Checkmarx();
		
		cm.setHigh(10);
		
		Assert.assertEquals((Integer)10, cm.getHigh());
	}
	
	@Test
	public void mediumtest(){
		Checkmarx cm = new Checkmarx();
		
		cm.setMedium(10);
		
		Assert.assertEquals((Integer)10, cm.getMedium());
	}
	
	@Test
	public void lowtest(){
		Checkmarx cm = new Checkmarx();
		
		cm.setLow(10);
		
		Assert.assertEquals((Integer)10, cm.getLow());
	}
}
