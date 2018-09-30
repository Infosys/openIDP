package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class RebaseDataTest {

	@Test
	public void bugtest(){
		RebaseData rd = new RebaseData();
		
		rd.setBugTR("bug");
		Assert.assertEquals("bug", rd.getBugTR());
	}
	
	@Test
	public void dummytest(){
		RebaseData rd = new RebaseData();
		
		rd.setDummyTR("dummy");
		Assert.assertEquals("dummy", rd.getDummyTR());
	}
	
	@Test
	public void onametest(){
		RebaseData rd = new RebaseData();
		
		rd.setObjectName("bug");
		Assert.assertEquals("bug", rd.getObjectName());
	}
	
	@Test
	public void otypetest(){
		RebaseData rd = new RebaseData();
		
		rd.setObjectType("otype");
		
		
		Assert.assertEquals("otype", rd.getObjectType());
	}
	
	@Test
	public void springtest(){
		RebaseData rd = new RebaseData();
		
		rd.setSprintTR("sprint");
		Assert.assertEquals("sprint", rd.getSprintTR());
	}
}
