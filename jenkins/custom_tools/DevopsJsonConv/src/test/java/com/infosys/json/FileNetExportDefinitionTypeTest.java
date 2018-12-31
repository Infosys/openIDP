package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportDefinitionTypeTest {
	@Test
	public void idtest(){
		
		FileNetExportClassDefinitionType fne = new FileNetExportClassDefinitionType();
		
		fne.setId("1");
		
		Assert.assertEquals("1", fne.getId());
	}
	
	@Test
	public void nametest(){
		
		FileNetExportClassDefinitionType fne = new FileNetExportClassDefinitionType();
		
		fne.setName("infosys");
		
		Assert.assertEquals("infosys", fne.getName());
	}
	
	@Test
	public void ostoretest(){
		
		FileNetExportClassDefinitionType fne = new FileNetExportClassDefinitionType();
		
		fne.setObjectStore("trial");
		
		Assert.assertEquals("trial", fne.getObjectStore());
	}
	
	@Test
	public void otypetest(){
		
		FileNetExportClassDefinitionType fne = new FileNetExportClassDefinitionType();
		
		fne.setObjectType("trial");
		
		Assert.assertEquals("trial", fne.getObjectType());
	}
}
