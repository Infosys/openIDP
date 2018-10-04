


package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class SonarIssuesTest {
	
	@Test
	public void debttest(){
		SonarIssues si = new SonarIssues();
		
		si.setDebt("infosys");
		
		Assert.assertEquals("infosys", si.getDebt());
	}
	
	@Test
	public void efforttest(){
		SonarIssues si = new SonarIssues();
		
		si.setEffort("infosys");
		
		Assert.assertEquals("infosys", si.getEffort());
	}
	
	@Test
	public void orgtest(){
		SonarIssues si = new SonarIssues();
		
		si.setOrganization("infosys");
		
		Assert.assertEquals("infosys", si.getOrganization());
	}
	
	@Test
	public void test(){
		SonarIssues si = new SonarIssues();
		
		si.setType("general");
		
		Assert.assertEquals("general", si.getType());
	}
}
