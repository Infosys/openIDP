
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class CheckmarxreportsTest {
	@Test
	
	public void urltest(){
		CheckmarxReports cr = new CheckmarxReports();
		
		cr.setURL("infosys.com");
		Assert.assertEquals("infosys.com", cr.getURL());
	}
}
