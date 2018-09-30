package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class JsonClassTest {

	@Test
	public void apptest(){
		JsonClass jc = new JsonClass();
		
		jc.setApplication("app");
		Assert.assertEquals("app", jc.getApplication());
	}
	
	@Test
	public void idtest(){
		JsonClass jc = new JsonClass();
		
		jc.setBuildId("1");
		Assert.assertEquals("1", jc.getBuildId());
	}
	
	@Test
	public void bidtest(){
		JsonClass jc = new JsonClass();
		
		jc.setbuildId("app");
		Assert.assertEquals("app", jc.getbuildId());
	}
	
	@Test
	public void test(){
		JsonClass jc = new JsonClass();
		
		jc.setLog("log");
		Assert.assertEquals("log", jc.getLog());
	}
}
