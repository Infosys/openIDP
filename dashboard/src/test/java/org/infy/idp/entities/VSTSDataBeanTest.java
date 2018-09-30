package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class VSTSDataBeanTest {

	@Test
	public void arttest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setArtilink("infosys.com");
		
		Assert.assertEquals("infosys.com", vd.getArtilink());
	}
	
	@Test
	public void valuetest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setArtivalue("infosys.com");
		
		Assert.assertEquals("infosys.com", vd.getArtivalue());
	}
	
	@Test
	public void statustest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setBldstatus("success");
		
		Assert.assertEquals("success", vd.getBldstatus());
	}
	
	@Test
	public void buildidtest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setBuild("1");
		
		Assert.assertEquals("1", vd.getBuild());
	}
	
	@Test
	public void deploytest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setDeploy("success");
		
		Assert.assertEquals("success", vd.getDeploy());
	}
	
	@Test
	public void depstatustest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setDepStatus("success");
		
		Assert.assertEquals("success", vd.getDepStatus());
	}
	
	@Test
	public void envtest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setEnv("Dev");
		
		Assert.assertEquals("Dev", vd.getEnv());
	}
	
	@Test
	public void execnotest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setExecNo("1");
		
		Assert.assertEquals("1", vd.getExecNo());
	}
	
	@Test
	public void execnotestlink(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setExecNoLink("success");
		
		Assert.assertEquals("success", vd.getExecNoLink());
	}
	
	@Test
	public void passtest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setPass("success");
		
		Assert.assertEquals("success", vd.getPass());
	}
	
	@Test
	public void pipelinetest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setPipelineName("trial");
		
		Assert.assertEquals("trial", vd.getPipelineName());
	}
	
	@Test
	public void releasetest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setReleaseName("trialRelease");
		
		Assert.assertEquals("trialRelease", vd.getReleaseName());
	}
	
	@Test
	public void branchtest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setScmBranch("master");
		
		Assert.assertEquals("master", vd.getScmBranch());
	}
	
	@Test
	public void testtest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setTest("selenium");
		
		Assert.assertEquals("selenium", vd.getTest());
	}
	
	@Test
	public void tststatustest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setTstStatus("trialRelease");
		
		Assert.assertEquals("trialRelease", vd.getTstStatus());
	}
	
	@Test
	public void urltest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setUrl("infosys.com");
		
		Assert.assertEquals("infosys.com", vd.getUrl());
	}
	
	@Test
	public void usertest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setUser("admin");
		
		Assert.assertEquals("admin", vd.getUser());
	}
	
	@Test
	public void usernametest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setUserName("admin");
		
		Assert.assertEquals("admin", vd.getUserName());
	}
	
	@Test
	public void witest(){
		VSTSDataBean vd = new VSTSDataBean();
		
		vd.setWiNum("1");
		
		Assert.assertEquals("1", vd.getWiNum());
	}
}
