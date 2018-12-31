package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FindBugsTest {

	@Test
	public void blockertest() {
		FindBugs fb = new FindBugs();
		
		fb.setBlocker(100);
		
		Assert.assertEquals((Integer)100, fb.getBlocker());
	}
	
	@Test
	public void criticaltest() {
		FindBugs fb = new FindBugs();
		
		fb.setCritical(100);
		
		Assert.assertEquals((Integer)100, fb.getCritical());
	}

	
	@Test
	public void infotest() {
		FindBugs fb = new FindBugs();
		
		fb.setInfo(100);
		
		Assert.assertEquals((Integer)100, fb.getInfo());
	}

	
	
	@Test
	public void majortest() {
		FindBugs fb = new FindBugs();
		
		fb.setMajor(100);
		
		Assert.assertEquals((Integer)100, fb.getMajor());
	}

	
	@Test
	public void minortest() {
		FindBugs fb = new FindBugs();
		
		fb.setMinor(100);
		
		Assert.assertEquals((Integer)100, fb.getMinor());
	}

}
