package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportFolderTypeTest {

	@Test
	public void idtest(){
		
		FileNetExportFolderType fne = new FileNetExportFolderType();
		
		fne.setId("1");
		
		Assert.assertEquals("1", fne.getId());
	}
	
	@Test
	public void nametest(){
		
		FileNetExportFolderType fne = new FileNetExportFolderType();
		
		fne.setName("infosys");
		
		Assert.assertEquals("infosys", fne.getName());
	}
	
	@Test
	public void ostoretest(){
		
		FileNetExportFolderType fne = new FileNetExportFolderType();
		
		fne.setObjectStore("trial");
		
		Assert.assertEquals("trial", fne.getObjectStore());
	}
	
	@Test
	public void otypetest(){
		
		FileNetExportFolderType fne = new FileNetExportFolderType();
		
		fne.setObjectType("trial");
		
		Assert.assertEquals("trial", fne.getObjectType());
	}
}
