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

/*This class provides Test cases for Coverage Details*/
public class CoverageDetailsTest {

	@Test
	public void Categorytest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setCategory("infosys");
		
		Assert.assertEquals("infosys", cd.getCategory());
	}
	@Test
	public void Classnametest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setClassName("infosys");
		
		Assert.assertEquals("infosys", cd.getClassName());
	}
	
	@Test
	public void LineCoveragetest(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setLineCoverage("100");
		
		Assert.assertEquals("100", cd.getLineCoverage());
	}
	
	@Test
	public void test(){
		CoverageDetails cd = new CoverageDetails();
		
		cd.setPckage("infosys");
		
		Assert.assertEquals("infosys", cd.getPckage());
	}
}
