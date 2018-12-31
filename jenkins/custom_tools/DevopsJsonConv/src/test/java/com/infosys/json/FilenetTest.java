package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FilenetTest {

	@Test
	public void envtest(){
		FileNet fn = new FileNet();
		
		fn.setEnv("dev");
		
		Assert.assertEquals("dev", fn.getEnv());
	}
	
	@Test
	public void idtest(){
		FileNet fn = new FileNet();
		
		fn.setTriggerId(1);
		
		Assert.assertEquals((Integer)1, fn.getTriggerId());
	}
	
	@Test
	public void exporttest(){
		FileNet fn = new FileNet();
		
		fn.setFileNetExport(null);
		
		Assert.assertEquals(null, fn.getFileNetExport());
	}
	
	@Test
	public void importtest(){
		FileNet fn = new FileNet();
		
		fn.setFileNetImport(null);
		
		Assert.assertEquals(null, fn.getFileNetImport());
	}
}
