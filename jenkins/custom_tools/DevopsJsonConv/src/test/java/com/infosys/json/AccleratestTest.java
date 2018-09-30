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

/*This class provides Test cases for Accelerate Reports*/
public class AccleratestTest {

	@Test
	public void TotalTest(){
		Accleratest at = new Accleratest();
		at.setTotalTest(10);
		
		Assert.assertEquals((Integer)10, at.getTotalTest());
	}
	
	@Test
	public void passed(){
		Accleratest at = new Accleratest();
		at.setPassed(10);
		
		Assert.assertEquals((Integer)10, at.getPassed());
	}
	
	@Test
	public void failed(){
		Accleratest at = new Accleratest();
		at.setFailed(10);
		
		Assert.assertEquals((Integer)10, at.getFailed());
	}
}
