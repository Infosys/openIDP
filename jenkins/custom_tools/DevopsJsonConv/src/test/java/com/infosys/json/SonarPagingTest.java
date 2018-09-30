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

/*This class provides Test cases for sonar paging*/

public class SonarPagingTest {

	@Test
	public void indextest(){
		SonarPaging sp = new SonarPaging();
		
		sp.setPageIndex("0");
		
		Assert.assertEquals("0", sp.getPageIndex());
	}
	
	@Test
	public void pagesizetest(){
		SonarPaging sp = new SonarPaging();
		
		sp.setPageSize("100");
		
		Assert.assertEquals("100", sp.getPageSize());
	}
	
	@Test
	public void totaltest(){
		SonarPaging sp = new SonarPaging();
		
		sp.setTotal("100");
		
		Assert.assertEquals("100", sp.getTotal());
	}
}
