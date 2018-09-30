package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class PmdTest {

	
	@Test
	public void blockertest(){
		Pmd p = new Pmd();
		
		p.setBlocker(1);
		
		Assert.assertEquals((Integer)1, p.getBlocker());
	}
	
	@Test
	public void criticaltest(){
		Pmd p = new Pmd();
		
		p.setCritical(1);;
		
		Assert.assertEquals((Integer)1, p.getCritical());
	}
	
	@Test
	public void infotest(){
		Pmd p = new Pmd();
		
		p.setInfo(1);;
		
		Assert.assertEquals((Integer)1, p.getInfo());
	}
	
	@Test
	public void majortest(){
		Pmd p = new Pmd();
		
		p.setMajor(0);
		
		Assert.assertEquals((Integer)0, p.getMajor());
	}
	
	@Test
	public void minortest(){
		Pmd p = new Pmd();
		
		p.setMinor(1);
		
		Assert.assertEquals((Integer)1, p.getMinor());
	}
}
