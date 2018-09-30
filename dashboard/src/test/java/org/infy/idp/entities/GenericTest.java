package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class GenericTest {

	@Test
	public void urltest(){
		GenericInfo gi = new GenericInfo();
		
		gi.setCheckstyleURL("infosys.com");
		
		Assert.assertEquals("infosys.com", gi.getCheckstyleURL());
	}
	
	@Test
	public void bugstest(){
		GenericInfo gi = new GenericInfo();
		
		gi.setFindbugsURL("urlbugs");
		
		Assert.assertEquals("urlbugs", gi.getFindbugsURL());
	}
	
	@Test
	public void unittest(){
		GenericInfo gi = new GenericInfo();
		
		gi.setJunitURL("uniturl");
		
		Assert.assertEquals("uniturl", gi.getJunitURL());
	}
	
	@Test
	public void pmdtest(){
		GenericInfo gi = new GenericInfo();
		
		gi.setPmdURL("pmdurl");
		
		Assert.assertEquals("pmdurl", gi.getPmdURL());
	}
}
