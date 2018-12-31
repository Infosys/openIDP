package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class SlaveDetailTest {

	@Test
	public void buildtest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setBuild("build");
		
		Assert.assertEquals("build", sd.getBuild());
	}
	
	@Test
	public void ostest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setBuildServerOS("os");
		
		Assert.assertEquals("os", sd.getBuildServerOS());
	}
	
	@Test
	public void slavetest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setCreateNewSlave("slave");
		
		Assert.assertEquals("slave", sd.getCreateNewSlave());
	}
	
	@Test
	public void deploytest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setDeploy("deploy");
		
		Assert.assertEquals("deploy", sd.getDeploy());
	}
	
	@Test
	public void labelstest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setLabels("labels");
		
		Assert.assertEquals("labels", sd.getLabels());
	}
	
	@Test
	public void slavenametest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setSlaveName("slave");
		
		Assert.assertEquals("slave", sd.getSlaveName());
	}
	
	@Test
	public void usagetest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setSlaveUsage("usage");
		
		Assert.assertEquals("usage", sd.getSlaveUsage());
	}
	
	@Test
	public void keytest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setSSHKeyPath("key");
		
		Assert.assertEquals("key", sd.getSSHKeyPath());
	}
	
	@Test
	public void test(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setTest("trial");
		
		Assert.assertEquals("trial", sd.getTest());
	}
	
	@Test
	public void pathtest(){
		SlavesDetail sd  = new SlavesDetail();
		
		sd.setWorkspacePath("c:");
		
		Assert.assertEquals("c:", sd.getWorkspacePath());
	}
}
