package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class SonarDetailsTest {

	@Test
	public void bugstest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setBugs("code");
		
		Assert.assertEquals("code", sd.getBugs());
	}
	
	@Test
	public void codetest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setCodesmells("code");
		
		Assert.assertEquals("code", sd.getCodesmells());
	}
	
	@Test
	public void loctest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setLoc(1000);
		
		Assert.assertEquals(1000, sd.getLoc());
	}
	
	@Test
	public void rphtest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setRateperhour("100");
		
		Assert.assertEquals("100", sd.getRateperhour());
	}
	
	@Test
	public void keytest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setSonarPrjctKey("sonar100");
		
		Assert.assertEquals("sonar100", sd.getSonarPrjctKey());
	}
	
	@Test
	public void servertest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setSonarServer("blr");
		
		Assert.assertEquals("blr", sd.getSonarServer());
	}
	
	@Test
	public void debttest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setTechnicalDebt("debt");
		
		Assert.assertEquals("debt", sd.getTechnicalDebt());
	}
	
	@Test
	public void vultest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setVulnerabilities("vul");
		
		Assert.assertEquals("vul", sd.getVulnerabilities());
	}
}
