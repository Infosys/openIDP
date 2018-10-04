
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class ProtractorReportsTest {
	
	@Test
	public void testurl(){
		ProtractorReports pr = new ProtractorReports();
		
		pr.setURL("infosys.com");
		
		
		Assert.assertEquals("infosys.com", pr.getURL());
	}
}
