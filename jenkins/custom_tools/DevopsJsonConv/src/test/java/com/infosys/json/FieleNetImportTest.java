package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FieleNetImportTest {

	@Test
	public void desttest() {
		FileNetImport fni = new FileNetImport();
		
		fni.setDestination("dest");
		
		Assert.assertEquals("dest",fni.getDestination());
	}
	
	@Test
	public void srctest() {
		FileNetImport fni = new FileNetImport();
		
		fni.setSource("src");
		
		Assert.assertEquals("src",fni.getSource());
	}
	
	@Test
	public void idtest() {
		FileNetImport fni = new FileNetImport();
		
		fni.setTriggerId(1);
		
		Assert.assertEquals((Integer) 1 ,fni.getTriggerId());
	}
}
