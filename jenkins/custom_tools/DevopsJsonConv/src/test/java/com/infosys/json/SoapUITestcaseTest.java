
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class SoapUITestcaseTest {

	@Test
	public void nametest(){
		
		SoapUITestcase sc = new SoapUITestcase();
		
		sc.setName("infosys");
		
		Assert.assertEquals("infosys", sc.getName());
	}
	
	@Test
	public void timetest(){
		
		SoapUITestcase sc = new SoapUITestcase();
		
		sc.setTime("10");
		
		Assert.assertEquals("10", sc.getTime());
	}
	
	@Test
	public void failtest(){
		
		SoapUITestcase sc = new SoapUITestcase();
		
		sc.setFailures(null);
		
		Assert.assertEquals(null, sc.getFailures());
	}
}
