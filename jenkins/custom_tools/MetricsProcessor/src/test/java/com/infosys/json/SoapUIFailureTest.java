
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class SoapUIFailureTest {

	@Test
	public void msgtest(){
		SoapUIFailure so = new SoapUIFailure();
		
		so.setMessage("infosys");
		
		Assert.assertEquals("infosys", so.getMessage());
	}
	
	@Test
	public void typetest(){
		SoapUIFailure so = new SoapUIFailure();
		
		so.setType("infosys");
		
		Assert.assertEquals("infosys", so.getType());
	}
}
