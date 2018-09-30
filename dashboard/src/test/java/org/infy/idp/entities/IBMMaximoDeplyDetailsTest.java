package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class IBMMaximoDeplyDetailsTest {

	@Test
	public void artitest(){
		IBMMaximoDeployDetails ibm = new IBMMaximoDeployDetails();
		
		ibm.setArtifactName("artifact");
		
		Assert.assertEquals("artifact", ibm.getArtifactName());
	}
	
	@Test
	public void deploytest(){
		IBMMaximoDeployDetails ibm = new IBMMaximoDeployDetails();
		
		ibm.setDeployStepName("deploystep");
		
		Assert.assertEquals("deploystep", ibm.getDeployStepName());
	}
	
	@Test
	public void typetest(){
		IBMMaximoDeployDetails ibm = new IBMMaximoDeployDetails();
		
		ibm.setDeployStepType("type");
		
		Assert.assertEquals("type", ibm.getDeployStepType());
	}
	
	@Test
	public void durationtest(){
		IBMMaximoDeployDetails ibm = new IBMMaximoDeployDetails();
		
		ibm.setDuration("10");
		
		Assert.assertEquals("10", ibm.getDuration());
	}
	
	@Test
	public void hosttest(){
		IBMMaximoDeployDetails ibm = new IBMMaximoDeployDetails();
		
		ibm.setHostName("artifact");
		
		Assert.assertEquals("artifact", ibm.getHostName());
	}
	
	@Test
	public void statustest(){
		IBMMaximoDeployDetails ibm = new IBMMaximoDeployDetails();
		
		ibm.setStatus("success");
		
		Assert.assertEquals("success", ibm.getStatus());
	}
}
