package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class FileNetExportChoiceListTypeTest {

	@Test
	public void idtest(){ 
		FileNetExportChoiceListType fn = new FileNetExportChoiceListType();
		
		fn.setId("1");
		
		Assert.assertEquals("1", fn.getId());
	}
	
	@Test
	public void nametest(){ 
		FileNetExportChoiceListType fn = new FileNetExportChoiceListType();
		
		fn.setName("infosys");
		
		Assert.assertEquals("infosys", fn.getName());
	}
	
	@Test
	public void ostoretest(){ 
		FileNetExportChoiceListType fn = new FileNetExportChoiceListType();
		
		fn.setObjectStore("trial");
		
		Assert.assertEquals("trial", fn.getObjectStore());
	}
	
	@Test
	public void otypetest(){ 
		FileNetExportChoiceListType fn = new FileNetExportChoiceListType();
		
		fn.setObjectType("trial");
		
		Assert.assertEquals("trial", fn.getObjectType());
	}
}
