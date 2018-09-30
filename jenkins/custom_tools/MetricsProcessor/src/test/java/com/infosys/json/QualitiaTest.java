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

/*This class is for testing qualiti*/
public class QualitiaTest {
	@Test
	public void failtest(){
		Qualitia q = new Qualitia();
		
		q.setFail(0);
		
		Assert.assertEquals((Integer)0, q.getFail());
	}
	
	@Test
	public void passtest(){
		Qualitia q = new Qualitia();
		
		q.setPass(0);
		
		Assert.assertEquals((Integer)0, q.getPass());
	}
	
	@Test
	public void totaltest(){
		Qualitia q = new Qualitia();
		
		q.setTotalTest(0);
		
		Assert.assertEquals((Integer)0, q.getTotalTest());
	}
}
