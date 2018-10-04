
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class CoberturaReportsTest {
	@Test
	public void urltest(){
		CoberturaReports cr = new CoberturaReports();
		cr.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", cr.getURL());
	}
}
