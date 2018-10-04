
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


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
