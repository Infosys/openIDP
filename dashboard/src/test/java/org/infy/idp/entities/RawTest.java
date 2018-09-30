package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class RawTest {

	@Test
	public void fromtest(){
		Raw r = new Raw();
		
		r.setFrom("100");
		
		Assert.assertEquals("100", r.getFrom());
	}
	
	@Test
	public void totest(){
		Raw r = new Raw();
		
		r.setTo("300");
		
		Assert.assertEquals("300", r.getTo());
	}
}
