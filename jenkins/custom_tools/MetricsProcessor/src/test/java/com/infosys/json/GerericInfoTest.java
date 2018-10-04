
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class GerericInfoTest {

	@Test
	public void urltest(){
		GenericInfo gi = new GenericInfo();
		
		gi.setCheckstyleURL("infosys.com");
		
		Assert.assertEquals("infosys.com", gi.getCheckstyleURL());
	}
	
	@Test
	public void bugsurltest(){
		GenericInfo gi = new GenericInfo();
		
		gi.setFindbugsURL("infosys.com");
		
		Assert.assertEquals("infosys.com", gi.getFindbugsURL());
	}
	
	@Test
	public void juniturltest(){
		GenericInfo gi = new GenericInfo();
		
		gi.setJunitURL("infosys.com");
		
		Assert.assertEquals("infosys.com", gi.getJunitURL());
	}
	
	@Test
	public void pmdurltest(){
		GenericInfo gi = new GenericInfo();
		
		gi.setPmdURL("infosys.com");
		
		Assert.assertEquals("infosys.com", gi.getPmdURL());
	}
	
}
