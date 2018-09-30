package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class RangeTest {

	@Test
	public void fromtest(){
		Range r = new Range();
		
		r.setFrom("100");
		
		Assert.assertEquals("100", r.getFrom());
	}
	
	@Test
	public void totest(){
		Range r = new Range();
		
		r.setTo("300");
		
		Assert.assertEquals("300", r.getTo());
	}
}
