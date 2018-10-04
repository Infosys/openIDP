
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class SeleniumReportsTest {

	@Test
	public void test(){
		SeleniumReports sr = new SeleniumReports();
		
		sr.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", sr.getURL());
	}
}
