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

/*This class is for testing ecatt*/
public class EcattTest {

	@Test
	public void Failtest(){
		Ecatt et = new Ecatt();
		
		et.setFail(0);
		
		Assert.assertEquals((Integer)0, et.getFail());
		
	}
	
	@Test
	public void Passtest(){
		Ecatt et = new Ecatt();
		
		et.setPass(100);
		
		Assert.assertEquals((Integer)100, et.getPass());
	}
	
	@Test
	public void Totaltest(){
		Ecatt et = new Ecatt();
		
		et.setTotalTest(100);
		
		Assert.assertEquals((Integer)100, et.getTotalTest());
	}
}
