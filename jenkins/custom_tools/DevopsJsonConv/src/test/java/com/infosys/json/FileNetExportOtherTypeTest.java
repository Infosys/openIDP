package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportOtherTypeTest {

	@Test
	public void idtest(){
		
		FileNetExportOtherType fne = new FileNetExportOtherType();
		
		fne.setId("1");
		
		Assert.assertEquals("1", fne.getId());
	}
	
	@Test
	public void nametest(){
		
		FileNetExportOtherType fne = new FileNetExportOtherType();
		
		fne.setName("infosys");
		
		Assert.assertEquals("infosys", fne.getName());
	}
	
	@Test
	public void ostoretest(){
		
		FileNetExportOtherType fne = new FileNetExportOtherType();
		
		fne.setObjectStore("trial");
		
		Assert.assertEquals("trial", fne.getObjectStore());
	}
	
	@Test
	public void otypetest(){
		
		FileNetExportOtherType fne = new FileNetExportOtherType();
		
		fne.setObjectType("trial");
		
		Assert.assertEquals("trial", fne.getObjectType());
	}
}
