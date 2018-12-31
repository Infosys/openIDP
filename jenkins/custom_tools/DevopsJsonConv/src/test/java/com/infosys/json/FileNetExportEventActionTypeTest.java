package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportEventActionTypeTest {

	@Test
	public void idtest(){
		
		FileNetExportEventActionType fne = new FileNetExportEventActionType();
		
		fne.setId("1");
		
		Assert.assertEquals("1", fne.getId());
	}
	
	@Test
	public void nametest(){
		
		FileNetExportEventActionType fne = new FileNetExportEventActionType();
		
		fne.setName("infosys");
		
		Assert.assertEquals("infosys", fne.getName());
	}
	
	@Test
	public void ostoretest(){
		
		FileNetExportEventActionType fne = new FileNetExportEventActionType();
		
		fne.setObjectStore("trial");
		
		Assert.assertEquals("trial", fne.getObjectStore());
	}
	
	@Test
	public void otypetest(){
		
		FileNetExportEventActionType fne = new FileNetExportEventActionType();
		
		fne.setObjectType("trial");
		
		Assert.assertEquals("trial", fne.getObjectType());
	}
}
