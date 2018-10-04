
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class FindBugsReportsTest {

	@Test
	
	public void urltest(){
		FindBugsReports bgr = new FindBugsReports();
		
		bgr.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", bgr.getURL());
	}
}
