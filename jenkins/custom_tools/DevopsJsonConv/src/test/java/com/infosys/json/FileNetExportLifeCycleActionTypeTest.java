package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportLifeCycleActionTypeTest {

	@Test
	public void idtest(){
		
		FileNetExportLifeCycleActionType fne = new FileNetExportLifeCycleActionType();
		
		fne.setId("1");
		
		Assert.assertEquals("1", fne.getId());
	}
	
	@Test
	public void nametest(){
		
		FileNetExportLifeCycleActionType fne = new FileNetExportLifeCycleActionType();
		
		fne.setName("infosys");
		
		Assert.assertEquals("infosys", fne.getName());
	}
	
	@Test
	public void ostoretest(){
		
		FileNetExportLifeCycleActionType fne = new FileNetExportLifeCycleActionType();
		
		fne.setObjectStore("trial");
		
		Assert.assertEquals("trial", fne.getObjectStore());
	}
	
	@Test
	public void otypetest(){
		
		FileNetExportLifeCycleActionType fne = new FileNetExportLifeCycleActionType();
		
		fne.setObjectType("trial");
		
		Assert.assertEquals("trial", fne.getObjectType());
	}
}
