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

/*This class provides Test cases for Json*/

public class TestNgJsonTest {

	
	@Test
	public void failtest(){
		
		
		TestNgJson tng = new TestNgJson();
		
		tng.setFail(0);
		
		Assert.assertEquals((Integer) 0, tng.getFail());
	}
	
	@Test
	public void passtest(){
		
		
		TestNgJson tng = new TestNgJson();
		
		tng.setPass(0);
		
		Assert.assertEquals((Integer) 0, tng.getPass());
	}
	
	@Test
	public void totaltest(){
		
		
		TestNgJson tng = new TestNgJson();
		
		tng.setTotalTest(0);;
		
		Assert.assertEquals((Integer) 0, tng.getTotalTest());
	}
}
