package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportLifeCyclePolicyTypeTest {

	@Test
	public void idtest(){
		
		FileNetExportLifeCyclePolicyType fne = new FileNetExportLifeCyclePolicyType();
		
		fne.setId("1");
		
		Assert.assertEquals("1", fne.getId());
	}
	
	@Test
	public void nametest(){
		
		FileNetExportLifeCyclePolicyType fne = new FileNetExportLifeCyclePolicyType();
		
		fne.setName("infosys");
		
		Assert.assertEquals("infosys", fne.getName());
	}
	
	@Test
	public void ostoretest(){
		
		FileNetExportLifeCyclePolicyType fne = new FileNetExportLifeCyclePolicyType();
		
		fne.setObjectStore("trial");
		
		Assert.assertEquals("trial", fne.getObjectStore());
	}
	
	@Test
	public void otypetest(){
		
		FileNetExportLifeCyclePolicyType fne = new FileNetExportLifeCyclePolicyType();
		
		fne.setObjectType("trial");
		
		Assert.assertEquals("trial", fne.getObjectType());
	}
}
