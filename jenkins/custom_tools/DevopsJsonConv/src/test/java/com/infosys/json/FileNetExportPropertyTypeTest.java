package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportPropertyTypeTest {

	@Test
	public void idtest(){
		
		FileNetExportPropertyType fne = new FileNetExportPropertyType();
		
		fne.setId("1");
		
		Assert.assertEquals("1", fne.getId());
	}
	
	@Test
	public void nametest(){
		
		FileNetExportPropertyType fne = new FileNetExportPropertyType();
		
		fne.setName("infosys");
		
		Assert.assertEquals("infosys", fne.getName());
	}
	
	@Test
	public void ostoretest(){
		
		FileNetExportPropertyType fne = new FileNetExportPropertyType();
		
		fne.setObjectStore("trial");
		
		Assert.assertEquals("trial", fne.getObjectStore());
	}
	
	@Test
	public void otypetest(){
		
		FileNetExportPropertyType fne = new FileNetExportPropertyType();
		
		fne.setObjectType("trial");
		
		Assert.assertEquals("trial", fne.getObjectType());
	}
}
