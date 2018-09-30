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

/*This class is for testing robot json*/
public class RobotJsonTest {
	
	@Test
	public void failedtest(){
		RobotJson rj = new RobotJson();
		
		rj.setFailed(0);
		
		Assert.assertEquals((Integer)0, rj.getFailed());
	}
	
	@Test
	public void passtest(){
		RobotJson rj = new RobotJson();
		
		rj.setPassed(0);
		
		Assert.assertEquals((Integer)0, rj.getPassed());
	}
	
	@Test
	public void totaltest(){
		RobotJson rj = new RobotJson();
		
		rj.setTotalTest(0);
		
		Assert.assertEquals((Integer)0, rj.getTotalTest());
	}
}
