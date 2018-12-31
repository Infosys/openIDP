package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class LintTest {

	@Test
	public void hightest() {
		Lint l = new Lint();
		
		l.setHighViolations(100);
		
		Assert.assertEquals((Integer)100, l.getHighViolations());
	}
	
	@Test
	public void midtest() {
		Lint l = new Lint();
		
		l.setMediumViolations(100);
		
		Assert.assertEquals((Integer)100, l.getMediumViolations());
	}
	
	@Test
	public void lowtest() {
		Lint l = new Lint();
		
		l.setLowViolations(100);
		
		Assert.assertEquals((Integer)100, l.getLowViolations());
	}
}
