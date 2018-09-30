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

/*This class is for testing junit*/
public class JunitTest {

	@Test
	public void errortest(){
		JUnit ju = new JUnit();
		ju.setError(0);
		
		Assert.assertEquals((Integer)0, ju.getError());
	}
	
	@Test
	public void failtest(){
		JUnit ju = new JUnit();
		ju.setFail(0);
		
		Assert.assertEquals((Integer)0, ju.getFail());
	}
	
	@Test
	public void passtest(){
		JUnit ju = new JUnit();
		ju.setPass(0);
		
		Assert.assertEquals((Integer)0, ju.getPass());
	}
	
	@Test
	public void skiptest(){
		JUnit ju = new JUnit();
		ju.setSkip(0);
		
		Assert.assertEquals((Integer)0, ju.getSkip());
	}
	
	@Test
	public void test(){
		JUnit ju = new JUnit();
		ju.setTotalTest(0);
		
		Assert.assertEquals((Integer)0, ju.getTotalTest());
	}
}
