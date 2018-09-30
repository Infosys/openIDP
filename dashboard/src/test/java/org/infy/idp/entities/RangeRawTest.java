package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class RangeRawTest {

	@Test
	public void fromtest(){
		RangeRaw rr = new RangeRaw();
		
		rr.setFrom("100");
		
		Assert.assertEquals("100", rr.getFrom());
	}
	
	@Test
	public void totest(){
		RangeRaw rr = new RangeRaw();
		
		rr.setTo("300");
	
		Assert.assertEquals("300", rr.getTo());
	}
}
