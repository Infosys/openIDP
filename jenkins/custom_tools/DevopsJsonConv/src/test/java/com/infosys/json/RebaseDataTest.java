package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class RebaseDataTest {

	@Test
	public void bugtest(){
		RebaseData rdt = new RebaseData();
		
		rdt.setBugTR("bug");
		Assert.assertEquals("bug", rdt.getBugTR());
		
	}
	
	@Test
	public void dummytest(){
		RebaseData rdt = new RebaseData();
		
		rdt.setDummyTR("dummy");
		Assert.assertEquals("dummy", rdt.getDummyTR());
		
	}
	
	@Test
	public void nametest(){
		RebaseData rdt = new RebaseData();
		
		rdt.setObjectName("oname");
		Assert.assertEquals("oname", rdt.getObjectName());
		
	}
	
	@Test
	public void typetest(){
		RebaseData rdt = new RebaseData();
		
		rdt.setObjectType("otype");
		Assert.assertEquals("otype", rdt.getObjectType());
		
	}
	
	@Test
	public void sprinttest(){
		RebaseData rdt = new RebaseData();
		
		rdt.setSprintTR("sprint");
		Assert.assertEquals("sprint", rdt.getSprintTR());
		
	}
}
