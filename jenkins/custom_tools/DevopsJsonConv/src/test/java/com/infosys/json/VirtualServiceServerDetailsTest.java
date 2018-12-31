package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class VirtualServiceServerDetailsTest {

	@Test
	public void pwdtest(){
		VirtualServiceServerDetails vssd = new VirtualServiceServerDetails();
		
		vssd.setPassword("pwd");
		
		Assert.assertEquals("pwd", vssd.getPassword());
	}
	
	@Test
	public void urltest(){
		VirtualServiceServerDetails vssd = new VirtualServiceServerDetails();
		
		vssd.setServerUrl("pwd.com");
		
		Assert.assertEquals("pwd.com", vssd.getServerUrl());
	}
	
	@Test
	public void nametest(){
		VirtualServiceServerDetails vssd = new VirtualServiceServerDetails();
		
		vssd.setUserName("pwd");
		
		Assert.assertEquals("pwd", vssd.getUserName());
	}
}
