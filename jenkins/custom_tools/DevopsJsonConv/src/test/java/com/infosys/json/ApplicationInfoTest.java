package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class ApplicationInfoTest {

	@Test
	public void nametest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setApplicationName("idp");
		
		Assert.assertEquals("idp",ai.getApplicationName());
	}
	
	@Test
	public void artitest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setArtifactToStage(null);
		
		Assert.assertEquals(null,ai.getArtifactToStage());
	}
	
	@Test
	public void devtest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setDevelopers("dev");
		
		Assert.assertEquals("dev",ai.getDevelopers());
	}
	
	@Test
	public void envtest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setEnvironmentOwnerDetails(null);
		
		Assert.assertEquals(null,ai.getEnvironmentOwnerDetails());
	}
	
	@Test
	public void pipetest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setPipelineAdmins("master");
		
		Assert.assertEquals("master",ai.getPipelineAdmins());
	}
	
	@Test
	public void reltest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setReleaseManager("rm");
		
		Assert.assertEquals("rm",ai.getReleaseManager());
	}
	
	@Test
	public void saptest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setSapApplication("sap");
		
		Assert.assertEquals("sap",ai.getSapApplication());
	}

	@Test
	public void slavetest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setSlavesDetails(null);
		
		Assert.assertEquals(null,ai.getSlavesDetails());
	}
	
	@Test
	public void virtualserverdetailstest(){
		
		ApplicationInfo ai = new ApplicationInfo();
		ai.setVirtualServiceServerDetails(null);
		
		Assert.assertEquals(null,ai.getVirtualServiceServerDetails());
	}
	
	
}

