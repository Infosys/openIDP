/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

/*This class is for testing sonar details*/
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
}
