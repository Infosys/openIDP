
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class TSLintTest {

	@Test
	
	public void hightest(){
		TSLint ts = new TSLint();
		
		ts.setHighViolations(10);
		
		Assert.assertEquals((Integer)10, ts.getHighViolations());
	}
	
	@Test
	
	public void mediumtest(){
		TSLint ts = new TSLint();
		
		ts.setMediumViolations(10);
		
		Assert.assertEquals((Integer)10, ts.getMediumViolations());
	}
	
	@Test
	
	public void lowtest(){
		TSLint ts = new TSLint();
		
		ts.setLowViolations(10);
		
		Assert.assertEquals((Integer)10, ts.getLowViolations());
	}
}
