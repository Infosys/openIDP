
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class JUnitReportsTest {
	
	@Test
	public void urltest(){
		JUnitReports jur = new JUnitReports();
		
		jur.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", jur.getURL());
	}
}
