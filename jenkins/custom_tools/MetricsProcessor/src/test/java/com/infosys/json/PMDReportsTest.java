
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class PMDReportsTest {

	@Test
	public void urltest(){
		PMDReports pmdr = new PMDReports();
		
		pmdr.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", pmdr.getURL());
	}
}
