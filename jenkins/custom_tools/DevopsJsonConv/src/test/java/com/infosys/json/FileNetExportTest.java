package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportTest {

	@Test
	public void idtest(){
		FileNetExport fn = new FileNetExport();
		
		fn.setTriggerId("1");
		
		Assert.assertEquals("1", fn.getTriggerId());
	}
	
	@Test
	public void test(){
		FileNetExport fn = new FileNetExport();
		
		fn.setEnviornment("dev");
		
		Assert.assertEquals("dev", fn.getEnviornment());
	}
}
