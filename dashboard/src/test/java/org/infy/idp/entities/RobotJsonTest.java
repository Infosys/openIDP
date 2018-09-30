package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class RobotJsonTest {

	@Test
	public void failtest(){
		RobotJson rj = new RobotJson();
		
		rj.setFailed(0);
		
		Assert.assertEquals((Integer)0, rj.getFailed());
	}
	
	@Test
	public void passtest(){
		RobotJson rj = new RobotJson();
		
		rj.setPassed(100);
		
		Assert.assertEquals((Integer)100, rj.getPassed());
	}
	@Test
	public void totaltest(){
		RobotJson rj = new RobotJson();
		
		rj.setTotalTest(100);
		
		Assert.assertEquals((Integer)100, rj.getTotalTest());
		
	}
}
