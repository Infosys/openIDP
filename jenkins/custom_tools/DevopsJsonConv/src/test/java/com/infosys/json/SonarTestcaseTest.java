


package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class SonarTestcaseTest {

	@Test
	public void nametest(){
		SonarTestcase stc = new SonarTestcase();
		
		stc.setName("infosys");
		
		Assert.assertEquals("infosys",stc.getName());
	}
	
	@Test
	public void timetest(){
		SonarTestcase stc = new SonarTestcase();
		
		stc.setTime("10");
		
		Assert.assertEquals("10",stc.getTime());
	}
	
	@Test
	public void failtest(){
		SonarTestcase stc = new SonarTestcase();
		
		stc.setFailures(null);
		
		Assert.assertEquals(null,stc.getFailures());
	}
}
