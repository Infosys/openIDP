package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetImportTest {

	@Test
	public void desttest(){
		FileNetImport fni = new FileNetImport();
		
		fni.setDestination("pun");
		
		Assert.assertEquals("pun", fni.getDestination());
	}
	
	@Test
	public void srctest(){
		FileNetImport fni = new FileNetImport();
		
		fni.setSource("blr");
		
		Assert.assertEquals("blr", fni.getSource());
	}
	
	@Test
	public void idtest(){
		FileNetImport fni = new FileNetImport();
		
		fni.setTriggerId(1);
		
		Assert.assertEquals((Integer)1, fni.getTriggerId());
	}
}
