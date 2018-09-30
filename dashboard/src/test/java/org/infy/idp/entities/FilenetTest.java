package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class FilenetTest {

	@Test
	public void envtest(){
		FileNet fn = new FileNet();
		
		fn.setEnv("dev");
		
		Assert.assertEquals("dev", fn.getEnv());
	}
	
	@Test
	public void idtest(){
		FileNet fn = new FileNet();
		
		fn.setTriggerId(1);
		
		Assert.assertEquals((Integer)1, fn.getTriggerId());
	}
}
