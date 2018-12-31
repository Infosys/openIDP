


package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class SonarDetailsTest {

	@Test
	public void bugstest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setBugs("none");
		
		Assert.assertEquals("none", sd.getBugs());
	}
	
	@Test
	public void codesmellstest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setCodesmells("none");
		
		Assert.assertEquals("none", sd.getCodesmells());
	}
	
	@Test
	public void loctest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setLoc(100);
		
		Assert.assertEquals(100, sd.getLoc());
	}
	
	@Test
	public void ratetest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setRateperhour("none");
		
		Assert.assertEquals("none", sd.getRateperhour());
	}
	
	@Test
	public void keytest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setSonarPrjctKey("sonar123");
		
		Assert.assertEquals("sonar123", sd.getSonarPrjctKey());
	}
	
	@Test
	public void servertest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setSonarServer("none");
		
		Assert.assertEquals("none", sd.getSonarServer());
	}
	
	@Test
	public void debttest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setTechnicalDebt("none");
		
		Assert.assertEquals("none", sd.getTechnicalDebt());
	}
	
	@Test
	public void vultest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setVulnerabilities("none");
		
		Assert.assertEquals("none", sd.getVulnerabilities());
	}
	
	@Test
	public void sonarservertest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setSonarServer("server");
		
		Assert.assertEquals("server", sd.getSonarServer());
	}
	
	@Test
	public void sonarpasswordtest(){
		SonarDetails sd = new SonarDetails();
		
		sd.setSonarPassword("pwd");
		
		Assert.assertEquals("pwd", sd.getSonarPassword());
	}
}
