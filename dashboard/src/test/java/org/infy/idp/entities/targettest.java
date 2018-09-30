package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class targettest {

	@Test
	public void idtest(){
		
		Target  t = new Target();
		
		t.setRefId("one");
		
		Assert.assertEquals("one", t.getRefId());
	}
	
	@Test
	public void targettest(){
		
		Target  t = new Target();
		
		t.setTarget("pass");
		
		Assert.assertEquals("pass", t.getTarget());
	}
	
	@Test
	public void typetest(){
		
		Target  t = new Target();
		
		t.setType("dev");
		
		Assert.assertEquals("dev", t.getType());
	}
}
