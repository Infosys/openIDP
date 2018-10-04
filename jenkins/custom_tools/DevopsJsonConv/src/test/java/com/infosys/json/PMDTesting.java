
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class PMDTesting {
	
	@Test
	public void blockertest(){
		Pmd pd = new Pmd();
		pd.setBlocker(0);
		
		Assert.assertEquals((Integer) 0, pd.getBlocker());
	}
	
	@Test
	public void criticaltest(){
		Pmd pd = new Pmd();
		pd.setCritical(0);
		
		Assert.assertEquals((Integer) 0, pd.getCritical());
	}

	@Test
	public void infotest(){
		Pmd pd = new Pmd();
		pd.setInfo(0);
		
		Assert.assertEquals((Integer) 0, pd.getInfo());
	}
	
	@Test
	public void majortest(){
		Pmd pd = new Pmd();
		pd.setMajor(0);
		
		Assert.assertEquals((Integer) 0, pd.getMajor());
	}
	
	@Test
	public void minortest(){
		Pmd pd = new Pmd();
		pd.setMinor(0);;
		
		Assert.assertEquals((Integer) 0, pd.getMinor());
	}
}
