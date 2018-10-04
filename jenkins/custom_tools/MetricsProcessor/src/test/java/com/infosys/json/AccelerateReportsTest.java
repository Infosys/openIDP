
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class AccelerateReportsTest {

	@Test
	public void UrlTest(){
		AccelerateReports ar = new AccelerateReports();
		ar.setURL("www.infosys.com");
		
		Assert.assertEquals("www.infosys.com", ar.getURL());
	}
}