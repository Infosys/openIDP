
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class SonarDetailsLOCmeasuresTest {

	@Test
	public void metrictets(){
		
		SonarDetailsLocmeasures sloc = new SonarDetailsLocmeasures();
		
		sloc.setMetric("infosys");
		
		Assert.assertEquals("infosys", sloc.getMetric());
	}
	
	@Test
	public void valuetest(){
		
		SonarDetailsLocmeasures sloc = new SonarDetailsLocmeasures();
		
		sloc.setValue(101);
		
		Assert.assertEquals(101, sloc.getValue());
	}
}
