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

/*This class provides Test cases for passed,failed and total test*/

public class SeleniumTest {

	@Test
	public void failtest(){
		Selenium s = new Selenium();
		
		s.setFail(0);
		
		Assert.assertEquals((Integer)0, s.getFail());
	}
	
	@Test
	public void passtest(){
		Selenium s = new Selenium();
		
		s.setPass(0);
		
		Assert.assertEquals((Integer)0, s.getPass());
	}
	
	@Test
	public void totaltest(){
		Selenium s = new Selenium();
		
		s.setTotalTest(0);;
		
		Assert.assertEquals((Integer)0, s.getTotalTest());
	}
}
