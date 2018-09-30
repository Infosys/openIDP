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

/*This class is for testing failed,passed and total  test*/
public class IFastTest {

	@Test
	public void failtest(){
		IFast ifast = new IFast();
		
		ifast.setFail(0);
		
		Assert.assertEquals((Integer)0, ifast.getFail());
	}
	
	@Test
	public void passtest(){
		IFast ifast = new IFast();
		
		ifast.setPass(100);
		
		Assert.assertEquals((Integer)100, ifast.getPass());
	}
	
	@Test
	public void totaltest(){
		IFast ifast = new IFast();
		
		ifast.setTotalTest(100);
		
		Assert.assertEquals((Integer)100, ifast.getTotalTest());
	}
}
