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

/*This class provides Test cases for sonar json*/

public class SonarJsonTest {

	@Test
	public void test(){
		SonarJson sj = new SonarJson();
		
		sj.setP("none");
		
		Assert.assertEquals("none", sj.getP());		
		
	}
	
	@Test
	public void pstest(){
		SonarJson sj = new SonarJson();
		
		sj.setPs("none");
		
		Assert.assertEquals("none", sj.getPs());		
		
	}
	
	@Test
	public void totaltest(){
		SonarJson sj = new SonarJson();
		
		sj.setTotal("none");
		
		Assert.assertEquals("none", sj.getTotal());		
		
	}
}
