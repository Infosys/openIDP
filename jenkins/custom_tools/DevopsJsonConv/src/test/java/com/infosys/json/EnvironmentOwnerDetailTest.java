
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class EnvironmentOwnerDetailTest {

	@Test
	public void clienttest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setClient("client");
		
		Assert.assertEquals("client", eod.getClient());
	}
	
	@Test
	public void ownertest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setdBOwners("owner");
		
		Assert.assertEquals("owner", eod.getdBOwners());
	}
	
	@Test
	public void envtest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setEnvironmentName("dev");
		
		Assert.assertEquals("dev", eod.getEnvironmentName());
	}
	
	@Test
	public void eownertest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setEnvironmentOwners("owner");
		
		Assert.assertEquals("owner", eod.getEnvironmentOwners());
	}
	
	@Test
	public void hnametest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setHostName("host");
		
		Assert.assertEquals("host", eod.getHostName());
	}
	
	@Test
	public void numbertest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setInstanceNumber("ino");
		
		Assert.assertEquals("ino", eod.getInstanceNumber());
	}
	
	@Test
	public void typetest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setLandscapeType("type");
		
		Assert.assertEquals("type", eod.getLandscapeType());
	}
	
	@Test
	public void langtest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setLanguage("java");
		
		Assert.assertEquals("java", eod.getLanguage());
	}
	
	@Test
	public void pwdtest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setPassword("pwd");
		
		Assert.assertEquals("pwd", eod.getPassword());
	}
	
	@Test
	public void qatest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setQa("qa");
		
		Assert.assertEquals("qa", eod.getQa());
	}
	
	@Test
	public void idtest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setSystemId("1");
		
		Assert.assertEquals("1", eod.getSystemId());
	}
	
	@Test
	public void unametest(){
		EnvironmentOwnerDetail eod = new EnvironmentOwnerDetail();
		
		eod.setUserName("uname");
		
		Assert.assertEquals("uname", eod.getUserName());
	}
}
