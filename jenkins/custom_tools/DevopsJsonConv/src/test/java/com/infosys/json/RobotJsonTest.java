package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class RobotJsonTest {
	@Test
	public void failtest(){
		RobotJson rj = new RobotJson();
		
		rj.setFail(0);
		
		Assert.assertEquals((Integer)0, rj.getFail());
	}
	
	@Test
	public void passtest(){
		RobotJson rj = new RobotJson();
		
		rj.setPass(100);
		
		Assert.assertEquals((Integer)100, rj.getPass());
	}
	
	@Test
	public void totaltest(){
		RobotJson rj = new RobotJson();
		
		rj.setTotalTest(100);
		
		Assert.assertEquals((Integer)100, rj.getTotalTest());
	}
}
