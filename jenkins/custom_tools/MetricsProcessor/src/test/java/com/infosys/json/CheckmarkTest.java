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


/*This class is for testing checkmarx*/
public class CheckmarkTest {
	@Test
	public void hightest(){
		Checkmarx cm = new Checkmarx();
		
		cm.setHigh(10);
		
		Assert.assertEquals((Integer)10, cm.getHigh());
	}
	
	@Test
	public void mediumtest(){
		Checkmarx cm = new Checkmarx();
		
		cm.setMedium(10);
		
		Assert.assertEquals((Integer)10, cm.getMedium());
	}
	
	@Test
	public void lowtest(){
		Checkmarx cm = new Checkmarx();
		
		cm.setLow(10);
		
		Assert.assertEquals((Integer)10, cm.getLow());
	}
}
